package sistema;
import java.util.*;

/**
 * 
 */
public class MesaMayor65 extends MesaGenerica {
	private ArrayList<FranjaHoraria> franjasHorarias = new ArrayList<FranjaHoraria>();
    private int cupo = 10;


    /**
     * Default constructor
     */
    public MesaMayor65(Votante presidenteMesa) {
        super(presidenteMesa, "Mayor65");
 	
    	for(int franja = 8; franja < 18; franja++) {
    		agregarFranjaHoraria(new FranjaHoraria(franja,cupo));
    	}
    	//this.tipoMesa = "Mayor65";
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
			if(f.consultarTurnosRestantes() > 0) {
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