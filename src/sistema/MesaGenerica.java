package sistema;


import java.util.*;

/**
 * 
 */
public abstract class MesaGenerica {
	
    private static int numeroMesa = 0;
    private ArrayList<FranjaHoraria> franjasHorarias;
    private Votante presidenteMesa;
    private int cupo;
    private String tipoMesa;
    
    /**
     * Default constructor
     */
    
    public MesaGenerica(Votante presidenteMesa , String tipoMesa) {
    	MesaGenerica.numeroMesa += 1;
    	this.presidenteMesa = presidenteMesa;
    	this.franjasHorarias = new ArrayList<FranjaHoraria>();
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

	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		cadena.append("Mesa: (Numero de Mesa: ");
		cadena.append(numeroMesa);
		cadena.append(", Tipo de Mesa: ").append(tipoMesa);
		cadena.append(", Presidente de Mesa: (DNI: ");
		cadena.append(presidenteMesa.consultarDni());
		cadena.append(" Nombre: ").append(presidenteMesa.consultarNombre()).append(")");
		return cadena.toString();
	}


	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		if (!(obj instanceof MesaGenerica))
			return false;
		
		MesaGenerica otraMesa = (MesaGenerica) obj;
		
		if(this.mostrarNumeroMesa() != otraMesa.mostrarNumeroMesa()) {
			return false;
		} else {
			return true;
		}
		
	}


}