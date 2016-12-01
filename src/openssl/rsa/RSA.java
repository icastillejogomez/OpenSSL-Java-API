package openssl.rsa;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import excepciones.OpenSSL_Exception;
import openssl.tmp.Tmp;
import openssl.util.Util;

public class RSA implements interfaces.rsa.RSA_Interfaz{

	/**
	 * genRSA genera una clave privada RSA de la longitud en bits indicada
	 * y la guarda en la ruta donde el usuario desee. Si ya existe un archivo 
	 * en esa ruta será sobrescrito al guardar al nueva clave generada
	 * 
	 * @param bits longitud de la clave
	 * @param rutaClavePrivada ruta donde se debe guardar la clave privada generada
	 * 
	 * @return devuelve un objeto File que representa el archivo que contiene la clave privada generada
	 */
	public File genRSA(int bits, String rutaClavePrivada) throws OpenSSL_Exception{
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "genrsa" , "-out" , rutaClavePrivada , String.valueOf(bits)};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Obtenemos los streams de salida standart y error
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		// --- > Leemos la entrada standart pero no la mostramos por pantalla
		String linea = "" , error = "";
		try { while((linea = in.readLine()) != null) System.out.println(linea);  } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida estandar del comando ejecutado");
		}
		// --- > Leemos la salida error y la acumulamos para lanzarla con la excepcion
		linea = "";
		try { while((linea = errors.readLine()) != null) error += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado:\n" + error);
		}
		// --- > Debido a OpenSSL usa el error de salida normal y de error 
		return new File(rutaClavePrivada);
	}

	public File getClavePublica(File clavePrivada, String rutaClavePublica) throws OpenSSL_Exception {
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rsa" , "-in" , clavePrivada.getAbsolutePath() , "-inform" , "PEM" , "-pubout" , "-out" , rutaClavePublica};
		Process terminal = null;
		try { terminal = Runtime.getRuntime().exec(comando); } catch (IOException e) { 
			throw new OpenSSL_Exception("No se puede ejecutar el comando en el terminal");
		}
		// --- > Leemos la salida standart y la salida error del comando
		BufferedReader errors = new BufferedReader(new InputStreamReader(terminal.getErrorStream()));
		BufferedReader in = new BufferedReader(new InputStreamReader(terminal.getInputStream()));
		// --- > Leemos la entrada standart pero no la mostramos por pantalla
		String linea = "" , error = "";
		try { while((linea = in.readLine()) != null)  System.out.println(linea);  } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida estandar del comando ejecutado");
		}
		// --- > Leemos la salida error y la acumulamos para lanzarla con la excepcion
		linea = "";
		try { while((linea = errors.readLine()) != null) error += linea + "\n"; } catch (IOException e) {
			throw new OpenSSL_Exception("No se puede leer la salida error del comando ejecutado:\n" + error);
		}
		// --- > Debido a OpenSSL usa el error de salida normal y de error 
		return new File(rutaClavePublica);
	}

	public byte[] cifrarRSA(byte[] texto, File clavePublica)  throws OpenSSL_Exception{
		// --- > Guardamos el texto en un archivo temporal
		File tmp = Tmp.newtmp();
		Util util = new Util();
		util.guardarArchivo(texto, tmp.getAbsolutePath());
		// --- > Ciframos el archivo temporal en otro archivo temporal
		File tmp_enc = Tmp.newtmp();
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rsautl" , "-inkey" , clavePublica.getAbsolutePath() , "-pubin" , "-in" ,tmp.getAbsolutePath() , "-encrypt" , "-out" , tmp_enc.getAbsolutePath()};
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
		// --- > Leemos el fichero
		byte[] res = util.leerArchivo(tmp_enc);
		// --- > Borramos los archivo temporales
		Tmp.deletetmp(tmp_enc);
		Tmp.deletetmp(tmp);
		// --- > Devolvemos el resultado en base64
		return res;

	}

	public byte[] descifrarRSA(byte[] texto, File clavePrivada)  throws OpenSSL_Exception{
		// --- > Guardamos la cadena de texto en un archivo temporal
		Util util = new Util();
		File tmp_enc = Tmp.newtmp();
		tmp_enc = util.guardarArchivo(texto, tmp_enc.getAbsolutePath());
		// --- > Desciframos el mensaje con la clave Privada
		File tmp_dec = Tmp.newtmp();
		String[] comando = new String[]{ "openssl" , "rsautl" , "-inkey" , clavePrivada.getAbsolutePath() , "-in" ,tmp_enc.getAbsolutePath() , "-decrypt" , "-out" , tmp_dec.getAbsolutePath()};
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
		// --- > Leemos el archivo descifrado
		byte[] res = util.leerArchivo(tmp_dec);
		// --- > Borramos los archivo temporales
		Tmp.deletetmp(tmp_dec);
		Tmp.deletetmp(tmp_enc);
		return res;

	}

	public File cifrarRSA(byte[] texto, File clavePublica, String rutaDestino)  throws OpenSSL_Exception{
		// --- > Guardamos el texto en un archivo temporal
		File tmp = Tmp.newtmp();
		Util util = new Util();
		util.guardarArchivo(texto, tmp.getAbsolutePath());
		// --- > Ejecutamos el comando para cifrar el archivo temporal en la rutaDestino
		String[] comando = new String[]{ "openssl" , "rsautl" , "-inkey" , clavePublica.getAbsolutePath() , "-pubin" , "-in" ,tmp.getAbsolutePath() , "-encrypt" , "-out" , rutaDestino};
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
		// --- > Borramos los archivo temporales
		Tmp.deletetmp(tmp);
		return new File(rutaDestino);
	}

	public File descifrarRSA(byte[]  texto, File clavePrivada, String rutaDestino) throws OpenSSL_Exception {
		// --- > Guardamos el texto en un archivo temporal
		File tmp_enc = Tmp.newtmp();
		Util util = new Util();
		util.guardarArchivo(texto, tmp_enc.getAbsolutePath());
		// --- > Desciframos el mensaje con la clave Privada
		String[] comando = new String[]{ "openssl" , "rsautl" , "-inkey" , clavePrivada.getAbsolutePath() , "-in" ,tmp_enc.getAbsolutePath() , "-decrypt" , "-out" , rutaDestino};
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
		// --- > Borramos los archivo temporales
		Tmp.deletetmp(tmp_enc);
		return new File(rutaDestino);
	}

	public byte[] cifrarRSA(File archivo , File clavePublica)  throws OpenSSL_Exception{
		// --- > Creamos un archivo temporal para guardar la salida del cifrado
		File tmp = Tmp.newtmp();
		Util util = new Util();
		// --- > Ejecutamos el comando para cifrar el archivo en el archivo temporal
		String[] comando = new String[]{ "openssl" , "rsautl" , "-inkey" , clavePublica.getAbsolutePath() , "-pubin" , "-in" ,archivo.getAbsolutePath() , "-encrypt" , "-out" , tmp.getAbsolutePath()};
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
		// --- > Obtenemos el resultado del archivo temporal
		byte[] res = util.leerArchivo(tmp);
		// --- > Borramos el archivo temporal
		Tmp.deletetmp(tmp);
		return res;
	}

	public byte[] descifrarRSA(File archivo , File clavePrivada)  throws OpenSSL_Exception{
		// --- > Creamos un archivo temporal para guardar la salida del descifrado
		File tmp = Tmp.newtmp();
		Util util = new Util();
		// --- > Ejecutamos el comando para descifrar el archivo en el archivo temporal
		String[] comando = new String[]{ "openssl" , "rsautl" , "-inkey" , clavePrivada.getAbsolutePath() , "-in" , archivo.getAbsolutePath() , "-decrypt" , "-out" , tmp.getAbsolutePath()};
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
		// --- > Obtenemos el resultado del archivo temporal
		byte[] res = util.leerArchivo(tmp);
		// --- > Borramos el archivo temporal
		Tmp.deletetmp(tmp);
		return res;
	}

	public byte[] firmarRSA(byte[] texto, File clavePrivada)  throws OpenSSL_Exception{
		// --- > Guardamos el texto en un archivo temporal
		File tmp = Tmp.newtmp();
		Util util = new Util();
		util.guardarArchivo(texto, tmp.getAbsolutePath());
		// --- > Ciframos el archivo temporal en otro archivo temporal
		File tmp_enc = Tmp.newtmp();
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rsautl" , "-in" , tmp.getAbsolutePath() , "-out" , tmp_enc.getAbsolutePath() , "-inkey" , clavePrivada.getAbsolutePath() , "-sign"};
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
		// --- > Leemos el fichero
		byte[] res = util.leerArchivo(tmp_enc);
		// --- > Borramos los archivo temporales
		Tmp.deletetmp(tmp_enc);
		Tmp.deletetmp(tmp);
		// --- > Devolvemos el resultado
		return res;
	}

	public byte[] verificarRSA(byte[]  texto, File clavePublica)  throws OpenSSL_Exception{
		// --- > Guardamos el texto en un archivo temporal
		File tmp = Tmp.newtmp();
		Util util = new Util();
		util.guardarArchivo(texto, tmp.getAbsolutePath());
		// --- > Desiframos el archivo temporal en otro archivo temporal
		File tmp_dec = Tmp.newtmp();
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rsautl" , "-in" , tmp.getAbsolutePath() , "-out" , tmp_dec.getAbsolutePath() , "-inkey" , clavePublica.getAbsolutePath() , "-pubin" , "-verify"};
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
		// --- > Leemos el fichero
		byte[] res = util.leerArchivo(tmp_dec);
		// --- > Borramos los archivo temporales
		Tmp.deletetmp(tmp_dec);
		Tmp.deletetmp(tmp);
		// --- > Devolvemos el resultado
		return res;
	}

	public byte[] firmarRSA(File archivo , File clavePrivada)  throws OpenSSL_Exception{
		// --- > Creamos un archivo temporal para guardar la firma
		File tmp_sign = Tmp.newtmp();
		Util util = new Util();
		// --- > Ejercutamos el comando
		String[] comando = new String[]{ "openssl" , "rsautl" , "-in" , archivo.getAbsolutePath() , "-out" , tmp_sign.getAbsolutePath() , "-inkey" , clavePrivada.getAbsolutePath() , "-sign"};
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
		// --- > Leemos el archivo donde se guardo el resultado
		byte[] res = util.leerArchivo(tmp_sign);
		// --- > Borramos los archivo temporales
		Tmp.deletetmp(tmp_sign);
		return res;
	}

	public byte[] verificarRSA(File archivo , File clavePublica)  throws OpenSSL_Exception{
		// --- > Creamos un archivo temporal para guardar la verificacion
		File tmp = Tmp.newtmp();
		Util util = new Util();
		// --- > Ejecutamos el comando
		String[] comando = new String[]{ "openssl" , "rsautl" , "-in" , archivo.getAbsolutePath() , "-out" , tmp.getAbsolutePath() , "-inkey" , clavePublica.getAbsolutePath() , "-pubin" , "-verify"};
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
		// --- > Leemos el archivo donde se guardo el resultado
		byte[] res = util.leerArchivo(tmp);
		// --- > Borramos los archivo temporales
		Tmp.deletetmp(tmp);
		return res;
	}


}
