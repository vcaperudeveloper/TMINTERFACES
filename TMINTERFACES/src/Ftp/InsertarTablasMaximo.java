package Ftp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Conexion.OracleConexion;;

public class InsertarTablasMaximo {

	
	public static boolean fverificalocalidad(String id) throws IOException
	{   boolean rs=false;
		Connection cn=null;
		try {
            String sqltm_localidad="select * from tm_localidad";
            cn=OracleConexion.obtenerConexion();
            Statement stmt = cn.createStatement();
    	    ResultSet rst = stmt.executeQuery(sqltm_localidad);
    	    
    	    while (rst.next()){
    	    	if(rst.getString("localidad").equalsIgnoreCase(id)){
    	    		rs=true;
    	    		break;
    	    	}
    	    }
    	    stmt.close();
            cn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }	
		return rs;
	}
	
	
	
	public static boolean fverificasublocalidad(String id) throws IOException
	{   boolean rs=false;
		Connection cn=null;
		try {
            String sqltm_localidad="select * from tm_sublocalidad";
            cn=OracleConexion.obtenerConexion();
            Statement stmt = cn.createStatement();
    	    ResultSet rst = stmt.executeQuery(sqltm_localidad);
    	    
    	    while (rst.next()){
    	    	if(rst.getString("sublocalidad").equalsIgnoreCase(id)){
    	    		rs=true;
    	    		break;
    	    	}
    	    }
    	    stmt.close();
            cn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }	
		return rs;
	}
	
	public static boolean fverificabodega(String id) throws IOException
	{   boolean rs=false;
		Connection cn=null;
		try {
            String sqltm_localidad="select * from tm_BODEGA";
            cn=OracleConexion.obtenerConexion();
            Statement stmt = cn.createStatement();
    	    ResultSet rst = stmt.executeQuery(sqltm_localidad);
    	    
    	    while (rst.next()){
    	    	if(rst.getString("BODEGASTC").equalsIgnoreCase(id)){
    	    		rs=true;
    	    		break;
    	    	}
    	    }
    	    stmt.close();
            cn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }	
		return rs;
	}
	

	public static void pinserrlocalidad(String id[]) throws IOException
	{   
		Connection cn=null;
		try {
			cn=OracleConexion.obtenerConexion();
		    cn.setAutoCommit(false);
            String sql="INSERT INTO TM_ERR_BODEGA VALUES (TM_ERR_BODEGAID,DESCRIPTION,LOCALIDAD,SUBLOCALIDAD,BODEGA,ESTADO,DIRECCION,OBSERVACIONES,HASLD,ROWSTAMP)";
        	sql+="values(TM_ERR_BODEGAIDSEQ.NEXTVAL,?,?,?,?,?,?,?,0,'')";
        	PreparedStatement pst=cn.prepareStatement(sql);
            pst.setString(1, id[0]);          		
            pst.setString(2, id[1]);
            pst.setString(3, id[2]);
            pst.setString(4, id[3]);
            pst.setString(5, id[4]);
            pst.setString(6, id[5]);
            pst.setString(7, "Localidad No existe");
            pst.close();
            cn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }	

	}
	
	
	
	public static void pinserrsublocalidad(String id[]) throws IOException
	{   
		Connection cn=null;
		try {
			cn=OracleConexion.obtenerConexion();
		    cn.setAutoCommit(false);
            String sql="INSERT INTO TM_ERR_BODEGA VALUES (TM_ERR_BODEGAID,DESCRIPTION,LOCALIDAD,SUBLOCALIDAD,BODEGA,ESTADO,DIRECCION,OBSERVACIONES,HASLD,ROWSTAMP)";
        	sql+="values(TM_ERR_BODEGAIDSEQ.NEXTVAL,?,?,?,?,?,?,?,0,'')";
        	PreparedStatement pst=cn.prepareStatement(sql);
            pst.setString(1, id[0]);          		
            pst.setString(2, id[1]);
            pst.setString(3, id[2]);
            pst.setString(4, id[3]);
            pst.setString(5, id[4]);
            pst.setString(6, id[5]);
            pst.setString(7, "subLocalidad No existe");
            pst.close();
            cn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }	

	}
	
