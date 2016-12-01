package interfaces.rand;

import java.io.File;

import excepciones.OpenSSL_Exception;

public interface Rand_Interfaz {

	public String rand(int tamaño) throws OpenSSL_Exception;

	public String randhex(int tamaño) throws OpenSSL_Exception;

	public File rand(int tamaño , String ruta_destino) throws OpenSSL_Exception;

	public File randhex(int tamaño , String ruta_destino) throws OpenSSL_Exception;
}
