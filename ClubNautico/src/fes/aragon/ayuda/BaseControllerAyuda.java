package fes.aragon.ayuda;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fes.aragon.modelo.Socios;
import fes.aragon.recovery.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class BaseControllerAyuda {
	protected static String nombreVentana;
	@SuppressWarnings("rawtypes")
	protected static TableView tabla;
	protected static int indice;
	protected static ArrayList<Button> btn = new ArrayList<>();
	protected static ObservableList<Socios> listaSociosBandera = null;
	protected static boolean modificacionValida = false;
	protected static boolean modificarGerente = true;
	protected static boolean modificarAuto = true;
	protected boolean rfcValido = true;
	protected boolean correoValido = true;
	protected boolean telefonoValido = true;
	protected boolean precioValido = true;
	protected boolean kmValido = true;
	protected boolean edadValido = false;
	protected boolean pinValido = true;
	protected boolean contraseniaValido = true;
	protected boolean usuarioValido = true;

	private String[] expresiones = {"(\\d+)(\\.\\d{1,2})","(\\d+)(\\.\\d{1,2})", "(\\w){13}",
			"^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", "(\\d){10}", "(\\d){2}", 
			"^[1-9]{1}[0-9]{2}[1-9]{1}$","^[A-Z][A-Za-z0-9]{4,7}$","^[A-Za-z][A-Za-z0-9]{5,12}$"};
	
	@SuppressWarnings("rawtypes")
	public void eliminarFila(TableView tabla, int indice) {
		tabla.getItems().remove(indice);
	}

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
			
			if(error == TipoError.CORREO) {
				 this.correoValido = match.matches();
			 }
			if(error == TipoError.TELEFONO) {
				 this.telefonoValido = match.matches();
			 }
			if(error == TipoError.RFC) {
				 this.rfcValido = match.matches();
			 }
			if(error == TipoError.PRECIO) {
				 this.precioValido = match.matches();
			 }
			if(error == TipoError.KM) {
				 this.kmValido = match.matches();
			 }
			if(error == TipoError.EDAD) {
				 this.edadValido = match.matches();
			 }
			if(error == TipoError.PIN) {
				 this.pinValido = match.matches();
			 }
			if(error == TipoError.CONTRASENIA) {
				 this.contraseniaValido = match.matches();
			 }
			if(error == TipoError.USUARIO) {
				 this.usuarioValido = match.matches();
			 }
		});
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