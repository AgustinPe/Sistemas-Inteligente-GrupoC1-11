public class Nodo {
	
	private int id;
	private int costo;
	private int[] id_estado;
	private int[] id_padre;
	private int profundidad;
	private int heuristica;
	private int valor;
	
	public Nodo(int id, int costo, int[] id_estado, int[] id_padre, int profundidad, int heuristica, int valor) {
		this.id = id;
		this.costo = costo;
		this.id_estado = id_estado;
		this.id_padre = id_padre;
		this.profundidad = profundidad;
		this.heuristica = heuristica;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public int[] getId_estado() {
		return id_estado;
	}

	public void setId_estado(int[] id_estado) {
		this.id_estado = id_estado;
	}

	public int[] getId_padre() {
		return id_padre;
	}

	public void setId_padre(int[] id_padre) {
		this.id_padre = id_padre;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public int getHeuristica() {
		return heuristica;
	}

	public void setHeuristica(int heuristica) {
		this.heuristica = heuristica;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	} 	

}
