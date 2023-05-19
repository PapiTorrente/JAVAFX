package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Socios;
import fes.aragon.recovery.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class InsertarSociosController extends BaseController implements Initializable{
	
	private Socios socio;
	
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
    void insertarSocios(ActionEvent event) {
    	socio.setNombresSocios(this.txtNombres.getText());
    	socio.setApPaternoSocio(this.txtAPaterno.getText());
    	socio.setApMaternoSocio(this.txtAMaterno.getText());
    	socio.setEsSocioDueno(this.cmbEsDueno.getValue());
    	socio.setFechaNacimientoSocio(Date.valueOf(this.dtpFechaNacimiento.getValue()));
    	socio.setNoCelularSocio(this.txtNumeroCelular.getText());
    	try {
			socio.setMatriculaSocio(this.cn.insertarSocios(socio));
			ListaDeRegistros.getObjeto().getGrupoSocios().set(
					ListaDeRegistros.getObjeto().getGrupoSocios().size()-1, socio);
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
		this.socio = ListaDeRegistros.getObjeto().getGrupoSocios().get(
				ListaDeRegistros.getObjeto().getGrupoSocios().size()-1);
	}

}
