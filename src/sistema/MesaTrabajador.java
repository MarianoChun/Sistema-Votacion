package sistema;
import java.util.*;

/**
 * 
 */
public class MesaTrabajador extends MesaGenerica {

    /**
     * Default constructor
     */
    public MesaTrabajador() {
    }

    /**
     * 
     */
    private FranjaHoraria franjasHorarias;

    /**
     * 
     */
    private int cupo;

    /**
     * 
     */
    private String tipoMesa;

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
        return new LinkedList<Votante>();
    }

    /**
     * @param int franja 
     * @param Votante votante 
     * @return
     */
    public void AsignarVotanteAFranjaHoraria(int franja, Votante votante) {
        // TODO implement here
        return;
    }

    /**
     * @return
     */
    public int consultarTurnosTotalesFranjas() {
        // TODO implement here
        return 0;
    }

}