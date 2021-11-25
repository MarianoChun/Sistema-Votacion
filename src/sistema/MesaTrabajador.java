package sistema;
import java.util.*;

/**
 * 
 */
public class MesaTrabajador extends MesaGenerica {
	

    private FranjaHoraria franjaHoraria;
    private int cupo = 2147483647;

    /**
     * Default constructor
     */
    public MesaTrabajador(Votante presidenteMesa) {
    	super(presidenteMesa, "Trabajador");

    	this.franjaHoraria = new FranjaHoraria(8,cupo);
    	// Asigno al presidente de mesa a una franja y le asigno el turno
    	FranjaHoraria franja = franjaConDisponibilidad();
    	asignarVotanteAFranjaHoraria(franja.consultarFranja(),presidenteMesa); 
    	presidenteMesa.crearTurno(this, franja);

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