	public static void pinsertabodegamax(String id[]) throws IOException
	{   
		Connection cn=null;
		try {
			cn=OracleConexion.obtenerConexion();
		    cn.setAutoCommit(false);
            String sql="INSERT INTO TM_BODEGA VALUES (TM_BODEGAID,DESCRIPTION,LOCALIDAD,SUBLOCALIDAD,BODEGA,ESTADO,DIRECCION,HASLD)";
        	sql+="values(TM_BODEGAIDSEQ.NEXTVAL,?,?,?,?,?,?,?,0)";
        	PreparedStatement pst=cn.prepareStatement(sql);
            pst.setString(1, id[0]);          		
            pst.setString(2, id[1]);
            pst.setString(3, id[1]+id[2]+id[3]);
            pst.setString(4, id[3]);
            pst.setString(5, id[4]);
            pst.setString(6, id[5]);
            pst.setString(7, id[6]);
            pst.close();
            cn.commit();
            cn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }	

	}
	
	public static void pactualizabodegamax(String id[]) throws IOException
	{   
		Connection cn=null;
		try {
			cn=OracleConexion.obtenerConexion();
		    cn.setAutoCommit(false);
            String sql="UPDATE TM_BODEGA SET DESCRIPTION=?,DIRECCION=? WHERE BODEGASTC=?";
        	PreparedStatement pst=cn.prepareStatement(sql);
            pst.setString(1, id[0]);          		
            pst.setString(2, id[1]);
            pst.setString(3, id[2]);
            pst.close();
            cn.commit();
            cn.close();           
        } catch (SQLException e) {
            e.printStackTrace();
        }	

	}
	public static String pvalidartablaint_registrartablasMaximo() throws IOException
	{   String rs="";
		Connection cn=null;
		try {
			cn=OracleConexion.obtenerConexion();
			
            String sqlTM_INT_BODEGA="select * from TM_INT_BODEGA";          
            Statement stmt = cn.createStatement();
    	    ResultSet rsetTM_INT_BODEGA = stmt.executeQuery(sqlTM_INT_BODEGA);
    	    while (rsetTM_INT_BODEGA.next()){
    	    	if(!fverificalocalidad(rsetTM_INT_BODEGA.getString("localidad"))){
	    	    		String id[]={
	    	    				rsetTM_INT_BODEGA.getString("DESCRIPTION"),
	    	    				rsetTM_INT_BODEGA.getString("LOCALIDAD"),
	    	    				rsetTM_INT_BODEGA.getString("SUBLOCALIDAD"),
	    	    				rsetTM_INT_BODEGA.getString("BODEGA"),
	    	    				rsetTM_INT_BODEGA.getString("ESTADO"),
	    	    				rsetTM_INT_BODEGA.getString("DIRECCION")
	    	    				};
	    	    		pinserrlocalidad(id);
    	    	}
    	    	if(!fverificasublocalidad(rsetTM_INT_BODEGA.getString("sublocalidad"))){
	    	    		String id[]={
	    	    				rsetTM_INT_BODEGA.getString("DESCRIPTION"),
	    	    				rsetTM_INT_BODEGA.getString("LOCALIDAD"),
	    	    				rsetTM_INT_BODEGA.getString("SUBLOCALIDAD"),
	    	    				rsetTM_INT_BODEGA.getString("BODEGA"),
	    	    				rsetTM_INT_BODEGA.getString("ESTADO"),
	    	    				rsetTM_INT_BODEGA.getString("DIRECCION")
	    	    				};
	    	    		pinserrsublocalidad(id);
	    	    		 rs="ERROR EN LOCALIDAD : \n";
    	    	}
    	    	if(!fverificabodega(rsetTM_INT_BODEGA.getString("BODEGA"))){
    	    		String id[]={
    	    				rsetTM_INT_BODEGA.getString("DESCRIPTION"),
    	    				rsetTM_INT_BODEGA.getString("LOCALIDAD"),
    	    				rsetTM_INT_BODEGA.getString("SUBLOCALIDAD"),
    	    				rsetTM_INT_BODEGA.getString("BODEGA"),
    	    				rsetTM_INT_BODEGA.getString("ESTADO"),
    	    				rsetTM_INT_BODEGA.getString("DIRECCION")
    	    				};
    	    		pinsertabodegamax(id);
    	    		rs="BODEGA NO EXISTE : \n";
	    	   }else{
	    		   String id[]={
	    				rsetTM_INT_BODEGA.getString("LOCALIDAD")+
	    				rsetTM_INT_BODEGA.getString("SUBLOCALIDAD")+
	    				rsetTM_INT_BODEGA.getString("BODEGA"),
   	    				rsetTM_INT_BODEGA.getString("DESCRIPTION"),
   	    				rsetTM_INT_BODEGA.getString("DIRECCION")
   	    				};
	    		   pactualizabodegamax(id);
	    	   }
    	    }
    	    stmt.close();
    	    
            cn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }	
		return rs;
	}
	

}
