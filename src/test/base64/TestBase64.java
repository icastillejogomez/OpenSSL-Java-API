package test.base64;

import java.io.File;
import excepciones.OpenSSL_Exception;
import interfaces.base64.BASE64_Interfaz;
import openssl.base64.Base64;

public class TestBase64 {

	public static BASE64_Interfaz base64 = new Base64();

	public static void main(String[] args) {

		System.out.println("Iniciando secuencia de pruebas de OpenSSL (Base64)");

		System.out.println("Iniciando prueba 1");
		prueba1();
		System.out.println("\tPrueba 1 terminada");

		System.out.println("Iniciando prueba 2");
		prueba2();
		System.out.println("\tPrueba 2 terminada");

		System.out.println("Iniciando prueba 3");
		prueba3();
		System.out.println("\tPrueba 3 terminada");

		System.out.println("Iniciando prueba 4");
		prueba4();
		System.out.println("\tPrueba 4 terminada");

		System.out.println("Iniciando prueba 5");
		prueba5();
		System.out.println("\tPrueba 5 terminada");

		System.out.println("Iniciando prueba 6");
		prueba6();
		System.out.println("\tPrueba 6 terminada");
		
		System.out.println("Finalizada la secuencia de pruebas de OpenSSL (Base64)");

	}


	public static void prueba1(){
		String texto = "Archivo que voy a cifrar";
		String texto64 = null;
		try { texto64 = new String(base64.cifrar64(texto)); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba1");
			e.printStackTrace();
			return;
		}
		String texto64_dec = null;
		try { texto64_dec = new String(base64.descifrar64(texto64)); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba1");
			e.printStackTrace();
			return;
		}
		if(!texto64_dec.equals(texto)){
			System.out.println("Fallo en prueba1: El texto cifrado y luego descifrado no coincide");
		}
	}


	public static void prueba2(){
		File archivo = new File("./pruebas/Archivo.txt");
		String resultado = "QXJjaGl2byBxdWUgdm95IGEgY2lmcmFy";
		String enc = null;
		try { enc = new String(base64.cifrar64(archivo)); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 2");
			e.printStackTrace();
			return;
		}
		if(!resultado.equals(enc)){
			System.out.println("Fallo en la prueba2: el archivo cifrado en base64 no coincide con el resultado esperado");
		}
	}

	public static void prueba3(){
		File archivo = new File("./pruebas/Archivo.txt.64");
		String resultado = "Archivo que voy a cifrar";
		String dec = null;
		try { dec = new String(base64.descifrar64(archivo)); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 3");
			e.printStackTrace();
			return;
		}
		if(!resultado.equals(dec)){
			System.out.println("Fallo en la prueba3: el archivo cifrado en base64 no coincide con el resultado esperado");
		}
	}

	public static void prueba4(){
		String texto = "Archivo que voy a cifrar";
		File destino = new File("./pruebas/Archivo.txt.64.prueba");
		if(destino.exists()) destino.delete();
		try { destino = base64.cifrar64(texto, destino.getAbsolutePath()); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 4");
			e.printStackTrace();
			return;
		}
		System.out.println("\tComprobar Archivo " + destino.getAbsolutePath() + " y descifrarlo para comprobar si es: " + texto);
	}

	public static void prueba5(){
		String texto_enc = "QXJjaGl2byBxdWUgdm95IGEgY2lmcmFyCg==";
		String resultado = "Archivo que voy a cifrar";
		File destino = new File("./pruebas/Archivo.txt.64.prueba2");
		if(destino.exists()) destino.delete();
		try { destino = base64.descifrar64(texto_enc, destino.getAbsolutePath()); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 5");
			e.printStackTrace();
			return;
		}
		System.out.println("\tComprobar Archivo " + destino.getAbsolutePath() + " y comprobar si contiene: " + resultado);
	}

	public static void prueba6(){
		File archivo = new File("./pruebas/Archivo.txt");
		File archivo_enc = new File("./pruebas/Archivo.enc.64");
		File archivo_dec = new File("./pruebas/Archivo.dec.64");
		if(archivo_enc.exists()) archivo_enc.delete();
		if(archivo_dec.exists()) archivo_dec.delete();
		try {
			archivo_enc = base64.cifrar64(archivo, archivo_enc.getAbsolutePath());
			archivo_dec = base64.descifrar64(archivo_enc, archivo_dec.getAbsolutePath());
		} catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 6");
			e.printStackTrace();
			return;
		}
		System.out.println("\tComprobar el archivo " + archivo_dec.getAbsolutePath() + " y ver si es igual que " + archivo.getAbsolutePath());
	}
}
