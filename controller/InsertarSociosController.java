package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Socios;
import fes.aragon.modelo.TipoError;
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
	
	String mensaje = "";
	
    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnSalir;

    @FXML
    private ChoiceBox<String> cmbEsDueno;

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

    @FXML
    void insertarSocios(ActionEvent event) {
    	if(this.verificar()) {
    	socio.setNombresSocios(this.txtNombres.getText());
    	socio.setApPaternoSocio(this.txtAPaterno.getText());
    	socio.setApMaternoSocio(this.txtAMaterno.getText());
    	socio.setEsSocioDueno(Boolean.valueOf(this.cmbEsDueno.getValue()));
    	socio.setFechaNacimientoSocio(Date.valueOf(this.dtpFechaNacimiento.getValue()));
    	socio.setNoCelularSocio(this.txtNumeroCelular.getText());
    	try {
			socio.setMatriculaSocio(this.cn.insertarSocios(socio));
			ListaDeRegistros.getObjeto().getGrupoSocios().set(
					ListaDeRegistros.getObjeto().getGrupoSocios().size()-1, socio);
			this.tabla = null;
	    	this.indice = -1;
	    	this.telefonoValido = true;
	    	this.nombreValido = true;
	    	this.aPaternoValido = true;
	    	this.aMaternoValido = true;
	    	this.cn.cerrarConexion();
		} catch (SQLException e) {
		}
    	this.cerrarVentana(this.btnInsertar);
    	this.tabla = null;
    	this.indice = -1;
    	this.telefonoValido = true;
    	this.nombreValido = true;
    	this.aPaternoValido = true;
    	this.aMaternoValido = true;
    	this.cn.cerrarConexion();
    	}else {
    		this.ventanaEmergente("Error", "Error de guardado", this.mensaje);
    		this.mensaje = "";
    	}
    }

    @FXML
    void cerrarVentana(ActionEvent event) {
		ListaDeRegistros.getObjeto().getGrupoSocios().remove(
				ListaDeRegistros.getObjeto().getGrupoSocios().size()-1);
    	this.cerrarVentana(this.btnSalir);
    	this.tabla = null;
    	this.indice = -1;
    	this.telefonoValido = true;
    	this.nombreValido = true;
    	this.aPaternoValido = true;
    	this.aMaternoValido = true;
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
		this.cmbEsDueno.getItems().addAll("Selecciona una opción:", "true", "false");
		this.cmbEsDueno.getSelectionModel().select(0);
		this.socio = ListaDeRegistros.getObjeto().getGrupoSocios().get(
				ListaDeRegistros.getObjeto().getGrupoSocios().size()-1);
		this.verificarEntrada(txtNumeroCelular, TipoError.TELEFONO);
		this.verificarEntrada(txtNombres, TipoError.NOMBRE);
		this.verificarEntrada(txtAPaterno, TipoError.APELLIDOPATERNO);
		this.verificarEntrada(txtAMaterno, TipoError.APELLIDOMATERNO);
	}
	
	private boolean verificar() {
		boolean valido = true;

		if ((this.txtNombres.getText() == null)
				|| (this.txtNombres.getText() != null && this.txtNombres.getText().isEmpty())) {
			this.mensaje += "- El nombre no es valido, es vacio.\n";
			valido = false;
		}
		
		if (this.txtNombres.getText().length() <= 3) {
			this.mensaje += "- El nombre no es valido, debe \n tener al menos cuatro caracteres.\n";
			valido = false;
		}
		
		if (this.txtNombres.getText().length() > 40) {
			this.mensaje += "- El nombre no es valido, debe tener máximo 40 caracteres.\n";
			valido = false;
		}
		
		if (!this.nombreValido) {
			this.mensaje += "- El nombre del socio solo debe contener letras.\n";
			valido = false;
		}
		
		if ((this.txtAPaterno.getText() == null)
				|| (this.txtAPaterno.getText() != null && this.txtAPaterno.getText().isEmpty())) {
			this.mensaje += "- El apellido paterno no es valido, es vacio.\n";
			valido = false;
		}
		
		if (this.txtAPaterno.getText().length() <= 2) {
			this.mensaje += "- El apellido paterno no es valido, debe \n tener al menos tres caracteres.\n";
			valido = false;
		}
		
		if (this.txtAPaterno.getText().length() > 20) {
			this.mensaje += "- El apellido paterno no es valido, debe \n tener máximo 20 caracteres.\n";
			valido = false;
		}
		
		if (!this.aPaternoValido) {
			this.mensaje += "- El apellido paterno del socio solo debe contener letras.\n";
			valido = false;
		}
		
		if ((this.txtAMaterno.getText() == null)
				|| (this.txtAMaterno.getText() != null && this.txtAMaterno.getText().isEmpty())) {
			this.mensaje += "- El apellido materno no es valido, es vacio.\n";
			valido = false;
		}
		
		if (this.txtAMaterno.getText().length() <= 2) {
			this.mensaje += "- El apellido materno no es valido, debe \n tener al menos tres caracteres.\n";
			valido = false;
		}
		
		if (this.txtAMaterno.getText().length() > 20) {
			this.mensaje += "- El apellido materno no es valido, debe \n tener máximo 20 caracteres.\n";
			valido = false;
		}
		
		if (!this.aMaternoValido) {
			this.mensaje += "- El apellido materno del socio solo debe contener letras.\n";
			valido = false;
		}
		
		if((this.cmbEsDueno.getSelectionModel().getSelectedIndex() == 0) || 
				(this.cmbEsDueno.getSelectionModel().getSelectedIndex() == -1)) {
			this.mensaje += "- Seleccione una opción sobre el dueño del barco.\n";
			valido = false;
		}
		
		if ((this.dtpFechaNacimiento.getValue() == null)
				|| (this.dtpFechaNacimiento.getValue() != null && 
				String.valueOf(this.dtpFechaNacimiento.getValue()).equals(""))) {
			this.mensaje += "- La fecha de nacimiento no es valido, es vacio.\n";
			valido = false;
		}
		
		if ((this.txtNumeroCelular.getText() == null)
				|| (this.txtNumeroCelular.getText() != null && this.txtNumeroCelular.getText().isEmpty())) {
			this.mensaje += "- El número celular no es valido, es vacio.\n";
			valido = false;
		}
		

		if (!this.telefonoValido) {
			this.mensaje += "- El número celuar del socio debe contener diez dígitos.\n";
			valido = false;
		}
		
		return valido;
	}

}
