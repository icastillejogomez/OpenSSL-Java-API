package interfaces.hash;

import java.io.File;

public interface HASH_Interfaz {

	public byte[] MD5(String texto) throws excepciones.OpenSSL_Exception;

	public byte[] MD5(File archivo) throws excepciones.OpenSSL_Exception;


	public byte[] SHA1(String texto) throws excepciones.OpenSSL_Exception;

	public byte[] SHA1(File archivo) throws excepciones.OpenSSL_Exception;


	public byte[] SHA(String texto) throws excepciones.OpenSSL_Exception;

	public byte[] SHA(File archivo) throws excepciones.OpenSSL_Exception;

	
	public byte[] SHA256(String texto) throws excepciones.OpenSSL_Exception;

	public byte[] SHA256(File archivo) throws excepciones.OpenSSL_Exception;


}
