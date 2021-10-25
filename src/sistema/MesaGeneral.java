package sistema;
import java.util.*;

/**
 * 
 */
public class MesaGeneral extends MesaGenerica {
	
	    private ArrayList<FranjaHoraria> franjasHorarias = new ArrayList<FranjaHoraria>();
	    private int cupo = 30;
	    private String tipoMesa;

    /**
     * Default constructor
     */
    public MesaGeneral(int numeroMesa, Votante presidenteMesa) {
    	super(numeroMesa, presidenteMesa);  	
    	for(int franja = 8; franja < 18; franja++) {
    		franjasHorarias.add(new FranjaHoraria(franja,cupo));
    	}
    	this.tipoMesa = "General";
    }


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
    	return turno;
    }


	@Override
	public String consultarTipoMesa() {
		// TODO Auto-generated method stub
		return tipoMesa;
	}


	@Override
	public int consultarCupo() {
		// TODO Auto-generated method stub
		return cupo;
	}
    
    
    
    
    
    
    
    
    
    

}