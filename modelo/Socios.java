package fes.aragon.modelo;

import java.sql.Date;

public class Socios {
	private int matriculaSocio;
	private String nombresSocios;
	private String apPaternoSocio;
	private String apMaternoSocio;
	private boolean esSocioDueno;
	private Date fechaNacimientoSocio;
	private String noCelularSocio;
	
	public Socios() {
		
	}
	
	public Socios(int matriculaSocio, String nombresSocios, String apPaternoSocio, String apMaternoSocio,
			boolean esSocioDueno, Date fechaNacimientoSocio, String noCelularSocio) {
		super();
		this.matriculaSocio = matriculaSocio;
		this.nombresSocios = nombresSocios;
		this.apPaternoSocio = apPaternoSocio;
		this.apMaternoSocio = apMaternoSocio;
		this.esSocioDueno = esSocioDueno;
		this.fechaNacimientoSocio = fechaNacimientoSocio;
		this.noCelularSocio = noCelularSocio;
	}

	public int getMatriculaSocio() {
		return matriculaSocio;
	}

	public void setMatriculaSocio(int matriculaSocio) {
		this.matriculaSocio = matriculaSocio;
	}

	public String getNombresSocios() {
		return nombresSocios;
	}

	public void setNombresSocios(String nombresSocios) {
		this.nombresSocios = nombresSocios;
	}

	public String getApPaternoSocio() {
		return apPaternoSocio;
	}

	public void setApPaternoSocio(String apPaternoSocio) {
		this.apPaternoSocio = apPaternoSocio;
	}

	public String getApMaternoSocio() {
		return apMaternoSocio;
	}

	public void setApMaternoSocio(String apMaternoSocio) {
		this.apMaternoSocio = apMaternoSocio;
	}

	public boolean isEsSocioDueno() {
		return esSocioDueno;
	}

	public void setEsSocioDueno(boolean esSocioDueno) {
		this.esSocioDueno = esSocioDueno;
	}

	public Date getFechaNacimientoSocio() {
		return fechaNacimientoSocio;
	}

	public void setFechaNacimientoSocio(Date fechaNacimientoSocio) {
		this.fechaNacimientoSocio = fechaNacimientoSocio;
	}

	public String getNoCelularSocio() {
		return noCelularSocio;
	}

	public void setNoCelularSocio(String noCelularSocio) {
		this.noCelularSocio = noCelularSocio;
	}

	@Override
	public String toString() {
		return nombresSocios;
	}
	
	
}
