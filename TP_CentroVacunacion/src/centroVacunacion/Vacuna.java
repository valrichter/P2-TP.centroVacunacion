package centroVacunacion;

public abstract class Vacuna {
	private String nombreVacuna;
	private int temperatura;
	private Fecha fechaIngreso;
	
	/*Constructor*/
	public Vacuna(String nombreVacuna, int temperatura, Fecha fechaIngreso){
		this.nombreVacuna=nombreVacuna;
		this.temperatura=temperatura;
		this.fechaIngreso=fechaIngreso;
	}

	public String getNombreVacuna() {
		return nombreVacuna;
	}

	public int getTemperatura() {
		return temperatura;
	}

	public Fecha getfechaIngreso() {
		return fechaIngreso;
	}
	
	//METODOS AGREGADOS
	public abstract boolean estaVencidaFecha(Fecha fecha);
	public abstract boolean estaVencida();
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaIngreso == null) ? 0 : fechaIngreso.hashCode());
		result = prime * result + ((nombreVacuna == null) ? 0 : nombreVacuna.hashCode());
		result = prime * result + temperatura;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Vacuna))
			return false;
		Vacuna other = (Vacuna) obj;
		
		if(	this.fechaIngreso.equals(other.fechaIngreso) &&
			this.nombreVacuna.equals(other.nombreVacuna) &&
			(this.temperatura == other.temperatura)) {
			
			return true;
			
		}else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VACUNA [");
		builder.append("Nombre: '");
		builder.append(nombreVacuna);
		builder.append("', Temperatura: '");
		builder.append(temperatura);
		builder.append("', FechaIngreso: ");
		builder.append(fechaIngreso);
		builder.append("] ");
		return builder.toString();
	}
	
}
