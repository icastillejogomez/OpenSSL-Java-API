package interfaces.aes;

import java.io.File;
import excepciones.OpenSSL_Exception;

public interface AES_Interfaz {
	
	public byte[] cifraraes(String texto, String contraseña) throws OpenSSL_Exception;
	
	public byte[] descifraraes(String texto, String contraseña) throws OpenSSL_Exception;
	
	
	public byte[] cifraraes(File archivo, String contraseña) throws OpenSSL_Exception;
	
	public byte[] descifraraes(File archivo, String contraseña) throws OpenSSL_Exception;
	
	
	public File cifraraes(String texto , String contraseña , String ruta_destino) throws OpenSSL_Exception;
	
	public File descifraraes(String texto, String contraseña, String ruta_destino) throws OpenSSL_Exception;

	
	public File cifraraes(File archivo , String contraseña, String ruta_destino) throws OpenSSL_Exception ;
	
	public File descifraraes(File archivo , String contraseña , String ruta_destino) throws OpenSSL_Exception ;
	
	
}
