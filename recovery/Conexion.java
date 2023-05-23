package fes.aragon.recovery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import fes.aragon.controller.BaseController;
import fes.aragon.modelo.Barcos;
import fes.aragon.modelo.Destinos;
import fes.aragon.modelo.ListaDeRegistros;
import fes.aragon.modelo.Patrones;
import fes.aragon.modelo.Registros;
import fes.aragon.modelo.Socios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Conexion extends BaseController{
	private String url="jdbc:mariadb://127.0.0.1:3306/clubnautico?serverTimeZone=UTC";
	private String usuario="root";
	private String clave="C00Mand#";
	public  Connection conexion;
	
	public Conexion() throws ClassNotFoundException, SQLException{
			Class.forName("org.mariadb.jdbc.Driver");
			conexion=DriverManager.getConnection(url, usuario, clave);	
	}
	
	// M�todos_Tabla_Registros
	public void llenarTablaRegistros() throws SQLException {
		String fecha;
		String query = "select r.no_serie_barcoREGISTROS, r.matricula_socioR, r.matricula_patronR, r.salida, "
				+ "r.llegada, r.id_puertoREGISTROS, b.nom_barco as nom"
				+ " from barcos b join registros r on b.no_serie_barco = r.no_serie_barcoREGISTROS";
		Statement st;
		st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ListaDeRegistros.getObjeto().getGrupoRegistros().clear();
		this.mapearBarcos();
		this.mapearSocios();
		this.mapearPatrones();
		this.mapearDestinos();
		while(rs.next()) {
			Date d;
			Registros r = new Registros();
			r.setBarco(BaseController.mapBarcos.get(rs.getInt(1)));
			r.setSocio(BaseController.mapSocios.get(rs.getInt(2)));
			r.setPatron(BaseController.mapPatrones.get(rs.getInt(3)));
			r.setFechaSalida(String.valueOf(rs.getDate(4) + " " + String.valueOf(rs.getTime(4))));
			fecha = String.valueOf(rs.getDate(4) + " " + String.valueOf(rs.getTime(4)));
			r.setFechaLlegada(String.valueOf(rs.getDate(5) + " " + String.valueOf(rs.getTime(5))));
			r.setDestino(BaseController.mapDestinos.get(rs.getInt(6)));
			ListaDeRegistros.getObjeto().getGrupoRegistros().add(r);
		}
		rs.close();
		st.close();
	}
	
	public ObservableList<Registros> buscarRegistros(String patron) throws SQLException {
		String fecha;
		String query = "select r.no_serie_barcoREGISTROS, r.matricula_socioR, r.matricula_patronR, r.salida, "
				+ "r.llegada, r.id_puertoREGISTROS, b.nom_barco as nom"
				+ " from barcos b join registros r on b.no_serie_barco = r.no_serie_barcoREGISTROS "
				+ "where b.nom_barco like '%" + patron + "%'";
		Statement st;
		st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Registros> datos = FXCollections.observableArrayList();
		this.mapearBarcos();
		this.mapearSocios();
		this.mapearPatrones();
		this.mapearDestinos();
		while(rs.next()) {
			Date d;
			Registros r = new Registros();
			r.setBarco(BaseController.mapBarcos.get(rs.getInt(1)));
			r.setSocio(BaseController.mapSocios.get(rs.getInt(2)));
			r.setPatron(BaseController.mapPatrones.get(rs.getInt(3)));
			r.setFechaSalida(String.valueOf(rs.getDate(4) + " " + String.valueOf(rs.getTime(4))));
			fecha = String.valueOf(rs.getDate(4) + " " + String.valueOf(rs.getTime(4)));
			r.setFechaLlegada(String.valueOf(rs.getDate(5) + " " + String.valueOf(rs.getTime(5))));
			r.setDestino(BaseController.mapDestinos.get(rs.getInt(6)));
			datos.add(r);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public void insertarRegistro(Registros registro) throws SQLException {
		String query = "insert into registros values (?,?,?,?,?,?)";
		PreparedStatement pr = conexion.prepareStatement
				(query, Statement.RETURN_GENERATED_KEYS);
		pr.setInt(1, registro.getBarco().getNoSerieBarco());
		pr.setInt(2, registro.getSocio().getMatriculaSocio());
		pr.setInt(3, registro.getPatron().getMatriculaPatron());
		pr.setString(4, registro.getFechaSalida());
		pr.setString(5, registro.getFechaLlegada());
		pr.setInt(6, registro.getDestino().getNoSerieDestino());
		pr.execute();
	}
	
	//M�todos Tabla Barcos
	public void llenarTablaBarcos() throws SQLException {
		String query = "select * from barcos";
		Statement st;
		st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ListaDeRegistros.getObjeto().getGrupoBarcos().clear();
		while(rs.next()) {
			Barcos b = new Barcos();
			b.setNoSerieBarco(rs.getInt(1));
			b.setNomBarco(rs.getString(2));
			b.setCuota(rs.getString(3));
			b.setNoAgarre(rs.getInt(4));
			ListaDeRegistros.getObjeto().getGrupoBarcos().add(b);
		}
		rs.close();
		st.close();
	}
	
	public ObservableList<Barcos> buscarBarcos() throws SQLException {//Devuelve la lista observable que se edito
		String query = "select * from barcos";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Barcos> datos = FXCollections.observableArrayList();
		while (rs.next()) {
			Barcos b = new Barcos();
			b.setNoSerieBarco(rs.getInt(1));
			b.setNomBarco(rs.getString(2));
			b.setCuota(rs.getString(3));
			b.setNoAgarre(rs.getInt(4));
			datos.add(b);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public ObservableList<Barcos> buscarBarco(String patron) throws SQLException {//Devuelve la lista observable que se edito
		String query = "select * from barcos where nom_barco like '%"+patron+"%'";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Barcos> datos = FXCollections.observableArrayList();
		while (rs.next()) {
			Barcos b = new Barcos();
			b.setNoSerieBarco(rs.getInt(1));
			b.setNomBarco(rs.getString(2));
			b.setCuota(rs.getString(3));
			b.setNoAgarre(rs.getInt(4));
			datos.add(b);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public int insertarBarcos(Barcos barco) throws SQLException {
		String query = "insert into barcos values (null,?,?,?)";
		PreparedStatement pr = conexion.prepareStatement
				(query, Statement.RETURN_GENERATED_KEYS);
		pr.setString(1, barco.getNomBarco());
		pr.setString(2, barco.getCuota());
		pr.setInt(3, barco.getNoAgarre());
		pr.execute();
		ResultSet rs = pr.getGeneratedKeys();
		int id = -1;// Variable para recuperar id
		if(rs.next()) {
			id = rs.getInt(1);
			/*Recupera el id del nuevo registro de pelicula*/
		}
		rs.close();
		pr.close();
		return id;
	}
	
	private void mapearBarcos() throws SQLException {//Solo se ocupa en el llenado de la tabla general
		String query = "select * from barcos";
		Statement st;
		st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		BaseController.mapBarcos.clear();
		while(rs.next()) {
			Barcos b = new Barcos();
			b.setNoSerieBarco(rs.getInt(1));
			b.setNomBarco(rs.getString(2));
			b.setCuota(rs.getString(3));
			b.setNoAgarre(rs.getInt(4));
			BaseController.mapBarcos.put(b.getNoSerieBarco(), b);
		}
		rs.close();
		st.close();
	}
	
	// M�todos Tabla Socios
	public void llenarTablaSocios() throws SQLException {
		String query = "select * from socios";
		Statement st;
		st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ListaDeRegistros.getObjeto().getGrupoSocios().clear();
		while(rs.next()) {
			Socios s = new Socios();
			s.setMatriculaSocio(rs.getInt(1));
			s.setNombresSocios(rs.getString(2));
			s.setApPaternoSocio(rs.getString(3));
			s.setApMaternoSocio(rs.getString(4));
			s.setEsSocioDueno(rs.getBoolean(5));
			s.setFechaNacimientoSocio(rs.getDate(6));
			s.setNoCelularSocio(rs.getString(7));
			ListaDeRegistros.getObjeto().getGrupoSocios().add(s);
		}
		rs.close();
		st.close();
	}
	
	public ObservableList<Socios> buscarSocios() throws SQLException {//Devuelve la lista observable que se edito
		String query = "select * from socios";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Socios> datos = FXCollections.observableArrayList();
		while (rs.next()) {
			Socios s = new Socios();
			s.setMatriculaSocio(rs.getInt(1));
			s.setNombresSocios(rs.getString(2));
			s.setApPaternoSocio(rs.getString(3));
			s.setApMaternoSocio(rs.getString(4));
			s.setEsSocioDueno(rs.getBoolean(5));
			s.setFechaNacimientoSocio(rs.getDate(6));
			s.setNoCelularSocio(rs.getString(7));
			datos.add(s);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public ObservableList<Socios> buscarSocio(String socio) throws SQLException {//Devuelve la lista observable que se edito
		String query = "select * from socios where nombres_socio like '%"+socio+"%'";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Socios> datos = FXCollections.observableArrayList();
		while (rs.next()) {
			Socios s = new Socios();
			s.setMatriculaSocio(rs.getInt(1));
			s.setNombresSocios(rs.getString(2));
			s.setApPaternoSocio(rs.getString(3));
			s.setApMaternoSocio(rs.getString(4));
			s.setEsSocioDueno(rs.getBoolean(5));
			s.setFechaNacimientoSocio(rs.getDate(6));
			s.setNoCelularSocio(rs.getString(7));
			datos.add(s);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public int insertarSocios(Socios socios) throws SQLException {
		String query = "insert into socios values (null,?,?,?,?,?,?)";
		PreparedStatement pr = conexion.prepareStatement
				(query, Statement.RETURN_GENERATED_KEYS);
		pr.setString(1, socios.getNombresSocios());
		pr.setString(2, socios.getApPaternoSocio());
		pr.setString(3, socios.getApMaternoSocio());
		pr.setBoolean(4, socios.isEsSocioDueno());
		pr.setDate(5, socios.getFechaNacimientoSocio());
		pr.setString(6, socios.getNoCelularSocio());
		pr.execute();
		ResultSet rs = pr.getGeneratedKeys();
		int id = -1;// Variable para recuperar id
		if(rs.next()) {
			id = rs.getInt(1);
			/*Recupera el id del nuevo registro de pelicula*/
		}
		rs.close();
		pr.close();
		return id;
	}
	
	private void mapearSocios() throws SQLException {//Solo se ocupa en el llenado de la tabla general
		String query = "select * from socios";
		Statement st;
		st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		BaseController.mapSocios.clear();
		while(rs.next()) {
			Socios s = new Socios();
			s.setMatriculaSocio(rs.getInt(1));
			s.setNombresSocios(rs.getString(2));
			s.setApPaternoSocio(rs.getString(3));
			s.setApMaternoSocio(rs.getString(4));
			s.setEsSocioDueno(rs.getBoolean(5));
			s.setFechaNacimientoSocio(rs.getDate(6));
			s.setNoCelularSocio(rs.getString(7));
			BaseController.mapSocios.put(s.getMatriculaSocio(), s);
		}
		rs.close();
		st.close();
	}
	
	// M�todo Tabla Patrones
	public void llenarTablaPatrones() throws SQLException {
		String query = "select * from patrones";
		Statement st;
		st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ListaDeRegistros.getObjeto().getGrupoPatrones().clear();
		this.mapearPatrones();
		while(rs.next()) {
			Patrones p = new Patrones();
			p.setMatriculaPatron(rs.getInt(1));
			p.setNombresPatron(rs.getString(2));
			p.setApPaternoPatron(rs.getString(3));
			p.setApMaternoPatron(rs.getString(4));
			p.setEsPatronDueno(rs.getBoolean(5));
			p.setFechaNacimientoPatron(rs.getDate(6));
			p.setNoCelularPatron(rs.getString(7));
			ListaDeRegistros.getObjeto().getGrupoPatrones().add(p);
		}
		rs.close();
		st.close();
	}
	
	public ObservableList<Patrones> buscarPatrones() throws SQLException {//Devuelve la lista observable que se edito
		String query = "select * from patrones";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Patrones> datos = FXCollections.observableArrayList();
		while (rs.next()) {
			Patrones p = new Patrones();
			p.setMatriculaPatron(rs.getInt(1));
			p.setNombresPatron(rs.getString(2));
			p.setApPaternoPatron(rs.getString(3));
			p.setApMaternoPatron(rs.getString(4));
			p.setEsPatronDueno(rs.getBoolean(5));
			p.setFechaNacimientoPatron(rs.getDate(6));
			p.setNoCelularPatron(rs.getString(7));
			datos.add(p);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public ObservableList<Patrones> buscarPatrones(String patron) throws SQLException {//Devuelve la lista observable que se edito
		String query = "select * from patrones where nombres_patron like '%"+ patron+ "%'";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Patrones> datos = FXCollections.observableArrayList();
		while (rs.next()) {
			Patrones p = new Patrones();
			p.setMatriculaPatron(rs.getInt(1));
			p.setNombresPatron(rs.getString(2));
			p.setApPaternoPatron(rs.getString(3));
			p.setApMaternoPatron(rs.getString(4));
			p.setEsPatronDueno(rs.getBoolean(5));
			p.setFechaNacimientoPatron(rs.getDate(6));
			p.setNoCelularPatron(rs.getString(7));
			datos.add(p);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public ObservableList<Patrones> buscarPatron(String patron) throws SQLException {//Devuelve la lista observable que se edito
		String query = "select * from patrones where nombres_patron like '%"+patron+"%'";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Patrones> datos = FXCollections.observableArrayList();
		while (rs.next()) {
			Patrones p = new Patrones();
			p.setMatriculaPatron(rs.getInt(1));
			p.setNombresPatron(rs.getString(2));
			p.setApPaternoPatron(rs.getString(3));
			p.setApMaternoPatron(rs.getString(4));
			p.setEsPatronDueno(rs.getBoolean(5));
			p.setFechaNacimientoPatron(rs.getDate(6));
			p.setNoCelularPatron(rs.getString(7));
			datos.add(p);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public int insertarPatrones(Patrones patrones) throws SQLException {
		String query = "insert into patrones values (null,?,?,?,?,?,?)";
		PreparedStatement pr = conexion.prepareStatement
				(query, Statement.RETURN_GENERATED_KEYS);
		pr.setString(1, patrones.getNombresPatron());
		pr.setString(2, patrones.getApPaternoPatron());
		pr.setString(3, patrones.getApMaternoPatron());
		pr.setBoolean(4, patrones.isEsPatronDueno());
		pr.setDate(5, patrones.getFechaNacimientoPatron());
		pr.setString(6, patrones.getNoCelularPatron());
		pr.execute();
		ResultSet rs = pr.getGeneratedKeys();
		int id = -1;// Variable para recuperar id
		if(rs.next()) {
			id = rs.getInt(1);
			/*Recupera el id del nuevo registro de pelicula*/
		}
		rs.close();
		pr.close();
		return id;
	}

	private void mapearPatrones() throws SQLException {
		String query = "select * from patrones";
		Statement st;
		st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		BaseController.mapPatrones.clear();
		while(rs.next()) {
			Patrones p = new Patrones();
			p.setMatriculaPatron(rs.getInt(1));
			p.setNombresPatron(rs.getString(2));
			p.setApPaternoPatron(rs.getString(3));
			p.setApMaternoPatron(rs.getString(4));
			p.setEsPatronDueno(rs.getBoolean(5));
			p.setFechaNacimientoPatron(rs.getDate(6));
			p.setNoCelularPatron(rs.getString(7));
			BaseController.mapPatrones.put(p.getMatriculaPatron(), p);
		}
		rs.close();
		st.close();
	}
	//M�todo Tabla Destinos
	public void llenarTablaDestinos() throws SQLException {
		String query = "select * from destinos";
		Statement st;
		st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ListaDeRegistros.getObjeto().getGrupoDestinos().clear();
		this.mapearDestinos();
		while(rs.next()) {
			Destinos d = new Destinos();
			d.setNoSerieDestino(rs.getInt(1));
			d.setPuertoDest(rs.getString(2));
			ListaDeRegistros.getObjeto().getGrupoDestinos().add(d);
		}
		rs.close();
		st.close();
	}
	
	public ObservableList<Destinos> buscarDestinos() throws SQLException {//Devuelve la lista observable que se edito
		String query = "select * from destinos";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Destinos> datos = FXCollections.observableArrayList();
		while (rs.next()) {
			Destinos d = new Destinos();
			d.setNoSerieDestino(rs.getInt(1));
			d.setPuertoDest(rs.getString(2));
			datos.add(d);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public ObservableList<Destinos> buscarDestino(String patron) throws SQLException {//Devuelve la lista observable que se edito
		String query = "select * from destinos where puerto_dest like '%"+patron+"%'";
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ObservableList<Destinos> datos = FXCollections.observableArrayList();
		while (rs.next()) {

			Destinos d = new Destinos();
			d.setNoSerieDestino(rs.getInt(1));
			d.setPuertoDest(rs.getString(2));
			datos.add(d);
		}
		rs.close();
		st.close();
		return datos;
	}
	
	public int insertarDestinos(Destinos destinos) throws SQLException {
		String query = "insert into destinos values (null,?)";
		PreparedStatement pr = conexion.prepareStatement
				(query, Statement.RETURN_GENERATED_KEYS);
		pr.setString(1, destinos.getPuertoDest());
		pr.execute();
		ResultSet rs = pr.getGeneratedKeys();
		int id = -1;// Variable para recuperar id
		if(rs.next()) {
			id = rs.getInt(1);
			/*Recupera el id del nuevo registro de pelicula*/
		}
		rs.close();
		pr.close();
		return id;
	}
	
	private void mapearDestinos() throws SQLException {
		String query = "select * from Destinos";
		Statement st;
		st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		BaseController.mapDestinos.clear();
		while(rs.next()) {
			Destinos d = new Destinos();
			d.setNoSerieDestino(rs.getInt(1));
			d.setPuertoDest(rs.getString(2));
			BaseController.mapDestinos.put(d.getNoSerieDestino(), d);
		}
		rs.close();
		st.close();
	}
	public void cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
