package sistema;
import java.util.*;

/**
 * 
 */
public class MesaTrabajador extends MesaGenerica {
	private ArrayList<FranjaHoraria> franjasHorarias;
    private FranjaHoraria franjaHoraria;
    private int cupo = 2147483647;
    private String tipoMesa;
    /**
     * Default constructor
     */
    public MesaTrabajador(int numeroMesa, Votante presidenteMesa) {
    	super(numeroMesa, presidenteMesa);
    	//this.cupo = 2147483647;
    	this.tipoMesa = "Trabajador";
    	this.franjaHoraria = new FranjaHoraria(8,cupo);
    }

    /**
     * 
     */


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
    @Override
    public int consultarCupo() {
        // TODO implement here
        return cupo;
    }
    
    public ArrayList<FranjaHoraria> mostrarFranjasHorarias(){
		return franjasHorarias;
    	
    }
    
    public FranjaHoraria mostrarFranjaHoraria(int franja){
		 return mostrarFranjaHoraria(0);
    }

}