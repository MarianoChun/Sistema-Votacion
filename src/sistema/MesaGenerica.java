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
   
    }


    public ArrayList<FranjaHoraria> mostrarFranjasHorarias() {
        // TODO implement here
        return null;
    }

    public FranjaHoraria mostrarFranjaHoraria(int franja) {
        // TODO implement here
        return null;
    }


    public int mostrarNumeroMesa() {
        // TODO implement here
        return 0;
    }


    public int mostrarDniPresidenteMesa() {
        // TODO implement here
        return 0;
    }


    public String mostrarNombrePresidenteMesa() {
        // TODO implement here
        return "";
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

    public String consultarTipoMesa() {
        // TODO implement here
        return "";
    }


    public int consultarCupo() {
        // TODO implement here
        return cupo;
    }


    public FranjaHoraria franjaConDisponibilidad() {
        // TODO implement here
        return null;
    }

    public Map<Integer,List<Integer>> votantesTodasLasFranjas() {
        // TODO implement here
        return null;
    }

}