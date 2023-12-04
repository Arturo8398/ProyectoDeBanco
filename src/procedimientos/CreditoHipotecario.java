package procedimientos;

public class CreditoHipotecario extends Banco{
	private String direccion;
	private String valor;
	
	public CreditoHipotecario(String id, String direccion, String valor) {
		super(id);
		this.direccion = direccion;
		this.valor = valor;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
}
