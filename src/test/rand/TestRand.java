package test.rand;

import excepciones.OpenSSL_Exception;
import openssl.rand.Rand;

public class TestRand {
	
	public static interfaces.rand.Rand_Interfaz rand = new Rand();

	public static void main(String[] args) {
		System.out.println("Comenzando las pruebas de OpenSSL (Rand)");

		System.out.println("Empezando la prueba 1");
		prueba1();
		System.out.println("\tPrueba 1 terminada");
		
		System.out.println("Empezando la prueba 2");
		prueba2();
		System.out.println("\tPrueba 2 terminada");
		

		System.out.println("Finalizadas las pruebas de OpenSSL (Rand)");
	}

	private static void prueba1() {
		try {
			String texto = rand.rand(100);
			System.out.println(texto);
		} catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 1");
		}
	}

	private static void prueba2() {
		try {
			String texto = rand.randhex(100);
			System.out.println(texto);
		} catch (OpenSSL_Exception e) {
			System.out.println("Fallo en la prueba 1");
		}
	}

}
