package sistema;
import java.util.*;

/**
 * 
 */
public class MesaEnfPreex extends MesaGenerica {
	
    private ArrayList<FranjaHoraria> franjasHorarias = new ArrayList<FranjaHoraria>();
    private int cupo = 20;

    /**
     * Default constructor
     */
    public MesaEnfPreex(Votante presidenteMesa) {
    	super(presidenteMesa, "Enf_Preex");
    	
    	for(int franja = 8; franja < 18; franja++) {
    		franjasHorarias.add(new FranjaHoraria(franja,cupo));
    	}

    	// Asigno al presidente de mesa a una franja y le asigno el turno
    	FranjaHoraria franja = franjaConDisponibilidad();
    	asignarVotanteAFranjaHoraria(franja.consultarFranja(),presidenteMesa); 
    	presidenteMesa.crearTurno(this, franja);
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
		int turnosTotales = 0;
		for(FranjaHoraria f : franjasHorarias) {
			turnosTotales += f.consultarTurnosRestantes();
		}
		return turnosTotales;
	}

	@Override
	public int consultarCupo() {
		// TODO Auto-generated method stub
		return cupo;
	}

	@Override
	public FranjaHoraria franjaConDisponibilidad() {
		
		for(FranjaHoraria f : franjasHorarias) {
			if(f.consultarTurnosRestantes() != 0) {
				return f;
			}
		}
		// Si no hay franja con turnos disponibles, devuelvo null
		return null;
	}

	@Override
	public Map<Integer, List<Integer>> votantesTodasLasFranjas() {
		
		Map<Integer, List<Integer>> hashVotantes = new HashMap<Integer, List<Integer>>();
		
		for(FranjaHoraria f : franjasHorarias) {
			hashVotantes.put(f.consultarFranja(), f.mostrarDnisVotantes());
		}
		return hashVotantes;
	}
    
    
    
    
    
    

}