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

    public static int agregarMesaSegunTipo(String tipoMesa, Votante presidenteMesa, LinkedList<MesaGenerica> listaMesas) {
    	MesaGenerica mesaNueva;
    	
    	if (tipoMesa.equals("Trabajador")) {
			mesaNueva = new MesaTrabajador(presidenteMesa);
		} else if (tipoMesa.equals("Mayor65")) {
			mesaNueva = new MesaMayor65(presidenteMesa);
		} else if (tipoMesa.equals("Enf_Preex")) {
			mesaNueva = new MesaEnfPreex(presidenteMesa);
		} else if (tipoMesa.equals("General")) {
			mesaNueva = new MesaGeneral(presidenteMesa);
		} else {
			throw new RuntimeException("Tipo de mesa invalida");
		}
    	
    	listaMesas.add(mesaNueva);
		return mesaNueva.mostrarNumeroMesa();
    }
    
    public static int cantVotantesConTurno(String tipoMesa, LinkedList<MesaGenerica> listaMesas) {
    	int votantesConTurno = 0;
		for (MesaGenerica mesa : listaMesas) {
			if (mesa.consultarTipoMesa().equals(tipoMesa)) {
				votantesConTurno += mesa.votantesTodasLasFranjas().size();
			}
		}
		return votantesConTurno;
    }
    
    public static List<Tupla<String, Integer>> cantVotantesSinTurnoPorTipoMesa(HashMap<Integer,Votante> listaVotantes){
    	// Posible IREP? ver que no nos ingresen una listaVotantes erronea
    	List<Tupla<String, Integer>> listaTiposMesa = new LinkedList<Tupla<String, Integer>>();

		int contadorTrabajador = 0;
		int contadorGeneral = 0;
		int contadorEnf_Preex = 0;
		int contadorMayor65 = 0;

		// Recorro por valor el registro de votantes registrados
		for (Votante v : listaVotantes.values()) {
			if (v.consultarTurno() == null) {
				// Verifico a que tipo de mesa corresponde que sea asignado el votante
				if (v.consultarEsTrabajador()) {
					contadorTrabajador++;
				} else if (v.consultarEsMayor() && !v.consultarTieneEnfPreex()) {
					contadorMayor65++;
				} else if (!v.consultarEsMayor() && v.consultarTieneEnfPreex()) {
					contadorEnf_Preex++;
				} else if (v.consultarEsMayor() && v.consultarTieneEnfPreex()) {
					contadorEnf_Preex++;
					contadorMayor65++;
				} else {
					contadorGeneral++;
				}
			}
		}
		// Sumamos las tuplas de cada tipo de mesa a la lista.
		listaTiposMesa.add(new Tupla<String, Integer>("Trabajador", contadorTrabajador));
		listaTiposMesa.add(new Tupla<String, Integer>("General", contadorGeneral));
		listaTiposMesa.add(new Tupla<String, Integer>("Enf_Preex", contadorEnf_Preex));
		listaTiposMesa.add(new Tupla<String, Integer>("Mayor65", contadorMayor65));

		return listaTiposMesa;
    }
    
    public static Turno asignaVotanteAMesa(Votante votante, MesaGenerica mesa){
    	// Si pude asignar el turno, lo devuelvo, sino devuelvo null
    	if (votante.consultarEsTrabajador()) {
			if (mesa.consultarTipoMesa().equals("Trabajador") && mesa.consultarTurnosTotalesFranjas() > 0) {
				// Obtengo la primera franja con disponibilidad
				FranjaHoraria franjaDisponible = mesa.franjaConDisponibilidad();
				// Creo el turno y se lo asigno al votante
				votante.crearTurno(mesa, franjaDisponible);
				// Lo asigno a la franja
				mesa.asignarVotanteAFranjaHoraria(franjaDisponible.consultarFranja(), votante);
				// Devuelvo el turno
				return votante.consultarTurno();
			}
		} else if (votante.consultarEsMayor() || votante.consultarTieneEnfPreex()) {
			if ((mesa.consultarTipoMesa().equals("Mayor65") || mesa.consultarTipoMesa().equals("Enf_Preex"))
					&& mesa.consultarTurnosTotalesFranjas() > 0) {
				// Obtengo la primera franja con disponibilidad
				FranjaHoraria franjaDisponible = mesa.franjaConDisponibilidad();
				// Creo el turno y se lo asigno al votante
				votante.crearTurno(mesa, franjaDisponible);
				// Lo asigno a la franja
				mesa.asignarVotanteAFranjaHoraria(franjaDisponible.consultarFranja(), votante);
				// Devuelvo el turno
				return votante.consultarTurno();
			}
		} else {
			if (mesa.consultarTipoMesa().equals("General") && mesa.consultarTurnosTotalesFranjas() > 0) {
				// Obtengo la primera franja con disponibilidad
				FranjaHoraria franjaDisponible = mesa.franjaConDisponibilidad();
				// Creo el turno y se lo asigno al votante
				votante.crearTurno(mesa, franjaDisponible);
				// Lo asigno a la franja
				mesa.asignarVotanteAFranjaHoraria(franjaDisponible.consultarFranja(), votante);
				// Devuelvo el turno
				return votante.consultarTurno();
			}
		}
    	
    	return null;	

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + cupo;
		//result = prime * result + ((franjasHorarias == null) ? 0 : franjasHorarias.hashCode());
		result = prime * result + ((presidenteMesa == null) ? 0 : presidenteMesa.hashCode());
		result = prime * result + ((tipoMesa == null) ? 0 : tipoMesa.hashCode());
		return result;
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