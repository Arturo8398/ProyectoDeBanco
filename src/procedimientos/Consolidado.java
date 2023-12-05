package procedimientos;

public class Consolidado extends Banco{
	private String nombre;
	private String apellidos;
	private String correo;
	
	public Consolidado(String id, String nombre, String apellidos, String correo) {
		super(id);
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.correo = correo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	
}
