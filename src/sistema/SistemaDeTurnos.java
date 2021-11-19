package sistema;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SistemaDeTurnos {

	private String nombreSistema;
	private HashMap<Integer, Votante> votantesRegistrados;
	private LinkedList<MesaGenerica> mesas;

	public SistemaDeTurnos(String nombreSistema) {
		super();

		if (nombreSistema.equals(null)) {
			throw new RuntimeException("El nombre de sistema no debe ser vacio");
		}
		this.nombreSistema = nombreSistema;
		this.votantesRegistrados = new HashMap<>();
		this.mesas = new LinkedList<>();
	}

	public void registrarVotante(int dni, String nombre, int edad, boolean tieneEnfPreex, boolean esTrabajador) {
		if (verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante ya esta registrado");
		}
		
		Votante votanteNuevo = new Votante(dni, nombre, edad, tieneEnfPreex, esTrabajador);
		agregarVotanteARegistro(dni,votanteNuevo);

	}

	private void agregarVotanteARegistro(int dni, Votante votante) {
		votantesRegistrados.put(dni, votante);
	}
	
	public int agregarMesa(String tipoMesa, int dni) {
		if (!verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El presidente de mesa no esta registrado");
		}

		Votante posiblePresiMesa = obtenerVotante(dni);
		if (!(posiblePresiMesa.consultarTurno() == null)) {
			throw new RuntimeException("El presidente de mesa ya esta asignado a una mesa");
		}
		
		return MesaGenerica.agregarMesaSegunTipo(tipoMesa, posiblePresiMesa, mesas);
	}

	public Tupla<Integer, Integer> asignarTurnos(int dni) {
		if (!verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante no esta registrado");
		}
		Votante v = obtenerVotante(dni);
		// Si tiene turno, lo devuelvo
		if (!(v.consultarTurno() == null)) {
			Turno t = v.consultarTurno();
			return new Tupla<Integer, Integer>(t.mostrarNumMesaTurno(), t.mostrarFranjaTurno());
		}
		// Si no tiene turno, le asigno uno disponible
		Turno turno = null;
		
		for (MesaGenerica mesa : mesas) {
			turno = MesaGenerica.asignaVotanteAMesa(v, mesa);
			if(turno != null) {
				break;
			}
		}
			
		if(turno == null) {
			return null;
		}
		
		return new Tupla<Integer, Integer>(turno.mostrarNumMesaTurno(),
				turno.mostrarFranjaTurno());
	}

	public int asignarTurnos() {
		int cantidadTurnosAsignados = 0;
		Turno turno;
		for (Votante v : votantesRegistrados.values()) {
			if (v.consultarTurno() == null) { // Si no tiene turno, le asigno uno
				for (MesaGenerica mesa : mesas) {
					turno = MesaGenerica.asignaVotanteAMesa(v, mesa);
					if(turno != null) {
						cantidadTurnosAsignados++;
						break;
					}
				}
			}
		}
		return cantidadTurnosAsignados;
	}

	public boolean verificarVotanteEnSistema(int dni) {
		return votantesRegistrados.containsKey(dni);
	}

	public Votante obtenerVotante(int dni) {
		if(!verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante no se encuentra registrado");
		}	
		return votantesRegistrados.get(dni);
	}
	
	public int consultarCantTurnosDisponibles(int numMesa) {
		if(!verificarMesaEnSistema(numMesa)) {
			throw new RuntimeException("La mesa no existe en el registro de mesas");
		}
		
		return buscarMesa(numMesa).consultarTurnosTotalesFranjas();

	}

	public Tupla<Integer, Integer> consultaTurno(int dni) {
		if (!verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante no esta registrado");
		}

		Votante vot = obtenerVotante(dni);

		if (!vot.consultarTurno().equals(null)) {
			Turno t = vot.consultarTurno();
			return new Tupla<Integer, Integer>(t.mostrarNumMesaTurno(), t.mostrarFranjaTurno());
		}

		return null;
	}

	public boolean sePresentoVotar(int dni) {
		if (!verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante no esta registrado");
		}

		return obtenerVotante(dni).consultarSiFueAVotar();
	}

	public boolean votar(int dni) {
		if (sePresentoVotar(dni)) {
			return false;
		}
		obtenerVotante(dni).votar();
		return true;
	}

	public int votantesConTurno(String tipoMesa) {
		return MesaGenerica.cantVotantesConTurno(tipoMesa, mesas);
	}

	public boolean verificarMesaEnSistema(int numMesa) {
		if(buscarMesa(numMesa) != null) {
			return true;
		}
		
		return false;
	}

	public MesaGenerica buscarMesa(int numMesa) {
		for (MesaGenerica mesa : mesas) {
			if (mesa.mostrarNumeroMesa() == numMesa) {
				return mesa;
			}
		}
		return null;
	}

	public Map<Integer, List<Integer>> asignadosAMesa(int numMesa) {
		if (!verificarMesaEnSistema(numMesa)) {
			throw new RuntimeException("El numero de mesa no es valido para ninguna mesa del sistema");
		}

		MesaGenerica mesa = buscarMesa(numMesa);
		return mesa.votantesTodasLasFranjas();
	}

	public List<Tupla<String, Integer>> sinTurnoSegunTipoMesa() {
		return MesaGenerica.cantVotantesSinTurnoPorTipoMesa(votantesRegistrados);
	}

	public List<Tupla<Integer, Tupla<Integer, Integer>>> mostrarTurnosVotantes() {
		// Contiene el DNI del votante relacionado y su Turno (numMesa y franja horaria)
		List<Tupla<Integer, Tupla<Integer, Integer>>> listaVotantesYTurnos = new LinkedList<Tupla<Integer, Tupla<Integer, Integer>>>();
		for (Votante v : votantesRegistrados.values()) { // Recorro los votantes
			if (v.consultarTurno() != null) { // Si tiene turno, lo agrego a la lista de tuplas. (DNI,<numMesa,Franja>)
				Turno t = v.consultarTurno();
				listaVotantesYTurnos.add(new Tupla<Integer, Tupla<Integer, Integer>>(v.consultarDni(),
						new Tupla<Integer, Integer>(t.mostrarNumMesaTurno(), t.mostrarFranjaTurno())));
			}
		}
		return listaVotantesYTurnos;

	}

	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		cadena.append("-------------------------------------\n");
		cadena.append(nombreSistema);
		cadena.append("\n-------------------------------------\n");
		cadena.append("Votantes en espera para un turno (Segun tipo de mesa):\n");
		cadena.append(this.sinTurnoSegunTipoMesa());
		cadena.append("\n-------------------------------------\n");
		cadena.append("Votantes con turno asignado:\n");
		for (Votante v : votantesRegistrados.values()) {
			if (v.consultarTurno() != null) {
				cadena.append(v.toString());
			}

		}
		cadena.append("\n-------------------------------------\n");
		cadena.append("Mesas habilitadas en el sistema\n");
		for (MesaGenerica m : mesas) {
			cadena.append(m.toString());
		}

		return cadena.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method st
		Votante votante1 = new Votante(43858084,"Mariano",20,false,false);
		Votante votante2 = new Votante(43000000,"Pepe",25,true,false);
		Votante votante3 = new Votante(50000100,"Marcos",30,true,true);
		MesaGenerica mesa1 = new MesaTrabajador(votante1);
		MesaGenerica mesa2 = new MesaTrabajador(votante2);
		MesaGenerica mesa3 = new MesaTrabajador(votante3);
		
		System.out.println(mesa1.mostrarNumeroMesa());
		System.out.println(mesa2.mostrarNumeroMesa());
		System.out.println(mesa3.mostrarNumeroMesa());
	}

}
