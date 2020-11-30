public class Nodo {
	
	private long id;
	private double costo;
	private int[] id_estado;
	private long id_padre;
	private int profundidad;
	private double heuristica;
	private double valor;
	private String accion;

	public Nodo() {
		this.id = 0;
		this.costo = 0;
		this.id_estado = new int[2];
		this.id_padre = 0;
		this.accion = null;
		this.profundidad = 0;
		this.heuristica = 0;
		this.valor = 0;
	}
	public Nodo(long id, double costo, int[] id_estado, long id_padre, String accion, int profundidad, double heuristica, double valor) {
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

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
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

	public double getHeuristica() {
		return heuristica;
	}

	public void setHeuristica(double heuristica) {
		this.heuristica = heuristica;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
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
