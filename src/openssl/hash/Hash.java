package openssl.hash;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import excepciones.OpenSSL_Exception;

public class Hash implements interfaces.hash.HASH_Interfaz{

	public byte[] MD5(String texto) throws OpenSSL_Exception {
		// Ejecutamos el comando en el terminal
		String[] comando = new String[]{ "openssl" , "dgst" , "-md5"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Mandamos el texto 
		PrintWriter out = new PrintWriter(terminal.getOutputStream());
		out.print(texto);
		out.flush();
		out.close();
		// --- > Leemos la salida  error del comando
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , error = "" ;
		try { while( (linea = errors.readLine()) != null) error += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el error que ha producido el comando");
		}
		// --- > Si hubo salida error lanzamos al excepcion con el error
		if(!error.equals(""))throw new OpenSSL_Exception("Error al ejecutar el comando:\n" + error );
		// --- > Leemos el resultado obtenido
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		String res = "";
		try { while((linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el resultado que ha producido el comando");
		}
		return res.getBytes();
	}

	public byte[] MD5(File archivo) throws OpenSSL_Exception {
		// Ejecutamos el comando en el terminal
		String[] comando = new String[]{ "openssl" , "dgst" , "-md5" , archivo.getAbsolutePath()};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Leemos la salida  error del comando
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , error = "" ;
		try { while( (linea = errors.readLine()) != null) error += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el error que ha producido el comando");
		}
		// --- > Si hubo salida error lanzamos al excepcion con el error
		if(!error.equals(""))throw new OpenSSL_Exception("Error al ejecutar el comando:\n" + error );
		// --- > Leemos el resultado obtenido
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		String res = "";
		try { while((linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el resultado que ha producido el comando");
		}
		return res.substring(res.lastIndexOf(" ") + 1 ).getBytes();
	}

	public byte[] SHA1(String texto) throws OpenSSL_Exception {
		// Ejecutamos el comando en el terminal
		String[] comando = new String[]{ "openssl" , "dgst" , "-sha1"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Mandamos el texto 
		PrintWriter out = new PrintWriter(terminal.getOutputStream());
		out.print(texto);
		out.flush();
		out.close();
		// --- > Leemos la salida  error del comando
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , error = "" ;
		try { while( (linea = errors.readLine()) != null) error += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el error que ha producido el comando");
		}
		// --- > Si hubo salida error lanzamos al excepcion con el error
		if(!error.equals(""))throw new OpenSSL_Exception("Error al ejecutar el comando:\n" + error );
		// --- > Leemos el resultado obtenido
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		String res = "";
		try { while((linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el resultado que ha producido el comando");
		}
		return res.getBytes();
	}

	public byte[] SHA1(File archivo) throws OpenSSL_Exception {
		// Ejecutamos el comando en el terminal
		String[] comando = new String[]{ "openssl" , "dgst" , "-sha1" , archivo.getAbsolutePath()};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Leemos la salida  error del comando
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , error = "" ;
		try { while( (linea = errors.readLine()) != null) error += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el error que ha producido el comando");
		}
		// --- > Si hubo salida error lanzamos al excepcion con el error
		if(!error.equals(""))throw new OpenSSL_Exception("Error al ejecutar el comando:\n" + error );
		// --- > Leemos el resultado obtenido
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		String res = "";
		try { while((linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el resultado que ha producido el comando");
		}
		return res.substring(res.lastIndexOf(" ") + 1 ).getBytes();
	}

	public byte[] SHA(String texto) throws OpenSSL_Exception {
		// Ejecutamos el comando en el terminal
		String[] comando = new String[]{ "openssl" , "dgst" , "-sha"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Mandamos el texto 
		PrintWriter out = new PrintWriter(terminal.getOutputStream());
		out.print(texto);
		out.flush();
		out.close();
		// --- > Leemos la salida  error del comando
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , error = "" ;
		try { while( (linea = errors.readLine()) != null) error += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el error que ha producido el comando");
		}
		// --- > Si hubo salida error lanzamos al excepcion con el error
		if(!error.equals(""))throw new OpenSSL_Exception("Error al ejecutar el comando:\n" + error );
		// --- > Leemos el resultado obtenido
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		String res = "";
		try { while((linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el resultado que ha producido el comando");
		}
		return res.getBytes();
	}

	public byte[] SHA(File archivo) throws OpenSSL_Exception {
		// Ejecutamos el comando en el terminal
		String[] comando = new String[]{ "openssl" , "dgst" , "-sha" , archivo.getAbsolutePath()};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Leemos la salida  error del comando
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , error = "" ;
		try { while( (linea = errors.readLine()) != null) error += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el error que ha producido el comando");
		}
		// --- > Si hubo salida error lanzamos al excepcion con el error
		if(!error.equals(""))throw new OpenSSL_Exception("Error al ejecutar el comando:\n" + error );
		// --- > Leemos el resultado obtenido
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		String res = "";
		try { while((linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el resultado que ha producido el comando");
		}
		return res.substring(res.lastIndexOf(" ") + 1 ).getBytes();
	}

	public byte[] SHA256(String texto) throws OpenSSL_Exception {
		// Ejecutamos el comando en el terminal
		String[] comando = new String[]{ "openssl" , "dgst" , "-sha256"};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Mandamos el texto 
		PrintWriter out = new PrintWriter(terminal.getOutputStream());
		out.print(texto);
		out.flush();
		out.close();
		// --- > Leemos la salida  error del comando
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , error = "" ;
		try { while( (linea = errors.readLine()) != null) error += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el error que ha producido el comando");
		}
		// --- > Si hubo salida error lanzamos al excepcion con el error
		if(!error.equals(""))throw new OpenSSL_Exception("Error al ejecutar el comando:\n" + error );
		// --- > Leemos el resultado obtenido
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		String res = "";
		try { while((linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el resultado que ha producido el comando");
		}
		return res.getBytes();
	}

	public byte[] SHA256(File archivo) throws OpenSSL_Exception {
		// Ejecutamos el comando en el terminal
		String[] comando = new String[]{ "openssl" , "dgst" , "-sha256" , archivo.getAbsolutePath()};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Leemos la salida  error del comando
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		String linea = "" , error = "" ;
		try { while( (linea = errors.readLine()) != null) error += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el error que ha producido el comando");
		}
		// --- > Si hubo salida error lanzamos al excepcion con el error
		if(!error.equals(""))throw new OpenSSL_Exception("Error al ejecutar el comando:\n" + error );
		// --- > Leemos el resultado obtenido
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		String res = "";
		try { while((linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer el resultado que ha producido el comando");
		}
		return res.substring(res.lastIndexOf(" ") + 1 ).getBytes();
	}

}
