package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.Anno;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Anno> getAnni(){
		String sql = "select Year(datetime) as anno, count(id) as avvistamenti " + 
				"from sighting " + 
				"where country = 'us' " + 
				"group by Year(datetime) ";
		List<Anno> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(new Anno(res.getInt("anno"), res.getInt("avvistamenti")));
			}
			
			conn.close();
			return result ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getStati(Integer anno){
		String sql = "select distinct state " + 
				"from sighting " + 
				"where country = 'us' " + 
				"and Year(datetime) = ? " ;
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(res.getString("state"));
			}
			
			conn.close();
			return result ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public boolean esisteArco(String stato1, String stato2, Integer anno) {
		String sql = "select count(*) as c " + 
				"from sighting s1, sighting s2 " + 
				"where s1.country = 'us' " + 
				"and s2.country = 'us' " + 
				"and Year(s1.datetime) = ? " + 
				"and Year(s2.datetime) = ? " + 
				"and s1.state= ? " + 
				"and s2.state= ? " + 
				"and s2.datetime > s1.datetime " ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, anno);
			st.setString(3, stato1);
			st.setString(4, stato2);
			ResultSet res = st.executeQuery() ;
			res.first() ;
			
			int risultati = res.getInt("c") ;
			conn.close();
				
			if(risultati == 0) 
				return false;
			else
				return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false ;
		}
	}

}
