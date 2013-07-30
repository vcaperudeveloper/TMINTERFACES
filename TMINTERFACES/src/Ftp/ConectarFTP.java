package Ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;
import com.enterprisedt.net.ftp.FileTransferClient;

public class ConectarFTP {
	

	private boolean downloadFileByFTP(String server, String user, String pass, String localPath, String remotePath) {
		try {
		URL url = new URL("ftp://" + user + ":" + pass + "@" + server + remotePath + ";type=i");
		URLConnection urlc = url.openConnection();
		InputStream is = urlc.getInputStream();
		BufferedWriter bw = new BufferedWriter(new FileWriter(localPath));
		int c;
		while ((c = is.read()) != -1) {
		bw.write(c);
		}
		is.close();
		bw.flush();
		bw.close();
		return true;
		} catch (Exception ex) {
		ex.printStackTrace();
		System.out.println(ex.getMessage());
		return false;
		}
		}
	
	
	private boolean uploadFileByFTP(String server, String user, String pass, String localPath, String remotePath) {
		try {
		URL url = new URL("ftp://" + user + ":" + pass + "@" + server + remotePath + ";type=i");
		URLConnection urlc = url.openConnection();
		OutputStream os = urlc.getOutputStream();
		BufferedReader br = new BufferedReader(new FileReader(localPath));
		int c;
		while ((c = br.read()) != -1) {
		os.write(c);
		}
		os.flush();
		os.close();
		br.close();
		return true;
		} catch (Exception ex) {
		ex.printStackTrace();
		return false;
		}
		}

	
	public static String fechafile(){
		  java.util.Date fecha=new java.util.Date();
		   String ano=fecha.toLocaleString().substring(6,10);
		   String mes=fecha.toLocaleString().substring(3,5);
		   String dia=fecha.toLocaleString().substring(0,2);
		  
		return ano+mes+dia;
	}
	
	public static void  pdescargarftparchivotoftpinterfaces(){
		
		try {
			
			System.out.println("Descargando Archivos Ftp ctock a ruta local...");
			String astock[]=CargarArchivo.cargardatos("cstock");
			String server = astock[0].trim();
	        int port = 21;
	        String user = astock[1].trim();
	        String password = astock[2].trim();
	        FileTransferClient ftp = new FileTransferClient();
	        ftp.setRemoteHost(server);
	        ftp.setRemotePort(port);
	        ftp.setUserName(user);
	        ftp.setPassword(password);
	        ftp.connect();
	        com.enterprisedt.net.ftp.FTPFile[] files = ftp.directoryList(astock[4].trim());
	        for (com.enterprisedt.net.ftp.FTPFile file : files){
	        	if(file.getName().endsWith(".TXT")||file.getName().endsWith(".txt") || file.getName().endsWith(".XLS")||file.getName().endsWith(".xls")){
	        		if(file.getName().indexOf(fechafile())>=0){
	        			//System.out.println("archivo descargar "+file.getName());
	        			ConectarFTP descargar= new ConectarFTP();
	        			descargar.downloadFileByFTP(astock[0].trim(), astock[1].trim(), astock[2].trim(), astock[3].trim()+file.getName(), astock[4].trim()+file.getName());
	        		}else{
	        			//System.out.println("archivo no descargar "+file.getName());
	        		}
	        	}
			}
	        ftp.disconnect();
			System.out.println("Se descargaron Correctamente Archivos Ftp ctock a ruta local");
			
			
			
			System.out.println("Subiendo Archivos ruta local a TMFS...");
			String atm[]=CargarArchivo.cargardatos("tm");
			File dir = new File(atm[3].trim());
			String[] ficheros = dir.list();
			
		    if (ficheros == null)
		        System.out.println("No hay ficheros en el directorio especificado");
		        else {
		        for (int x=0;x<ficheros.length;x++){
		        	if(ficheros[x].endsWith(".TXT")||ficheros[x].endsWith(".txt") || ficheros[x].endsWith(".XLS")||ficheros[x].endsWith(".xls")){
		        		if(ficheros[x].indexOf(fechafile())>=0){
		        			//System.out.println("archivo descargar "+file.getName());
		        			ConectarFTP subir= new ConectarFTP();
		        			subir.uploadFileByFTP(atm[0].trim(), atm[1].trim(), atm[2].trim(), atm[3].trim()+ficheros[x], atm[4].trim()+ficheros[x]);
		        		}else{
		        			//System.out.println("archivo no descargar "+file.getName());
		        		}
		        	}
		        }        
		    }
		    
			System.out.println("Se Subieron Correctamente Archivos ruta local a TMFS");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en Cargar Archivos Ftp:"+e);
			
		}
		
		
	}
}
	
