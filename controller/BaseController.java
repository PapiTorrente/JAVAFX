package fes.aragon.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fes.aragon.modelo.Barcos;
import fes.aragon.modelo.Destinos;
import fes.aragon.modelo.Patrones;
import fes.aragon.modelo.Socios;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BaseController {
	protected static String nombreVentana;
	@SuppressWarnings("rawtypes")
	protected static TableView tabla;
	protected static int indice;
	protected static Map<Integer, Barcos> mapBarcos = new HashMap<>();
	protected static Map<Integer, Socios> mapSocios = new HashMap<>();
	protected static Map<Integer, Patrones> mapPatrones = new HashMap<>();
	protected static Map<Integer, Destinos> mapDestinos = new HashMap<>();
	protected static InicioController tablaController = new InicioController();
	
	@SuppressWarnings("rawtypes")
	public void eliminarFila(TableView tabla, int indice) {
		tabla.getItems().remove(indice);
	}

	public void nuevaVentana(String archivo) {
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("/fes/aragon/fxml/" + archivo + ".fxml"));
			Scene scene = new Scene(root);
			Stage escenario = new Stage();
			escenario.setScene(scene);
			escenario.initModality(Modality.APPLICATION_MODAL);
			escenario.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ventanaEmergente(String titulo, String encabezado, String contenido) {
		Alert alerta;
		alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle(titulo);
		alerta.setHeaderText(encabezado);
		alerta.setContentText(contenido);
		alerta.showAndWait();
	}

	public void cerrarVentana(Button buton) {
		Stage stage = (Stage) buton.getScene().getWindow();
		stage.close();
	}
	
}