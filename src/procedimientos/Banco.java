package procedimientos;

abstract class Banco {
	protected String id;
	
	public Banco(String id) {
		super();
		this.id = id;
	}
	
	public Banco() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
