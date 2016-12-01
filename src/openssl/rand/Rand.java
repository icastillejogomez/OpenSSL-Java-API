package openssl.rand;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import excepciones.OpenSSL_Exception;

public class Rand implements interfaces.rand.Rand_Interfaz{

	public String rand(int tamaño) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rand" , "-base64" , "" + tamaño};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos la salida del comando
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		// --- > Comprobamos la salida error
		String linea = "" , error = "" , res = "";
		try { while( (linea = errors.readLine()) != null) error += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado:\n" + error);
		}
		if(!error.equals("")) throw new OpenSSL_Exception("Fallo al ejecutar el comando:\n" + error);
		// --- > Leemos la salida del comando
		linea = "";
		try { while( (linea = in.readLine()) != null) res += linea;
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado:\n" + error);
		}
		return res;
	}

	public String randhex(int tamaño) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rand" , "-hex" , "" + tamaño};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos la salida del comando
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		// --- > Comprobamos la salida error
		String linea = "" , error = "" , res = "";
		try { while( (linea = errors.readLine()) != null) error += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado:\n" + error);
		}
		if(!error.equals("")) throw new OpenSSL_Exception("Fallo al ejecutar el comando:\n" + error);
		// --- > Leemos la salida del comando
		linea = "";
		try { while( (linea = in.readLine()) != null) res += linea;
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado:\n" + error);
		}
		return res;
	}

	public File rand(int tamaño, String ruta_destino) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rand" , "-base64" , "-out" , ruta_destino , String.valueOf(tamaño)};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos la salida del comando
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		// --- > Comprobamos la salida error
		String linea = "" , error = "";
		try { while( (linea = errors.readLine()) != null) error += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado:\n" + error);
		}
		if(!error.equals("")) throw new OpenSSL_Exception("Fallo al ejecutar el comando:\n" + error);

		return new File(ruta_destino);
	}

	public File randhex(int tamaño, String ruta_destino) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rand" , "-hex" , "-out" , ruta_destino , String.valueOf(tamaño)};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos la salida del comando
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		// --- > Comprobamos la salida error
		String linea = "" , error = "";
		try { while( (linea = errors.readLine()) != null) error += linea + "\n";
		} catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado:\n" + error);
		}
		if(!error.equals("")) throw new OpenSSL_Exception("Fallo al ejecutar el comando:\n" + error);

		return new File(ruta_destino);
	}

}
