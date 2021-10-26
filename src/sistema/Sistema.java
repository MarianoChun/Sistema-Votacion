package sistema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Sistema {
	
	private String nombreSistema;
	private HashMap<Integer,Votante> votantesRegistrados;
	private LinkedList<MesaGenerica> mesas;
	
	
	public Sistema(String nombreSistema) {
		super();
		
		if(nombreSistema.equals(null)) {
			throw new RuntimeException("El nombre de sistema no debe ser vacio");
		}
		
		this.votantesRegistrados = new HashMap<>();
		this.mesas = new LinkedList<>();
	}

	public void registrarVotante(int dni, String nombre, int edad, boolean tieneEnfPreex, boolean esTrabajador){
		if(verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante ya esta registrado");
		}
				
		Votante votanteNuevo = new Votante(dni,nombre,edad,tieneEnfPreex,esTrabajador);
		votantesRegistrados.put(dni, votanteNuevo);
		
	}
	
	public int agregarMesa(String tipoMesa, int dni){
		if(!verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El presidente de mesa no esta registrado");
		}
		
		Votante posiblePresiMesa = votantesRegistrados.get(dni);
		if(!posiblePresiMesa.consultarTurno().equals(null)) {
			throw new RuntimeException("El presidente de mesa ya esta asignado a una mesa");
		}
		
		if(tipoMesa.equals("Trabajador")) {
			MesaGenerica mesaNueva = new MesaTrabajador(posiblePresiMesa);
			mesas.add(mesaNueva);
			return mesaNueva.mostrarNumeroMesa();
			
		} else if(tipoMesa.equals("Mayor65")) {
			MesaGenerica mesaNueva = new MesaMayor65(posiblePresiMesa);
			mesas.add(mesaNueva);
			return mesaNueva.mostrarNumeroMesa();
			
		} else if(tipoMesa.equals("Enf_Preex")) {
			MesaGenerica mesaNueva = new MesaEnfPreex(posiblePresiMesa);
			mesas.add(mesaNueva);
			return mesaNueva.mostrarNumeroMesa();
			
		} else if(tipoMesa.equals("General")) {
			MesaGenerica mesaNueva = new MesaGeneral(posiblePresiMesa);
			mesas.add(mesaNueva);
			return mesaNueva.mostrarNumeroMesa();
			
		} else {
			throw new RuntimeException("Tipo de mesa invalida");
		}
	}
	
	public Tupla<Integer,Integer> asignarTurno(int dni){
		return new Tupla<Integer,Integer>(1,1);
	}
	
	public int asignarTurno() {
		return 1;
	}
	
	public boolean verificarVotanteEnSistema(int dni) {
		
		return votantesRegistrados.containsKey(dni);
	}
	
	public int consultarCantTurnosDisponibles(int numMesa){
		for(MesaGenerica mesa : mesas) {
			if(mesa.mostrarNumeroMesa() == numMesa) {
				return mesa.consultarTurnosTotalesFranjas();
			}
		}
		// Si no encontro la mesa, devuelvo -1
		return -1;
	}
	
	public Tupla<Integer,Integer> consultarTurno(int dni){
		if(!verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante no esta registrado");
		}
		
		Votante vot = votantesRegistrados.get(dni);
		
		if(!vot.consultarTurno().equals(null)) {
			Turno t = vot.consultarTurno();
			return new Tupla<Integer,Integer>(t.mostrarNumMesaTurno(),t.mostrarFranjaTurno());
		}
		
		return null;
	}
	
	public boolean sePresentoVotar(int dni) {
		if(!verificarVotanteEnSistema(dni)) {
			throw new RuntimeException("El votante no esta registrado");
		}
		
		return votantesRegistrados.get(dni).consultarSiFueAVotar();
	}
	
	public boolean votar(int dni) {
		
		if(sePresentoVotar(dni)) {
			return false;
		}
		votantesRegistrados.get(dni).votar();
		return true;
		
	}
	
	public int votantesConTurno(String tipoMesa) {
		if(!tipoMesa.equals("Mayor65") || !tipoMesa.equals("General") ||
			!tipoMesa.equals("Enf_Preex") || !tipoMesa.equals("Trabajador")) {
			throw new RuntimeException("Tipo de mesa invalida");
		}
		
		int votantesConTurno = 0;
		for(MesaGenerica mesa : mesas) {
			if(mesa.consultarTipoMesa().equals(tipoMesa)) {
				votantesConTurno += mesa.votantesTodasLasFranjas().size();
			}
		}
		return votantesConTurno;
	}
	
	public boolean verificarMesaEnSistema(int numMesa) {
		for(MesaGenerica mesa : mesas) {
			if (mesa.mostrarNumeroMesa() == numMesa) {
				return true;
			}
		}
		return false;
	}
	
	public MesaGenerica buscarMesa(int numMesa) {
		for(MesaGenerica mesa : mesas) {
			if (mesa.mostrarNumeroMesa() == numMesa) {
				return mesa;
			}
		}
		return null;
	}
	public HashMap<Integer,ArrayList<Integer>> asignadosAMesa(int numMesa){
		if(!verificarMesaEnSistema(numMesa)) {
			throw new RuntimeException("El numero de mesa no es valido para ninguna mesa del sistema");
		}
		
		MesaGenerica mesa = buscarMesa(numMesa);	
		return mesa.votantesTodasLasFranjas();
	}
	
	public LinkedList<Tupla<String,Integer>> sinTurnoSegunTipomesa(){
		LinkedList<Tupla<String,Integer>> listaTiposMesa = new LinkedList<Tupla<String,Integer>>();

		int contadorTrabajador = 0;
		int contadorGeneral = 0;
		int contadorEnf_Preex = 0;
		int contadorMayor65 = 0;
		
		// Recorro por valor el registro de votantes registrados
		for(Votante v : votantesRegistrados.values()) {
			if(v.consultarTurno().equals(null)) {
				// Verifico a que tipo de mesa corresponde que sea asignado el votante
				if(v.consultarEsTrabajador()) {
					contadorTrabajador++;
				} else if(v.consultarEsMayor() && !v.consultarTieneEnfPreex()) {
					contadorMayor65++;
				} else if(!v.consultarEsMayor() && v.consultarTieneEnfPreex()) {
					contadorEnf_Preex++;
				} else if(v.consultarEsMayor() && v.consultarTieneEnfPreex()) {
					contadorEnf_Preex++;
					contadorMayor65++;
				} else {
					contadorGeneral++;
				}
			}
		}
		
		listaTiposMesa.add(new Tupla<String,Integer>("Trabajador",contadorTrabajador));
		listaTiposMesa.add(new Tupla<String,Integer>("General",contadorGeneral));
		listaTiposMesa.add(new Tupla<String,Integer>("Enf_Preex",contadorEnf_Preex));
		listaTiposMesa.add(new Tupla<String,Integer>("Mayor65",contadorMayor65));
		
		return new LinkedList<Tupla<String,Integer>>();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method st

		Votante v = new Votante(1,"h",18,true,true);
		MesaGenerica m = new MesaTrabajador(v);
		System.out.println(m.consultarCupo());		
		System.out.println(m.consultarTipoMesa());
		System.out.println("Numero mesa " + m.mostrarNumeroMesa());
		System.out.println(m.mostrarNombrePresidenteMesa());
		
		Votante v2 = new Votante(10,"hola",18,true,true);
		m.asignarVotanteAFranjaHoraria(10, v);
		System.out.println(m.votantesTodasLasFranjas());
		
		Votante v1 = new Votante(1,"h",18,true,true);
		MesaGenerica mGeneral = new MesaGeneral(v1);
		mGeneral.asignarVotanteAFranjaHoraria(11, v1);
		//mGeneral.asignarVotanteAFranjaHoraria(12, v1);
		System.out.println("Numero mesa " + mGeneral.mostrarNumeroMesa());
		System.out.println(mGeneral.mostrarFranjasHorarias());
		System.out.println(mGeneral.consultarTurnosTotalesFranjas());
		
		MesaGeneral mGeneral1 = new MesaGeneral(v1);
		System.out.println("Numero mesa " + mGeneral1.mostrarNumeroMesa());
	}

}
