package interfaces.x509;

import java.io.File;

import excepciones.OpenSSL_Exception;

public interface X509_Interfaz {
	
	// --- > CA
	
	public File genCA(File clavePrivada , String rutaDestino , int dias , String[] datos) throws OpenSSL_Exception;
	
	public File firmarPeticion(File certificadoCA , File clavePrivadaCA , File peticion , String rutaDestino , int dias) throws OpenSSL_Exception;
	
	// --- > x509
	
	public String x509toString(File certificado) throws OpenSSL_Exception;
	
	public String csrToString(File CSR) throws OpenSSL_Exception;
	
	public File genPeticion(File clavePrivada , String rutaDestino , String[] datos) throws OpenSSL_Exception;
	
	// --- > Converter
	
	public File PEMtoDER(File certificado , String rutaDestino) throws OpenSSL_Exception;
	
	public File DERtoPEM(File certificado , String rutaDestino) throws OpenSSL_Exception;
		
	// --- > Validar un certificado (null) si es valido sino error
	
	public String validarX509(File certificadoCA , File certificado) throws OpenSSL_Exception;
	
	// --- > Cifrar con la clave publica
	
	public byte[] cifrarX509(byte[] texto , File certificado) throws OpenSSL_Exception;
	
	public byte[] cifrarX509(File archivo , File certificado) throws OpenSSL_Exception;
	
	// --- > Verificar con la clave publica
	
	public byte[] verificarX509(byte[] texto , File certificado) throws OpenSSL_Exception;
	
	public byte[] verificarX509(File archivo , File certificado) throws OpenSSL_Exception;
	
	// --- > Getters (clave publica, emisor, propietario, validez , etc...)
	
	public File getClavePublicaX509(File certificado , String rutaDestino) throws OpenSSL_Exception;
	
	public String getEmisor(File certificado) throws OpenSSL_Exception;
	
	public String getPropietario(File certificado) throws OpenSSL_Exception;
	
	public String getFingerprintMD5(File certificado) throws OpenSSL_Exception;
	
	public String getFingerprintSHA1(File certificado) throws OpenSSL_Exception;
	
	public String getFingerprintSHA256(File certificado) throws OpenSSL_Exception;
	
	public String[] getFechas(File certificado) throws OpenSSL_Exception;
	
	public String getEmail(File certificado) throws OpenSSL_Exception;
	
	public String getHashPropietario(File certificado) throws OpenSSL_Exception;
	
	public String getNumeroSerie(File certificado) throws OpenSSL_Exception;

	
}
