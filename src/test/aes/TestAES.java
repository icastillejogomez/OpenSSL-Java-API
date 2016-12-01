package test.aes;

import java.io.File;
import excepciones.OpenSSL_Exception;
import interfaces.aes.AES_Interfaz;
import openssl.aes.AES;

public class TestAES {

	public static AES_Interfaz aes = new AES();

	public static void main(String[] args) {

		System.out.println("Empezando secuencia de pruebas de OpenSSL (AES)");

		System.out.println("Comenzando prueba 1");
		prueba1();
		System.out.println("\tPrueba 1 finalizada");

		System.out.println("Comenzando prueba 2");
		prueba2();
		System.out.println("\tPrueba 2 finalizada");


		System.out.println("Comenzando prueba 3");
		prueba3();
		System.out.println("\tPrueba 3 finalizada");

		System.out.println("Comenzando prueba 4");
		prueba4();
		System.out.println("\tPrueba 4 finalizada");

		System.out.println("Comenzando prueba 5");
		prueba5();
		System.out.println("\tPrueba 5 finalizada");
		
		
		System.out.println("Comenzando prueba 6");
		prueba6();
		System.out.println("\tPrueba 6 finalizada");

		System.out.println("Fin de la secuencia de pruebas de openSSL (AES)");
	}


	// public byte[] (des)cifrar(String texto, String contraseña)
	public static void prueba1(){
		String texto = "Archivo que voy a cifrar";
		String contraseña = "qwerty";
		byte[] enc = null, dec = null;

		try { enc = aes.cifraraes(texto, contraseña); } 
		catch (OpenSSL_Exception e) { System.out.println("Fallo prueba 1:"); e.printStackTrace(); }
		
		
		try { dec = aes.descifraraes(new String(enc), contraseña); } 
		catch (OpenSSL_Exception e) { System.out.println("Fallo prueba 1:"); e.printStackTrace(); }

		if(!texto.equals(new String(dec))) System.out.println("Fallo prueba 1: no coincide el texto original con el descifrado del mismo");

	}

	// public byte[] cifrar(File archivo, String contraseña) 
	public static void prueba2(){
		File archivo = new File("/Users/nacho/Documents/pruebas/openssl/aes/Archivo.txt");
		String contraseña = "qwerty";
		byte[] enc = null, dec = null;

		try { enc = aes.cifraraes(archivo, contraseña); } 
		catch (OpenSSL_Exception e) { System.out.println("Fallo prueba 2:"); e.printStackTrace(); }


		try { dec = aes.descifraraes(new String(enc), contraseña); } 
		catch (OpenSSL_Exception e) { System.out.println("Fallo prueba 2:"); e.printStackTrace(); }

		if(!"Archivo que voy a cifrar".equals(new String(dec))) System.out.println("Fallo prueba 2: no coincide el texto original con el descifrado del mismo");

	}

	// public byte[] descifrar(File archivo, String contraseña) 
	public static void prueba3(){

		File archivo = new File("/Users/nacho/Documents/pruebas/openssl/aes/Archivo.txt.enc");
		String contraseña = "qwerty";
		byte[]  dec = null;

		try { dec = aes.descifraraes(archivo, contraseña); } 
		catch (OpenSSL_Exception e) { System.out.println("Fallo prueba 3:"); e.printStackTrace(); }

		if(!"Archivo que voy a cifrar".equals(new String(dec))) System.out.println("Fallo prueba 3: no coincide el texto original con el descifrado del mismo");
	}


	// File cifrar(String texto, String contraseña, String ruta_destino)
	public static void prueba4(){
		String texto = "Archivo que voy a cifrar";
		String contraseña = "qwerty";
		String ruta_destino = "./pruebas/archivo.enc";
		File archivo_cifrado = new File("./pruebas/archivo.enc");
		if(archivo_cifrado.exists()) archivo_cifrado.delete();
		try { archivo_cifrado = aes.cifraraes(texto, contraseña, ruta_destino); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo prueba 4:");
			e.printStackTrace();
			return;
		}
	}


	// File descifrar(String texto, String contraseña, String ruta_destino)
	public static void prueba5(){
		String texto_cifrado = "U2FsdGVkX1+EZdtxktKmCgqOugXFViOgbHbxZV+EgvAh1JFSDWapnLz0Ci3qIyG/";
		String contraseña = "qwerty";
		String ruta_destino = "./pruebas/texto.dec";
		File archivo = new File(ruta_destino);
		if(archivo.exists()) archivo.delete();
		try { archivo = aes.descifraraes(texto_cifrado, contraseña, ruta_destino); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 5");
			e.printStackTrace();
			return;
		}
	}

	// File (des)cifrar(File archivo, String contraseña, String ruta_destino)
	public static void prueba6(){
		File archivo = new File("./pruebas/Archivo.txt");
		String contraseña = "qwerty";
		String ruta_destino = "./pruebas/Archivo.txt.enc";
		File archivo_enc = new File(ruta_destino);
		if(archivo_enc.exists()) archivo_enc.delete();
		try { archivo_enc = aes.cifraraes(archivo, contraseña, ruta_destino); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 6");
			e.printStackTrace();
		}
		String ruta_destino2 = "./pruebas/Archivo.txt.dec";
		File archivo_dec = new File(ruta_destino2);
		if(archivo_dec.exists()) archivo_dec.delete();
		try { archivo_dec = aes.descifraraes(archivo_enc, contraseña, ruta_destino2); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 6");
			e.printStackTrace();
		}
		
		
	}



}



