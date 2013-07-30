package Ftp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;



import Conexion.OracleConexion;

public class CargarArchivo {

	///ddddd
	public static String[] cargardatos(String ptipo) throws Exception{
		 File  f= new File("");
		 FileInputStream fstream = new FileInputStream(f.getAbsolutePath()+"\\dataftp.txt");
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            String a[]=new String[6];
            while ((strLinea = buffer.readLine()) != null){
            	
            	String servidor= strLinea.substring(0,20);
            	String usuario = strLinea.substring(20,40);
            	String clave = strLinea.substring(40,60);
            	String carpetalocal = strLinea.substring(60,160);
            	String rutaftp = strLinea.substring(160,210);
            	String tipo = strLinea.substring(210,230);
            	if(ptipo.equalsIgnoreCase(tipo.trim())){
            		a[0]= servidor;
            		a[1] = usuario;
            		a[2] = clave;
            		a[3] = carpetalocal;
            		a[4] = rutaftp;
            		a[5] = tipo;
            	}           	
            }
            entrada.close();
            return a; 
	}
	public static void pcargarftptotablasint() throws SQLException {
		try {
			pcargarBodegasFTP();
			pcargarentidad();
			//stockbodegas
			File dir = new File("D:\\vcaperu\\proyectos\\fuentes\\CargarArchivoPlano\\tarea5");
			String[] ficheros = dir.list();
			
		    if (ficheros == null)
		        System.out.println("No hay ficheros en el directorio especificado");
		        else {
		        for (int x=0;x<ficheros.length;x++){
		        	if(ficheros[x].endsWith("txt")){
		        		 System.out.println(ficheros[x]+" archivo:N°"+ x);
		        		 
		        		 System.out.println(" °"+ ficheros[x].substring(0,8));
		        		 pcargarstockbodegasmasivo("D:\\vcaperu\\proyectos\\fuentes\\CargarArchivoPlano\\tarea5\\"+ficheros[x],ficheros[x].substring(0,8));
		        	}
		        }        
		    }
			//ventasbodegas
			File dir2 = new File("D:\\vcaperu\\proyectos\\fuentes\\CargarArchivoPlano\\tarea6");
			String[] ficheros2 = dir2.list();
		    if (ficheros2 == null)
		        System.out.println("No hay ficheros en el directorio especificado");
		        else {
		        for (int x=0;x<ficheros2.length;x++){
		        	if(ficheros2[x].endsWith("TXT")){
		        		 System.out.println(" °"+ ficheros2[x]);
		        		 pcargarventasbodegasmasivo("D:\\vcaperu\\proyectos\\fuentes\\CargarArchivoPlano\\tarea6\\"+ficheros2[x]);
		        	}
		        }        
		    }
		    
			System.out.println("Se insertaron las tablas int Correctamente");
		} catch (Exception e) {
			System.out.println("Error insertaron las tablas int con error:"+e);
		}
		
	}
	

	private static void pcargarventasbodegasmasivo(String ruta) throws SQLException {
		Connection cn=null;
		PreparedStatement pst=null;
		try{
            FileInputStream fstream = new FileInputStream(ruta);
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            cn=OracleConexion.obtenerConexion();
            cn.setAutoCommit(false);
            String sql="insert into TM_INT_VENTA_BODEGA (TM_INT_VENTA_BODEGAID,CODADM,ALMACENSAP,TRANSACCIONSAP,CODIGOSAP,SERIE,FECHA,TIPOINVENTARIO,TIPOMODELO,HASLD)";
        	sql+="values(TM_INT_VENTA_BODEGAIDSEQ.NEXTVAL,?,?,?,?,?,TO_DATE(?,  'YYYY-MM-DD'),?,?,0)";
        	pst=cn.prepareStatement(sql);
            int cont=0;
            while ((strLinea = buffer.readLine()) != null){
            	String P1= strLinea.substring(1,5);
            	String P2 = strLinea.substring(5,9);
            	String P3 = strLinea.substring(10,13);
            	String P4 = strLinea.substring(23,41);
            	String P5 = strLinea.substring(41,66);
            	String P6 = strLinea.substring(93,101);
            	String P7 = strLinea.substring(127,128);
            	String P8 = strLinea.substring(128,129);
            	String fecha=P6.substring(0,4)+"-"+P6.substring(4,6)+"-"+P6.substring(6,8);
            	/*System.out.print ("\tCodigo de CODADM : "+P1);
            	System.out.print ("\tCodigo de ALMACEN  : "+P2);
            	System.out.print ("\tCodigo de TRANSACCIONSAP : "+P3);
            	System.out.print ("\tCodigo de CODIGOSAP : "+P4);
            	System.out.print ("\tCodigo de SERIE: "+P5);
            	System.out.print ("\tCodigo de fecha fianl: "+fecha);
            	System.out.print ("\tCodigo de TIPOINVENTARIO: "+P7);
            	System.out.print("\tCodigo de TIPOMODELO: "+P8);
            	System.out.println("");*/
            	//System.out.println ("fila : "+strLinea);
            	pst.setString(1, P1);
			  	pst.setString(2,P2);
			  	pst.setString(3, P3);
    			pst.setString(4, P4.trim());
    			pst.setString(5, P5);
    			pst.setString(6,fecha);
    			pst.setString(7,P7);
    			pst.setString(8,P8);
    			pst.addBatch();
    			cont++;
            }
            entrada.close();
            pst.executeBatch();
            cn.commit();
            System.out.println("ejecutadoscont:"+cont);
            System.out.println("\nPROCESO CORRECTO");
        }catch (Exception e){ 
            cn.rollback();
            System.out.println("OCURRIO ERROR : " + e.getMessage());
        }finally{
        	pst.clearBatch();
        	pst.close();
        	cn.close();
        }
	}
	
