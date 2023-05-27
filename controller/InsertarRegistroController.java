package fes.aragon.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.Barcos;
import fes.aragon.modelo.Destinos;
import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Patrones;
import fes.aragon.modelo.Registros;
import fes.aragon.modelo.Socios;
import fes.aragon.recovery.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class InsertarRegistroController extends BaseController implements Initializable{
	
	private Registros r;
	
	private Conexion cn = this.conexionSQL();
	
	String mensaje = "";
	
	@FXML
	private Button btnInsertar;

    @FXML
    private Button btnSalir;

    @FXML
    private ComboBox<Barcos> cmbBarco;

    @FXML
    private ComboBox<Destinos> cmbDestinos;

    @FXML
    private ComboBox<Patrones> cmbPatrones;

    @FXML
    private ComboBox<Socios> cmbSocios;

    @FXML
    private DatePicker dtpFechaLlegada;

    @FXML
    private DatePicker dtpFechaSalida;

    @FXML
    private Spinner<String> sprHoraLlegada;

    @FXML
    private Spinner<String> sprHoraSalida;

    @FXML
    private Spinner<String> sprMinutoLlegada;

    @FXML
    private Spinner<String> sprMinutoSalida;

    @FXML
    void insertar(ActionEvent event) {
    	if(this.verificar()) {
    	this.r.setBarco(this.cmbBarco.getValue());
    	this.r.setSocio(this.cmbSocios.getValue());
    	this.r.setPatron(this.cmbPatrones.getValue());
    	this.r.setDestino(this.cmbDestinos.getValue());
    	this.r.setFechaSalida(this.dtpFechaSalida.getValue() + " " + 
    	this.sprHoraSalida.getValue() + ":" + this.sprMinutoSalida.getValue() + ":00");
    	this.r.setFechaLlegada(this.dtpFechaLlegada.getValue() + " " + 
    	this.sprHoraLlegada.getValue() + ":" + this.sprMinutoLlegada.getValue() + ":00");
    	try {
			this.cn.insertarRegistro(this.r);
			ListaDeRegistros.getObjeto().getGrupoRegistros().set(
					ListaDeRegistros.getObjeto().getGrupoRegistros().size()-1, this.r);
		} catch (SQLException e) {
		}
    	this.cerrarVentana(this.btnInsertar);
    	this.tabla = null;
    	this.indice = -1;
    	this.cn.cerrarConexion();
    	}else {
    		this.ventanaEmergente("Error", "Error de guardado", this.mensaje);
    		this.mensaje = "";
    	}
    }

    @FXML
    void salir(ActionEvent event) {
    	ListaDeRegistros.getObjeto().getGrupoRegistros().remove(
				ListaDeRegistros.getObjeto().getGrupoRegistros().size()-1);
    	this.cerrarVentana(this.btnSalir);
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
		this.r = ListaDeRegistros.getObjeto().getGrupoRegistros().get(
				ListaDeRegistros.getObjeto().getGrupoRegistros().size()-1);
		ObservableList<String> listaHoras = FXCollections.observableArrayList();
		listaHoras.addAll("00","01","02","03","04","05","06","07","08","09","10","11",
				"12","13","14","15","16","17","18","19","20","21","22","23");
		ObservableList<String> listaMinutos = FXCollections.observableArrayList();
		listaMinutos.addAll("00","05","10","15","20","25","30","35","40","45","50","55"); 
		
		try {
			this.cmbBarco.setItems(this.cn.buscarBarcos());
			this.cmbBarco.getSelectionModel().select(0);
			this.cmbSocios.setItems(this.cn.buscarSocios());
			this.cmbSocios.getSelectionModel().select(0);
			this.cmbPatrones.setItems(this.cn.buscarPatrones());
			this.cmbPatrones.getSelectionModel().select(0);
			this.cmbDestinos.setItems(this.cn.buscarDestinos());
			this.cmbDestinos.getSelectionModel().select(0);
			this.sprHoraSalida.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(listaHoras));
			this.sprMinutoSalida.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(listaMinutos));
			this.sprHoraLlegada.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(listaHoras));
			this.sprMinutoLlegada.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(listaMinutos));
		} catch (SQLException e) {
		}
	}
	
	private boolean verificar() {
		boolean valido = true;
		String fechaSalida = "";
		String fechaLlegada = "";
		
		if ((this.dtpFechaSalida.getValue() == null)
				|| (this.dtpFechaSalida.getValue() != null && 
				String.valueOf(this.dtpFechaSalida.getValue()).equals(""))) {
			this.mensaje += "- La fecha de salida no es valido, es vacio.\n";
			valido = false;
		}
		
		fechaSalida = String.valueOf(this.dtpFechaSalida.getValue()) + " " 
					  + this.sprHoraSalida.getValue()+":"+this.sprMinutoSalida.getValue()+":00";
		
		if(fechaSalida.equals(BaseController.mapFechasSalida.get(fechaSalida))) {
			this.mensaje += "- La fecha de salida (" +fechaSalida+ ") ya se asigno \na un barco previamente.\n";
			valido = false;
		}
		
		if ((this.dtpFechaLlegada.getValue() == null)
				|| (this.dtpFechaLlegada.getValue() != null && 
				String.valueOf(this.dtpFechaLlegada.getValue()).equals(""))) {
			this.mensaje += "- La fecha de llegada no es valido, es vacio.\n";
			valido = false;
		}
		
		fechaLlegada = String.valueOf(this.dtpFechaLlegada.getValue()) + " " 
				  + this.sprHoraLlegada.getValue()+":"+this.sprMinutoLlegada.getValue()+":00";
	
		if(fechaLlegada.equals(BaseController.mapFechasLlegada.get(fechaLlegada))) {
		this.mensaje += "- La fecha de llegada (" +fechaLlegada+ ") ya se asigno \na un barco previamente.\n";
			valido = false;
		}
		
		return valido;
	}

}
