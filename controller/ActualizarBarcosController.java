package fes.aragon.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.Barcos;
import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.TipoError;
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
	
	String mensaje = "";
	
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
    	if(this.verificar()) {
    	this.actualizarBarco();
    	ListaDeRegistros.getObjeto().getGrupoBarcos().set(this.indice, this.b);
    	this.cn.llenarTablaRegistros();
    	BaseController.tablaController.getTblTabla().setItems(ListaDeRegistros.getObjeto().getGrupoRegistros());
    	this.cerrarVentana(this.btnActualizar);
    	this.tabla = null;
    	this.indice = -1;
    	this.nombreValido = true;
    	this.noAmarreValido = true;
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
    	this.nombreValido = true;
    	this.noAmarreValido = true;
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
		this.cmbCuota.getItems().addAll("Selecciona una opción:","$1","$2","$3");
		this.cmbCuota.setValue(this.b.getCuota());
		this.txtNumeroAmarre.setText(String.valueOf(this.b.getNoAgarre()));
		this.verificarEntrada(txtNombreBarco, TipoError.NOMBREBARCO);
		this.verificarEntrada(txtNumeroAmarre, TipoError.NOAMARRE);
	}
	
	private boolean verificar() {
		boolean valido = true;

		if ((this.txtNombreBarco.getText() == null)
				|| (this.txtNombreBarco.getText() != null && this.txtNombreBarco.getText().isEmpty())) {
			this.mensaje += "- El nombre no es valido, es vacio.\n";
			valido = false;
		}
		
		if (this.txtNombreBarco.getText().length() > 40) {
			this.mensaje += "- El nombre del barco no es valido, debe tener máximo 40 caracteres.\n";
			valido = false;
		}
		
		if (!this.nombreValido) {
			this.mensaje += "- El nombre del barco solo puede contener letras.\n";
			valido = false;
		}
		
		
		if((this.cmbCuota.getSelectionModel().getSelectedIndex() == 0) || 
				(this.cmbCuota.getSelectionModel().getSelectedIndex() == -1)) {
			this.mensaje += "- Seleccione una opción sobre la cuota del barco.\n";
			valido = false;
		}
		
		if ((this.txtNumeroAmarre.getText() == null)
				|| (this.txtNumeroAmarre.getText() != null && this.txtNumeroAmarre.getText().isEmpty())) {
			this.mensaje += "- El número de amarre no es valido, es vacio.\n";
			valido = false;
		}
		

		if (!this.noAmarreValido) {
			this.mensaje += "- El número de amarre solo debe contener dígitos numéricos.\n";
			valido = false;
		}
		
		return valido;
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
		
		if(!(b.getCuota().equals(this.cmbCuota.getValue()))||
				!(this.cmbCuota.getSelectionModel().getSelectedIndex() == 0) || 
				!(this.cmbCuota.getSelectionModel().getSelectedIndex() == -1)) {
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
