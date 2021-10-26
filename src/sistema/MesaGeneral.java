package sistema;
import java.util.*;

/**
 * 
 */
public class MesaGeneral extends MesaGenerica {
	
	    private ArrayList<FranjaHoraria> franjasHorarias = new ArrayList<FranjaHoraria>();
	    private int cupo = 30;
	    private String tipoMesa;

    /**
     * Default constructor
     */
    public MesaGeneral(int numeroMesa, Votante presidenteMesa) {
    	super(numeroMesa, presidenteMesa);  	
    	for(int franja = 8; franja < 18; franja++) {
    		franjasHorarias.add(new FranjaHoraria(franja,cupo));
    	}
    	this.tipoMesa = "General";
    }

    @Override
    public void asignarVotanteAFranjaHoraria(int franja, Votante votante) {
    	
    	if(franja < 8 || franja > 17) {
    		throw new RuntimeException("Franja invalida");
    	}
    	
    	for (FranjaHoraria f : franjasHorarias) {
    		if (f.consultarFranja() == franja) {
    			f.agregarVotante(votante);
    		}
    	}
        
    }
    
    @Override
    public ArrayList<FranjaHoraria> mostrarFranjasHorarias(){
		return franjasHorarias;
    	
    }
    
    @Override
    public FranjaHoraria mostrarFranjaHoraria(int franja){
    	if(franja < 8 || franja > 17) {
    		throw new RuntimeException("Franja invalida");
    	}
    	
    	FranjaHoraria franjaRetorno = null;
    	for (FranjaHoraria f : franjasHorarias) {
    		if (f.consultarFranja() == franja) {
    			franjaRetorno = f;
    		}
    	}
    	return franjaRetorno;
    }
    
    @Override
    public int turnosRestantesFranjaHoraria(int franja) {
    	if(franja < 8 || franja > 17) {
    		throw new RuntimeException("Franja invalida");
    	}
    	
    	int turnosRestantes = 0;
    	for (FranjaHoraria f : franjasHorarias) {
    		if (f.consultarFranja() == franja) {
    			turnosRestantes = f.consultarTurnosRestantes();
    		}
    	}
    	
    	return turnosRestantes;
    }


	@Override
	public String consultarTipoMesa() {
		// TODO Auto-generated method stub
		return tipoMesa;
	}


	@Override
	public int consultarCupo() {
		// TODO Auto-generated method stub
		return cupo;
	}

	@Override
	public LinkedList<Votante> votantesFranjaHoraria(int franja) {
		
		if(franja < 8 || franja > 17) {
    		throw new RuntimeException("Franja invalida");
    	}
		
		
		for(FranjaHoraria f : franjasHorarias) {
			if(f.consultarFranja() == franja) {
				return f.mostrarVotantes();
			}
		}
		
		return null;
	}

	@Override
	public int consultarTurnosTotalesFranjas() {
		// TODO Auto-generated method stub
		int turnosTotales = 0;
		for(FranjaHoraria f : franjasHorarias) {
			turnosTotales += f.consultarTurnosRestantes();
		}
		return turnosTotales;
	}

	@Override
	public FranjaHoraria franjaConDisponibilidad() {
		// TODO Auto-generated method stub
		for(FranjaHoraria f : franjasHorarias) {
			if(f.consultarTurnosRestantes() != 0) {
				return f;
			}
		}
		// Si no hay franja con turnos disponibles, devuelvo null
		return null;
	}

	@Override
	public HashMap<Integer, ArrayList<Integer>> votantesTodasLasFranjas() {
		// TODO Auto-generated method stub
		HashMap<Integer, ArrayList<Integer>> hashVotantes = new HashMap<Integer, ArrayList<Integer>>();
		
		for(FranjaHoraria f : franjasHorarias) {
			hashVotantes.put(f.consultarFranja(), f.mostrarDnisVotantes());
		}
		return hashVotantes;
	}
    
    
    
    
    
    
    
    
    
    

}