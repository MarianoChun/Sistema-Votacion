package sistema;
import java.util.*;

/**
 * 
 */
public class FranjaHoraria {

    private int franja;
    private LinkedList<Votante> votantes;
    private int turnosRestantes;
    
    /**
     * Default constructor
     */
    public FranjaHoraria(int franja, int turnosRestantes) {
    	if(franja < 8 || franja > 18) {
    		throw new RuntimeException("La franja ingresada es invalida");
    	}
    	this.franja = franja;
    	this.turnosRestantes = turnosRestantes;
    	this.votantes = new LinkedList<Votante>();
    }


    @Override
	public String toString() {
    	StringBuilder cadena = new StringBuilder();
    	cadena.append("Franja: ");
    	cadena.append(franja);

		return cadena.toString();
	}

    public int cantTotalVotantesFranja() {
    	return votantes.size();
    }

	public int consultarTurnosRestantes() {
        // TODO implement here
        return turnosRestantes;
    }

    public LinkedList<Votante> mostrarVotantes() {
        // TODO implement here
        return votantes;
    }


    public int consultarFranja() {
        // TODO implement here
        return franja;
    }


    private void restarTurno() {
        // TODO implement here
        turnosRestantes -= 1;
    }


    public void agregarVotante(Votante votante) {
        // TODO implement here
        if(!votanteEstaEnFranja(votante.consultarDni())
        		&& consultarTurnosRestantes() > 0) {
        	votantes.add(votante);
        	restarTurno();
        } else {
        	throw new RuntimeException("Error: El votante ya esta agregado");
        }
    }


    public boolean votanteEstaEnFranja(int dni) {
        // TODO implement here
    	boolean estaVotante = false;
    	
    	for(Votante v : votantes) {
    		estaVotante = estaVotante || (v.consultarDni() == dni);
    	}
        return estaVotante;
    }
    
    public ArrayList<Integer> mostrarDnisVotantes(){
    	ArrayList<Integer> dnis = new ArrayList<Integer>();
    	
    	for (Votante v : votantes) {
    		dnis.add(v.consultarDni());
    	}
    	
    	return dnis;
    }

}