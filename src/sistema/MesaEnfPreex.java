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
    		agregarFranjaHoraria(new FranjaHoraria(franja,cupo));
    	}

    	// Asigno al presidente de mesa a una franja y le asigno el turno
    	FranjaHoraria franja = franjaConDisponibilidad();
    	asignarVotanteAFranjaHoraria(franja.consultarFranja(),presidenteMesa); 
    	presidenteMesa.crearTurno(this, franja);
    }
    private void agregarFranjaHoraria(FranjaHoraria franjaHoraria) {
    	if(franjaHoraria == null) {
    		throw new RuntimeException("Ingrese una franja valida");
    	}
    	if(franjasHorarias.size() == 10) {
    		throw new RuntimeException("No se pueden agregar mas mesas");
    	}
    	franjasHorarias.add(franjaHoraria);
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
	public int turnosRestantesTodasLasFranjas() {
		int turnosTotales = 0;
		for(FranjaHoraria f : franjasHorarias) {
			turnosTotales += f.consultarTurnosRestantes();
		}
		return turnosTotales;
	}

	@Override
	public FranjaHoraria franjaConDisponibilidad() {
		
		for(FranjaHoraria f : franjasHorarias) {
			if(f.consultarTurnosRestantes() > 0) {
				return f;
			}
		}
		// Si no hay franja con turnos disponibles, devuelvo null
		return null;
	}

	@Override
	public Map<Integer, List<Integer>> votantesDeFranjas() {
		
		Map<Integer, List<Integer>> hashVotantes = new HashMap<Integer, List<Integer>>();
		
		for(FranjaHoraria f : franjasHorarias) {
			hashVotantes.put(f.consultarFranja(), f.mostrarDnisVotantes());
		}
		return hashVotantes;
	}
    
	@Override
	public int cantTotalVotantesMesa() {
		int cantVotantes = 0;
		for(FranjaHoraria f : franjasHorarias) {
			cantVotantes += f.cantTotalVotantesFranja();
		}
		return cantVotantes;
	}
	@Override
	public boolean validarVotante(Votante votante) {
		if(votante.consultarEsTrabajador()) {
			return false;
		}

		return votante.consultarTieneEnfPreex() || (votante.consultarEsMayor() && votante.consultarTieneEnfPreex());
	}
    
    
    
    

}