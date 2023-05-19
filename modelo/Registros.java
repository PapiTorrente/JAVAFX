package fes.aragon.modelo;

import java.sql.Date;

public class Registros {
	private Socios socio;
	private int matriculaSocio;
	private Patrones patron;
	private int matriculaPatron;
	private Date salidaBarcos;
	private Date llegadaBarcos;
	
	
	public Registros() {
		// TODO Auto-generated constructor stub
	}

	public Socios getSocio() {
		return socio;
	}

	public void setSocio(Socios socio) {
		this.socio = socio;
	}
	
	public int getMatriculaSocio(){
		return this.getSocio().getMatriculaSocio();
	}

	public void setMatriculaSocio(int matriculaSocio) {
		this.socio.setMatriculaSocio(matriculaSocio);
	}
	
	public Patrones getPatron() {
		return patron;
	}

	public void setPatron(Patrones patron) {
		this.patron = patron;
	}
	
	public int getMatriculaPatron(){
		return this.getPatron().getMatriculaPatron();
	}

	public void setMatriculaPatron(int matriculaPatron) {
		this.patron.setMatriculaPatron(matriculaPatron);
	}

	public Date getSalidaBarcos() {
		return salidaBarcos;
	}

	public void setSalidaBarcos(Date salidaBarcos) {
		this.salidaBarcos = salidaBarcos;
	}

	public Date getLlegadaBarcos() {
		return llegadaBarcos;
	}

	public void setLlegadaBarcos(Date llegadaBarcos) {
		this.llegadaBarcos = llegadaBarcos;
	}

}
