package fes.aragon.modelo;

public class Destinos {
	private int noSerieDestino;
	private String puertoDest;
	
	public Destinos() {
		// TODO Auto-generated constructor stub
	}

	public int getNoSerieDestino() {
		return noSerieDestino;
	}

	public void setNoSerieDestino(int noSerieDestino) {
		this.noSerieDestino = noSerieDestino;
	}

	public String getPuertoDest() {
		return puertoDest;
	}

	public void setPuertoDest(String puertoDest) {
		this.puertoDest = puertoDest;
	}

	@Override
	public String toString() {
		return puertoDest;
	}
}
