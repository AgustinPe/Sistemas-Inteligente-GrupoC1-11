public class Estado {

	private int [] id;
	private int value;
	private boolean [] neighbors;
	private boolean visitada;
	
	public Estado(int[] id) {
		this.id = id;
		this.value = 0;
		this.neighbors = new boolean [4];
		this.visitada = false;
	}
	public int [] getPosicion() {
		return id;
	}
	public void setPosicion(int coordenada, int posicion) {
		this.id[coordenada] = posicion;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public boolean [] getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(int coordenada, boolean estado) {		
		this.neighbors[coordenada] = estado;
	}
	public boolean getVisitada() {
		return visitada;
	}
	public void setVisitada(boolean visitada) {
		this.visitada = visitada;
	}

}
