package centroVacunacion;

public class VacunaModerna extends VacunaTodoPublico {
	public VacunaModerna(Fecha fechaIngreso) {
		super("Moderna", -18, fechaIngreso);
	}
	
	//METODOS AGREGADOS
	@Override
	public boolean estaVencidaFecha(Fecha fecha) {
		//avanzamos 60 dias la fecha de ingreso para calcular cuando vence
		//debemos crear un nuevo objeto Fecha para que al calcular el vencimiento no se modifique la fecha de ingreso
		Fecha fechaVencimiento = new Fecha(super.getfechaIngreso());
		fechaVencimiento.avanzarDias(60);
		
		return fechaVencimiento.anterior(fecha);
	}
	
	@Override
	public boolean estaVencida() {
		return estaVencidaFecha(Fecha.hoy());
	}
	
}
