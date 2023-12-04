package procedimientos;

public class CreditoPersonal extends Banco{
	private String ingresos;

	public CreditoPersonal(String id, String ingresos) {
		super(id);
		this.ingresos = ingresos;
	}

	public String getIngresos() {
		return ingresos;
	}

	public void setIngresos(String ingresos) {
		this.ingresos = ingresos;
	}
}
