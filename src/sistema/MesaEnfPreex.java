package sistema;
import java.util.*;

/**
 * 
 */
public class MesaEnfPreex extends MesaGenerica {
	
    private ArrayList<FranjaHoraria> franjasHorarias;
    private int cupo;
    private String tipoMesa;
    /**
     * Default constructor
     */
    public MesaEnfPreex(int numeroMesa, Votante presidenteMesa) {
    	super(numeroMesa, presidenteMesa);
    	
    }

    /**
     * 
     */
  

    /**
     * 
     */
    

    /**
     * @param int franja 
     * @param Votante votante 
     * @return
     */
    public void asignarVotanteAFranjaHoraria(int franja, Votante votante) {
        // TODO implement here
        return;
    }
    
    
    public ArrayList<FranjaHoraria> mostrarFranjasHorarias(){
		return franjasHorarias;
    	
    }
    
    public FranjaHoraria mostrarFranjaHoraria(int franja){
		 return mostrarFranjaHoraria(0);
    }
    
    public int turnosRestantesFranjaHoraria(int franja) {
    	for(franjasHorarias: ffranjasHorarias) {
    		
    	}
    }
    
    
    
    
    
    

}