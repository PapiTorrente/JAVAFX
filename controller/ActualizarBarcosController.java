package fes.aragon.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.Barcos;
import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.recovery.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ActualizarBarcosController extends BaseController implements Initializable{
	
	private Conexion cn = this.conexionSQL();
	
	private Barcos b;
	
    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnSalir;

    @FXML
    private ChoiceBox<String> cmbCuota;

    @FXML
    private TextField txtNumeroAmarre;;

    @FXML
    private TextField txtIDBarco;

    @FXML
    private TextField txtNombreBarco;

    @FXML
    void actualizarBarcos(ActionEvent event) throws SQLException {
    	this.actualizarBarco();
    	ListaDeRegistros.getObjeto().getGrupoBarcos().set(this.indice, this.b);
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
		this.b = ListaDeRegistros.getObjeto().getGrupoBarcos().get(indice);
		this.txtIDBarco.setText(String.valueOf(this.b.getNoSerieBarco()));
		this.txtNombreBarco.setText(this.b.getNomBarco());
		this.cmbCuota.getItems().addAll("$1","$2","$3");
		this.cmbCuota.setValue(this.b.getCuota());
		this.txtNumeroAmarre.setText(String.valueOf(this.b.getNoAgarre()));
	}
	
	public void actualizarBarco() throws SQLException {
		String nomBarco = "nom_barco";
		String cuota = "cuota";
		String noAmarre = "no_amarre";
		String queryUno = "update barcos set ";
		String queryDos = " where nom_barco='" + this.b.getNomBarco() + "'";
		boolean nomBarcoBandera = false;
		boolean cuotaBandera = false;
		boolean noAmarreBandera = false;
		 
		if(!(b.getNomBarco().equals(this.txtNombreBarco.getText()))) {
			queryUno = queryUno + nomBarco + "='" + this.txtNombreBarco.getText() + "'";
			this.b.setNomBarco(this.txtNombreBarco.getText());
			nomBarcoBandera = true;
		}
		
		if(!(b.getCuota().equals(this.cmbCuota.getValue()))) {
			if(nomBarcoBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + cuota + "='" + this.cmbCuota.getValue() + "'";
			this.b.setCuota(this.cmbCuota.getValue());
			cuotaBandera = true;
		}
		
		if(!(String.valueOf(b.getNoAgarre()).equals(this.txtNumeroAmarre.getText()))) {
			if(nomBarcoBandera | cuotaBandera) {
				queryUno += ",";
			}
			queryUno = queryUno + noAmarre + "='" + this.txtNumeroAmarre.getText() + "'";
			this.b.setNoAgarre(Integer.parseInt(this.txtNumeroAmarre.getText()));
			noAmarreBandera = true;
		}
		
		if(nomBarcoBandera | cuotaBandera | noAmarreBandera) {
		queryUno = queryUno + queryDos;
		PreparedStatement pr = this.cn.conexion.prepareStatement(queryUno);
		pr.execute();
		pr.close();
		}
	}

}
