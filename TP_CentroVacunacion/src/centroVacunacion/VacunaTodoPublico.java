package centroVacunacion;

public class VacunaTodoPublico extends Vacuna {
	private int edadPersona = 18;

	public VacunaTodoPublico(String nombre, int temperatura, Fecha fechaIngreso) {
		super(nombre, temperatura, fechaIngreso);
		if (edadPersona<18){
			throw new RuntimeException("La vacuna no es apta para menores de 18");
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
