package test.hash;

import java.io.File;

import excepciones.OpenSSL_Exception;
import openssl.hash.Hash;
import openssl.util.Util;

public class TestHash{

	private static final Hash hash = new Hash();
	private static final Util util = new Util();
	
	private static final File archivo = new File("./pruebas_hash/Archivo.txt");
	
			
	private static final String md5 = "0f0806e467f03efff5eca81c6cf5d952";
	private static final String sha = "9b1f556027e4893c2d61eeb16ce0e35fccf495c8";
	private static final String sha1 = "0e51f2d58742e0f2ed6ff3fbc441d7dea1863f5d";
	private static final String sha256 = "4083404091bd5d54c7fc4e9643defa314f7f07a4f450e1aa6d2754a225708c5b";

	
	public static void main(String[] args) throws OpenSSL_Exception {

		System.out.println("Comenzando las pruebas de OpenSSL (dgst)");

		String texto = new String(util.leerArchivo(archivo));
				
		if(!md5.equals(new String(hash.MD5(archivo))) || !md5.equals(new String(hash.MD5(texto))))
			System.out.println("\tMD5\tERROR");
		else System.out.println("\tMD5\tOK");
		
		if(!sha.equals(new String(hash.SHA(archivo))) || !sha.equals(new String(hash.SHA(texto))))
			System.out.println("\tSHA\tERROR");
		else System.out.println("\tSHA\tOK");
		
		if(!sha1.equals(new String(hash.SHA1(archivo))) || !sha1.equals(new String(hash.SHA1(texto))))
			System.out.println("\tSHA1\tERROR");
		else System.out.println("\tSHA1\tOK");
		
		if(!sha256.equals(new String(hash.SHA256(archivo))) || !sha256.equals(new String(hash.SHA256(texto))))
			System.out.println("\tSHA256\tERROR");
		else System.out.println("\tSHA256\tOK");

		System.out.println("Finalizadas las pruebas de OpenSSL (dgst)");
		
	}

	
}