	private static void pcargarentidad() throws SQLException {
		Connection cn=null;
		PreparedStatement  pst=null;
		try{
            // Abrimos el archivo
            FileInputStream fstream = new FileInputStream("D:\\vcaperu\\proyectos\\git\\InterfaceGit\\TMINTERFACES\\FTPTEMP\\ENTTXT20130705.TXT");
            // Creamos el objeto de entrada
            DataInputStream entrada = new DataInputStream(fstream);
            // Creamos el Buffer de Lectura
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            cn=OracleConexion.obtenerConexion();
            cn.setAutoCommit(false);
            String sql="insert into TM_ENTIDAD (TM_ENTIDADID,ENTIDAD,DESCRIPTION,CANALLOGISTICO,ESTADO,PAGACOMISION,HASLD)";
        	sql+="values(TM_ENTIDADIDSEQ.NEXTVAL,?,?,?,?,?,0)";
        	pst=cn.prepareStatement(sql);
           
            int cont=0;
            while ((strLinea = buffer.readLine()) != null)   {
            	String P1= strLinea.substring(0,5);
            	String P2 = strLinea.substring(5,45);
            	String P3 = strLinea.substring(45,47);
            	String P4 = strLinea.substring(47,48);
            	String P5 = strLinea.substring(48,49);
            	/*System.out.print ("\tCodigo de ENTIDAD : "+P1);
            	System.out.print ("\tCodigo de DESCRIPTION : "+P2);
            	System.out.print ("\tCodigo de CANALLOGISTICO : "+P3);
            	System.out.print ("\tCodigo de ESTADO : "+P4);
            	System.out.print ("\tCodigo de PAGACOMISION : "+P5);
            	System.out.println("");*/
            	pst.setString(1, P1);
			  	pst.setString(2, P2);
			  	pst.setString(3, P3);
    			pst.setString(4, ((P5.equalsIgnoreCase("S"))?"ACTIVO":"INACTIVO"));
    			pst.setInt(5, ((P5.equalsIgnoreCase("S"))?1:0));
    			pst.addBatch();
    			cont++;
            }
            entrada.close();
            pst.executeBatch();
            cn.commit();
            System.out.println("ejecutadoscont:"+cont);
            System.out.println("\nPROCESO CORRECTO");
        }catch (Exception e){ //Catch de excepciones
            cn.rollback();
            System.out.println("OCURRIO ERROR : " + e.getMessage());
        }finally{
        	pst.clearBatch();
        	pst.close();
        	cn.close();
        }
	}
	
