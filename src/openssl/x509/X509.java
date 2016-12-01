package openssl.x509;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import excepciones.OpenSSL_Exception;
import interfaces.x509.X509_Interfaz;
import openssl.tmp.Tmp;
import openssl.util.Util;

public class X509 implements X509_Interfaz{

	public File genCA(File clavePrivada, String rutaDestino, int dias, String[] datos) throws OpenSSL_Exception {
		// --- > Obtenemos la informacion
		String informacion = "/C=" + datos[0] + "/ST=" + datos[1] + "/L=" + datos[2] + "/O=" + datos[3] + "/OU=" + datos[4] + "/CN=" + datos[5] + "/emailAddress=" + datos[6] + "/";
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "req" , "-new" , "-x509" , "-days" , String.valueOf(dias) , "-key" , clavePrivada.getAbsolutePath() ,
				"-set_serial" , "1" , "-out" , rutaDestino , "-subj" , informacion};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar (que no deberia haber)
		try {
			while( (linea = in.readLine()) != null) salida1 += linea + "\n";
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida estandar
		if(!salida1.equals("")) throw new OpenSSL_Exception("Error al generar CA: no deberia haber salida:\n" + salida1);
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido crear la CA:\n" + salida2);
		return new File(rutaDestino);
	}

	public File firmarPeticion(File certificadoCA, File clavePrivadaCA, File peticion, String rutaDestino, int dias) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-req" , "-days" , String.valueOf(dias) , "-in" , peticion.getAbsolutePath(),
				"-CA" , certificadoCA.getAbsolutePath() , "-CAkey" ,clavePrivadaCA.getAbsolutePath() , "-set_serial" , "1" , "-out" , rutaDestino};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida del comando 
		try {
			linea = error.readLine();
			if(linea != null && !linea.equals("Signature ok")) salida2 += linea + "\n";
			linea = error.readLine();
			if(linea != null && !linea.substring(0,8).equals("subject=")) salida2 += linea + "\n";
			linea = error.readLine();
			if(linea != null && !linea.equals("Getting CA Private Key")) salida2 += linea + "\n";
			while((linea = error.readLine()) != null) salida2 += linea + "\n" ;
			if(!salida2.equals("")) throw new OpenSSL_Exception("Error al firmar la peticion\n" + salida2);
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		return new File(rutaDestino);
	}

	public String x509toString(File certificado) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-in" , certificado.getAbsolutePath() , "-noout" , "-text"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos el resultado por la salida estandart
		try { while( (linea = in.readLine()) != null) salida1 += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida de convertir un certificado a String");
		}
		// --- > Leemos la salida error por si lo hubiera
		try { while( (linea = error.readLine()) != null) salida2 += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del error de convertir un certificado a String");
		}
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error al intentar convertir un certificado en una cadena de texto.\n" + salida2);
		return salida1;
	}

	public String csrToString(File CSR) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "req" , "-in" , CSR.getAbsolutePath() , "-noout" , "-text"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos el resultado por la salida estandart
		try { while( (linea = in.readLine()) != null) salida1 += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida de convertir un certificado a String");
		}
		// --- > Leemos la salida error por si lo hubiera
		try { while( (linea = error.readLine()) != null) salida2 += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del error de convertir un certificado a String");
		}
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error al intentar convertir un certificado en una cadena de texto.\n" + salida2);
		return salida1;
	}

	public File genPeticion(File clavePrivada, String rutaDestino, String[] datos) throws OpenSSL_Exception {
		// --- > Obtenemos la informacion
		String informacion = "/C=" + datos[0] + "/ST=" + datos[1] + "/L=" + datos[2] + "/O=" + datos[3] + "/OU=" + datos[4] + "/CN=" + datos[5] + "/emailAddress=" + datos[6] + "/";
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "req" , "-new" , "-key" , clavePrivada.getAbsolutePath() , "-out" , rutaDestino , "-subj" , informacion};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar (que no deberia haber)
		try {
			while( (linea = in.readLine()) != null) salida1 += linea + "\n";
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida estandar
		if(!salida1.equals("")) throw new OpenSSL_Exception("Error al generar CSR: no deberia haber salida:\n" + salida1);
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido crear la CSR:\n" + salida2);
		return new File(rutaDestino);
	}

	public File PEMtoDER(File certificado, String rutaDestino) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-inform" , "PEM" , "-in" , certificado.getAbsolutePath() , "-outform" , "DER" , "-out" , rutaDestino};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar (que no deberia haber)
		try {
			while( (linea = in.readLine()) != null) salida1 += linea + "\n";
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida estandar
		if(!salida1.equals("")) throw new OpenSSL_Exception("Error al convertir el certificado: no deberia haber salida:\n" + salida1);
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido convertir el certificado:\n" + salida2);
		return new File(rutaDestino);
	}

	public File DERtoPEM(File certificado, String rutaDestino) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-inform" , "DER" , "-in" , certificado.getAbsolutePath() , "-outform" , "PEM" , "-out" , rutaDestino};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar (que no deberia haber)
		try {
			while( (linea = in.readLine()) != null) salida1 += linea + "\n";
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida estandar
		if(!salida1.equals("")) throw new OpenSSL_Exception("Error al convertir el certificado: no deberia haber salida:\n" + salida1);
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido convertir el certificado:\n" + salida2);
		return new File(rutaDestino);
	}

	public String validarX509(File certificadoCA, File certificado) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "verify" , "-CAfile" , certificadoCA.getAbsolutePath() , certificado.getAbsolutePath()};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar (con error y sin error(ok) )
		try {
			while( (linea = in.readLine()) != null) salida1 += linea; // --- > Leemos todo en una linea analizamos al final
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido convertir el certificado:\n" + salida2);
		// --- > Comprobamos si el certificado no devuelve OK (correcto) sino INCORRECTO
		String correcto = certificado.getAbsolutePath() + ": OK";
		return (salida1.equals(correcto))? null : salida1;
	}

	public byte[] cifrarX509(byte[] texto, File certificado) throws OpenSSL_Exception {
		// --- > Creamos el util
		Util util = new Util();
		// --- > Guardamos el texto en un archivo temporal
		File tmp = Tmp.newtmp();
		tmp = util.guardarArchivo(texto, tmp.getAbsolutePath());
		// --- > Creamos el archivo donde ira el cifrado
		File tmp_enc = Tmp.newtmp();
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rsautl" , "-in" , tmp.getAbsolutePath() , "-out" , tmp_enc.getAbsolutePath() , "-inkey" , certificado.getAbsolutePath() , "-certin" , "-encrypt"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar (que no deberia haber)
		try {
			while( (linea = in.readLine()) != null) salida1 += linea + "\n";
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida estandar
		if(!salida1.equals("")) throw new OpenSSL_Exception("Error al cifrar el mensaje: no deberia haber salida:\n" + salida1);
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido cifrar el mensaje:\n" + salida2);
		// --- > Leemos el archivo encriptado
		byte[] resultado = util.leerArchivo(tmp_enc);
		// --- > Borramos los archivos temporales
		Tmp.deletetmp(tmp);
		Tmp.deletetmp(tmp_enc);
		return resultado;
	}

	public byte[] cifrarX509(File archivo, File certificado) throws OpenSSL_Exception {
		// --- > Creamos el util
		Util util = new Util();
		// --- > Creamos el archivo donde ira el cifrado
		File tmp_enc = Tmp.newtmp();
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rsautl" , "-in" , archivo.getAbsolutePath() , "-out" , tmp_enc.getAbsolutePath() , "-inkey" , certificado.getAbsolutePath() , "-certin" , "-encrypt"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar (que no deberia haber)
		try {
			while( (linea = in.readLine()) != null) salida1 += linea + "\n";
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida estandar
		if(!salida1.equals("")) throw new OpenSSL_Exception("Error al cifrar el archivo: no deberia haber salida:\n" + salida1);
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido cifrar el archivo:\n" + salida2);
		// --- > Leemos el archivo encriptado
		byte[] resultado = util.leerArchivo(tmp_enc);
		// --- > Borramos los archivos temporales
		Tmp.deletetmp(tmp_enc);
		return resultado;
	}

	public byte[] verificarX509(byte[] texto, File certificado) throws OpenSSL_Exception {
		// --- > Creamos el util
		Util util = new Util();
		// --- > Guardamos el texto en un archivo temporal
		File tmp = Tmp.newtmp();
		tmp = util.guardarArchivo(texto, tmp.getAbsolutePath());
		// --- > Creamos el archivo donde ira el descifrado
		File tmp_dec = Tmp.newtmp();
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rsautl" , "-in" , tmp.getAbsolutePath() , "-out" , tmp_dec.getAbsolutePath() , "-inkey" , certificado.getAbsolutePath() , "-certin" , "-verify"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar (que no deberia haber)
		try {
			while( (linea = in.readLine()) != null) salida1 += linea + "\n";
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida estandar
		if(!salida1.equals("")) throw new OpenSSL_Exception("Error al verificar mensaje: no deberia haber salida:\n" + salida1);
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido verificar el mensaje:\n" + salida2);
		// --- > Leemos el archivo desencriptado
		byte[] resultado = util.leerArchivo(tmp_dec);
		// --- > Borramos los archivos temporales
		Tmp.deletetmp(tmp);
		Tmp.deletetmp(tmp_dec);
		return resultado;
	}

	public byte[] verificarX509(File archivo, File certificado) throws OpenSSL_Exception {
		// --- > Creamos el util
		Util util = new Util();
		// --- > Creamos el archivo donde ira el descifrado
		File tmp_dec = Tmp.newtmp();
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rsautl" , "-in" , archivo.getAbsolutePath() , "-out" , tmp_dec.getAbsolutePath() , "-inkey" , certificado.getAbsolutePath() , "-certin" , "-verify"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar (que no deberia haber)
		try {
			while( (linea = in.readLine()) != null) salida1 += linea + "\n";
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida estandar
		if(!salida1.equals("")) throw new OpenSSL_Exception("Error al verificar el archivo: no deberia haber salida:\n" + salida1);
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido verificar el archivo:\n" + salida2);
		// --- > Leemos el archivo desencriptado
		byte[] resultado = util.leerArchivo(tmp_dec);
		// --- > Borramos los archivos temporales
		Tmp.deletetmp(tmp_dec);
		return resultado;
	}

	public File getClavePublicaX509(File certificado, String rutaDestino) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-pubkey" , "-noout" , "-in" , certificado.getAbsolutePath()};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar y el error
		try {
			while( (linea = in.readLine()) != null) salida1 += linea + "\n"; // --- > Leemos toda la clave publica
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido obtener la clave publica del certificado:\n" + salida2);
		Util util = new Util();
		return util.guardarArchivo(salida1.getBytes(), rutaDestino);
	}

	public String getEmisor(File certificado) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-noout" , "-in" , certificado.getAbsolutePath() , "-issuer"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar y el error
		try {
			while( (linea = in.readLine()) != null) salida1 += linea; // --- > Leemos toda salida en 1 linea
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido obtener el emisor del certificado:\n" + salida2);
		// --- > issuer= /C=......../ Omitimos "issuer= " 
		return salida1.substring(salida1.indexOf("=") + 2);
	}

	public String getPropietario(File certificado) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-noout" , "-in" , certificado.getAbsolutePath() , "-subject"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar y el error
		try {
			while( (linea = in.readLine()) != null) salida1 += linea; // --- > Leemos toda salida en 1 linea
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido obtener el propietario del certificado:\n" + salida2);
		// --- > subject= /C=......../ Omitimos "issuer= " 
		return salida1.substring(salida1.indexOf("=") + 2);
	}

	public String getFingerprintMD5(File certificado) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-md5" ,  "-noout" , "-in" , certificado.getAbsolutePath() , "-fingerprint"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar y el error
		try {
			while( (linea = in.readLine()) != null) salida1 += linea; // --- > Leemos toda salida en 1 linea
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido obtener el fingerprint del certificado:\n" + salida2);
		// --- > MD5 Fingerprint=7F:08:F5:7B:A1:4B:12:57:AF:D4:4C:E3:75:17:15:87 Omitimos "MD5 Fingerprint="
		return salida1.substring(salida1.indexOf("=") + 1);
	}

	public String getFingerprintSHA1(File certificado) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-sha1" ,  "-noout" , "-in" , certificado.getAbsolutePath() , "-fingerprint"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar y el error
		try {
			while( (linea = in.readLine()) != null) salida1 += linea; // --- > Leemos toda salida en 1 linea
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido obtener el fingerprint del certificado:\n" + salida2);
		// --- > SHA1 Fingerprint=7F:08:F5:7B:A1:4B:12:57:AF:D4:4C:E3:75:17:15:87 Omitimos "SHA1 Fingerprint="
		return salida1.substring(salida1.indexOf("=") + 1);
	}

	public String getFingerprintSHA256(File certificado) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-sha256" ,  "-noout" , "-in" , certificado.getAbsolutePath() , "-fingerprint"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar y el error
		try {
			while( (linea = in.readLine()) != null) salida1 += linea; // --- > Leemos toda salida en 1 linea
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido obtener el fingerprint del certificado:\n" + salida2);
		// --- > SHA256 Fingerprint=7F:08:F5:7B:A1:4B:12:57:AF:D4:4C:E3:75:17:15:87 Omitimos "SHA256 Fingerprint="
		return salida1.substring(salida1.indexOf("=") + 1);
	}

	public String[] getFechas(File certificado) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-noout" , "-in" , certificado.getAbsolutePath() , "-dates"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos el resultado y el error a la vez
		String[] resultado = new String[2];
		int i = 0;
		try {
			while( (linea = in.readLine()) != null){ salida1 += linea + "\n"; resultado[i++] = linea; } // --- > Leemos toda salida en 1 linea
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido obtener las fechas del certificado:\n" + salida2);
		// Comprobamos el tamaño del array
		if(i != 2) throw new OpenSSL_Exception("Error: no se ha podido obtener las fechas del certificado:\n" + salida1);
		// Formateamos el resultado
		for(i = 0 ; i < resultado.length ; i++) resultado[i] = resultado[i].substring(resultado[i].indexOf("=")+1);
		return resultado;
	}

	public String getEmail(File certificado) throws OpenSSL_Exception{
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-noout" , "-in" , certificado.getAbsolutePath() , "-email"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar y el error
		try {
			while( (linea = in.readLine()) != null) salida1 += linea; // --- > Leemos toda salida en 1 linea
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido obtener el emisor del certificado:\n" + salida2);
		// --- > la salida del email es limpia
		return salida1;
	}

	public String getHashPropietario(File certificado) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-noout" , "-in" , certificado.getAbsolutePath() , "-hash"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar y el error
		try {
			while( (linea = in.readLine()) != null) salida1 += linea; // --- > Leemos toda salida en 1 linea
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido obtener el emisor del certificado:\n" + salida2);
		// --- > issuer= /C=......../ Omitimos "issuer= " 
		return salida1;
	}

	public String getNumeroSerie(File certificado) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "x509" , "-noout" , "-in" , certificado.getAbsolutePath() , "-serial"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , salida1 = "" , salida2 = ""; // Salida 1 = estandar    Salida2 = error
		// --- > Leemos la salida estandar y el error
		try {
			while( (linea = in.readLine()) != null) salida1 += linea; // --- > Leemos toda salida en 1 linea
			while( (linea = error.readLine()) != null) salida2 += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida del comando.\n---Salida estandar:\n" + salida1 + "---Salida error:\n" + salida2);
		}
		// --- > Comprobamos si hay salida error
		if(!salida2.equals("")) throw new OpenSSL_Exception("Error: no se ha podido obtener el emisor del certificado:\n" + salida2);
		// --- > issuer= /C=......../ Omitimos "issuer= " 
		return salida1.substring(salida1.indexOf("=") + 2);
	}

}
