package openssl.base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import excepciones.OpenSSL_Exception;


public class Base64 implements interfaces.base64.BASE64_Interfaz{

	public byte[] cifrar64(String texto) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "base64" , "-e"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams E/S
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		PrintWriter out = new PrintWriter(terminal.getOutputStream());
		// --- > Introducimos todo el texto y cerramos el stream
		out.println(texto);
		out.flush();
		out.close();
		// --- > Leemos el flujo de datos cifrados en base64
		String res = "" , linea = "" , error = "";
		try { while( (linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida estandar del comando ejecutado");
		}
		try { in.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		// --- > Comprobamos si hay salida error
		linea = "";
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return res.getBytes();
	}

	public byte[] descifrar64(String texto) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "base64" , "-d"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams E/S
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		PrintWriter out = new PrintWriter(terminal.getOutputStream());
		// --- > Introducimos todo el texto y cerramos el stream
		out.println(texto);
		out.flush();
		out.close();
		// --- > Leemos el flujo de datos descifrados en base64
		String res = "" , linea = "" , error = "";
		try { while( (linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida estandar del comando ejecutado");
		}
		try { in.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		// --- > Comprobamos si hay salida error
		linea = "";
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return res.getBytes();
	}

	public byte[] cifrar64(File archivo) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "base64" , "-e" , "-in" , archivo.getAbsolutePath()};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams E/S
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		// --- > Leemos el flujo de datos cifrados en base64
		String res = "" , linea = "" , error = "";
		try { while( (linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida estandar del comando ejecutado");
		}
		try { in.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		// --- > Comprobamos si hay salida error
		linea = "";
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return res.getBytes();
	}

	public byte[] descifrar64(File archivo) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "base64" , "-d" , "-in" , archivo.getAbsolutePath()};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams E/S
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		// --- > Leemos el flujo de datos cifrados en base64
		String res = "" , linea = "" , error = "";
		try { while( (linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida estandar del comando ejecutado");
		}
		try { in.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		// --- > Comprobamos si hay salida error
		linea = "";
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return res.getBytes();
	}

	public File cifrar64(String texto, String ruta_destino) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "base64" , "-e" , "-out" , ruta_destino};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams E/S
		PrintWriter out = new PrintWriter(terminal.getOutputStream());
		// --- > Introducimos todo el texto y cerramos el stream
		out.println(texto);
		out.flush();
		out.close();
		// --- > Comprobamos si hay salida error
		String linea = "" , error = "";
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return new File(ruta_destino);
	}

	public File descifrar64(String texto, String ruta_destino) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "base64" , "-d" , "-out" , ruta_destino};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams E/S
		PrintWriter out = new PrintWriter(terminal.getOutputStream());
		// --- > Introducimos todo el texto y cerramos el stream
		out.println(texto);
		out.flush();
		out.close();
		// --- > Comprobamos si hay salida error
		String linea = "" , error = "";
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return new File(ruta_destino);
	}

	public File cifrar64(File archivo, String ruta_destino) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "base64" , "-e" , "-in" , archivo.getAbsolutePath() , "-out" , ruta_destino};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Comprobamos si hay salida error
		String linea = "" , error = "";
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return new File(ruta_destino);
	}

	public File descifrar64(File archivo, String ruta_destino) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "base64" , "-d" , "-in" , archivo.getAbsolutePath() , "-out" , ruta_destino};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Comprobamos si hay salida error
		String linea = "" , error = "";
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return new File(ruta_destino);
	}

}
