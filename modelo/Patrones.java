package fes.aragon.modelo;

import java.sql.Date;

public class Patrones {
	private int matriculaPatron;
	private String nombresPatron;
	private String apPaternoPatron;
	private String apMaternoPatron;
	private boolean esPatronDueno;
	private Date fechaNacimientoPatron;
	private String noCelularPatron;
	
	public Patrones() {
		// TODO Auto-generated constructor stub
	}

	public int getMatriculaPatron() {
		return matriculaPatron;
	}

	public void setMatriculaPatron(int matriculaPatron) {
		this.matriculaPatron = matriculaPatron;
	}

	public String getNombresPatron() {
		return nombresPatron;
	}

	public void setNombresPatron(String nombresPatron) {
		this.nombresPatron = nombresPatron;
	}

	public String getApPaternoPatron() {
		return apPaternoPatron;
	}

	public void setApPaternoPatron(String apPaternoPatron) {
		this.apPaternoPatron = apPaternoPatron;
	}

	public String getApMaternoPatron() {
		return apMaternoPatron;
	}

	public void setApMaternoPatron(String apMaternoPatron) {
		this.apMaternoPatron = apMaternoPatron;
	}

	public boolean isEsPatronDueno() {
		return esPatronDueno;
	}

	public void setEsPatronDueno(boolean esPatronDueno) {
		this.esPatronDueno = esPatronDueno;
	}

	public Date getFechaNacimientoPatron() {
		return fechaNacimientoPatron;
	}

	public void setFechaNacimientoPatron(Date fechaNacimientoPatron) {
		this.fechaNacimientoPatron = fechaNacimientoPatron;
	}

	public String getNoCelularPatron() {
		return noCelularPatron;
	}

	public void setNoCelularPatron(String noCelularPatron) {
		this.noCelularPatron = noCelularPatron;
	}


	@Override
	public String toString() {
		return nombresPatron;
	}
	
}
