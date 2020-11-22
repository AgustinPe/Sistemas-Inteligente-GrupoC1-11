
public class Sucesor {
	String accion;
	int[] estado;
	float costo_move;
	
	
	public Sucesor() {
		
	}
	
	public Sucesor(String accion, int[] estado, float costo_move) {
		this.accion = accion;
		this.estado = estado;
		this.costo_move = costo_move;
	}
	
	public String getMove() {
		return accion;
	}
	public void setMove(String accion) {
		this.accion = accion;
	}
	public int[] getEstado() {
		return estado;
	}
	public void setEstado(int[] estado) {
		this.estado = estado;
	}
	public float getCosto_move() {
		return costo_move;
	}
	public void setCosto_move(float costo_move) {
		this.costo_move = costo_move;
	}
	
	
}
