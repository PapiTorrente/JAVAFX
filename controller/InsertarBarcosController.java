package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.Barcos;
import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.recovery.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class InsertarBarcosController extends BaseController implements Initializable{
	
	private Barcos b;
	
	private Conexion cn = this.conexionSQL();
	
    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnSalir;

    @FXML
    private ChoiceBox<String> cmbCuota;

    @FXML
    private TextField txtNombresDeBarco;

    @FXML
    private TextField txtNumeroAmarre;

    @FXML
    void cerrarVentana(ActionEvent event) {
    	this.cerrarVentana(this.btnSalir);
    	this.tabla = null;
    	this.indice = -1;
    	this.cn.cerrarConexion();
    }

    @FXML
    void insertarBarco(ActionEvent event) {
    	if(this.txtNombresDeBarco.getLength() > 2 && this.txtNombresDeBarco.getLength() < 41) {
        	this.b.setNomBarco(this.txtNombresDeBarco.getText());
    	} else {
    		if(this.txtNombresDeBarco.getLength() <= 2) {
    			Alert alerta=new Alert(Alert.AlertType.ERROR);
    			alerta.setTitle("¡Cuidado!");
    			alerta.setHeaderText("Hay un error con la inserción del dato.");
    			alerta.setContentText("No puedes nombrar así el barco porque el nombre debe ser mayor a dos carácteres.");
    			alerta.showAndWait();
    		}
    		if(this.txtNombresDeBarco.getLength() >= 41) {
    			Alert alerta=new Alert(Alert.AlertType.ERROR);
    			alerta.setTitle("¡Cuidado!");
    			alerta.setHeaderText("Hay un error con la inserción del dato.");
    			alerta.setContentText("No puedes nombrar así el barco porque el nombre debe ser menor a cuarenta y un carácteres.");
    			alerta.showAndWait();
    		}
    	}
    	
    	try {
			this.b.setNoSerieBarco(this.cn.insertarBarcos(this.b));
			ListaDeRegistros.getObjeto().getGrupoBarcos().set(
					ListaDeRegistros.getObjeto().getGrupoBarcos().size()-1, this.b);
		} catch (SQLException e) {
		}
    	this.cerrarVentana(this.btnInsertar);
    	this.tabla = null;
    	this.indice = -1;
    	this.cn.cerrarConexion();
    }
    
    public Conexion conexionSQL() {
		try {
			return new Conexion();
		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {
		}
		return null;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.b = ListaDeRegistros.getObjeto().getGrupoBarcos().get(
				ListaDeRegistros.getObjeto().getGrupoBarcos().size()-1);
		this.cmbCuota.getItems().addAll("$1","$2","$3");
		this.cmbCuota.getSelectionModel().select(0);
		
	}

}
