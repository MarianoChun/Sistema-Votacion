package sistema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Sistema {
	
	String nombreSistema;
	HashMap<Integer,Votante> votantesRegistrados;
	LinkedList<MesaGenerica> mesas;
	
	
	public Sistema(String nombreSistema) {
		super();
		
		if(nombreSistema == null) {
			throw new RuntimeException("El nombre de sistema no debe ser vacio");
		}
		
		this.votantesRegistrados = new HashMap<>();
		this.mesas = new LinkedList<>();
	}

	public void registrarVotante(int dni, String nombre, int edad, boolean tieneEnfPreex, boolean esTrabajador){
		
	}
	
	public int agregarMesa(String tipoMesa, int dni){
		return 1;
	}
	
	public Tupla<Integer,Integer> asignarTurno(int dni){
		return new Tupla<Integer,Integer>(1,1);
	}
	
	public int asignarTurno() {
		return 1;
	}
	
	public boolean verificarVotanteEnSistema(int dni) {
		return true;
	}
	
	public int consultarCantTurnosDisponibles(int numMesa){
		return 1;
	}
	
	public Tupla<Integer,Integer> consultarTurno(int dni){
		return new Tupla<Integer,Integer>(1,1);
	}
	
	public boolean sePresentoVotar(int dni) {
		return true;
	}
	
	public boolean votar(int dni) {
		return true;
	}
	
	public int votantesConTurno(String tipoMesa) {
		return 1;
	}
	
	public HashMap<Integer,ArrayList<Integer>> asignadosAMesa(int numMesa){
		return new HashMap<Integer,ArrayList<Integer>>();
	}
	
	public ArrayList<Tupla<String,Integer>> sinTurnoSegunTipomesa(){
		return new ArrayList<Tupla<String,Integer>>();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method st

		Votante v = new Votante(1,"h",18,true,true);
		MesaGenerica m = new MesaTrabajador(2,v);
		System.out.println(m.consultarCupo());		
		System.out.println(m.consultarTipoMesa());
		System.out.println(m.mostrarNombrePresidenteMesa());
		
		Votante v2 = new Votante(10,"hola",18,true,true);
		m.asignarVotanteAFranjaHoraria(10, v);
		System.out.println(m.votantesTodasLasFranjas());
		
		Votante v1 = new Votante(1,"h",18,true,true);
		MesaGenerica mGeneral = new MesaGeneral(1,v1);
		mGeneral.asignarVotanteAFranjaHoraria(11, v1);
		//mGeneral.asignarVotanteAFranjaHoraria(12, v1);
		System.out.println(mGeneral.mostrarFranjasHorarias());
		System.out.println(mGeneral.consultarTurnosTotalesFranjas());
		
	}

}
