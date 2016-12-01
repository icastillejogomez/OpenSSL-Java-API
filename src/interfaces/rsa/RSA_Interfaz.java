package interfaces.rsa;

import java.io.File;
import excepciones.OpenSSL_Exception;

public interface RSA_Interfaz {

	public File genRSA(int bits , String rutaClavePrivada) throws OpenSSL_Exception;

	public File getClavePublica(File clavePrivada, String rutaClavePublica) throws OpenSSL_Exception;


	public byte[] cifrarRSA(byte[] texto , File clavePublica) throws OpenSSL_Exception;

	public byte[] descifrarRSA(byte[] texto , File clavePrivada) throws OpenSSL_Exception;


	public File cifrarRSA(byte[] texto , File clavePublica , String rutaDestino) throws OpenSSL_Exception;

	public File descifrarRSA(byte[]  texto , File clavePrivada , String rutaDestino) throws OpenSSL_Exception;


	public byte[] cifrarRSA(File archivo , File clavePublica)  throws OpenSSL_Exception;

	public byte[] descifrarRSA(File archivo , File clavePrivada)  throws OpenSSL_Exception;


	public byte[] firmarRSA(byte[] texto , File clavePrivada) throws OpenSSL_Exception;

	public byte[] verificarRSA(byte[]  texto , File clavePublica) throws OpenSSL_Exception;

	
	public byte[] firmarRSA(File archivo , File clavePrivada) throws OpenSSL_Exception;

	public byte[] verificarRSA(File archivo , File clavePublica) throws OpenSSL_Exception;
	
}
