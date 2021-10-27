package sistema;
import java.util.*;

/**
 * 
 */
public class MesaTrabajador extends MesaGenerica {
	

    private FranjaHoraria franjaHoraria;
    private int cupo = 2147483647;
    private String tipoMesa;
    /**
     * Default constructor
     */
    public MesaTrabajador(Votante presidenteMesa) {
    	super(presidenteMesa);
    	//this.cupo = 2147483647;

    	//this.franjasHorarias = new ArrayList<FranjaHoraria>();
    	this.tipoMesa = "Trabajador";
    	this.franjaHoraria = new FranjaHoraria(8,cupo);
    	//this.franjaHoraria = 
    }

    @Override
    public int turnosRestantesFranjaHoraria(int franja) {
        // TODO implement here
    	
    	if(franja < 8 || franja > 11) {
    		throw new RuntimeException("Franja invalida");
    	}
    	
        return franjaHoraria.consultarTurnosRestantes();
    }
    
    @Override
    public LinkedList<Votante> votantesFranjaHoraria(int franja) {
        // TODO implement here
    	
    	if(franja < 8 || franja > 11) {
    		throw new RuntimeException("Franja invalida");
    	}
    	
        return franjaHoraria.mostrarVotantes();
    }
    
    @Override
    public void asignarVotanteAFranjaHoraria(int franja, Votante votante) {
        // TODO implement here
    	
    	if(franja < 8 || franja > 11) {
    		throw new RuntimeException("Franja invalida");
    	}
    	
    	if(franjaHoraria.votanteEstaEnFranja(votante.consultarDni())) {
    		throw new RuntimeException("El votante ya se encuentra en la franja");
    	}
    	franjaHoraria.agregarVotante(votante);
    }
    
    @Override
    public int consultarTurnosTotalesFranjas() {
        // TODO implement here
        return franjaHoraria.consultarTurnosRestantes();
    }
    
    @Override
    public int consultarCupo() {
        // TODO implement here
        return cupo;
    }
    
    @Override
    public ArrayList<FranjaHoraria> mostrarFranjasHorarias(){
    	ArrayList<FranjaHoraria> franja = new ArrayList<FranjaHoraria>();
    	franja.add(franjaHoraria);
		return franja;
    	
    }
    @Override
    public FranjaHoraria mostrarFranjaHoraria(int franja){
    	if(franja < 8 || franja > 11) {
    		throw new RuntimeException("Franja invalida");
    	} 
		 return franjaHoraria;
    }

	@Override
	public String consultarTipoMesa() {
		// TODO Auto-generated method stub
		return tipoMesa;
	}

	@Override
	public FranjaHoraria franjaConDisponibilidad() {
		// Siempre devuelve la franja unica xq el cupo no tiene limite
		return franjaHoraria;
	}

	@Override
	public Map<Integer, List<Integer>> votantesTodasLasFranjas() {
		// TODO Auto-generated method stub
		
		Map<Integer, List<Integer>> hashVotantes = new HashMap<Integer, List<Integer>>();
		hashVotantes.put(franjaHoraria.consultarFranja(), franjaHoraria.mostrarDnisVotantes());
		
		return hashVotantes;
	}

}