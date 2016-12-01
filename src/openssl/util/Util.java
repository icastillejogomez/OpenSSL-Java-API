package openssl.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import excepciones.OpenSSL_Exception;

public class Util implements interfaces.util.UTIL_Interfaz{

	public byte[] leerArchivo(File archivo) throws OpenSSL_Exception {
		try { return FileUtils.readFileToByteArray(archivo); } catch (IOException e) {
			throw new OpenSSL_Exception("No se ha podido leer el archivo");
		}
	}

	public byte[] leerArchivo(String rutaArchivo) throws OpenSSL_Exception {
		return leerArchivo(new File(rutaArchivo));
	}

	public File guardarArchivo(byte[] datos , String rutaDestino) throws OpenSSL_Exception {
		try { FileUtils.writeByteArrayToFile(new File(rutaDestino), datos); } catch (IOException e) {
			throw new OpenSSL_Exception("No se ha podido guardar la informacion en el archivo");
		}
		return new File(rutaDestino);
	}

}
