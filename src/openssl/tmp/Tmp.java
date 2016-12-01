package openssl.tmp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import es.upm.babel.cclib.Monitor;
import excepciones.OpenSSL_Exception;

public class Tmp {

	// --- > Metodos auxiliares < ---  //

	private static Monitor mutex = new Monitor();
	private static interfaces.rand.Rand_Interfaz rand = new openssl.rand.Rand();
	private static HashMap<String, File> map = new HashMap<String, File>();
	private static final String path = "./tmp/";

	// --- > Metodos auxiliares < ---  //

	public static File newtmp(){
		// --- > Entramos en la exclusion mutua
		mutex.enter();
		// --- > Obtenemos un nombre de archivo que no se este usando
		String nombre = "";
		do{ try {  nombre = rand.randhex(15); } catch (OpenSSL_Exception e) {
			e.printStackTrace();
			mutex.leave();
			return null;
		}}while(esta(nombre));
		// --- > Añadimos el archivo al mapa de archivos temporales
		File res = añadirNombre(nombre);
		// --- > Salimos de la exclusion mutua y devolvemos el resultado
		mutex.leave();
		return res;
	}

	public static void deletetmp(File tmp){
		// --- > Entramos en la exclusion mutua
		mutex.enter();
		if(esta(tmp.getName()))
			borrartmp(tmp);
		// --- > Salimos de la exclusion mutua 
		mutex.leave();
	}



	// --- > Metodos auxiliares < ---  //

	private static boolean esta(String nombre){
		boolean encontrado = false;
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext() && !( encontrado = nombre.equals(it.next())));
		return encontrado;
	}

	private static File añadirNombre(String nombre){
		File tmp = new File(path + nombre);
		map.put(nombre, tmp);
		return tmp;
	}

	private static void borrartmp(File tmp){
		try {
			FileUtils.forceDelete(map.remove(tmp.getName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
