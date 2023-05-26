package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.Destinos;
import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Socios;
import fes.aragon.recovery.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InsertarDestinosController extends BaseController implements Initializable{
	
	private Destinos d;
	
	private Conexion cn = this.conexionSQL();
	
    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnSalir;

    @FXML
    private TextField txtDestino;

    @FXML
    void cerrarVentana(ActionEvent event) {
    	this.cerrarVentana(btnSalir);
    	this.tabla = null;
    	this.indice = -1;
    	this.cn.cerrarConexion();
    }

    @FXML
    void insertarDestino(ActionEvent event) {
    	if(this.txtDestino.getLength() > 2 && this.txtDestino.getLength() < 31) {
    		this.d.setPuertoDest(this.txtDestino.getText());
    	} else {
    		if(this.txtDestino.getLength() <= 2) {
    			Alert alerta=new Alert(Alert.AlertType.ERROR);
    			alerta.setTitle("¡Cuidado!");
    			alerta.setHeaderText("Hay un error con la inserción del dato.");
    			alerta.setContentText("No puedes agregar este destino porque el nombre debe ser mayor a dos carácteres.");
    			alerta.showAndWait();
    		}
    		if(this.txtDestino.getLength() >= 31) {
    			Alert alerta=new Alert(Alert.AlertType.ERROR);
    			alerta.setTitle("¡Cuidado!");
    			alerta.setHeaderText("Hay un error con la inserción del dato.");
    			alerta.setContentText("No puedes agregar este destino porque el nombre debe ser menor a treinta y un carácteres.");
    			alerta.showAndWait();
    		}
    	}
    	
    	try {
			this.d.setNoSerieDestino(this.cn.insertarDestinos(this.d));
			ListaDeRegistros.getObjeto().getGrupoDestinos().set(
					ListaDeRegistros.getObjeto().getGrupoDestinos().size()-1, this.d);
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
		this.d = ListaDeRegistros.getObjeto().getGrupoDestinos().get(
				ListaDeRegistros.getObjeto().getGrupoDestinos().size()-1);
	}

}
