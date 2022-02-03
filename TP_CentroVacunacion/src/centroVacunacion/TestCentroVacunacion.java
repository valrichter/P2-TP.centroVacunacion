package centroVacunacion;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestCentroVacunacion {
	CentroVacunacion centro;

	@Before
	public void setUp() throws Exception {
		Fecha.setFechaHoy();
		
		centro = new CentroVacunacion("UNGS", 5);
		centro.ingresarVacunas("Sputnik", 10,new Fecha(20,3,2021));
		centro.ingresarVacunas("AstraZeneca", 10,new Fecha(20,3,2021));

		centro.inscribirPersona(34701000, new Fecha(1, 5, 1989), false, false);  // 32 NS NP 4
		centro.inscribirPersona(29959000, new Fecha(20, 11, 1982), false, true); // 38 S  NP 1
		centro.inscribirPersona(24780201, new Fecha(1, 6, 1972), true, false);   // 49 NS P  3
		centro.inscribirPersona(29223000, new Fecha(2, 5, 1982), false, true);   // 39 S  NP 1
		centro.inscribirPersona(13000000, new Fecha(1, 5, 1958), true, false);   // 63 NS P  2
		centro.inscribirPersona(13000050, new Fecha(20, 6, 1958), false, true);  // 62 S  NP 1
		centro.inscribirPersona(14000000, new Fecha(1, 1, 1961), false, false);  // 60 NS NP 2
		centro.inscribirPersona(14005000, new Fecha(20, 12, 1961), true, false); // 59 NS P  3
	}

	@Test
	public void testIngresarVacunas() {
		Fecha.setFechaHoy(15,4,2021);
		
		assertEquals(20, centro.vacunasDisponibles());

		centro.ingresarVacunas("Pfizer", 10,new Fecha(20,3,2021));
		centro.ingresarVacunas("Moderna", 10,new Fecha(20,3,2021));
		centro.ingresarVacunas("Sinopharm", 10,new Fecha(20,3,2021));

		assertEquals(50, centro.vacunasDisponibles());
	}

	@Test
	public void testInscripcion() {
		assertEquals(8, centro.listaDeEspera().size());

		centro.inscribirPersona(34780201, new Fecha(1, 7, 1989), false, false);
		centro.inscribirPersona(29223959, new Fecha(10, 11, 1982), false, true);

		assertEquals(10, centro.listaDeEspera().size());
	}

	@Test
	public void testGenerarYConsultarTurnos() {
		Fecha fechaInicial = new Fecha(2, 7, 2021);
		Fecha fechaSiguiente = new Fecha(3, 7, 2021);
		Fecha fechaAnteriorSinTurnos = new Fecha(1, 7, 2021);
		Fecha fechaPosteriorSinTurnos = new Fecha(4, 7, 2021);

		assertEquals(8, centro.listaDeEspera().size());
		assertEquals(20, centro.vacunasDisponibles());
		
		centro.generarTurnos(fechaInicial);
		
		assertEquals(0, centro.listaDeEspera().size());
		assertEquals(12, centro.vacunasDisponibles());

		// son 8 anotados y la capacidad diaria es 5 personas.
		assertEquals(5, centro.turnosConFecha(fechaInicial).size());
		assertEquals(3, centro.turnosConFecha(fechaSiguiente).size());
		assertEquals(0, centro.turnosConFecha(fechaAnteriorSinTurnos).size());
		assertEquals(0, centro.turnosConFecha(fechaPosteriorSinTurnos).size());
	}

	@Test
	public void testGenerarTurnosYRegistrarVacunacion() {
		int dniAVacunar=29959000;
		Fecha fecha = new Fecha(30,6,2021);
		assertEquals(20, centro.vacunasDisponibles());
		assertTrue(centro.listaDeEspera().contains(dniAVacunar));
		assertFalse(centro.reporteVacunacion().containsKey(dniAVacunar));

		centro.generarTurnos(fecha);
		
		assertEquals(12, centro.vacunasDisponibles());
		assertFalse(centro.listaDeEspera().contains(dniAVacunar));
		assertFalse(centro.reporteVacunacion().keySet().contains(dniAVacunar));

		centro.vacunarInscripto(dniAVacunar,fecha);

		assertTrue(centro.reporteVacunacion().keySet().contains(dniAVacunar));
		
		
		// Simulo que pasó la fecha del turno y reviso que los turnos no
		// cumplidos  devuelvan las vacunas al STOCK y no quede gente en
		// lista de espera.
		Fecha.setFechaHoy(2, 7, 2021);

		centro.generarTurnos(new Fecha(5,7,2021));
		
		assertEquals(19, centro.vacunasDisponibles());
		assertTrue(centro.listaDeEspera().isEmpty());
	}
	
	@Test
	public void testReporteVacunasVencidas() {
		
		CentroVacunacion centroConVacunasVencidas = new CentroVacunacion("UNGS 2", 5);
		// Simulo que hoy es el 20 de abril
		Fecha.setFechaHoy(20,4,2021);
		centroConVacunasVencidas.ingresarVacunas("Pfizer", 10,new Fecha(30,3,2021));
		centroConVacunasVencidas.ingresarVacunas("Pfizer", 10,new Fecha(20,4,2021));

		assertEquals(20, centroConVacunasVencidas.vacunasDisponibles("Pfizer"));
		
		// Simulo que hoy es el 19 de mayo 
		Fecha.setFechaHoy(19,5,2021);
		
		centroConVacunasVencidas.generarTurnos(new Fecha(20,5,2021));
		
		assertEquals(10, centroConVacunasVencidas.vacunasDisponibles("Pfizer"));
		assertEquals(10, centroConVacunasVencidas.reporteVacunasVencidas().get("Pfizer").intValue());
	}

	/*************************************************************************/
	/********************* Casos que deben fallar ****************************/
	/*************************************************************************/	

	@Test
	public void testIngresarVacunasConCantidadInvalida() {
		try {
			centro.ingresarVacunas("AstraZeneca", 0, new Fecha(20,3,2021));
			fail("Permitió ingresar una vacuna con cantidad 0");
		} catch (RuntimeException e) { }
	
		try {
			centro.ingresarVacunas("Moderna", -10, new Fecha(20,3,2021));
			fail("Permitió ingresar una vacuna con cantidad negativa");
		} catch (RuntimeException e) { }
	}

	@Test(expected = RuntimeException.class)
	public void testGenerarTurnosParaUnaFechaPasada() {
		centro.generarTurnos(new Fecha(10,05,2021));
	}

	@Test(expected = RuntimeException.class)
	public void testVacunarPersonaNoRegistrada() {
		centro.vacunarInscripto(17000000,new Fecha(31,12,2021));
	}

	@Test(expected = RuntimeException.class)
	public void testVacunarPersonaConTurnoParaOtraFecha() {
		int dniAVacunar=29959000;
		Fecha fecha = new Fecha(30,6,2021);
		Fecha fechaIncorrecta = new Fecha(29,6,2021);
		centro.generarTurnos(fecha);
		
		centro.vacunarInscripto(dniAVacunar,fechaIncorrecta);
	}
	
}
