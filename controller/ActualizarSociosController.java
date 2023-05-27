package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
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

public class ActualizarSociosController extends BaseController implements Initializable{
	
	private Conexion cn = this.conexionSQL();
	
	private Socios s;
	
	private Date d;
	
	String mensaje = "";
	
    @FXML
    private Button btnActualizar;

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
    private TextField txtID;

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtNumeroCelular;

    @FXML
    void actualizarSocios(ActionEvent event) throws SQLException {
    	if(this.verificar()) {
    	this.actualizarSocio();
    	ListaDeRegistros.getObjeto().getGrupoSocios().set(this.indice, this.s);
    	this.cn.llenarTablaRegistros();
    	BaseController.tablaController.getTblTabla().setItems(ListaDeRegistros.getObjeto().getGrupoRegistros());
    	this.cerrarVentana(this.btnActualizar);
    	this.tabla = null;
    	this.indice = -1;
    	this.telefonoValido = true;
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
    	this.cerrarVentana(btnSalir);
    	this.tabla = null;
    	this.indice = -1;
    	this.telefonoValido = true;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.s = ListaDeRegistros.getObjeto().getGrupoSocios().get(indice);
		d = this.s.getFechaNacimientoSocio();
		this.txtID.setDisable(true);
		this.cmbEsDueno.getItems().addAll("Selecciona una opción:", "true", "false");
		this.txtNombres.setText(this.s.getNombresSocios());
		this.txtAPaterno.setText(this.s.getApPaternoSocio());
		this.txtAMaterno.setText(this.s.getApMaternoSocio());
		this.txtID.setText(String.valueOf(this.s.getMatriculaSocio()));
		this.txtNumeroCelular.setText(this.s.getNoCelularSocio());
		this.cmbEsDueno.setValue(String.valueOf(this.s.isEsSocioDueno()));
		this.dtpFechaNacimiento.setValue(this.d.toLocalDate());
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
	
	public void actualizarSocio() throws SQLException {
		String nombre = "nombres_socio";
		String aPaterno = "ap_paterno_socio";
		String aMaterno = "ap_materno_socio";
		String esSocio = "es_socio_dueno";
		String fechaNacimiento = "fecha_nacimiento_socio";
		String numeroCelular = "no_celular_socio";
		String queryUno = "update socios set ";
		String queryDos = " where matricula_socio='" + this.s.getMatriculaSocio() + "'";
		boolean nombreBandera = false;
		boolean aPaternoBandera = false;
		boolean aMaternoBandera = false;
		boolean esSocioBandera = false;
		boolean fechaNacimientoBandera = false;
		@SuppressWarnings("unused")
		boolean numeroCelularBandera = false;
		 
		if(!(s.getNombresSocios().equals(this.txtNombres.getText()))) {
			queryUno = queryUno + nombre + "='" + this.txtNombres.getText() + "'";
			this.s.setNombresSocios(this.txtNombres.getText());
			nombreBandera = true;
		}
		
		if(!(s.getApPaternoSocio().equals(this.txtAPaterno.getText()))) {
			if(nombreBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + aPaterno + "='" + this.txtAPaterno.getText() + "'";
			this.s.setApPaternoSocio(this.txtAPaterno.getText());
			aPaternoBandera = true;
		}
		
		if(!(s.getApMaternoSocio().equals(this.txtAMaterno.getText()))) {
			if(nombreBandera | aPaternoBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + aMaterno + "='" + this.txtAMaterno.getText() + "'";
			this.s.setApMaternoSocio(this.txtAMaterno.getText());
			aMaternoBandera = true;
		}
		
		if(!(this.cmbEsDueno.getValue().equals(String.valueOf(this.s.isEsSocioDueno()))) ||
				!(this.cmbEsDueno.getSelectionModel().getSelectedIndex() == 0) || 
				!(this.cmbEsDueno.getSelectionModel().getSelectedIndex() == -1)) {
			if(nombreBandera | aPaternoBandera | aMaternoBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + esSocio + "=" + this.cmbEsDueno.getValue();
			this.s.setEsSocioDueno(Boolean.valueOf(this.cmbEsDueno.getValue()));
			esSocioBandera = true;
		}
		
		if(!(this.d.toLocalDate().equals(this.dtpFechaNacimiento.getValue()))) {
			if(nombreBandera | aPaternoBandera | aMaternoBandera | esSocioBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + fechaNacimiento + "='" + this.dtpFechaNacimiento.getValue() + "'";
			this.s.setFechaNacimientoSocio(Date.valueOf(this.dtpFechaNacimiento.getValue()));
			this.d = this.s.getFechaNacimientoSocio();
			fechaNacimientoBandera = true;
		}
		
		if(!(s.getNoCelularSocio().equals(this.txtNumeroCelular.getText()))) {
			if(nombreBandera | aPaternoBandera | aMaternoBandera | esSocioBandera | fechaNacimientoBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + numeroCelular + "='" + this.txtNumeroCelular.getText() + "'";
			this.s.setNoCelularSocio(this.txtNumeroCelular.getText());
			numeroCelularBandera = true;
		}
		
		if(nombreBandera | aPaternoBandera | aMaternoBandera | esSocioBandera | fechaNacimientoBandera | numeroCelularBandera) {
		queryUno = queryUno + queryDos;
		PreparedStatement pr = this.cn.conexion.prepareStatement(queryUno);
		pr.execute();
		pr.close();
		}
	}
	
}
