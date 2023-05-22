package fes.aragon.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListaDeRegistros {
	private static ListaDeRegistros objeto = new ListaDeRegistros();
	private ObservableList<Socios> grupoSocios = FXCollections.observableArrayList();
	private ObservableList<Patrones> grupoPatrones = FXCollections.observableArrayList();
	private ObservableList<Barcos> grupoBarcos = FXCollections.observableArrayList();
	private ObservableList<Destinos> grupoDestinos = FXCollections.observableArrayList();
	private ObservableList<Registros> grupoRegistros = FXCollections.observableArrayList();
	
	public ListaDeRegistros() {
	}

	public ObservableList<Socios> getGrupoSocios() {
		return grupoSocios;
	}

	public void setGrupoSocios(ObservableList<Socios> grupoSocios) {
		this.grupoSocios = grupoSocios;
	}

	public static ListaDeRegistros getObjeto() {
		return objeto;
	}

	public ObservableList<Patrones> getGrupoPatrones() {
		return grupoPatrones;
	}

	public void setGrupoPatrones(ObservableList<Patrones> grupoPatrones) {
		this.grupoPatrones = grupoPatrones;
	}

	public ObservableList<Barcos> getGrupoBarcos() {
		return grupoBarcos;
	}

	public void setGrupoBarcos(ObservableList<Barcos> grupoBarcos) {
		this.grupoBarcos = grupoBarcos;
	}

	public ObservableList<Destinos> getGrupoDestinos() {
		return grupoDestinos;
	}

	public void setGrupoDestinos(ObservableList<Destinos> grupoDestinos) {
		this.grupoDestinos = grupoDestinos;
	}

	public ObservableList<Registros> getGrupoRegistros() {
		return grupoRegistros;
	}

	public void setGrupoRegistros(ObservableList<Registros> grupoRegistros) {
		this.grupoRegistros = grupoRegistros;
	}
}
