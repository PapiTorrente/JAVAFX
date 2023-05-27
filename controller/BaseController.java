package fes.aragon.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fes.aragon.modelo.Barcos;
import fes.aragon.modelo.Destinos;
import fes.aragon.modelo.Patrones;
import fes.aragon.modelo.Socios;
import fes.aragon.modelo.TipoError;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BaseController {
	protected static String nombreVentana;
	@SuppressWarnings("rawtypes")
	protected static TableView tabla;
	protected static int indice;
	protected static Map<Integer, Barcos> mapBarcos = new HashMap<>();
	protected static Map<Integer, Socios> mapSocios = new HashMap<>();
	protected static Map<Integer, Patrones> mapPatrones = new HashMap<>();
	protected static Map<Integer, Destinos> mapDestinos = new HashMap<>();
	protected static Map<String, String> mapFechasSalida = new HashMap<>();
	protected static Map<String, String> mapFechasLlegada = new HashMap<>();
	protected static InicioController tablaController = new InicioController();
	protected boolean telefonoValido =true;
	protected boolean nombreValido = true;
	protected boolean aPaternoValido = true;
	protected boolean aMaternoValido = true;
	protected boolean noAmarreValido = true;
	
	private String[] expresiones = {"(\\d){10}",//Núm Celular
			"^[A-Za-zÀ-ÿ\\u00f1\\u00d1 ]+$",//nombre
			"^[A-Za-zÀ-ÿ\\\\u00f1\\\\u00d1 ]+$",//A_paterno
			"^[A-Za-zÀ-ÿ\\\\u00f1\\\\u00d1 ]+$",//A_materno
			"^[A-Za-zÀ-ÿ\\\\u00f1\\\\u00d1 ]+$",//nombre_barco
			"[0-9]*",//no_amarre
			"^[A-Za-zÀ-ÿ\\\\u00f1\\\\u00d1 ]+$"};//Nombre_destino
	
	public void verificarEntrada(TextField caja, TipoError error) {
		caja.textProperty().addListener(evento -> {
			String text = caja.getText();
			if (text == null) {
				text = "";
			}
			String patron = expresiones[error.ordinal()];
			Pattern pt = Pattern.compile(patron);
			Matcher match = pt.matcher(text);
			caja.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), !match.matches());
			
			if(error == TipoError.TELEFONO) {
				 this.telefonoValido = match.matches();
			 }
			if(error == TipoError.NOMBRE) {
				 this.nombreValido = match.matches();
			 }
			if(error == TipoError.APELLIDOPATERNO) {
				 this.aPaternoValido = match.matches();
			 }
			if(error == TipoError.APELLIDOMATERNO) {
				 this.aMaternoValido = match.matches();
			 }
			if(error == TipoError.NOMBREBARCO) {
				 this.nombreValido = match.matches();
			 }
			
			if(error == TipoError.NOAMARRE) {
				 this.noAmarreValido = match.matches();
			 }
			
			if(error == TipoError.NOMBREDESTINO) {
				 this.nombreValido = match.matches();
			 }
		});
	}
	
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
			escenario.initStyle(StageStyle.UNDECORATED);
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