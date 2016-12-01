package test.x509;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import excepciones.OpenSSL_Exception;
import interfaces.x509.X509_Interfaz;
import openssl.rsa.RSA;
import openssl.util.Util;
import openssl.x509.X509;

public class TestX509 {

	public static final X509_Interfaz x509 = new X509();
	public static File clavePrivada = new File("./pruebas_x509/privada.pem");

	public static void main(String[] args) throws OpenSSL_Exception, IOException{

		long t = System.currentTimeMillis();

		System.out.println("Empezando la secuencia de pruebas de OpenSSL (x509)\n");

		System.out.println("\t1)  Generar un CA:\t\t\t" + generarCA());

		System.out.println("\t2)  Generar CSR:\t\t\t" + generarCSR());

		System.out.println("\t3)  Firmando CSR con CA:\t\t" + firmarCSR());

		System.out.println("\t4)  Imprimiendo la CA:\n\n" + imprimirCertificado() + "\n");

		System.out.println("\t5)  Imprimiendo la CSR:\n\n" + imprimirPeticion() + "\n");

		System.out.println("\t6)  Imprimiendo el CSR firmado:\n\n" + imprimirCertificadoUsuario());

		System.out.println("\t7)  PEM <-> DER:\t\t\t" + pemtoder());

		System.out.println("\t8)  Verificando certificados:\t\t" + verificarCertificados());

		System.out.println("\t9)  Obteniendo publica.pem de CA:\t" + getPublica());

		System.out.println("\t10) Emisor del CSR firmado:\t\t" + getEmisor());

		System.out.println("\t11) Propietario del CSR firmado:\t" + getPropietario());

		System.out.println("\t12) Fingerprint MD5 del CSR firmado:\t" + getFingerprintMD5());

		System.out.println("\t13) Fingerprint SHA1 del CSR firmado:\t" + getFingerprintSHA1());

		System.out.println("\t14) Fingerprint SHA256 del CSR firmado:\t" + getFingerprintSHA256());

		System.out.println("\t15) e-mail del CSR firmado:\t\t" + getEmail());
		
		System.out.println("\t16) Hash del propietario del CSR:\t" + getHashPropietario());
		
		System.out.println("\t17) Nº serie del CSR firmado:\t\t" + getNSerie());
		
		System.out.println("\t18) Fechas del CSR:\t\t\t" + getFechas());
		
		System.out.println("\t19) Cifrando mensaje:\t\t\t" + cifrar());
		
		System.out.println("\t20) Verificando firma:\t\t\t" + verificarFirma());

		System.out.println("\nSecuencia de pruebas OpenSSL (x509) finalizada en " + (System.currentTimeMillis() - t) + "ms.");
	}

