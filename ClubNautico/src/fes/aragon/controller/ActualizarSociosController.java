package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
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

public class ActualizarSociosController extends BaseController implements Initializable{
	
	private Conexion cn = this.conexionSQL();
	
	private Socios s;
	
	private Date d;
	
    @FXML
    private Button btnActualizar;

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
    private TextField txtID;

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtNumeroCelular;

    @FXML
    void actualizarSocios(ActionEvent event) throws SQLException {
    	this.actualizarSocio();
    	ListaDeRegistros.getObjeto().getGrupoSocios().set(this.indice, this.s);
    	this.cn.llenarTablaRegistros();
    	BaseController.tablaController.getTblTabla().setItems(ListaDeRegistros.getObjeto().getGrupoRegistros());
    	this.cerrarVentana(this.btnActualizar);
    	this.tabla = null;
    	this.indice = -1;
    	this.cn.cerrarConexion();
    }

    @FXML
    void cerrarVentana(ActionEvent event) {
    	this.cerrarVentana(btnSalir);
    	this.tabla = null;
    	this.indice = -1;
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
		this.cmbEsDueno.getItems().addAll(true,false);
		this.txtNombres.setText(this.s.getNombresSocios());
		this.txtAPaterno.setText(this.s.getApPaternoSocio());
		this.txtAMaterno.setText(this.s.getApMaternoSocio());
		this.txtID.setText(String.valueOf(this.s.getMatriculaSocio()));
		this.txtNumeroCelular.setText(this.s.getNoCelularSocio());
		this.cmbEsDueno.setValue(this.s.isEsSocioDueno());
		this.dtpFechaNacimiento.setValue(this.d.toLocalDate());
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
		
		if(Boolean.compare(s.isEsSocioDueno(), this.cmbEsDueno.getValue()) < 0 
				|| Boolean.compare(s.isEsSocioDueno(), this.cmbEsDueno.getValue()) >0) {
			if(nombreBandera | aPaternoBandera | aMaternoBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + esSocio + "=" + this.cmbEsDueno.getValue();
			this.s.setEsSocioDueno(this.cmbEsDueno.getValue());
			esSocioBandera = true;
		}
		
		if(!(this.d.toLocalDate().equals(this.dtpFechaNacimiento.getValue()))) {
			System.out.println((this.d.toLocalDate().equals(this.dtpFechaNacimiento.getValue())));
			System.out.println(!(this.d.toLocalDate().equals(this.dtpFechaNacimiento.getValue())));
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
