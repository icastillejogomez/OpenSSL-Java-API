package test.util;

import java.io.File;

import excepciones.OpenSSL_Exception;
import openssl.rand.Rand;
import openssl.util.Util;

public class UtilTest {

	public static Util util = new Util();
	public static Rand rand = new Rand();
	
	public static void main(String[] args) throws OpenSSL_Exception {
		System.out.println("Comenzando las pruebas de OpenSSL (Util)");

		System.out.println("Empezando la prueba 1");
		prueba1();
		System.out.println("\tPrueba 1 terminada");

		System.out.println("Finalizadas las pruebas de OpenSSL (Util)");

	}

	private static void prueba1() throws OpenSSL_Exception {
		String texto = "Texto de prueba bla bla\nbla";
		String res = "";
		File tmp = new File("./pruebas/" + rand.randhex(10) + ".tmp");
		while(tmp.exists()) tmp = new File("./pruebas/" + rand.randhex(10) + ".tmp");
		try {
			res = new String(util.leerArchivo(util.guardarArchivo(texto.getBytes(), tmp.getAbsolutePath())));
		} catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 1");
			e.printStackTrace();
			tmp.delete();
			return;
		}
		tmp.delete();
		if(!res.equals(texto))
			System.out.println("Fallo en la prueba 1: no coincide el texto original con el cifrado");
		System.out.println(res);
	}

}
