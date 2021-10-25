package sistema;


import java.util.*;

/**
 * 
 */
public abstract class MesaGenerica {
	
    private int numeroMesa;
    private ArrayList<FranjaHoraria> franjasHorarias;
    private Votante presidenteMesa;
    private int cupo;
    private String tipoMesa;
    /**
     * Default constructor
     */
    
    public MesaGenerica(int numeroMesa, Votante presidenteMesa) {
    	this.numeroMesa = numeroMesa;
    	this.presidenteMesa = presidenteMesa;
    	this.franjasHorarias = new ArrayList<FranjaHoraria>();

    }


    public abstract ArrayList<FranjaHoraria> mostrarFranjasHorarias();

    public abstract FranjaHoraria mostrarFranjaHoraria(int franja);


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


    public void asignarVotanteAFranjaHoraria(int franja, Votante votante) {
        // TODO implement here
        return;
    }


    public int turnosRestantesFranjaHoraria(int franja) {
        // TODO implement here
        return 0;
    }


    public LinkedList<Votante> votantesFranjaHoraria(int franja) {
        // TODO implement here
        return null;
    }


    public int consultarTurnosTotalesFranjas() {
        // TODO implement here
        return 0;
    }

    public abstract String consultarTipoMesa();


    public abstract int consultarCupo();


    public FranjaHoraria franjaConDisponibilidad() {
        // TODO implement here
        return null;
    }

    public Map<Integer,List<Integer>> votantesTodasLasFranjas() {
        // TODO implement here
        return null;
    }

}