package sistema;


import java.util.*;

/**
 * 
 */
public class MesaGenerica {

    /**
     * Default constructor
     */
    public MesaGenerica() {
    }

    /**
     * 
     */
    private int numeroMesa;

    /**
     * 
     */
    private ArrayList<FranjaHoraria> franjasHorarias;

    /**
     * 
     */
    private Votante presidenteMesa;

    /**
     * 
     */
    private int cupo;

    /**
     * 
     */
    private String tipoMesa;






    /**
     * @return
     */
    public ArrayList<FranjaHoraria> mostrarFranjasHorarias() {
        // TODO implement here
        return null;
    }

    /**
     * @param int franja 
     * @return
     */
    public FranjaHoraria mostrarFranjaHoraria(int franja) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public int mostrarNumeroMesa() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public int mostrarDniPresidenteMesa() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public String mostrarNombrePresidenteMesa() {
        // TODO implement here
        return "";
    }

    /**
     * @param int franja 
     * @param Votante votante 
     * @return
     */
    public void asignarVotanteAFranjaHoraria(int franja, Votante votante) {
        // TODO implement here
        return;
    }

    /**
     * @param int franja 
     * @return
     */
    public int turnosRestantesFranjaHoraria(int franja) {
        // TODO implement here
        return 0;
    }

    /**
     * @param int franja 
     * @return
     */
    public LinkedList<Votante> votantesFranjaHoraria(int franja) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public int consultarTurnosTotalesFranjas() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public String consultarTipoMesa() {
        // TODO implement here
        return "";
    }

    /**
     * @return
     */
    public int consultarCupo() {
        // TODO implement here
        return 0;
    }

    /**
     * @return
     */
    public FranjaHoraria franjaConDisponibilidad() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Map<Integer,List<Integer>> votantesTodasLasFranjas() {
        // TODO implement here
        return null;
    }

}