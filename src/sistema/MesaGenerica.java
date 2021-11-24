package sistema;


import java.util.*;

/**
 * 
 */
public abstract class MesaGenerica {
	
    private static int incrementoNumMesa = 0;
    private int numeroMesa;
    private Votante presidenteMesa;
    private String tipoMesa;
    
    /**
     * Default constructor
     */
    
    public MesaGenerica(Votante presidenteMesa , String tipoMesa) {
    	incrementoNumMesa += 1;
    	this.numeroMesa = incrementoNumMesa;
    	this.presidenteMesa = presidenteMesa;
    	this.tipoMesa = tipoMesa;
    }

    public abstract ArrayList<FranjaHoraria> mostrarFranjasHorarias();

    public abstract FranjaHoraria mostrarFranjaHoraria(int franja);

    public abstract void asignarVotanteAFranjaHoraria(int franja, Votante votante);

    public abstract int turnosRestantesFranjaHoraria(int franja);

    public abstract LinkedList<Votante> votantesFranjaHoraria(int franja);

    public abstract int consultarTurnosTotalesFranjas();

    public  String consultarTipoMesa() {
    	return tipoMesa;
    }

    public abstract int consultarCupo();

    public abstract FranjaHoraria franjaConDisponibilidad();

    public abstract Map<Integer, List<Integer>> votantesTodasLasFranjas();
    
    public int mostrarNumeroMesa() {
        // TODO implement here
        return numeroMesa;
    }

    public int mostrarDniPresidenteMesa() {
        // TODO implement here
        return presidenteMesa.consultarDni();
    }

    public String mostrarNombrePresidenteMesa() {
        // TODO implement here
        return presidenteMesa.consultarNombre();
    }

    // Si la mesa es valida, la crea asignandole su presidente de mesa y la devuelve
    public static MesaGenerica validarMesaSegunTipo(String tipoMesa, Votante presidenteMesa) {
    	if(presidenteMesa == null) {
    		throw new RuntimeException("El presidente de mesa no es valido");
    	}
    		
    	MesaGenerica mesaNueva;  	
    	if (tipoMesa.equals("Trabajador")) {
			mesaNueva = new MesaTrabajador(presidenteMesa);
		} else if (tipoMesa.equals("Mayor65")) {
			mesaNueva = new MesaMayor65(presidenteMesa);
		} else if (tipoMesa.equals("Enf_Preex")) {
			mesaNueva = new MesaEnfPreex(presidenteMesa);
		} else if (tipoMesa.equals("General")) {
			mesaNueva = new MesaGeneral(presidenteMesa);
		} else {
			throw new RuntimeException("Tipo de mesa invalida");
		}
    	
    	return mesaNueva;

    }
    
    public static int cantVotantesConTurnoDeTipoMesa(String tipoMesa, MesaGenerica mesa) {
    	 
		if (!tipoMesa.equals("Mayor65") || !tipoMesa.equals("General") || !tipoMesa.equals("Enf_Preex")
				|| !tipoMesa.equals("Trabajador")) {
			throw new RuntimeException("Tipo de mesa invalida");
		}
    	int votantesConTurno = 0;
    	// Si el tipo de mesa coincide con el pasado por parametro, devuelvo su cant de votantes
		if (mesa.consultarTipoMesa().equals(tipoMesa)) {
			votantesConTurno += mesa.votantesTodasLasFranjas().size();
		}
		
		return votantesConTurno;
    }
     
    public static Turno asignaVotanteAMesa(Votante votante, MesaGenerica mesa){
    	boolean mesaDisponible = false;
    	// Si pude asignar el turno, lo devuelvo, sino devuelvo null
    	if (votante.consultarEsTrabajador()) {
    		if(mesa.consultarTipoMesa().equals("Trabajador") && mesa.consultarTurnosTotalesFranjas() > 0) {
    			mesaDisponible = true;
    		}
		} else if(votante.consultarEsMayor() && !votante.consultarTieneEnfPreex()) {
			if(mesa.consultarTipoMesa().equals("Mayor65") && mesa.consultarTurnosTotalesFranjas() > 0) {
				mesaDisponible = true;
			}
		} else if(!votante.consultarEsMayor() && votante.consultarTieneEnfPreex()) {
			if(mesa.consultarTipoMesa().equals("Enf_Preex") && mesa.consultarTurnosTotalesFranjas() > 0) {
				mesaDisponible = true;
			}
		} else if (votante.consultarEsMayor() && votante.consultarTieneEnfPreex()) { 
			if ((mesa.consultarTipoMesa().equals("Mayor65") || mesa.consultarTipoMesa().equals("Enf_Preex"))
				&& mesa.consultarTurnosTotalesFranjas() > 0) {
				mesaDisponible = true;
			}
		} else {
			if (mesa.consultarTipoMesa().equals("General") && mesa.consultarTurnosTotalesFranjas() > 0) {
				mesaDisponible = true;
			}
		}
    	
    	if(mesaDisponible) {
    		// Obtengo la primera franja con disponibilidad
			FranjaHoraria franjaDisponible = mesa.franjaConDisponibilidad();
			// Creo el turno y se lo asigno al votante
			votante.crearTurno(mesa, franjaDisponible);
			// Lo asigno a la franja
			mesa.asignarVotanteAFranjaHoraria(franjaDisponible.consultarFranja(), votante);
			// Devuelvo el turno
			return votante.consultarTurno();
    	}
    	
    	return null;	

    }
	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		cadena.append("\nMesa: \n\tNumero de Mesa: ");
		cadena.append(numeroMesa);
		cadena.append(", \n\tTipo de Mesa: ").append(tipoMesa);
		cadena.append(presidenteMesa.toString(true));
		return cadena.toString();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime + numeroMesa;
		result = prime * result + ((presidenteMesa == null) ? 0 : presidenteMesa.hashCode());
		result = prime * result + ((tipoMesa == null) ? 0 : tipoMesa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		
		MesaGenerica otraMesa = (MesaGenerica) obj;
		
		if(this.mostrarNumeroMesa() != otraMesa.mostrarNumeroMesa()) {
			return false;
		} else {
			return true;
		}
		
	}
	
	


}