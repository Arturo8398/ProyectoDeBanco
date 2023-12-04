package procedimientos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DAOArchivosTXT implements DAO<Cliente>{
	
	private String nombreArchivo;
	
	public DAOArchivosTXT(String nomAr) {
		this.nombreArchivo = nomAr;
	}

	@Override
	public int create(Cliente cliente) throws SQLException {
	    PrintWriter linea = null;
	    FileWriter escribir = null;
	    File archivo;
	    archivo = new File(nombreArchivo);

	    try {
	        escribir = new FileWriter(archivo, true);
	        linea = new PrintWriter(escribir);
	        linea.println(cliente.formatoArchivo());

	        System.out.println("Cliente agregado al archivo.");

	    } catch (Exception e) {
	        System.out.println("Error: " + e);
	    } finally {
	        try {
	            if (escribir != null) {
	                escribir.close();
	            }
	            if (linea != null) {
	                linea.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    return 0;
	}



	@Override
	public Cliente read(String id) throws SQLException {
	    try {
	        File archivo = new File(nombreArchivo);
	        if (archivo.exists()) {
	            ArrayList<Cliente> listaUsuarios = readAll();
	            Iterator<Cliente> it = listaUsuarios.iterator();
	            
	            while(it.hasNext()) {
	            	Cliente clienteAux = it.next();
	            	if(clienteAux.getId().equals(id)) {
	            		return clienteAux;
	            	}
	            }
	            System.out.println("Cliente con ID " + id + " no encontrado.");
	        } else {
	            System.out.println("Lista no existente");
	        }
	    } catch (Exception e) {
	        System.out.println("Error " + e);
	    }
	    return null;
	}


	@Override
	public int update(Cliente cliente) throws SQLException {
	    int result = -1;

	    try {
	        File archivo = new File(nombreArchivo);
	        if (archivo.exists()) {
	            ArrayList<Cliente> listaUsuarios = readAll();
	            
	            Iterator<Cliente> i = listaUsuarios.iterator();
	            while(i.hasNext()) {
	            	Cliente clienteAux = i.next();
	            	if (clienteAux.getId() == cliente.getId()) {
	                	clienteAux.setNombre(cliente.getNombre());
	                	clienteAux.setApellidos(cliente.getApellidos());
	                	clienteAux.setCorreo(cliente.getCorreo());
	                	clienteAux.setTelefono(cliente.getTelefono());
	                	clienteAux.setTipoCuenta(cliente.getTipoCuenta());
	                    break;
	                }
	            }

	            PrintWriter linea = null;
	            FileWriter escribir = null;

	            try {
	                escribir = new FileWriter(archivo, false);
	                linea = new PrintWriter(escribir);

	                for (Cliente usuarioAux : listaUsuarios) {
	                    linea.println(usuarioAux.formatoArchivo());
	                }
	            } catch (IOException e) {
	                System.out.println("Error: " + e);
	            } finally {
	                if (linea != null) {
	                    linea.close();
	                }
	                if (escribir != null) {
	                    try {
	                        escribir.close();
	                    } catch (IOException e) {
	                        System.out.println("Error al cerrar FileWriter: " + e);
	                    }
	                }
	            }

	            result = 0;
	        } else {
	            System.out.println("Lista no existente");
	        }
	    } catch (Exception e) {
	        System.out.println("Error " + e);
	    }

	    return result;
	}
	
	@Override
	public int delete(String clienteId) throws SQLException {
	    int result = -1;

	    try {
	        File archivo = new File(nombreArchivo);
	        if (archivo.exists()) {
	            ArrayList<Cliente> listaUsuarios = readAll();
	            Cliente clienteEncontrado = null;
	            
	            Iterator<Cliente> it = listaUsuarios.iterator();
	            while(it.hasNext()) {
	            	Cliente usuarioAux = it.next();
	            	if (usuarioAux.getId().equals(clienteId)) {
	                    clienteEncontrado = usuarioAux;
	                    break;
	                }
	            }
	            
	            if (clienteEncontrado != null) {
	                listaUsuarios.remove(clienteEncontrado);

	                PrintWriter linea = null;
	                FileWriter escribir = null;

	                try {
	                    escribir = new FileWriter(archivo, false);
	                    linea = new PrintWriter(escribir);

	                    for (Cliente usuarioAux : listaUsuarios) {
	                        linea.println(usuarioAux.formatoArchivo());
	                    }
	                } catch (IOException e) {
	                    System.out.println("Error: " + e);
	                } finally {
	                    if (linea != null) {
	                        linea.close();
	                    }
	                    if (escribir != null) {
	                        try {
	                            escribir.close();
	                        } catch (IOException e) {
	                            System.out.println("Error al cerrar FileWriter: " + e);
	                        }
	                    }
	                }

	                result = 0;
	            } else {
	                System.out.println("Cliente con ID " + clienteId + " no encontrado");
	            }
	        } else {
	            System.out.println("Lista no existente");
	        }
	    } catch (Exception e) {
	        System.out.println("Error " + e);
	    }

	    return result;
	}



	@Override
	public ArrayList<Cliente> readAll() throws SQLException {
		Generic <Cliente> nuevoGen = new Generic<>();

        try {
            File archivo = new File(nombreArchivo);
            if (archivo.exists()) {
                FileReader leer = new FileReader(archivo);
                BufferedReader br = new BufferedReader(leer);
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] valores = linea.split("\\|");
                    String idUsuario = valores[0];
                    String nombre = valores[1];
                    String apellidos = valores[2];
                    String correo = valores[3];
                    String telefono = valores[4];
                    String tipoCuenta = valores[5];

                    Cliente aux = new Cliente(idUsuario, nombre, apellidos, correo, telefono, tipoCuenta);

                    nuevoGen.addElement(aux);

                }
                Collections.sort(nuevoGen.obtenerArrayList(), new CompararCliente(CompararCliente.NOMBRE));
            } else {
                System.out.println("Lista no existente");
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        
        return nuevoGen.obtenerArrayList();
	}
	
}
