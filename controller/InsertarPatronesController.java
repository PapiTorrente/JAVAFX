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
    	patron.setNombresPatron(this.txtNombres.getText());
    	patron.setApPaternoPatron(this.txtAPaterno.getText());
    	patron.setApMaternoPatron(this.txtAMaterno.getText());
    	patron.setEsPatronDueno(this.cmbEsDueno.getValue());
    	patron.setFechaNacimientoPatron(Date.valueOf(this.dtpFechaNacimiento.getValue()));
    	patron.setNoCelularPatron(this.txtNumeroCelular.getText());
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
