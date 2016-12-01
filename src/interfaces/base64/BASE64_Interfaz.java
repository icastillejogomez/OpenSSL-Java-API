package interfaces.base64;

import java.io.File;
import excepciones.OpenSSL_Exception;

public interface BASE64_Interfaz {


	public byte[] cifrar64( String texto ) throws OpenSSL_Exception;

	public byte[] descifrar64( String texto ) throws OpenSSL_Exception;
	
	
	public byte[] cifrar64(File archivo) throws OpenSSL_Exception;

	public byte[] descifrar64(File archivo) throws OpenSSL_Exception;


	
	public File cifrar64( String texto , String ruta_destino) throws OpenSSL_Exception;

	public File descifrar64( String texto , String ruta_destino) throws OpenSSL_Exception;
	
	
	public File cifrar64( File archivo , String ruta_destino) throws OpenSSL_Exception;

	public File descifrar64( File archivo , String ruta_destino) throws OpenSSL_Exception;




}