	private static void pcargarstockbodegasmasivo(String ruta,String fecha) throws SQLException {
		Connection cn=null;
		PreparedStatement pst=null;
		try{
            // Abrimos el archivo
            FileInputStream fstream = new FileInputStream(ruta);
            // Creamos el objeto de entrada
            DataInputStream entrada = new DataInputStream(fstream);
            // Creamos el Buffer de Lectura
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;
            cn=OracleConexion.obtenerConexion();
            cn.setAutoCommit(false);
            String sql="insert into TM_INT_STOCK_BODEGA (TM_INT_STOCK_BODEGAID,LOCALIDAD,SUBLOCALIDAD,BODEGA,CODIGOSAP,STOCK,TIPOINVENTARIO,HASLD,FECHA)";
        	sql+="values(TM_INT_STOCK_BODEGAIDSEQ.NEXTVAL,?,?,?,?,?,?,0, ( to_date(?, 'yyyymmdd') -1) )";
        	pst=cn.prepareStatement(sql);
            int cont=0;

            while ((strLinea = buffer.readLine()) != null)   {
            	
            	String P1= strLinea.substring(0,2);
            	String P2 = strLinea.substring(2,4);
            	String P3 = strLinea.substring(4,9);
            	String P4 = strLinea.substring(9,15);
            	String P5 = strLinea.substring(40,47);
            	String P6 = strLinea.substring(47,48);
            	
            	/*System.out.print ("\tCodigo de LOCALIDAD : "+P1);
            	System.out.print ("\tCodigo de SUBLOCALIDAD  : "+P2);
            	System.out.print ("\tCodigo de BODEGA : "+P3);
            	System.out.print ("\tCodigo de CODIGOSAP : "+P4);
            	System.out.print ("\tCodigo de STOCK: "+P5);
            	System.out.print ("\tCodigo de TIPOINVENTARIO: "+P6);*/
            	//System.out.println("");
            	pst.setString(1, P1);
			  	pst.setString(2,P2);
			  	pst.setString(3, P3);
    			pst.setString(4, P4);
    			pst.setString(5, P5);
    			pst.setString(6,P6);
    			pst.setString(7,fecha);
            	pst.addBatch();
    			cont++;
            }
            entrada.close();
            pst.executeBatch();
            cn.commit();
            System.out.println("ejecutadoscont:"+cont);
            System.out.println("\nPROCESO CORRECTO");
        }catch (Exception e){ 
            cn.rollback();
            System.out.println("OCURRIO ERROR : " + e.getMessage());
        }finally{
        	pst.clearBatch();
        	pst.close();
        	cn.close();
        }
	}
	
	
	public static void pcargarBodegasFTP() throws SQLException {
	        Connection cn=null;
	        PreparedStatement  pst=null;
	        try{
	        // Abrimos el archivo
	        FileInputStream fstream = new FileInputStream("D:\\vcaperu\\proyectos\\git\\InterfaceGit\\TMINTERFACES\\FTPTEMP\\BODTXT20130705.TXT");
	        // Creamos el objeto de entrada
	        DataInputStream entrada = new DataInputStream(fstream);
	        // Creamos el Buffer de Lectura
	        BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
	        String strLinea;
	        cn=OracleConexion.obtenerConexion();
	        cn.setAutoCommit(false);
	        String sql="insert into TM_INT_FTP_BODEGA (TM_INT_BODEGAID,LOCALIDAD,SUBLOCALIDAD,BODEGA,DESCRIPTION,ESTADO, DIRECCION,HASLD,ROWSTAMP)";
	        sql+="values(?,?,?,?,?,?,?,0,MAXSEQ.NEXTVAL)";
	        pst=cn.prepareStatement(sql);
	       
	        int cont=0;
	        int dato=0;
	        while ((strLinea = buffer.readLine()) != null)   {
	              String P1= strLinea.substring(0,2).trim();
	              String P2 = strLinea.substring(2,4).trim();
	              String P3 = strLinea.substring(4,9).trim();
	              String P4 = strLinea.substring(9,49).trim();
	              String P5 = strLinea.substring(54,55).trim();
	              String P6 = strLinea.substring(55,strLinea.length()).trim();
	              
//	              System.out.print ("\tCodigo de Codigo de Localidad : "+P1);
//	              System.out.print ("\tCodigo de Codigo de SubLocalidad : "+P2);
//	              System.out.print ("\tCodigo de Codigo de Bodega : "+P3);
//	              System.out.print ("\tCodigo de Nombre Bodega : "+P4);
//	              System.out.print ("\tCodigo de Estado : "+P5);
//	              System.out.print ("\tCodigo de Direccion : "+P6);
//	              System.out.println("");

	              
	              dato++;
	              pst.setInt(1,dato);          		
	              pst.setString(2, P1);
	              pst.setString(3, P2);
	              pst.setString(4, P3);
	              pst.setString(5, P4);
	              pst.setString(6, ((P5.equalsIgnoreCase("S"))?"ACTIVO":"INACTIVO"));
	              pst.setString(7, P6);
	              pst.addBatch();
	              cont++;
	        }
	        entrada.close();
	        pst.executeBatch();
	        cn.commit();
	        System.out.println("ejecutadoscont:"+cont);
	        System.out.println("\nPROCESO CORRECTO");
	    }catch (Exception e){ //Catch de excepciones
	        cn.rollback();
	        System.out.println("OCURRIO ERROR : " + e.getMessage());
	    }finally{
	        pst.clearBatch();
	        pst.close();
	        cn.close();
	    }
	  }

	
	

}
