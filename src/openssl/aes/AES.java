package openssl.aes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import excepciones.OpenSSL_Exception;


public class AES implements interfaces.aes.AES_Interfaz{

	public byte[] cifraraes(String texto, String contraseña) throws OpenSSL_Exception{
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "aes-256-cbc" , "-e" , "-base64" , "-k" , contraseña};
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
		// --- > Leemos el flujo de datos cifrados y cerramos el stream
		String res = "" , linea = "" , error = "";
		try { while( (linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida estandar del comando ejecutado");
		}
		try { in.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		// --- > Comprobamos si hay salida error
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return res.getBytes();
	}

	public byte[] descifraraes(String texto, String contraseña) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "aes-256-cbc" , "-d" , "-base64" , "-k" , contraseña};
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
		// --- > Leemos el flujo de datos descifrados y cerramos el stream
		String res = "" , linea = "" , error = "";
		try { while( (linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida estandar del comando ejecutado");
		}
		try { in.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		// --- > Comprobamos si hay salida error
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return res.getBytes();
	}


	public byte[] cifraraes(File archivo, String contraseña) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "aes-256-cbc" , "-e" , "-base64" , "-k" , contraseña , "-in" , archivo.getAbsolutePath()};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}		
		// --- > Obtenemos los streams E/S
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		// --- > Leemos el flujo de datos cifrados y cerramos el stream
		String res = "" , linea = "" , error = "";
		try { while( (linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida estandar del comando ejecutado");
		}
		try { in.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		// --- > Comprobamos si hay salida error
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return res.getBytes();
	}

	public byte[] descifraraes(File archivo, String contraseña) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "aes-256-cbc" , "-d" , "-base64" , "-k" , contraseña , "-in" , archivo.getAbsolutePath()};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}		
		// --- > Obtenemos los streams E/S
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		// --- > Leemos el flujo de datos descifrados y cerramos el stream
		String res = "" , linea = "" , error = "";
		try { while( (linea = in.readLine()) != null) res += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida estandar del comando ejecutado");
		}
		try { in.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		// --- > Comprobamos si hay salida error
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		try { while( (linea = errors.readLine()) != null) error += linea; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado");
		}
		try { errors.close(); } catch (IOException e) { throw new OpenSSL_Exception("No se puede cerrar el stream del terminal"); }
		if(!error.equals("")) throw new OpenSSL_Exception("No se ha podido ejecutar el comando:\n" + error);
		return res.getBytes();
	}


	public File cifraraes(String texto, String contraseña, String ruta_destino) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "aes-256-cbc" , "-e" , "-base64" , "-k" , contraseña , "-out" , ruta_destino};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}			// --- > Obtenemos los streams E/S
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

	public File descifraraes(String texto, String contraseña, String ruta_destino) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "aes-256-cbc" , "-d" , "-base64" , "-k" , contraseña , "-out" , ruta_destino};
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


	public File cifraraes(File archivo, String contraseña, String ruta_destino) throws OpenSSL_Exception  {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "aes-256-cbc" , "-e" , "-base64" , "-k" , contraseña , "-in" , archivo.getAbsolutePath() , "-out" , ruta_destino};
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

	public File descifraraes(File archivo, String contraseña, String ruta_destino) throws OpenSSL_Exception  {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "aes-256-cbc" , "-d" , "-base64" , "-k" , contraseña , "-in" , archivo.getAbsolutePath() , "-out" , ruta_destino};
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
