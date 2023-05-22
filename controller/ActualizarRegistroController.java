package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class ActualizarRegistroController extends BaseController implements Initializable{

	private Conexion cn = this.conexionSQL();
	
	private Registros r;
	
	private Date dUno;
	
	private Date dDos;
	
    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnSalir;
    
    @FXML
    private ComboBox<Barcos> cmbBarco;
    
    @FXML
    private ComboBox<Socios> cmbSocios;
    
    @FXML
    private ComboBox<Patrones> cmbPatrones;
    
    @FXML
    private ComboBox<Destinos> cmbDestinos;

    @FXML
    private DatePicker dtpFechaSalida;
    
    @FXML
    private DatePicker dtpFechaLlegada;

    @FXML
    private Spinner<String> sprHoraSalida;
    
    @FXML
    private Spinner<String> sprHoraLlegada;

    @FXML
    private Spinner<String> sprMinutoSalida;
    
    @FXML
    private Spinner<String> sprMinutoLlegada;

    @FXML
    void actualizar(ActionEvent event) {
    	try {
			this.actualizarRegistro();
			ListaDeRegistros.getObjeto().getGrupoRegistros().set(indice, r);
			this.cerrarVentana(btnActualizar);
		} catch (SQLException e) {
		}
    }

    @FXML
    void salir(ActionEvent event) {
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
		this.r = ListaDeRegistros.getObjeto().getGrupoRegistros().get(indice);
		ObservableList<String> listaHoras = FXCollections.observableArrayList();
		listaHoras.addAll("00","01","02","03","04","05","06","07","08","09","10","11",
				"12","13","14","15","16","17","18","19","20","21","22","23");
		ObservableList<String> listaMinutos = FXCollections.observableArrayList();
		listaMinutos.addAll("00","05","10","15","20","25","30","35","40","45","50","55"); 
		this.dUno = Date.valueOf(this.r.getFechaSalida().substring(0, 10));
		this.dDos = Date.valueOf(this.r.getFechaLlegada().substring(0, 10));
		try {
			this.cmbBarco.setItems(this.cn.buscarBarcos());
			this.cmbBarco.getSelectionModel().select(this.r.getBarco());
			this.cmbSocios.setItems(this.cn.buscarSocios());
			this.cmbSocios.getSelectionModel().select(this.r.getSocio());
			this.cmbPatrones.setItems(this.cn.buscarPatrones());
			this.cmbPatrones.getSelectionModel().select(this.r.getPatron());
			this.cmbDestinos.setItems(this.cn.buscarDestinos());
			this.cmbDestinos.getSelectionModel().select(this.r.getDestino());
			this.dtpFechaSalida.setValue(this.dUno.toLocalDate());
			this.dtpFechaLlegada.setValue(this.dDos.toLocalDate());
			this.sprHoraSalida.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(listaHoras));
			this.sprMinutoSalida.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(listaMinutos));
			this.sprHoraLlegada.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(listaHoras));
			this.sprMinutoLlegada.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(listaMinutos));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void actualizarRegistro() throws SQLException {
		String noSerieBarcosRegistro = "no_serie_barcoREGISTROS";
		String matriculaSocioR = "matricula_socioR";
		String matriculaPatronR = "matricula_patronR";
		String salida = "salida";
		String llegada = "llegada";
		String idPuertoRegistros = "id_puertoREGISTROS";
		String queryUno = "update registros set ";
		String queryDos = " where llegada='" + this.r.getFechaLlegada() + "' ";
		boolean noSerieBarcosRegistroBandera = false;
		boolean matriculaSocioRBandera = false;
		boolean matriculaPatronRBandera = false;
		boolean salidaBandera = false;
		boolean llegadaBandera = false;
		@SuppressWarnings("unused")
		boolean idPuertoRegistrosBandera = false;
		String fechaEvaluacionSalida = this.dtpFechaSalida.getValue() + " " 
		+ this.sprHoraSalida.getValue() + ":" + this.sprMinutoSalida.getValue() + ":00";
		String fechaEvaluacionLlegada = this.dtpFechaLlegada.getValue() + " " 
				+ this.sprHoraLlegada.getValue() + ":" + this.sprMinutoLlegada.getValue() + ":00";
		
		if(!(this.r.getBarco().equals(this.cmbBarco.getValue()))) {// Barcos
			queryUno = queryUno + noSerieBarcosRegistro + "='" + this.cmbBarco.getValue().getNoSerieBarco() + "'";
			this.r.setBarco(this.cmbBarco.getValue());
			noSerieBarcosRegistroBandera = true;
		}
		 
		if(!(this.r.getSocio().equals(this.cmbSocios.getValue()))) {
			if(noSerieBarcosRegistroBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + matriculaSocioR + "='" + this.cmbSocios.getValue().getMatriculaSocio() + "'";
			this.r.setSocio(this.cmbSocios.getValue());
			matriculaSocioRBandera = true;
		}
		
		if(!(this.r.getPatron().equals(this.cmbPatrones.getValue()))) {
			if(noSerieBarcosRegistroBandera | matriculaSocioRBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + matriculaPatronR + "='" + this.cmbPatrones.getValue().getMatriculaPatron() + "'";
			this.r.setPatron(this.cmbPatrones.getValue());
			matriculaPatronRBandera = true;
		}

		if(!(this.r.getFechaSalida().equals(fechaEvaluacionSalida))) {
			if(noSerieBarcosRegistroBandera | matriculaSocioRBandera | matriculaPatronRBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + salida + "='" + fechaEvaluacionSalida + "'";
			this.r.setFechaSalida(fechaEvaluacionSalida);
			salidaBandera = true;
		}
		
		if(!(this.r.getFechaLlegada().equals(fechaEvaluacionLlegada))) {
			if(noSerieBarcosRegistroBandera | matriculaSocioRBandera | matriculaPatronRBandera | salidaBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + salida + "='" + fechaEvaluacionLlegada + "'";
			this.r.setFechaLlegada(fechaEvaluacionLlegada);
			llegadaBandera = true;
		}
		
		if(!(this.r.getDestino().equals(this.cmbDestinos.getValue()))) {
			if(noSerieBarcosRegistroBandera | matriculaSocioRBandera | matriculaPatronRBandera | salidaBandera
					| llegadaBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + idPuertoRegistros + "='" + this.cmbDestinos.getValue().getNoSerieDestino() + "'";
			this.r.setDestino(this.cmbDestinos.getValue());
			idPuertoRegistrosBandera = true;
		}
		
		if(noSerieBarcosRegistroBandera | matriculaSocioRBandera | matriculaPatronRBandera | salidaBandera |
				llegadaBandera | idPuertoRegistrosBandera) {
		queryUno = queryUno + queryDos;
		PreparedStatement pr = this.cn.conexion.prepareStatement(queryUno);
		pr.execute();
		pr.close();
		}
	}

}
