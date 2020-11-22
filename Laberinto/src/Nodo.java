public class Nodo {
	
	private long id;
	private int costo;
	private int[] id_estado;
	private long id_padre;
	private int profundidad;
	private int heuristica;
	private float valor;
	private String accion;

	
	public Nodo(long id, int costo, int[] id_estado, long id_padre, String accion, int profundidad, int heuristica, float valor) {
		this.id = id;
		this.costo = costo;
		this.id_estado = id_estado;
		this.id_padre = id_padre;
		this.accion = accion;
		this.profundidad = profundidad;
		this.heuristica = heuristica;
		this.valor = valor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getId_padre() {
		return id_padre;
	}

	public void setId_padre(long id_padre) {
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

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	} 	

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	@Override
	public String toString() {
		if(this.id_padre == -1 && this.accion == null) {
			return "Nodo [" + id + "][" + costo + ",(" + id_estado[0] +"," + id_estado[1] + "),"
					+ "None,None," + profundidad + "," + heuristica + ","
					+ valor + "]";
		}else {
			return "Nodo [" + id + "][" + costo + ",(" + id_estado[0] +"," + id_estado[1] + "),"
					+ id_padre +"," + accion + "," + profundidad + "," + heuristica + ","
					+ valor + "]";			
		}
	}



}
