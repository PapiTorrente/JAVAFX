package fes.aragon.modelo;

public class Registros{
	private Barcos barco;
	private Socios socio;
	private Patrones patron;
	private Destinos destino;
	private String fechaSalida;
	private String fechaLlegada;
	
	public Registros() {
		// TODO Auto-generated constructor stub
	}

	public Registros(char ok) {
		this.socio = new Socios();
		this.barco = new Barcos();
		this.destino = new Destinos();
		this.patron = new Patrones();
	}



	public Barcos getBarco() {
		return barco;
	}

	public void setBarco(Barcos barco) {
		this.barco = barco;
	}
	
	public int getNoSerieBarco() {
		return this.barco.getNoSerieBarco();
	}

	public Socios getSocio() {
		return socio;
	}

	public void setSocio(Socios socio) {
		this.socio = socio;
	}

	public int getMatriculaSocio() {
		return this.socio.getMatriculaSocio();
	}

	public Patrones getPatron() {
		return patron;
	}

	public void setPatron(Patrones patron) {
		this.patron = patron;
	}
	
	public int getMatriculaPatron() {
		return this.patron.getMatriculaPatron();
	}

	public Destinos getDestino() {
		return destino;
	}

	public void setDestino(Destinos destino) {
		this.destino = destino;
	}
	
	public int getPuertoDest() {
		return this.destino.getNoSerieDestino();
	}
	
	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(String fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

}
