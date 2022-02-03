package centroVacunacion;

public class Turno {
	private Persona persona;
	private Fecha fecha;
	private Vacuna vacuna;
	
	public Turno(Persona persona, Fecha fecha, Vacuna vacuna) {
		this.persona = persona;
		this.fecha = fecha;
		this.vacuna = vacuna;
	}
	
	public Persona getPersona() {
		return persona;
	}
	
	public Fecha getFecha() {
		return fecha;
	}
	
	public Vacuna getVacuna() {
		return vacuna;
	}
	
	//METODOS AGREGADOS
	public boolean estaVencidoFecha(Fecha fecha) {
		Fecha fechaTurno = new Fecha(this.fecha);
		return fecha.posterior(fechaTurno);
	}
	
	public boolean estaVencido() {
		return estaVencidoFecha(Fecha.hoy());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((persona == null) ? 0 : persona.hashCode());
		result = prime * result + ((vacuna == null) ? 0 : vacuna.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turno other = (Turno) obj;
		
		if(	this.fecha.equals(other.fecha) &&
			this.persona.equals(other.persona) &&
			(this.vacuna== other.vacuna)) {
			return true;
				
		}else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TURNO [Persona: '");
		builder.append(persona);
		builder.append("', Fecha: ");
		builder.append(fecha);
		builder.append(", Vacuna: '");
		builder.append(vacuna);
		builder.append("']");
		return builder.toString();
	}

}
