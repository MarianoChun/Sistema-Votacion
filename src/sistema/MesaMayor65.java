package sistema;
import java.util.*;

/**
 * 
 */
public class MesaMayor65 extends MesaGenerica {
	private ArrayList<FranjaHoraria> franjasHorarias;
    private int cupo = 10;
    private String tipoMesa;

    /**
     * Default constructor
     */
    public MesaMayor65(Votante presidenteMesa) {
        super(presidenteMesa);
 	
    	for(int franja = 8; franja < 18; franja++) {
    		franjasHorarias.add(new FranjaHoraria(franja,cupo));
    	}
    	this.tipoMesa = "Mayor65";
    	
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