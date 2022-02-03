package centroVacunacion;

public class VacunaPfizer extends VacunaMayores {
	public VacunaPfizer(Fecha fechaIngreso) {
		super("Pfizer", -18, fechaIngreso);
	}
	
	//METODOS AGREGADOS
	@Override
	public boolean estaVencidaFecha(Fecha fecha) {
		//avanzamos 30 dias la fecha de ingreso para calcular cuando vence
		//debemos crear un nuevo objeto Fecha para que al calcular el vencimiento no se modifique la fecha de ingreso
		Fecha fechaVencimiento = new Fecha(super.getfechaIngreso());
		fechaVencimiento.avanzarDias(30);

		return fechaVencimiento.anterior(fecha);
	}
	
	@Override
	public boolean estaVencida() {
		return estaVencidaFecha(Fecha.hoy());
	}
	
}
