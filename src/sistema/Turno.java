package sistema;


import java.util.*;

/**
 * 
 */
public class Turno {

    private int numMesa;
    private int franja;
    /**
     * Default constructor
     */
    public Turno(int numMesa, int franja) {
    	this.numMesa = numMesa;
    	this.franja = franja;
    }


    public int mostrarNumMesaTurno() {
        return numMesa;
    }

    public int mostrarFranjaTurno() {
    	return franja;
    }


	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		cadena.append("Turno:");
		cadena.append("\n\tNumero de mesa: ").append(numMesa);
		cadena.append("\n\tFranja horaria: ").append(franja);

		return cadena.toString();
	}

    
}