package fes.aragon.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fes.aragon.modelo.Destinos;
import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Socios;
import fes.aragon.modelo.TipoError;
import fes.aragon.recovery.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InsertarDestinosController extends BaseController implements Initializable{
	
	private Destinos d;
	
	private Conexion cn = this.conexionSQL();
	
	String mensaje = "";
	
    @FXML
    private Button btnInsertar;

    @FXML
    private Button btnSalir;

    @FXML
    private TextField txtDestino;

    @FXML
    void cerrarVentana(ActionEvent event) {
    	ListaDeRegistros.getObjeto().getGrupoDestinos().remove(
				ListaDeRegistros.getObjeto().getGrupoDestinos().size()-1);
    	this.cerrarVentana(btnSalir);
    	this.tabla = null;
    	this.indice = -1;
    	this.nombreValido = true;
    	this.cn.cerrarConexion();
    }

    @FXML
    void insertarDestino(ActionEvent event) {
    	if(this.verificar()) {
    	this.d.setPuertoDest(this.txtDestino.getText());
    	try {
			this.d.setNoSerieDestino(this.cn.insertarDestinos(this.d));
			ListaDeRegistros.getObjeto().getGrupoDestinos().set(
					ListaDeRegistros.getObjeto().getGrupoDestinos().size()-1, this.d);
		} catch (SQLException e) {
		}
    	this.cerrarVentana(this.btnInsertar);
    	this.tabla = null;
    	this.indice = -1;
    	this.nombreValido = true;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.d = ListaDeRegistros.getObjeto().getGrupoDestinos().get(
				ListaDeRegistros.getObjeto().getGrupoDestinos().size()-1);
		this.verificarEntrada(txtDestino, TipoError.NOMBREDESTINO);
	}
	
	private boolean verificar() {
		boolean valido = true;

		if ((this.txtDestino.getText() == null)
				|| (this.txtDestino.getText() != null && this.txtDestino.getText().isEmpty())) {
			this.mensaje += "- El nombre del destino no es valido, es vacio.\n";
			valido = false;
		}
		
		if (this.txtDestino.getText().length() > 30) {
			this.mensaje += "- El nombre no es valido, debe tener máximo 30 caracteres.\n";
			valido = false;
		}
		
		if (!this.nombreValido) {
			this.mensaje += "- El nombre del destino solo debe contener letras.\n";
			valido = false;
		}
		
		return valido;
	}

}
