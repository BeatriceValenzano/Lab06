package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, DAY(DATA) as giorno, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getInt("giorno"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese) {		
		
		final String sql = "SELECT localita, Umidita, DAY(DATA) as giorno "
				+ "FROM situazione "
				+ "WHERE MONTH(DATA) = ? AND DAY(DATA) < 16";
		
		List<Rilevamento> rilevamenti = new LinkedList<Rilevamento>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese); //per il mese che ha il punto interrogativo
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Rilevamento r = new Rilevamento(rs.getString("localita"), rs.getInt("Umidita"), rs.getInt("giorno"));
				rilevamenti.add(r);
			}
			
			conn.close();
			return rilevamenti;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public List<String> getCitta() {   //mi seleziona quelle che sono tutte le città del database
		
		final String sql = "SELECT DISTINCT localita "
				+ "FROM situazione";
		
		List <String> listaCitta = new LinkedList<String>(); 
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				String citta = rs.getString("Localita");
				listaCitta.add(citta);
			}

			conn.close();
			return listaCitta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public String getUmiditaMedia(int mese) {
		
		final String sql = "SELECT localita, AVG(umidita) AS umiditaMedia "
				+ "FROM situazione "
				+ "WHERE MONTH(DATA) = ? "
				+ "GROUP BY localita";
		
		String s = "";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				s += "Località: " + rs.getString("localita") + "; " + "Umidità media:" + rs.getDouble("umiditaMedia") + ";\n";
			}

			conn.close();
			return s;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}
