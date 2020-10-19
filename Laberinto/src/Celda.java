
public class Celda {
	
	private int [] posicion;
	private int value;
	private boolean [] neighbors;
	private boolean visitada;
	public Celda() {
		this.posicion = new int [2];
		this.value = 0;
		this.neighbors = new boolean [4];
		this.visitada = false;
	}
	public int [] getPosicion() {
		return posicion;
	}
	public void setPosicion(int coordenada, int posicion) {
		this.posicion[coordenada] = posicion;
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
