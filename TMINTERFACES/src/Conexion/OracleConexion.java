package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConexion {
	
static{
		
		try {
			
			Class.forName("oracle.jdbc.OracleDriver");
//						
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static Connection obtenerConexion(){
		
		Connection cn = null;
				
		try {
			
			cn = 
				
				DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.160:1521:MAXTDP","maximo","maximo");
			
				System.out.println("Conexion Exitosa !");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Conexion Errada !");
		}
		
		return cn;
		
	}
	


}
