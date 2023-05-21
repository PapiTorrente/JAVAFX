package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Patrones;
import fes.aragon.modelo.Socios;
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
    private TextField txtIDPatrones;

    @FXML
    private TextField txtNombresPatron;

    @FXML
    private TextField txtNumeroCelular;

    @FXML
    void actualizarPatrones(ActionEvent event) throws SQLException {
    	this.actualizarPatron();
    	ListaDeRegistros.getObjeto().getGrupoPatrones().set(this.indice, this.p);
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
		this.p = ListaDeRegistros.getObjeto().getGrupoPatrones().get(indice);
		d = this.p.getFechaNacimientoPatron();
		this.txtIDPatrones.setDisable(true);
		this.cmbEsDueno.getItems().addAll(true,false);
		this.txtNombresPatron.setText(this.p.getNombresPatron());
		this.txtAPaterno.setText(this.p.getApPaternoPatron());
		this.txtAMaterno.setText(this.p.getApMaternoPatron());
		this.txtIDPatrones.setText(String.valueOf(this.p.getMatriculaPatron()));
		this.txtNumeroCelular.setText(this.p.getNoCelularPatron());
		this.cmbEsDueno.setValue(this.p.isEsPatronDueno());
		this.dtpFechaNacimiento.setValue(this.d.toLocalDate());
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
		
		if(Boolean.compare(p.isEsPatronDueno(), this.cmbEsDueno.getValue()) < 0 
				|| Boolean.compare(p.isEsPatronDueno(), this.cmbEsDueno.getValue()) >0) {
			if(nombreBandera | aPaternoBandera | aMaternoBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + esDueno + "=" + this.cmbEsDueno.getValue();
			this.p.setEsPatronDueno(this.cmbEsDueno.getValue());
			esSocioBandera = true;
		}
		
		if(!(this.d.toLocalDate().equals(this.dtpFechaNacimiento.getValue()))) {
			System.out.println((this.d.toLocalDate().equals(this.dtpFechaNacimiento.getValue())));
			System.out.println(!(this.d.toLocalDate().equals(this.dtpFechaNacimiento.getValue())));
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
