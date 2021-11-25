package sistema;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import static org.junit.Assert.assertEquals;
public class SistemaDeTurnosTestPropio {
	private SistemaDeTurnos sistema;
	private static final FixturePropio F = FixturePropio.INSTANCE;
	@Before
	public void setUp() {
		sistema = new SistemaDeTurnos("Sistema Ungs");
		// Dni, Nombre, Edad, enfPreex, Trabajador
		sistema.registrarVotante(43888910, "Pepe", 20, !F.tieneEnfPrevia, !F.trabaja);
		sistema.registrarVotante(37523415, "Juan", 22, !F.tieneEnfPrevia, !F.trabaja);
		sistema.registrarVotante(20100200, "Dolores", 70, F.tieneEnfPrevia, !F.trabaja);
		sistema.registrarVotante(50457200, "Raul", 50, F.tieneEnfPrevia, !F.trabaja);
		sistema.registrarVotante(41203901, "Tito", 29, !F.tieneEnfPrevia, F.trabaja);
		sistema.registrarVotante(34123657, "Amanda", 65, F.tieneEnfPrevia, F.trabaja);
		
		// # Votantes = 6
			// Mayores de 65 2
			// Trabajadores = 2
			// EnfPrexistente = 3
			// General = 2
	}
	/*
	 * Al crear varias mesas, cada una deberia tener un numero de
	 * mesa distinto, arrancando de 1 e incrementandose
	 */
	@Test
	public void testNumerosDeMesa() {
		
		final int numMesaEnfPreex = sistema.agregarMesa(F.enfPreexistente,F.dniPepe);
		final int numMesaTrabajador = sistema.agregarMesa(F.trabajador,F.dniJuan);
		final int numMesaMayores65 = sistema.agregarMesa(F.mayor65,F.dniDolores);
		final int numMesaGeneral = sistema.agregarMesa(F.general,F.dniRaul);
		final int numMesaTrabajador2 = sistema.agregarMesa(F.trabajador,F.dniTito);
		
		
		assertEquals(1,numMesaEnfPreex);
		assertEquals(2,numMesaTrabajador);
		assertEquals(3,numMesaMayores65);
		assertEquals(4,numMesaGeneral);
		assertEquals(5,numMesaTrabajador2);
	}
	
	/*
	 * Se presentara el caso en que se quiera asignar votante/s
	 * cuando no haya mesas presentes.
	 * 
	 */
	@Test
	public void asignacionTurno1(){
		sistema.asignarTurno();
		sistema.asignarTurno(F.dniDolores);
		int votantesAsignados = sistema.mostrarTurnosVotantes().size();
		assertEquals(0,votantesAsignados);
	}
	
	/*
	 * Se presentara el caso en que se quiera asignar votante/s
	 * cuando solo haya mesas que no coincidan con la que requiere
	 * el/los votante/s
	 * 
	 */
	@Test
	public void asignacionTurno2(){
		sistema.agregarMesa(F.trabajador, F.dniAmanda);
		sistema.agregarMesa(F.general, F.dniJuan);
		
		sistema.asignarTurno(F.dniDolores);
		sistema.asignarTurno(F.dniRaul);
		
		// Se restan  los turnos de los presidente de mesa, que son 2
		int votantesAsignados = sistema.mostrarTurnosVotantes().size() - 2;
		assertEquals(0,votantesAsignados);
	}

}