	private static String verificarFirma() {
		String texto = "Texto correcto del mensaje";
		File certificado = new File("./pruebas_x509/CA.pem");
		File privada = new File("./pruebas_x509/privada.pem");
		RSA rsa = new RSA();
		Util util = new Util();
		File archivo = new File("./pruebas_x509/Archivo.txt.sign");
		if(archivo.exists()) archivo.delete();
		byte[] enc = null , dec = null;
		try {
			enc = rsa.firmarRSA(texto.getBytes(), privada);
			dec = x509.verificarX509(enc, certificado);
			if(!texto.equals(new String(dec))) return "ERROR: no son iguales \"" + new String(dec) + "\"";
			archivo = util.guardarArchivo(rsa.firmarRSA(texto.getBytes(), privada), archivo.getAbsolutePath());
			dec = x509.verificarX509(archivo, certificado);
			if(!texto.equals(new String(dec))) return "ERROR: no son iguales \"" + new String(dec) + "\"";
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
		return "CORRECTO";
	}

	private static String cifrar() {
		String texto = "Texto correcto del mensaje";
		File certificado = new File("./pruebas_x509/CA.pem");
		File privada = new File("./pruebas_x509/privada.pem");
		RSA rsa = new RSA();
		byte[] enc = null , dec = null;
		try {
			enc = x509.cifrarX509(texto.getBytes(), certificado);
			dec = rsa.descifrarRSA(enc, privada);
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
		if(!texto.equals(new String(dec))) return "ERROR: no son iguales \"" + new String(dec) + "\"";
		File archivo = new File("./pruebas_x509/Archivo.txt");
		if(archivo.exists()) archivo.delete();
		Util util = new Util();
		try {
			archivo = util.guardarArchivo(texto.getBytes(), archivo.getAbsolutePath());
			enc = x509.cifrarX509(archivo, certificado);
			dec = rsa.descifrarRSA(enc, privada);
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
		if(!texto.equals(new String(dec))) return "ERROR: no son iguales \"" + new String(dec) + "\"";
		return "CORRECTO";
	}

	private static String getFechas() {
		File certificado = new File("./pruebas_x509/CUser.pem");
		try {
			String[] fechas =  x509.getFechas(certificado);
			if(fechas == null || fechas.length != 2) return "ERROR: no devuelve el formato adecuando";
			return "CORRECTO -> A partir de " + fechas[0] + " hasta " + fechas[1];
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private static String getNSerie() {
		File certificado = new File("./pruebas_x509/CUser.pem");
		try {
			return "CORRECTO -> " + x509.getNumeroSerie(certificado);
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private static String getHashPropietario() {
		File certificado = new File("./pruebas_x509/CUser.pem");
		try {
			return "CORRECTO -> " + x509.getHashPropietario(certificado);
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private static String getEmail() {
		File certificado = new File("./pruebas_x509/CUser.pem");
		try {
			return "CORRECTO -> " + x509.getEmail(certificado);
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private static String getFingerprintSHA256() {
		File certificado = new File("./pruebas_x509/CUser.pem");
		try {
			return "CORRECTO -> " + x509.getFingerprintSHA256(certificado);
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private static String getFingerprintSHA1() throws OpenSSL_Exception {
		File certificado = new File("./pruebas_x509/CUser.pem");
		try {
			return "CORRECTO -> " +  x509.getFingerprintSHA1(certificado);
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private static String getFingerprintMD5() throws OpenSSL_Exception {
		File certificado = new File("./pruebas_x509/CUser.pem");
		try {
			return "CORRECTO -> " +  x509.getFingerprintMD5(certificado);
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private static String getPropietario() throws OpenSSL_Exception {
		File certificado = new File("./pruebas_x509/CUser.pem");
		try {
			return "CORRECTO -> " +  x509.getPropietario(certificado);
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private static String getEmisor() throws OpenSSL_Exception {
		File certificado = new File("./pruebas_x509/CUser.pem");
		try {
			return "CORRECTO -> " +  x509.getEmisor(certificado);
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private static String getPublica(){
		File publica = new File("./pruebas_x509/publica.pem");
		if(publica.exists()) publica.delete();
		File CA = new File("./pruebas_x509/CA.pem");
		try {
			publica = x509.getClavePublicaX509(CA, publica.getAbsolutePath());
			return "CORRECTO -> " + publica.getAbsolutePath();
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private static String verificarCertificados() throws OpenSSL_Exception {
		File CA1 = new File("./pruebas_x509/validar/CA1.pem");
		File CA2 = new File("./pruebas_x509/validar/CA2.pem");
		File U1 = new File("./pruebas_x509/validar/User1.pem");
		File U2 = new File("./pruebas_x509/validar/User2.pem");
		if(!CA1.exists() || !CA2.exists() || !U1.exists() || !U2.exists()) throw new OpenSSL_Exception("Faltan los archivos necesarios para hacer el test verificar: CA1.pem CA2.pem User1.pem User2.pem");
		String v1 , v2 , v3 , v4;
		v1 = x509.validarX509(CA1, U1);
		v2 = x509.validarX509(CA2, U2);
		v3 = x509.validarX509(CA1, U2);
		v4 = x509.validarX509(CA2, U1);
		if(v1 == null && v2 == null && v3 != null && v4 != null)
			return "CORRECTO";
		return v1 + "\n" + v2 + "\n" + v3 + "\n" + v4 + "\n";
	}

	private static String pemtoder() throws OpenSSL_Exception, IOException {
		File certificadopem = new File("./pruebas_x509/CA.pem");
		File certificadoder = new File("./pruebas_x509/CA.der");
		File certificadopem2 = new File("./pruebas_x509/CA2.pem");
		if(certificadopem2.exists()) certificadopem2.delete();
		if(certificadoder.exists()) certificadoder.delete();
		certificadoder = x509.PEMtoDER(certificadopem, certificadoder.getAbsolutePath());
		certificadopem2 = x509.DERtoPEM(certificadoder, certificadopem2.getAbsolutePath());
		return (FileUtils.contentEquals(certificadopem, certificadopem2))? "CORRECTO" : "ERROR";
	}

	private static String imprimirCertificadoUsuario() throws OpenSSL_Exception {
		File CSR = new File("./pruebas_x509/CUser.pem");
		return x509.x509toString(CSR);
	}

	private static String imprimirPeticion() throws OpenSSL_Exception {
		File CSR = new File("./pruebas_x509/CSR.pem");
		return x509.csrToString(CSR);
	}

	private static String imprimirCertificado() throws OpenSSL_Exception {
		File certificadoCA = new File("./pruebas_x509/CA.pem");
		return x509.x509toString(certificadoCA);
	}

	private static String firmarCSR() {
		File certificado = new File("./pruebas_x509/CUser.pem");
		if(certificado.exists()) certificado.delete();
		try {
			certificado = x509.firmarPeticion(new File("./pruebas_x509/CA.pem"), clavePrivada, new File("./pruebas_x509/CSR.pem"), certificado.getAbsolutePath(), 2);
			return "CORRECTO -> " + certificado.getAbsolutePath();
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private static String generarCSR() {
		File csr = new File("./pruebas_x509/CSR.pem");
		if(csr.exists()) csr.delete();
		String[] datos = new String[]{"ES" , "Madrid" , "Madrid" , "Cliente de cloud" , 
				"Cliente para iPhone" , "Certificado usuario" , "nacho@nachooagoomez.com"};
		try {
			csr = x509.genPeticion(clavePrivada, csr.getAbsolutePath(), datos);
			return "CORRECTO -> " + csr.getAbsolutePath();
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	public static String generarCA(){
		File certificadoCA = new File("./pruebas_x509/CA.pem");
		if(certificadoCA.exists()) certificadoCA.delete();
		String[] datos = new String[]{"ES" , "Madrid" , "Madrid" , "Autoridad de Certificacion de Ignacio F. Castillejo Gomez" , 
				"CA de nachoogoomez" , "CA Ignacio Castillejo" , "nacho@nachoogoomez.com"};

		try {
			certificadoCA = x509.genCA(clavePrivada, certificadoCA.getAbsolutePath(), 2, datos);
			return "CORRECTO -> " + certificadoCA.getAbsolutePath();
		} catch (OpenSSL_Exception e) {
			return "ERROR: " + e.getMessage();
		}

	}

}
