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

}