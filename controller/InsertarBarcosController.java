package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
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

public class InsertarBarcosController extends BaseController implements Initializable{
	
	private Barcos b;
	
	private Conexion cn = this.conexionSQL();
	
	String mensaje = "";
	
    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnSalir;

    @FXML
    private ChoiceBox<String> cmbCuota;

    @FXML
    private TextField txtNombresDeBarco;

    @FXML
    private TextField txtNumeroAmarre;

    @FXML
    void cerrarVentana(ActionEvent event) {
    	ListaDeRegistros.getObjeto().getGrupoBarcos().remove(
				ListaDeRegistros.getObjeto().getGrupoBarcos().size()-1);
    	this.cerrarVentana(this.btnSalir);
    	this.tabla = null;
    	this.indice = -1;
    	this.nombreValido = true;
    	this.noAmarreValido = true;
    	this.cn.cerrarConexion();
    }

    @FXML
    void insertarBarco(ActionEvent event) {
    	if(this.verificar()) {
    	this.b.setNomBarco(this.txtNombresDeBarco.getText());
    	this.b.setCuota(this.cmbCuota.getValue());
    	this.b.setNoAgarre(Integer.parseInt(this.txtNumeroAmarre.getText()));
    	try {
			this.b.setNoSerieBarco(this.cn.insertarBarcos(this.b));
			ListaDeRegistros.getObjeto().getGrupoBarcos().set(
					ListaDeRegistros.getObjeto().getGrupoBarcos().size()-1, this.b);
		} catch (SQLException e) {
		}
    	this.cerrarVentana(this.btnInsertar);
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
    
    public Conexion conexionSQL() {
		try {
			return new Conexion();
		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {
		}
		return null;
	}
    
    private boolean verificar() {
		boolean valido = true;

		if ((this.txtNombresDeBarco.getText() == null)
				|| (this.txtNombresDeBarco.getText() != null && this.txtNombresDeBarco.getText().isEmpty())) {
			this.mensaje += "- El nombre no es valido, es vacio.\n";
			valido = false;
		}
		
		if (this.txtNombresDeBarco.getText().length() > 40) {
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.b = ListaDeRegistros.getObjeto().getGrupoBarcos().get(
				ListaDeRegistros.getObjeto().getGrupoBarcos().size()-1);
		this.cmbCuota.getItems().addAll("Selecciona una opción:","$1","$2","$3");
		this.cmbCuota.getSelectionModel().select(0);
		this.verificarEntrada(txtNombresDeBarco, TipoError.NOMBREBARCO);
		this.verificarEntrada(txtNumeroAmarre, TipoError.NOAMARRE);
	}

}
