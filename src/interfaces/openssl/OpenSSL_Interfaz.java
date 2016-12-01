package interfaces.openssl;

public interface OpenSSL_Interfaz {

	public interfaces.aes.AES_Interfaz getAES();
	
	public interfaces.base64.BASE64_Interfaz getBASE64();
	
	public interfaces.hash.HASH_Interfaz getHASH();
	
	public interfaces.rand.Rand_Interfaz getRAND();
	
	public interfaces.rsa.RSA_Interfaz getRSA();
	
	public interfaces.util.UTIL_Interfaz getUTIL();
	
	public interfaces.x509.X509_Interfaz getX509();
	
	
}
