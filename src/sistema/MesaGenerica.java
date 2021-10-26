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

    public abstract void asignarVotanteAFranjaHoraria(int franja, Votante votante);

    public abstract int turnosRestantesFranjaHoraria(int franja);

    public abstract LinkedList<Votante> votantesFranjaHoraria(int franja);

    public abstract int consultarTurnosTotalesFranjas();

    public abstract String consultarTipoMesa();

    public abstract int consultarCupo();

    public abstract FranjaHoraria franjaConDisponibilidad();

    public abstract HashMap<Integer,ArrayList<Integer>> votantesTodasLasFranjas();
    
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


}