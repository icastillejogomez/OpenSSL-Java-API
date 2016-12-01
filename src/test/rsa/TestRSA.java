package test.rsa;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import excepciones.OpenSSL_Exception;
import interfaces.rsa.RSA_Interfaz;
import openssl.rsa.RSA;
import openssl.tmp.Tmp;
import openssl.util.Util;

public class TestRSA {

	public static RSA_Interfaz rsa = new RSA();

	public static void main(String[] args) throws IOException, OpenSSL_Exception {
		
		long t = System.currentTimeMillis();


		System.out.println("Comenzando las pruebas de OpenSSL (RSA)");

		System.out.println("Empezando la prueba 1");
		prueba1();
		System.out.println("\tPrueba 1 terminada");

		System.out.println("Empezando la prueba 2");
		prueba2();
		System.out.println("\tPrueba 2 terminada");

		System.out.println("Empezando la prueba 3");
		prueba3();
		System.out.println("\tPrueba 3 terminada");

		System.out.println("Empezando la prueba 4");
		prueba4();
		System.out.println("\tPrueba 4 terminada");

		System.out.println("Empezando la prueba 5");
		prueba5();
		System.out.println("\tPrueba 5 terminada");

		System.out.println("Empezando la prueba 6");
		prueba6();
		System.out.println("\tPrueba 6 terminada");
		
		System.out.println("Empezando la prueba 7");
		prueba7();
		System.out.println("\tPrueba 7 terminada");

		System.out.println("Secuencia de pruebas OpenSSL (RSA) terminada en " + (System.currentTimeMillis() - t) + "ms.");

	}


	public static void prueba1(){
		File clavePrivada = new File("./pruebas_rsa/ClavePrivada.test.pem");
		int bits = 4096;
		if(clavePrivada.exists()) clavePrivada.delete();
		try { clavePrivada = rsa.genRSA(bits, clavePrivada.getAbsolutePath()); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 1");
			e.printStackTrace();
			return;
		}
		System.out.println("\tSe ha creado una clave privada de " + bits  + " bits en " + clavePrivada.getAbsolutePath());
	}

	public static void prueba2(){
		File clavePrivada = new File("./pruebas_rsa/ClavePrivada.test.pem");
		File clavePublica = null;
		try { clavePublica = rsa.getClavePublica(clavePrivada, "./pruebas_rsa/ClavePublica.test.pem"); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 2");
			e.printStackTrace();
			return;
		}
		System.out.println("\tComprobar si se ha creado la clave publica en " + clavePublica);
	}

	public static void prueba3(){
		String texto = "Archivo que voy a encriptar";
		File clavePrivada = new File("./pruebas_rsa/ClavePrivada.test.pem");
		File clavePublica = new File("./pruebas_rsa/ClavePublica.test.pem");
		byte[] enc = null;
		try { enc = rsa.cifrarRSA(texto.getBytes(), clavePublica); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 3");
			e.printStackTrace();
			return;
		}

		System.out.println("\tEl texto \"" + texto + "\" se ha encriptado en el array de " + enc.length + " bytes");
		// for(int i = 0 ; i < enc.length ; i++ ) System.out.print( enc[i] + ( (i < enc.length-1) ? ", " : "\n"));
		byte[] dec = null;
		try { dec = rsa.descifrarRSA(enc, clavePrivada); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 3");
			e.printStackTrace();
			return;
		}
		if(!texto.equals(new String(dec))){
			System.out.println("Fallo en la prueba 4: El texto original no es lo mismo que el cifrado");
		}
		System.out.println("\tEl texto desencriptado es: " + new String(dec));
	}

