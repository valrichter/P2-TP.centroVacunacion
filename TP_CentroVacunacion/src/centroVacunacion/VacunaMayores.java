package centroVacunacion;

public class VacunaMayores extends Vacuna {
	private int edadPersona = 60;
	
	public VacunaMayores(String nombre, int temperatura, Fecha fechaIngreso) {
		super(nombre, temperatura, fechaIngreso);
		
		if (edadPersona<60){
			throw new RuntimeException("La vacuna no es apta para mayores de 60");
		}
	}

	public int getEdadPersona() {
		return edadPersona;
	}
	
	//METODOS AGREGADOS
	@Override
	public boolean estaVencidaFecha(Fecha fecha) {
		return false;
	}
	
	@Override
	public boolean estaVencida() {
		return estaVencidaFecha(Fecha.hoy());
	}
	
}