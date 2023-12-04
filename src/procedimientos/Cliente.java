package procedimientos;

public class Cliente extends Banco{

	private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private String tipoCuenta;
    
    public Cliente(String id, String nombre, String apellidos, String correo, String telefono, String tipoCuenta) {
		super(id);
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.correo = correo;
		this.telefono = telefono;
		this.tipoCuenta = tipoCuenta;
	}
    
    public Cliente() {
    	
    }

	public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    
    public String formatoArchivo(){
        return id + "|"+ nombre + "|"+ apellidos + "|"+ correo + "|" + telefono + "|"+ tipoCuenta + "|";
    }

	@Override
	public String toString() {
		return "Cliente: idUsuario -> " + id + ", nombre -> " + nombre + ", apellidos ->" + apellidos + ", correo -> "
				+ correo + ", telefono -> " + telefono + ", tipoCuenta -> " + tipoCuenta;
	}
    
    
}