	public static void prueba4() throws IOException{
		String texto = "Archivo que voy a cifrar";
		File archivo_enc = new File("./pruebas_rsa/Prueba4.txt");
		if(archivo_enc.exists()) archivo_enc.delete();
		File clavePublica = new File("./pruebas_rsa/ClavePublica.test.pem");
		try { archivo_enc = rsa.cifrarRSA(texto.getBytes(), clavePublica, archivo_enc.getAbsolutePath()); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 4");
			e.printStackTrace();
			return;
		}
		byte[] text_enc = FileUtils.readFileToByteArray(archivo_enc);
		File archivo_dec = new File("./pruebas_rsa/Prueba4.dec.txt");
		File clavePrivada = new File("./pruebas_rsa/ClavePrivada.test.pem");
		if(archivo_dec.exists()) archivo_dec.delete();
		try { rsa.descifrarRSA(text_enc, clavePrivada, archivo_dec.getAbsolutePath()); } catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 4");
			e.printStackTrace();
			return;
		}
		byte[] text_dec = FileUtils.readFileToByteArray(archivo_dec);
		if(!texto.equals(new String(text_dec))){
			System.out.println("Fallo en la prueba 4 no cifra y descifra bien: " + new String(text_dec));
		}
		System.out.println("\tPrueba correcta: " + new String(text_dec));

	}

	
	public static void prueba5() throws IOException, OpenSSL_Exception{
		File clavePublica = new File("./pruebas_rsa/ClavePublica.test.pem");
		File clavePrivada = new File("./pruebas_rsa/ClavePrivada.test.pem");
		String texto = "Archivo que voy a cifrar";
		File tmp = Tmp.newtmp();
		FileUtils.writeByteArrayToFile(tmp, texto.getBytes());

		byte[] enc = rsa.cifrarRSA(tmp, clavePublica);

		if(!new String(rsa.descifrarRSA(enc, clavePrivada)).equals(texto)){
			System.out.println("Fallo en la prueba 5, fallo al cifrar");
			return;
		}

		Tmp.deletetmp(tmp);
		tmp = Tmp.newtmp();

		tmp = rsa.cifrarRSA(texto.getBytes(), clavePublica, tmp.getAbsolutePath());

		byte[] dec = rsa.descifrarRSA(tmp, clavePrivada);
		if(!texto.equals(new String(dec))){
			System.out.println("Fallo en la prueba 5, fallo al descifrar");
			return;
		}
		Tmp.deletetmp(tmp);
		System.out.println("\tPrueba correcta: " + new String(dec));
	}

	public static void prueba6() throws OpenSSL_Exception{
		File clavePublica = new File("./pruebas_rsa/ClavePublica.test.pem");
		File clavePrivada = new File("./pruebas_rsa/ClavePrivada.test.pem");
		String texto = "Texto que voy a firmar y luego verificar";
		byte[] enc = null , dec = null;
		enc = rsa.firmarRSA(texto.getBytes(), clavePrivada);
		dec = rsa.verificarRSA(enc, clavePublica);
		if(!texto.equals(new String(dec))){
			System.out.println("Fallo en la prueba 6: " + new String(dec));
			return;
		}
		System.out.println("\tPrueba correcta: " + new String(dec));
	}

	public static void prueba7() throws OpenSSL_Exception{
		File clavePublica = new File("./pruebas_rsa/ClavePublica.test.pem");
		File clavePrivada = new File("./pruebas_rsa/ClavePrivada.test.pem");
		String texto = "Texto que voy a firmar y luego verificar";
		byte[] enc = null , dec = null;
		File tmp = Tmp.newtmp();
		Util util = new Util();
		util.guardarArchivo(texto.getBytes(), tmp.getAbsolutePath());
		enc = rsa.firmarRSA(tmp, clavePrivada);
		util.guardarArchivo(enc, tmp.getAbsolutePath());
		dec = rsa.verificarRSA(tmp, clavePublica);
		if(!texto.equals(new String(dec))){
			System.out.println("Fallo en la prueba 7, no coincide el verificado " + new String(dec));
			Tmp.deletetmp(tmp);
			return;
		}
		Tmp.deletetmp(tmp);
		System.out.println("\tPrueba correcta: " + new String(dec));
	}

}
