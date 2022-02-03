package centroVacunacion;

public class Persona implements Comparable<Persona> {
	private Integer dni;
	private Fecha nacimiento;
	private boolean tieneEnfermedades;
	private boolean trabajaEnSalud;
	
	/*Constructor*/
	public Persona(Integer dni, Fecha nacimiento, boolean tieneEnfermedades, boolean trabajaEnSalud) {
		if (dni<999999 || dni>99999999){
			throw new RuntimeException("El dni no es valido");
		}
		this.dni = dni;
		this.nacimiento = nacimiento;
		this.tieneEnfermedades = tieneEnfermedades;
		this.trabajaEnSalud = trabajaEnSalud;
	}

	public Integer getDni() {
		return dni;
	}
	
	public Fecha getNacimiento() {
		return nacimiento;
	}
	
	public boolean isTieneEnfermedades() {
		return tieneEnfermedades;
	}

	public boolean isTrabajaEnSalud() {
		return trabajaEnSalud;
	}
	
	//METODO AGREGADO
	public int getEdad() {
		return Fecha.diferenciaAnios(Fecha.hoy(), nacimiento);
	}
	
	//este metodo fue echo para ordenar las personas segun su prioridad
	//es utilizado en la funcion listaEsperaOrdenada junto a Collections.sort()
	@Override
	public int compareTo(Persona persona) {
		
		if(isTrabajaEnSalud() && !persona.isTrabajaEnSalud()) {
			return -1;
		}else if(!isTrabajaEnSalud() && persona.isTrabajaEnSalud()){
			return 1;
		}
		
		if ((getEdad() >=60) && !(persona.getEdad() >= 60)) {
			return -1;
		}else if (!(getEdad() >=60) && (persona.getEdad() >= 60)) {
			return 1;
		}
		
		if (isTieneEnfermedades() && !persona.isTieneEnfermedades()) {
			return -1;
		}else if (!isTieneEnfermedades() && persona.isTieneEnfermedades()) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Persona other = (Persona) obj;
		
		if(this.dni.equals(other.dni)) {
			return true;

		}else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PERSONA [Dni: '");
		builder.append(dni);
		builder.append("', Nacimiento: ");
		builder.append(nacimiento);
		builder.append(", tieneEnfermedades: '");
		builder.append(tieneEnfermedades);
		builder.append("', trabajaEnSalud: '");
		builder.append(trabajaEnSalud);
		builder.append("']");
		return builder.toString();
	}
	
}
