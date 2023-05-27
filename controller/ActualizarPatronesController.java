package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Patrones;
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

public class ActualizarPatronesController extends BaseController implements Initializable{
	
	private Conexion cn = this.conexionSQL();
	
	private Patrones p;
	
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
    private TextField txtIDPatrones;

    @FXML
    private TextField txtNombresPatron;

    @FXML
    private TextField txtNumeroCelular;

    @FXML
    void actualizarPatrones(ActionEvent event) throws SQLException {
    	if(this.verificar()) {
    	this.actualizarPatron();
    	ListaDeRegistros.getObjeto().getGrupoPatrones().set(this.indice, this.p);
    	this.cn.llenarTablaRegistros();
    	BaseController.tablaController.getTblTabla().setItems(ListaDeRegistros.getObjeto().getGrupoRegistros());
    	this.cerrarVentana(this.btnActualizar);
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
    	this.cerrarVentana(btnSalir);
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
		this.p = ListaDeRegistros.getObjeto().getGrupoPatrones().get(indice);
		d = this.p.getFechaNacimientoPatron();
		this.txtIDPatrones.setDisable(true);
		this.cmbEsDueno.getItems().addAll("Selecciona una opción:", "true", "false");
		this.txtNombresPatron.setText(this.p.getNombresPatron());
		this.txtAPaterno.setText(this.p.getApPaternoPatron());
		this.txtAMaterno.setText(this.p.getApMaternoPatron());
		this.txtIDPatrones.setText(String.valueOf(this.p.getMatriculaPatron()));
		this.txtNumeroCelular.setText(this.p.getNoCelularPatron());
		this.cmbEsDueno.setValue(String.valueOf(this.p.isEsPatronDueno()));
		this.dtpFechaNacimiento.setValue(this.d.toLocalDate());
		this.verificarEntrada(txtNumeroCelular, TipoError.TELEFONO);
		this.verificarEntrada(txtNombresPatron, TipoError.NOMBRE);
		this.verificarEntrada(txtAPaterno, TipoError.APELLIDOPATERNO);
		this.verificarEntrada(txtAMaterno, TipoError.APELLIDOMATERNO);
	}
	
	private boolean verificar() {
		boolean valido = true;

		if ((this.txtNombresPatron.getText() == null)
				|| (this.txtNombresPatron.getText() != null && this.txtNombresPatron.getText().isEmpty())) {
			this.mensaje += "- El nombre no es valido, es vacio.\n";
			valido = false;
		}
		
		if (this.txtNombresPatron.getText().length() <= 3) {
			this.mensaje += "- El nombre no es valido, debe \n tener al menos cuatro caracteres.\n";
			valido = false;
		}
		
		if (this.txtNombresPatron.getText().length() > 40) {
			this.mensaje += "- El nombre no es valido, debe tener máximo 40 caracteres.\n";
			valido = false;
		}
		
		if (!this.nombreValido) {
			this.mensaje += "- El nombre del patrón solo debe contener letras.\n";
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
		
		if (this.txtAMaterno.getText().length() <= 2) {
			this.mensaje += "- El apellido materno no es valido, debe \n tener al menos tres caracteres.\n";
			valido = false;
		}
		
		if (!this.aMaternoValido) {
			this.mensaje += "- El apellido materno del patrón solo debe contener letras.\n";
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
			this.mensaje += "- El número celuar del patrón debe contener diez dígitos.\n";
			valido = false;
		}
		
		return valido;
	}
	
	public void actualizarPatron() throws SQLException {
		String nombre = "nombres_patron";
		String aPaterno = "ap_paterno_patron";
		String aMaterno = "ap_materno_patron";
		String esDueno = "es_patron_dueno";
		String fechaNacimiento = "fecha_nacimiento_patron";
		String numeroCelular = "no_celular_patron";
		String queryUno = "update patrones set ";
		String queryDos = " where matricula_patron='" + this.p.getMatriculaPatron() + "'";
		boolean nombreBandera = false;
		boolean aPaternoBandera = false;
		boolean aMaternoBandera = false;
		boolean esSocioBandera = false;
		boolean fechaNacimientoBandera = false;
		@SuppressWarnings("unused")
		boolean numeroCelularBandera = false;
		 
		if(!(p.getNombresPatron().equals(this.txtNombresPatron.getText()))) {
			queryUno = queryUno + nombre + "='" + this.txtNombresPatron.getText() + "'";
			this.p.setNombresPatron(this.txtNombresPatron.getText());
			nombreBandera = true;
		}
		
		if(!(p.getApPaternoPatron().equals(this.txtAPaterno.getText()))) {
			if(nombreBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + aPaterno + "='" + this.txtAPaterno.getText() + "'";
			this.p.setApPaternoPatron(this.txtAPaterno.getText());
			aPaternoBandera = true;
		}
		
		if(!(p.getApMaternoPatron().equals(this.txtAMaterno.getText()))) {
			if(nombreBandera | aPaternoBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + aMaterno + "='" + this.txtAMaterno.getText() + "'";
			this.p.setApMaternoPatron(this.txtAMaterno.getText());
			aMaternoBandera = true;
		}
		
		if(!(this.cmbEsDueno.getValue().equals(String.valueOf(this.p.isEsPatronDueno()))) ||
				!(this.cmbEsDueno.getSelectionModel().getSelectedIndex() == 0) || 
				!(this.cmbEsDueno.getSelectionModel().getSelectedIndex() == -1)) {
			if(nombreBandera | aPaternoBandera | aMaternoBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + esDueno + "=" + this.cmbEsDueno.getValue();
			this.p.setEsPatronDueno(Boolean.valueOf(this.cmbEsDueno.getValue()));
			esSocioBandera = true;
		}
		
		if(!(this.d.toLocalDate().equals(this.dtpFechaNacimiento.getValue()))) {
			if(nombreBandera | aPaternoBandera | aMaternoBandera | esSocioBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + fechaNacimiento + "='" + this.dtpFechaNacimiento.getValue() + "'";
			this.p.setFechaNacimientoPatron(Date.valueOf(this.dtpFechaNacimiento.getValue()));
			this.d = this.p.getFechaNacimientoPatron();
			fechaNacimientoBandera = true;
		}
		
		if(!(p.getNoCelularPatron().equals(this.txtNumeroCelular.getText()))) {
			if(nombreBandera | aPaternoBandera | aMaternoBandera | esSocioBandera | fechaNacimientoBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + numeroCelular + "='" + this.txtNumeroCelular.getText() + "'";
			this.p.setNoCelularPatron(this.txtNumeroCelular.getText());
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