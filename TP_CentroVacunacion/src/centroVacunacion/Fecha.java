package centroVacunacion;

import java.time.LocalDate;
import java.time.Period;

public class Fecha implements Comparable<Fecha> {

	/***************** Estatico ***********************/

	/**
	 * define cual es el dia actual.
	 */
	private static Fecha hoy = new Fecha();

	/**
	 * Permite crear una nueva fecha con la definicion del dia actual.
	 */
	public static Fecha hoy() {
		return new Fecha(hoy);
	}

	/**
	 * Definir que el dia actual es la fecha de hoy.
	 */
	public static void setFechaHoy() {
		LocalDate ld = LocalDate.now();
		setFechaHoy(ld.getDayOfMonth(), ld.getMonthValue(), ld.getYear());
	}

	/**
	 * Definir que el dia actual es una fecha en particular. 
	 * Permite simular que se está en un dia específico.
	 */
	public static void setFechaHoy(int dia, int mes, int anio) {
		hoy = new Fecha(dia, mes, anio);
	}

	/**************** Instancia ************************************/

	private LocalDate fecha;

	private Fecha() {
		fecha = LocalDate.now();
	}

	public Fecha(int dia, int mes, int anio) {
		fecha = LocalDate.of(anio, mes, dia);
	}

	public Fecha(Fecha otraFecha) {
		fecha = LocalDate.of(otraFecha.anio(), otraFecha.mes(), otraFecha.dia());
	}

	public int anio() {
		return fecha.getYear();
	}

	public int mes() {
		return fecha.getMonthValue();
	}

	public int dia() {
		return fecha.getDayOfMonth();
	}

	public static int diferenciaAnios(Fecha hoy, Fecha fNacimiento) {
		Period periodo = fNacimiento.fecha.until(hoy.fecha);
		return periodo.getYears();
	}

	public void avanzarUnDia() {
		avanzarDias(1);
	}
	
	//METODO AGREGADO
	public void avanzarDias(int dias){
		fecha = fecha.plusDays(dias);
	}

	public boolean anterior(Fecha otraFecha) {
		return this.compareTo(otraFecha) < 0;
	}

	public boolean posterior(Fecha otraFecha) {
		return this.compareTo(otraFecha) > 0;
	}
	
	@Override
	public int compareTo(Fecha otraFecha) {
		return fecha.compareTo(otraFecha.fecha);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fecha other = (Fecha) obj;
		
		if (fecha == null) 
			return other.fecha == null;
		else 
			return fecha.equals(other.fecha);
	}
	
	@Override
	public int hashCode() {
		return fecha == null ? 0 : fecha.hashCode();
	}
	
	//METODO AGREGADO
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("'");
		builder.append(fecha);
		builder.append("'");
		return builder.toString();
	}

}
