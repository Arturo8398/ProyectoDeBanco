package procedimientos;

public class Cliente {
    private String idUsuario;
    private String nombre;
    private String apellidos;
    private String correo;
    private String telefono;
    private String tipoCuenta;

    public Cliente(String idUsuario, String nombre, String apellidos, String correo, String telefono, String tipoCuenta) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.tipoCuenta = tipoCuenta;
    }
    
    public Cliente() {
    	
    }

    public String getIdUsuario() {
        return idUsuario;
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

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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
        return idUsuario + "|"+ nombre + "|"+ apellidos + "|"+ correo + "|" + telefono + "|"+ tipoCuenta + "|";
    }

	@Override
	public String toString() {
		return "Cliente: idUsuario -> " + idUsuario + ", nombre -> " + nombre + ", apellidos ->" + apellidos + ", correo -> "
				+ correo + ", telefono -> " + telefono + ", tipoCuenta -> " + tipoCuenta;
	}
    
    
}
