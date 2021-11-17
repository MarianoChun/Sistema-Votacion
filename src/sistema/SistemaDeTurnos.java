package sistema;

import java.util.ArrayList;
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
		votantesRegistrados.put(dni, votanteNuevo);

	}

	public int agregarMesa(String tipoMesa, int dni) {
		if (!verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El presidente de mesa no esta registrado");
		}

		Votante posiblePresiMesa = votantesRegistrados.get(dni);
		if (!(posiblePresiMesa.consultarTurno() == null)) {
			throw new RuntimeException("El presidente de mesa ya esta asignado a una mesa");
		}
		
		return MesaGenerica.agregarMesaSegunTipo(tipoMesa, posiblePresiMesa, mesas);
	}

	public Tupla<Integer, Integer> asignarTurnos(int dni) {
		if (!verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante no esta registrado");
		}

		Votante v = votantesRegistrados.get(dni);
		// Si tiene turno, lo devuelvo
		if (!(v.consultarTurno() == null)) {
			Turno t = v.consultarTurno();
			return new Tupla<Integer, Integer>(t.mostrarNumMesaTurno(), t.mostrarFranjaTurno());
		}
		// Si no tiene turno, le asigno uno disponible
		Turno turno = null;
		for (MesaGenerica mesa : mesas) {
			turno = MesaGenerica.asignaVotanteAMesa(v, mesa);
		}
		
		if(turno == null) {
			return null;
		}
		
		return new Tupla<Integer, Integer>(turno.mostrarNumMesaTurno(),
				turno.mostrarFranjaTurno());
	}

	public int asignarTurnos() {
		int cantidadTurnosAsignados = 0;
		boolean turnoAsignado;
		for (Votante v : votantesRegistrados.values()) {
			turnoAsignado = false;
			if (v.consultarTurno() == null) { // Si no tiene turno, le asigno uno
				for (MesaGenerica mesa : mesas) {
					if (v.consultarEsTrabajador() && !turnoAsignado) {
						if (mesa.consultarTipoMesa().equals("Trabajador") && mesa.consultarTurnosTotalesFranjas() > 0) {
							// Obtengo la primera franja con disponibilidad
							FranjaHoraria franjaDisponible = mesa.franjaConDisponibilidad();
							// Creo el turno y se lo asigno al votante
							v.crearTurno(mesa, franjaDisponible);
							// Lo asigno a la franja
							mesa.asignarVotanteAFranjaHoraria(franjaDisponible.consultarFranja(), v);
							cantidadTurnosAsignados++;
							turnoAsignado = true;

						}
					} else if (!v.consultarEsTrabajador() && (v.consultarEsMayor() || v.consultarTieneEnfPreex())
							&& !turnoAsignado) {

						if ((mesa.consultarTipoMesa().equals("Mayor65") || mesa.consultarTipoMesa().equals("Enf_Preex"))
								&& mesa.consultarTurnosTotalesFranjas() > 0) {
							// Obtengo la primera franja con disponibilidad
							FranjaHoraria franjaDisponible = mesa.franjaConDisponibilidad();
							// Creo el turno y se lo asigno al votante
							v.crearTurno(mesa, franjaDisponible);
							// Lo asigno a la franja
							mesa.asignarVotanteAFranjaHoraria(franjaDisponible.consultarFranja(), v);
							cantidadTurnosAsignados++;
							turnoAsignado = true;
						}
					} else if (!v.consultarEsTrabajador() && !v.consultarEsMayor() && !v.consultarTieneEnfPreex()) {
						if (mesa.consultarTipoMesa().equals("General") && mesa.consultarTurnosTotalesFranjas() > 0
								&& !turnoAsignado) {
							// Obtengo la primera franja con disponibilidad
							FranjaHoraria franjaDisponible = mesa.franjaConDisponibilidad();
							// Creo el turno y se lo asigno al votante
							v.crearTurno(mesa, franjaDisponible);
							// Lo asigno a la franja
							mesa.asignarVotanteAFranjaHoraria(franjaDisponible.consultarFranja(), v);
							cantidadTurnosAsignados++;
							turnoAsignado = true;
						}
					}
				}
			}
		}
		return cantidadTurnosAsignados;
	}

	public boolean verificarVotanteEnSistema(int dni) {

		return votantesRegistrados.containsKey(dni);
	}

	public int consultarCantTurnosDisponibles(int numMesa) {
		for (MesaGenerica mesa : mesas) {
			if (mesa.mostrarNumeroMesa() == numMesa) {
				return mesa.consultarTurnosTotalesFranjas();
			}
		}
		// Si no encontro la mesa, devuelvo -1
		return -1;
	}

	public Tupla<Integer, Integer> consultaTurno(int dni) {
		if (!verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante no esta registrado");
		}

		Votante vot = votantesRegistrados.get(dni);

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

		return votantesRegistrados.get(dni).consultarSiFueAVotar();
	}

	public boolean votar(int dni) {
		if (sePresentoVotar(dni)) {
			return false;
		}
		votantesRegistrados.get(dni).votar();
		return true;
	}

	public int votantesConTurno(String tipoMesa) {
		if (!tipoMesa.equals("Mayor65") || !tipoMesa.equals("General") || !tipoMesa.equals("Enf_Preex")
				|| !tipoMesa.equals("Trabajador")) {
			throw new RuntimeException("Tipo de mesa invalida");
		}

		return MesaGenerica.cantVotantesConTurno(tipoMesa, mesas);
	}

	public boolean verificarMesaEnSistema(int numMesa) {
		for (MesaGenerica mesa : mesas) {
			if (mesa.mostrarNumeroMesa() == numMesa) {
				return true;
			}
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
		for (Votante v : votantesRegistrados.values()) {
			if (v.consultarTurno() != null) {
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

		
	}

}
