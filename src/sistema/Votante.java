package sistema;


public class Votante {

	private int dni;
	private String nombre;
	private boolean esMayor65;
	private boolean esTrabajador;
	private boolean tieneEnfPreex;
	private boolean fueAVotar;
	private Turno turno;
	
	public Votante(int dni, String nombre, int edad, boolean tieneEnfPreex, boolean esTrabajador) {
		if(edad < 16) {
			throw new RuntimeException("Un votante menor de 16 no puede votar");
		} else if (edad >= 16 && edad < 65) {
			this.esMayor65 = false;
		} else if(edad >= 65) {
			this.esMayor65 = true;
		}
		
		this.dni = dni;
		this.nombre = nombre;
		this.esTrabajador = esTrabajador;
		this.tieneEnfPreex = tieneEnfPreex;
		this.fueAVotar = false;
		this.turno = null;
		
	}
	
	public int consultarDni() {
		return dni;
	}
	
	public String consultarNombre() {
		return nombre;
	}
	
	public boolean consultarEsTrabajador() {
		return esTrabajador;
	}
	
	public boolean consultarTieneEnfPreex() {
		return tieneEnfPreex;
	}
	
	public boolean consultarEsMayor() {
		return esMayor65;
	}
	
	public boolean consultarSiFueAVotar() {
		return fueAVotar;
	}
	
	public void votar() {
		if(fueAVotar == false) {
			fueAVotar = true;
		}
	}
	
	public Turno consultarTurno() {
		return turno;
	}
	
	public void crearTurno(MesaGenerica mesa,FranjaHoraria franjaHoraria) {
		if (turno != null) {
			throw new RuntimeException("No se puede asignar un turno si ya hay uno asignado");
		}
		
		turno = new Turno(mesa.mostrarNumeroMesa(),franjaHoraria.consultarFranja());
	}

	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		cadena.append("DNI: ").append(consultarDni());
		if(consultarTurno() != null) {
			Turno t = consultarTurno();

			cadena.append(", Turno: (");
			cadena.append("NumMesa: ").append(t.mostrarNumMesaTurno()).append(",");
			cadena.append("Franja: ").append(t.mostrarFranjaTurno()).append(")");
			
			cadena.append(", Fue a votar: ");
			if(consultarSiFueAVotar()) {
				cadena.append("Si | ");
			} else {
				cadena.append("No | ");
			}
		} else {
			cadena.append(", Turno: (").append(consultarTurno()).append(") | ");
			
		}
		
		return cadena.toString();
	}
	
	
}
