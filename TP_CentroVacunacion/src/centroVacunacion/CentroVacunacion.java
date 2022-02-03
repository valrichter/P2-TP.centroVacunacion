package centroVacunacion;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CentroVacunacion {
	private String nombreCentro;
	private int capacidadVacunacionDiaria;
	HashMap<Vacuna, Integer> vacunasDisponibles = new HashMap<Vacuna, Integer>();
	HashMap<String, Integer> vacunasVencidas = new HashMap<String, Integer>();
	HashSet<Persona> listaInscriptos = new HashSet<Persona>();
	HashMap<Integer, String> vacunasAplicadas = new HashMap<Integer, String>();
	HashMap<Integer, Turno> turnosAsignados = new HashMap<Integer, Turno>();
	
	
	/**
	* Constructor.
	* recibe el nombre del centro y la capacidad de vacunación diaria.
	* Si la capacidad de vacunación no es positiva se debe generar una excepción.
	* Si el nombre no está definido, se debe generar una excepción.
	*/
	public CentroVacunacion(String nombreCentro, int capacidadVacunacionDiaria) {
		
		if(capacidadVacunacionDiaria <= 0) {
			throw new RuntimeException("La capacidad de vacunacion diaria debe ser positiva");
		}
		//sacamos los espacios de nombreCentro para una mejor lectura
		nombreCentro = nombreCentro.replaceAll(" ","");
		
		if(nombreCentro == null || nombreCentro.isEmpty()) {
			throw new RuntimeException("El nombre del centro no puede ser vacio");
		}
		
		this.nombreCentro = nombreCentro;
		this.capacidadVacunacionDiaria = capacidadVacunacionDiaria;
		
		//inicializo el reporte de vacunas vencidas
		vacunasVencidas.put("Pfizer", 0);
		vacunasVencidas.put("Moderna", 0);
	}
	
	
	/**
	* Solo se pueden ingresar los tipos de vacunas planteados en la 1ra parte.
	* Si el nombre de la vacuna no coincidiera con los especificados se debe generar
	* una excepción.
	* También se genera excepción si la cantidad es negativa.
	* La cantidad se debe
	* sumar al stock existente, tomando en cuenta las vacunas ya utilizadas.
	*/
	public void ingresarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso) {
		//creamos el tipo de vacuna que se ingreso
		Vacuna vacunaIngresada = crearVacuna(nombreVacuna, fechaIngreso);
		
		if(cantidad <= 0) {
			throw new RuntimeException("La cantidad de vacunas deber ser positiva");
		}
		//agregamos la vacuna junto con su cantidad
		agregarVacunasAlStock(vacunaIngresada, cantidad);
	}
	
	/* Suma la cantidad de vacunas con la cantidad a sumar */
	private void sumarCantVacunas(Vacuna vacuna, Integer cantSumar) {
		Integer cantVacunas = vacunasDisponibles.get(vacuna);
		vacunasDisponibles.replace(vacuna, cantVacunas + cantSumar);
	}
	
	/* Agrega las vacunas ingresadas al stock */
	private void agregarVacunasAlStock(Vacuna vacunaNueva, Integer cantidadNueva) {
		
		//si la vacuna esta entonces sumamos sus cantidades, sino la agregamos como una nueva vacuna
		if(vacunasDisponibles.containsKey(vacunaNueva)) {
			sumarCantVacunas(vacunaNueva, cantidadNueva);
		
		}else {
			vacunasDisponibles.put(vacunaNueva, cantidadNueva);
		}
	}
	
	/* Crea una vacuna segun el nombre y con su fecha de ingreso*/
	private Vacuna crearVacuna(String nombreVacuna, Fecha fechaIngreso) {
		
		//segun que nombre creamos un tipo de vacuna
		if(nombreVacuna == "Pfizer") {
			VacunaPfizer vacunaPfizer = new VacunaPfizer(fechaIngreso);
			return vacunaPfizer;
			
		}else if(nombreVacuna == "Sputnik") {
			VacunaSputnik vacunaSputnik = new VacunaSputnik(fechaIngreso);
			return vacunaSputnik;
			
		}else if(nombreVacuna == "Sinopharm") {
			VacunaSinopharm vacunaSinopharm = new VacunaSinopharm(fechaIngreso);
			return vacunaSinopharm;
			
		}else if(nombreVacuna == "Moderna") {
			VacunaModerna vacunaModerna = new VacunaModerna(fechaIngreso);
			return vacunaModerna;
			
		}else if(nombreVacuna == "AstraZeneca") {
			VacunaAstraZeneca vacunaAstraZeneca = new VacunaAstraZeneca(fechaIngreso);
			return vacunaAstraZeneca;
		
		}else {
			throw new RuntimeException("El nombre de la vacuna no es valido");
		}
	}
	
	
	/**
	* total de vacunas disponibles no vencidas sin distinción por tipo.
	*/
	public int vacunasDisponibles() {
		//vemos que no haya vacunas vencidas
		eliminarVacunasVencidas();
		
		//contador de vacunas vencidas
		int totalVacunasDisponibles = 0;
		
		//suma todos los valores de las vacunasDisponibles
		for(Integer cantVacuna : vacunasDisponibles.values()) {
			totalVacunasDisponibles += cantVacuna;
		}
		return totalVacunasDisponibles;
	}
	
	/**
	* total de vacunas disponibles no vencidas que coincida con el nombre de
	* vacuna especificado.
	*/
	public int vacunasDisponibles(String nombreVacuna) {
		//vemos que no haya vacunas vencidas
		eliminarVacunasVencidas();
			
		//contador de vacunas vencidas
		int totalVacunasDisponiblesTipo = 0;
		
		//analizamos todo el conjunto de vacunas
		for(Vacuna vacuna : vacunasDisponibles.keySet()) {
			
			//si el nombre de la vacuna actual es igual a nombreVacuna, entonces obtiene el valor asociado a esa clave y lo suma
			if(vacuna.getNombreVacuna().equals(nombreVacuna)) {
				Integer cantVacuna = vacunasDisponibles.get(vacuna);
				totalVacunasDisponiblesTipo += cantVacuna;
			}
		}	
		return totalVacunasDisponiblesTipo;	
	}
	
	/* Actualiza el reporte de vacunasVencidas */
	private void actualizarReporteVacunasVencidas(String nombreVacunaVencida, Integer cantidadVencida) {
		
		//suma la cantidad anterior de vacunas vencidas con la nueva cantidad
		if(vacunasVencidas.containsKey(nombreVacunaVencida)) {
			Integer cantAnteriorVencidas = vacunasVencidas.get(nombreVacunaVencida);
			vacunasVencidas.replace(nombreVacunaVencida, cantAnteriorVencidas + cantidadVencida);
		}		
	}
	
	/* Elimina las vacunas vencidas del stock de vacunasDisponibles */
	private void eliminarVacunasVencidas() {
		//usamos iterator para poder eliminar mientras recorremos
		Iterator<Vacuna> it = vacunasDisponibles.keySet().iterator();
		
		while(it.hasNext()) {
			Vacuna vacuna = it.next();
			//si la vacuna esta vencida entonces obtenemos el nombre de la vacuna y su cantidad asocidad, la agrega al reporte de vacunasVencidas y la elimina de vacunasDisponibles
			if(vacuna.estaVencida()) {
				actualizarReporteVacunasVencidas(vacuna.getNombreVacuna(), vacunasDisponibles.get(vacuna));
				it.remove();
			}
		}
	}
	
	
	/**
	* Se inscribe una persona en lista de espera.
	* Si la persona ya se encuentra inscripta o es menor de 18 años, se debe
	* generar una excepción.
	* Si la persona ya fue vacunada, también debe generar una excepción.
	*/
	public void inscribirPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		//creamos la persona
		Persona persona = new Persona(dni, nacimiento, tienePadecimientos, esEmpleadoSalud);
		
		if(estaInscripto(persona.getDni()) || tieneTurno(persona.getDni())) {
			throw new RuntimeException("La persona ya se encuentra inscripta o tiene turno");
		
		}else if(persona.getEdad() < 18) {
			throw new RuntimeException("La persona es menor de edad");
		
		}else if(estaVacunada(dni)) {
			throw new RuntimeException("La persona ya fue vacunada");
		
		}else {
			agregarAListaInscriptos(persona);
		}
	}
	
	/* Agrega una persona a la lista de espera */
	private void agregarAListaInscriptos(Persona persona) {
		listaInscriptos.add(persona);
	}
	
	/* Verfica si la persona esta vacunada o no */
	private boolean estaVacunada(Integer dni) {
		return vacunasAplicadas.containsKey(dni);
	}

	/* Verfica si la persona tiene turno o no */
	private boolean tieneTurno(Integer dni) {
		return turnosAsignados.containsKey(dni);
	}

	/* Verfica si la persona esta inscripta o no */
	private boolean estaInscripto(Integer dni) {
		
		for (Persona persona: listaInscriptos) {
			
			//si el dni esta en la lista entonce devuelve true
			if(persona.getDni().equals(dni)) {
				return true;
			}
		}
		return false;
	}	

	
	/**
	* Devuelve una lista con los DNI de todos los inscriptos que no se vacunaron
	* y que no tienen turno asignado.
	* Si no quedan inscriptos sin vacunas debe devolver una lista vacía.
	*/
	public List<Integer> listaDeEspera() {
		//creamos una lista de dniS
		List<Integer> listaDeEspera = new LinkedList<>();
		
		//buscamos dentro le las listaIncriptos el dni de cada persona
		for (Persona persona: listaInscriptos) {
				listaDeEspera.add(persona.getDni());
		}	
		return listaDeEspera;
	}
	
	
	/**
	* Primero se verifica si hay turnos vencidos. En caso de haber turnos
	* vencidos, la persona que no asistió al turno debe ser borrada del sistema
	* y la vacuna reservada debe volver a estar disponible.
	*
	* Segundo, se deben verificar si hay vacunas vencidas y quitarlas del sistema.
	*
	* Por último, se procede a asignar los turnos a partir de la fecha inicial
	* recibida según lo especificado en la 1ra parte.
	* Cada vez que se registra un nuevo turno, la vacuna destinada a esa persona
	* dejará de estar disponible. Dado que estará reservada para ser aplicada
	* el día del turno.
	*
	*
	*/
	public void generarTurnos(Fecha fechaInicial) {
		
		//eliminamos los turnos vencidos y regresamos las vacunas
		eliminarTurnosPerdidos();
		//verifcamos si las vacunas que regresaron no estan vencidas
		eliminarVacunasVencidas();
		
		//verificamos que la fecha ingresada sea valida
		if(fechaInicial.anterior(Fecha.hoy())) {
			throw new RuntimeException("Fecha pasada. Fecha invalida");
		}
		
		//verificamos que haya vacunas diponibles
		if(vacunasDisponibles() <= 0) {
			throw new RuntimeException("No hay vacunas disponibles");
		}
		
		//creamos las varables fecha y capacidad para poder usarlas
		Fecha fecha = new Fecha(fechaInicial);
		Integer capacidadDiaria = capacidadVacunacionDiaria;
		
		//sabemos que la lista esta ordenada por prioridad
		Iterator<Persona> it = listaEsperaOrdenada().iterator();
		
		//mientra haya vacunas y gente para vacunar...
		while(vacunasDisponibles() > 0 && listaInscriptos.size() > 0 && it.hasNext()) {	

			Persona persona = it.next();

			//verificamos si la capacidad diaria llego a cero
			if(capacidadDiaria == 0) {
				fecha.avanzarUnDia();
				capacidadDiaria = capacidadVacunacionDiaria;
			}
			
			//obtenemos una vacuna segun la edad de la persona
			Vacuna vacuna = obtenerVacuna(fecha, persona.getEdad());
			
			//si la vacuna existe entonces creamos un turno
			if(vacuna != null) {
				Fecha fechaTurno = new Fecha(fecha);
				registrarTurno(persona, fechaTurno, vacuna);
				
				//sacamos a la persona de la lista de inscriptos ya que recibio un turno
				listaInscriptos.remove(persona);
				capacidadDiaria--;
			
			}else {
				throw new RuntimeException("No hay vacunas utiles para la fecha " + fecha + " o posterior");
			}
		}
	}
	
	/* Registra el turno en turnosAsignados */
	private void registrarTurno(Persona persona, Fecha fecha, Vacuna vacuna) {
		Turno nuevoTurno = new Turno(persona, fecha, vacuna);
		turnosAsignados.put(persona.getDni(),nuevoTurno);
	}
	
	/* Extrae una vacuna para todo publico del stock disponible */
	private Vacuna obtenerVacunaTodoPublico(Fecha fechaTurno) {
		
		//buscamos dentro de las vacunas que vacunas estan destinadas a los mayores
		for(Vacuna vacuna : vacunasDisponibles.keySet()) {
			
			//primero verificamos que la vacuna es para todo publico
			//segundo verificamos que la vacuna no este vencida para el dia del turno
			//tercero verificamos que la vacuna tenga stock disponible
			if (vacuna instanceof VacunaTodoPublico &&
				!vacuna.estaVencidaFecha(fechaTurno) &&
				vacunasDisponibles.get(vacuna) > 0){
				
				//restamos una vacuna al stock de vacunas
				restarCantVacunas(vacuna, 1);
				return vacuna;
			}
		}
		//si no hay vacunas para todo publico devolvemos null
		return null;
	}
	
	/* Resta la cantidad de vacunas con la cantidad a restar */
	private void restarCantVacunas(Vacuna vacuna, Integer cantRestar) {
		Integer cantVacunas = vacunasDisponibles.get(vacuna);
		vacunasDisponibles.replace(vacuna, cantVacunas - cantRestar);
	}
	
	/* Extrae una vacuna para mayores del stock disponible */
	private Vacuna obtenerVacunaMayores(Fecha fechaTurno) {
		
		//buscamos dentro de las vacunas que vacunas estan destinadas a los mayores
		for(Vacuna vacuna : vacunasDisponibles.keySet()) {
			
			//primero verificamos que la vacuna es para mayores
			//segundo verificamos que la vacuna no este vencida para el dia del turno
			//tercero verificamos que la vacuna tenga stock disponible
			if (vacuna instanceof VacunaMayores &&
				!vacuna.estaVencidaFecha(fechaTurno) &&
				vacunasDisponibles.get(vacuna) > 0){
				
				//restamos una vacuna al stock de vacunas
				restarCantVacunas(vacuna, 1);
				return vacuna;
			}
		}
		//si no hay vacunas para mayores devolvemos null
		return null;
	}
	
	/* Extrae una vacuna del stock disponible */
	private Vacuna obtenerVacuna(Fecha fechaTurno , int edad) {
		
		//si es mayor priorizamos las vacunas para mayores
		if(edad >= 60) {
			Vacuna vacunaMayores = obtenerVacunaMayores(fechaTurno);
			
			//si hay entonces returnamos la vacuna, sino sacamos una para todo publico
			if(vacunaMayores != null) {
				return vacunaMayores;
			}else {
				return obtenerVacunaTodoPublico(fechaTurno);
			}
		
		//si no es mayor de edad obtenemos una para todo publico
		}else {
			return obtenerVacunaTodoPublico(fechaTurno);
		}
	}
	
	/* Devuelve una lista de espera ordenada segun la prioridad de vacunacion*/
	private List<Persona> listaEsperaOrdenada() {
		List<Persona> listaEsperaOrdenada = new LinkedList<Persona>(listaInscriptos);
		
		//Collections.sort() usa el compareTo de Persona, esto me permite ordenar por prioridad
		Collections.sort(listaEsperaOrdenada);
		return listaEsperaOrdenada;
	}
	
	/* Vuelve a poner la vacuna disponible, elimina el turno y la persona del sistema*/
	private void eliminarTurnosPerdidos() {
		//usamos iterator para poder eliminar mientras recorremos
		Iterator<Turno> it = turnosAsignados.values().iterator();
		
		while(it.hasNext()) {
			Turno turno = it.next();
			//si el turno esta vencido entonces devolvemos la vacuna, eliminamos el turno y con ello a la persona del sistema
			if(turno.estaVencido()) {
				sumarCantVacunas(turno.getVacuna(), 1);
				it.remove();
			}
		}	
	}
	
	
	/**
	* Devuelve una lista con los dni de las personas que tienen turno asignado
	* para la fecha pasada por parámetro.
	* Si no hay turnos asignados para ese día, se debe devolver una lista vacía.
	* La cantidad de turnos no puede exceder la capacidad por día de la ungs.
	*/
	public List<Integer> turnosConFecha(Fecha fecha) {
		
		List<Integer> turnosConFecha = new LinkedList<>();
		
		for (Turno turno: turnosAsignados.values()) {
			
			//si el turno de la fecha es igual a la fecha pasada por parametro agrega el dni a la lista
			if(turnoFechaIgualFecha(turno ,fecha)) {
				turnosConFecha.add(turno.getPersona().getDni());
			}
		}
		return turnosConFecha;	
	}
	
	/* Verifica si la fecha del turno coincide con una fecha determinada */
	private boolean turnoFechaIgualFecha(Turno turno, Fecha fecha) {
		//si el turno de la fecha es igual a la fecha pasada por parametro returna true
		if(turno.getFecha().equals(fecha)) {
			return true;
		}	
		return false;
	}
	
	
	/**
	* Dado el DNI de la persona y la fecha de vacunación
	* se valida que esté inscripto y que tenga turno para ese dia.
	* - Si tiene turno y está inscripto se debe registrar la persona como
	* vacunada y la vacuna se quita del depósito.
	* - Si no está inscripto o no tiene turno ese día, se genera una Excepcion.
	*/
	public void vacunarInscripto(int dni, Fecha fechaVacunacion) {
		//obtenemos la lista de los que se vacunan ese dia
		List<Integer> turnosFechaVacunacion = turnosConFecha(fechaVacunacion);
		
		//si esta inscripto y aparece en la lista de turnosConFecha procede a ser vacunado
		if(turnosFechaVacunacion.contains(dni)) {
			Turno turno = turnosAsignados.get(dni);
			
			//agrega el dni y el nombre de la vacuna aplicada al reporte de vacunas aplicadas
			vacunasAplicadas.put(dni, turno.getVacuna().getNombreVacuna());
			
			//removemos la persona de la lista de turnos y con ellos tambien la vacuna utilizada
			turnosAsignados.remove(dni);
		
		}else {
			throw new RuntimeException("No esta inscripto o no tiene turno para el dia");
		}
	}
	

	/**
	* Devuelve un Diccionario donde
	* - la clave es el dni de las personas vacunadas
	* - Y, el valor es el nombre de la vacuna aplicada.
	*/
	public Map<Integer, String> reporteVacunacion() {
		return vacunasAplicadas;	
	}
	
	
	/**
	* Devuelve en O(1) un Diccionario:
	* - clave: nombre de la vacuna
	* - valor: cantidad de vacunas vencidas conocidas hasta el momento.
	*/
	public Map<String, Integer> reporteVacunasVencidas() {
		return vacunasVencidas;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NombreCentro: '");
		builder.append(nombreCentro);
		builder.append("'\n");
		builder.append("CapacidadVacunacionDiaria: '");
		builder.append(capacidadVacunacionDiaria);
		builder.append("'\n");
		builder.append("VacunasDisponibles: '");
		builder.append(vacunasDisponibles());
		builder.append("'\n");
		builder.append("PersonasEnListaEspera: '");
		builder.append(listaDeEspera().size());
		builder.append("'\n");
		builder.append("CantidadTurnos: '");
		builder.append(turnosAsignados.size());
		builder.append("'\n");
		builder.append("VacunasAplicadas: '");
		builder.append(vacunasAplicadas.size());
		builder.append("'");
		return builder.toString();
	}
	
}
