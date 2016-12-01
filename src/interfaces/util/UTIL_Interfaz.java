package interfaces.util;

import java.io.File;

import excepciones.OpenSSL_Exception;

public interface UTIL_Interfaz {
	
	public byte[] leerArchivo(File archivo) throws OpenSSL_Exception;
	
	public byte[] leerArchivo(String rutaArchivo) throws OpenSSL_Exception;
	
	public File guardarArchivo(byte[] datos , String rutaDestino) throws OpenSSL_Exception;
	

}
