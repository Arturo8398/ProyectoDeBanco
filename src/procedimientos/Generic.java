//Creado por Arturo García López
package procedimientos;

import java.util.ArrayList;
import java.util.Iterator;

public class Generic<T> {
	private ArrayList<T> coleccion;
	private T dato;
	private ArrayList<T> arreglo;
	
	
	
	public Generic(ArrayList<T> arreglo) {
		this.arreglo = arreglo;
	}
	
	public void recorrerLista() {
		Iterator<T> it = arreglo.iterator();
		
		while(it.hasNext()) {
			T dato = it.next();
			System.out.println(dato.toString());
		}
	}
	
	public void recorrerColeccion() {
		Iterator<T> it = coleccion.iterator();
		
		while(it.hasNext()) {
			T dato = it.next();
			System.out.println(dato.toString());
		}
	}


	public Generic(T dato) {
		this.dato = dato;
	}

	public Generic() {
		coleccion = new ArrayList<>();
	}

	public T getDato() {
		return dato;
	}

	public void setDato(T dato) {
		this.dato = dato;
	}
	
	public ArrayList<T> getColeccion() {
		return coleccion;
	}
	
	public void setColeccion(ArrayList<T> coleccion) {
		this.coleccion = coleccion;
	}
	
	public void addElement(T dato) {
		coleccion.add(dato);
	}
	
	public ArrayList<T> obtenerArrayList(){
		return coleccion;
	}
	
}
