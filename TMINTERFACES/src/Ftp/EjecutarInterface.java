package Ftp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import org.apache.log4j.Logger;
import com.enterprisedt.net.ftp.FileTransferClient;
import Conexion.OracleConexion;
import com.enterprisedt.net.ftp.FTPException;
public class EjecutarInterface {

		public static void main(String[] args) throws SQLException, IOException, FTPException, IOException, ParseException  {
			try {
			   ConectarFTP.pdescargarftparchivotoftpinterfaces();
			  /*  CargarArchivo.pcargarftptotablasint();
				String rs=InsertarTablasMaximo.pvalidartablaint_registrartablasMaximo();
			    if(rs==""){
			    	 System.out.println("sin log");
			    }else{
			    	 System.out.println("con log");
			    	 logger loger = new logger("D:\\vcaperu\\proyectos\\git\\InterfaceGit\\TMINTERFACES\\Log\\");
			         Logger log = loger.getLog();
			         log.debug(rs);
			   }*/
			   System.out.println("Proceso Completo Realizado");
			} catch (Exception e) {
				System.out.println("Proceso Completo tuvo Error:"+e);
			}
			
		}

}
