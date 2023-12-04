package procedimientos;

import java.util.Comparator;

public class CompararCliente implements Comparator<Cliente>{
	public final static int NOMBRE = 1;
	public final static int APELLIDOS = 2;
	public final static int IDUSUARIO = 3;
	private int tipo;
	
	public CompararCliente (int tipo) {
		this.tipo = tipo;
	}

	@Override
	public int compare(Cliente o1, Cliente o2) {
		switch(tipo) {
		case NOMBRE:
			return o1.getNombre().compareTo(o2.getNombre());
		case APELLIDOS:
			return o1.getApellidos().compareTo(o2.getApellidos());
		case IDUSUARIO:
			return o1.getId().compareTo(o2.getId());
		default:
			return o1.getNombre().compareTo(o2.getNombre());
		}
	}

}
