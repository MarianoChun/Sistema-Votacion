package sistema;


import java.util.*;

/**
 * 
 */
public class Turno {

    private MesaGenerica mesa;
    private FranjaHoraria franjaHoraria;
    /**
     * Default constructor
     */
    public Turno(MesaGenerica mesa, FranjaHoraria franjaHoraria) {
    	this.mesa = mesa;
    	this.franjaHoraria = franjaHoraria;
    }


    public int mostrarNumMesaTurno() {
        // TODO implement here
        return mesa.mostrarNumeroMesa();
    }

    public int mostrarFranjaTurno() {
        // TODO implement here
        return franjaHoraria.consultarFranja();
    }

}