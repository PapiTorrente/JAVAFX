package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Patrones;
import fes.aragon.recovery.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class InsertarPatronesController extends BaseController implements Initializable{
	
	private Patrones patron;
	
	private Conexion cn = this.conexionSQL();
	
    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnSalir;

    @FXML
    private ChoiceBox<Boolean> cmbEsDueno;

    @FXML
    private DatePicker dtpFechaNacimiento;

    @FXML
    private TextField txtAMaterno;

    @FXML
    private TextField txtAPaterno;

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtNumeroCelular;
    
    public Conexion conexionSQL() {
		try {
			return new Conexion();
		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {
		}
		return null;
	}

    @FXML
    void insertarPatrones(ActionEvent event) {
    	
    	/* Control de nombres */
    	if(this.txtNombres.getLength() > 2 && this.txtNombres.getLength() < 41) {
        	patron.setNombresPatron(this.txtNombres.getText());
    	} else {
    		if(this.txtNombres.getLength() <= 2) {
    			Alert alerta=new Alert(Alert.AlertType.ERROR);
    			alerta.setTitle("¡Cuidado!");
    			alerta.setHeaderText("Hay un error con la inserción del dato.");
    			alerta.setContentText("No puedes agregar este patrón porque el nombre debe ser mayor a dos carácteres.");
    			alerta.showAndWait();
    		}
    		if(this.txtNombres.getLength() >= 31) {
    			Alert alerta=new Alert(Alert.AlertType.ERROR);
    			alerta.setTitle("¡Cuidado!");
    			alerta.setHeaderText("Hay un error con la inserción del dato.");
    			alerta.setContentText("No puedes agregar este patrón porque el nombre debe ser menor a cuarenta y un carácteres.");
    			alerta.showAndWait();
    		}
    	}
    	
    	/* Control de apellidos paternos */
    	if(this.txtAPaterno.getLength() > 2 && this.txtAPaterno.getLength() < 41) {
        	patron.setApPaternoPatron(this.txtAPaterno.getText());

    	} else {
    		if(this.txtAPaterno.getLength() <= 2) {
    			Alert alerta=new Alert(Alert.AlertType.ERROR);
    			alerta.setTitle("¡Cuidado!");
    			alerta.setHeaderText("Hay un error con la inserción del dato.");
    			alerta.setContentText("No puedes agregar este patrón porque el apellido debe ser mayor a dos carácteres.");
    			alerta.showAndWait();
    		}
    		if(this.txtAPaterno.getLength() >= 31) {
    			Alert alerta=new Alert(Alert.AlertType.ERROR);
    			alerta.setTitle("¡Cuidado!");
    			alerta.setHeaderText("Hay un error con la inserción del dato.");
    			alerta.setContentText("No puedes agregar este patrón porque el apellido debe ser menor a cuarenta y un carácteres.");
    			alerta.showAndWait();
    		}
    	}
    	
    	/* Control de apellidos maternos */
    	if(this.txtAMaterno.getLength() > 2 && this.txtAMaterno.getLength() < 41) {
        	patron.setApMaternoPatron(this.txtAMaterno.getText());
    	} else {
    		if(this.txtAMaterno.getLength() <= 2) {
    			Alert alerta=new Alert(Alert.AlertType.ERROR);
    			alerta.setTitle("¡Cuidado!");
    			alerta.setHeaderText("Hay un error con la inserción del dato.");
    			alerta.setContentText("No puedes agregar este patrón porque el apellido debe ser mayor a dos carácteres.");
    			alerta.showAndWait();
    		}
    		if(this.txtAMaterno.getLength() >= 31) {
    			Alert alerta=new Alert(Alert.AlertType.ERROR);
    			alerta.setTitle("¡Cuidado!");
    			alerta.setHeaderText("Hay un error con la inserción del dato.");
    			alerta.setContentText("No puedes agregar este patrón porque el apellido debe ser menor a cuarenta y un carácteres.");
    			alerta.showAndWait();
    		}
    	}
    	
    	patron.setEsPatronDueno(this.cmbEsDueno.getValue());
    	patron.setFechaNacimientoPatron(Date.valueOf(this.dtpFechaNacimiento.getValue()));
    	
    	/* Control de numero celular */
    	if(this.txtNumeroCelular.getLength() > 9 && this.txtNumeroCelular.getLength() < 11) {
        	patron.setNoCelularPatron(this.txtNumeroCelular.getText());
    	} else {
			Alert alerta=new Alert(Alert.AlertType.ERROR);
			alerta.setTitle("¡Cuidado!");
			alerta.setHeaderText("Hay un error con la inserción del dato.");
			alerta.setContentText("No puedes agregar este número porque debe ser de 10 dígitos.");
			alerta.showAndWait();
    	}
    	
    	try {
			patron.setMatriculaPatron(this.cn.insertarPatrones(patron));
			ListaDeRegistros.getObjeto().getGrupoPatrones().set(
					ListaDeRegistros.getObjeto().getGrupoPatrones().size()-1, patron);
		} catch (SQLException e) {
		}
    	this.cerrarVentana(this.btnInsertar);
    }

    @FXML
    void cerrarVentana(ActionEvent event) {
    	this.cerrarVentana(this.btnSalir);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.cmbEsDueno.getItems().addAll(true,false);
		this.cmbEsDueno.getSelectionModel().select(0);
		this.patron = ListaDeRegistros.getObjeto().getGrupoPatrones().get(
				ListaDeRegistros.getObjeto().getGrupoPatrones().size()-1);
	}

}
