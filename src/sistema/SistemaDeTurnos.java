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
		if (estaVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante ya esta registrado");
		}
		
		Votante votanteNuevo = new Votante(dni, nombre, edad, tieneEnfPreex, esTrabajador);
		agregarVotanteARegistro(dni,votanteNuevo);

	}

	private void agregarVotanteARegistro(int dni, Votante votante) {
		votantesRegistrados.put(dni, votante);
	}
	
	public int agregarMesa(String tipoMesa, int dni) {
		if (!estaVotanteEnSistema(dni)) {
			throw new RuntimeException("El presidente de mesa no esta registrado");
		}

		Votante posiblePresiMesa = obtenerVotante(dni);
		if (posiblePresiMesa.tieneTurno()) {
			throw new RuntimeException("El presidente de mesa ya esta asignado a una mesa");
		}
		
		MesaGenerica mesaNueva = crearMesa(tipoMesa, posiblePresiMesa);
		mesas.add(mesaNueva);
		return mesaNueva.mostrarNumeroMesa();
	}

    // Si la mesa es valida, la crea asignandole su presidente de mesa y la devuelve
    private MesaGenerica crearMesa(String tipoMesa, Votante presidenteMesa) {
    	if(presidenteMesa == null) {
    		throw new RuntimeException("El presidente de mesa no es valido");
    	}
    	MesaGenerica mesaNueva;  	
    	if (tipoMesa.equals("Trabajador")) {
			mesaNueva = new MesaTrabajador(presidenteMesa);
		} else if (tipoMesa.equals("Mayor65")) {
			mesaNueva = new MesaMayor65(presidenteMesa);
		} else if (tipoMesa.equals("Enf_Preex")) {
			mesaNueva = new MesaEnfPreex(presidenteMesa);
		} else if (tipoMesa.equals("General")) {
			mesaNueva = new MesaGeneral(presidenteMesa);
		} else {
			throw new RuntimeException("Tipo de mesa invalida");
		}
    	return mesaNueva;
    }
    
	public Tupla<Integer, Integer> asignarTurno(int dni) {
		if (!estaVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante no esta registrado");
		}
		Votante v = obtenerVotante(dni);
		// Si tiene turno, lo devuelvo
		if (v.tieneTurno()) {
			Turno t = v.consultarTurno();
			return new Tupla<Integer, Integer>(t.mostrarNumMesaTurno(), t.mostrarFranjaTurno());
		}
		// Si no tiene turno, le asigno uno disponible
		Turno turno = null;
		for (MesaGenerica mesa : mesas) {
			turno = asignarVotanteAMesa(v, mesa);
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

	public int asignarTurno() {
		int cantidadTurnosAsignados = 0;
		Turno turno;
		for (Votante v : votantesRegistrados.values()) {
			if (!v.tieneTurno()) { // Si no tiene turno, le asigno uno
				for (MesaGenerica mesa : mesas) {
					turno = asignarVotanteAMesa(v, mesa);
					if(turno != null) {
						cantidadTurnosAsignados++;
						break;
					}
				}
			}
		}
		return cantidadTurnosAsignados;
	}
	private Turno asignarVotanteAMesa(Votante votante, MesaGenerica mesa) {
		if (esMesaValida(votante, mesa)) {
			// Obtengo la primera franja con disponibilidad
			FranjaHoraria franjaDisponible = mesa.franjaConDisponibilidad();
			// Creo el turno y se lo asigno al votante
			votante.crearTurno(mesa, franjaDisponible);
			// Asigno al votante a la franja
			mesa.asignarVotanteAFranjaHoraria(franjaDisponible.consultarFranja(), votante);
			// Devuelvo el turno
			return votante.consultarTurno();
		}

		return null;

	}
	
    // Determina si la mesa es valida para el votante
    private boolean esMesaValida(Votante votante, MesaGenerica mesa) {
    	if(mesa.consultarTurnosTotalesFranjas() > 0) {
    		String tipoMesa = mesa.consultarTipoMesa();
    		// Si pude asignar el turno, lo devuelvo, sino devuelvo null
        	if (votante.consultarEsTrabajador()) {
        		if(tipoMesa.equals("Trabajador")) {
        			return true;
        		}
    		} else if(votante.consultarEsMayor() && !votante.consultarTieneEnfPreex()) {
    			if(tipoMesa.equals("Mayor65")) {
    				return true;
    			}
    		} else if(!votante.consultarEsMayor() && votante.consultarTieneEnfPreex()) {
    			if(tipoMesa.equals("Enf_Preex")) {
    				return true;
    			}
    		} else if (votante.consultarEsMayor() && votante.consultarTieneEnfPreex()) { 
    			if ((tipoMesa.equals("Mayor65") || tipoMesa.equals("Enf_Preex"))) {
    				return true;
    			}
    		} else {
    			if (tipoMesa.equals("General")) {
    				return true;
    			}
    		}        	
    	}
    	return false;
    }
	public boolean esTipoMesaValida(String tipoMesa) {
		return tipoMesa.equals("Mayor65") || tipoMesa.equals("General") 
				|| tipoMesa.equals("Enf_Preex") || tipoMesa.equals("Trabajador");
			
	}
	public boolean estaVotanteEnSistema(int dni) {
		return votantesRegistrados.containsKey(dni);
	}

	public Votante obtenerVotante(int dni) {
		if(!estaVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante no se encuentra registrado");
		}	
		return votantesRegistrados.get(dni);
	}

	public Tupla<Integer, Integer> consultaTurno(int dni) {
		if (!estaVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante no esta registrado");
		}

		Votante vot = obtenerVotante(dni);

		if (vot.tieneTurno()) {
			Turno t = vot.consultarTurno();
			return new Tupla<Integer, Integer>(t.mostrarNumMesaTurno(), t.mostrarFranjaTurno());
		}

		return null;
	}

	public boolean sePresentoVotar(int dni) {
		if (!estaVotanteEnSistema(dni)) {
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

	public boolean estaMesaEnSistema(int numMesa) {
		return buscarMesa(numMesa) != null;
	}

	private MesaGenerica buscarMesa(int numMesa) {
		for (MesaGenerica mesa : mesas) {
			if (mesa.mostrarNumeroMesa() == numMesa) {
				return mesa;
			}
		}
		return null;
	}

	public Map<Integer, List<Integer>> asignadosAMesa(int numMesa) {
		if (!estaMesaEnSistema(numMesa)) {
			throw new RuntimeException("El numero de mesa no es valido para ninguna mesa del sistema");
		}

		MesaGenerica mesa = buscarMesa(numMesa);
		return mesa.votantesTodasLasFranjas();
	}

	public List<Tupla<String, Integer>> sinTurnoSegunTipoMesa() {
    	List<Tupla<String, Integer>> listaTiposMesa = new LinkedList<Tupla<String, Integer>>();

		int contadorTrabajador = 0;
		int contadorGeneral = 0;
		int contadorEnf_Preex = 0;
		int contadorMayor65 = 0;

		// Recorro por valor el registro de votantes registrados
		for (Votante v : votantesRegistrados.values()) {
			if (v.consultarTurno() == null) {
				// Verifico a que tipo de mesa corresponde que sea asignado el votante
				if (v.consultarEsTrabajador()) {
					contadorTrabajador++;
				} else if (v.consultarEsMayor() && !v.consultarTieneEnfPreex()) {
					contadorMayor65++;
				} else if (!v.consultarEsMayor() && v.consultarTieneEnfPreex()) {
					contadorEnf_Preex++;
				} else if (v.consultarEsMayor() && v.consultarTieneEnfPreex()) {
					contadorEnf_Preex++;
					contadorMayor65++;
				} else {
					contadorGeneral++;
				}
			}
		}
		// Sumamos las tuplas de cada tipo de mesa a la lista.
		listaTiposMesa.add(new Tupla<String, Integer>("Trabajador", contadorTrabajador));
		listaTiposMesa.add(new Tupla<String, Integer>("General", contadorGeneral));
		listaTiposMesa.add(new Tupla<String, Integer>("Enf_Preex", contadorEnf_Preex));
		listaTiposMesa.add(new Tupla<String, Integer>("Mayor65", contadorMayor65));

		return listaTiposMesa;
	}

	public List<Tupla<Integer, Tupla<Integer, Integer>>> mostrarTurnosVotantes() {
		// Contiene el DNI del votante relacionado y su Turno (numMesa y franja horaria)
		List<Tupla<Integer, Tupla<Integer, Integer>>> listaVotantesYTurnos = new LinkedList<Tupla<Integer, Tupla<Integer, Integer>>>();
		for (Votante v : votantesRegistrados.values()) { // Recorro los votantes
			if (v.tieneTurno()) { // Si tiene turno, lo agrego a la lista de tuplas. (DNI,<numMesa,Franja>)
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
		cadena.append("Cantidad de votantes en espera para un turno (Segun tipo de mesa):\n");
		cadena.append(this.sinTurnoSegunTipoMesa());
		cadena.append("\n-------------------------------------\n");
		cadena.append("Votantes con/sin turno asignado:\n");
		cadena.append(votantesRegistrados.values().toString());
		cadena.append("\n-------------------------------------\n");
		cadena.append("Mesas habilitadas en el sistema\n");
		cadena.append(mesas.toString());
		return cadena.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method st
		SistemaDeTurnos sistema = new SistemaDeTurnos("Sistema nuevo");
		Votante votante1 = new Votante(43858084,"Mariano",20,false,false);
		Votante votante2 = new Votante(43000000,"Pepe",25,true,false);
		Votante votante3 = new Votante(50000100,"Marcos",30,true,true);
		//MesaGenerica mesa1 = new MesaTrabajador(votante1);
		MesaGenerica mesa2 = new MesaTrabajador(votante2);
		MesaGenerica mesa3 = new MesaTrabajador(votante3);
		
		//System.out.println(mesa1.mostrarNumeroMesa());
		System.out.println(mesa2.mostrarNumeroMesa());
		System.out.println(mesa3.mostrarNumeroMesa());
		sistema.registrarVotante(10000, "Hola", 70, false, false);
		sistema.registrarVotante(43858084, "Mariano", 20, false, false);
		sistema.agregarMesa("Mayor65", 43858084);
		sistema.asignarTurno();
		sistema.registrarVotante(43000000, "Pepe", 20, false, false);
		System.out.println(sistema.toString());
		
		
	}

}
