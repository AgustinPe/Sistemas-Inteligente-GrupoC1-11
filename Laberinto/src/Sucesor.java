
public class Sucesor {
	String move;
	int[] estado;
	int costo_move;
	
	
	public Sucesor() {
		
	}
	
	public Sucesor(String move, int[] estado, int costo_move) {
		super();
		this.move = move;
		this.estado = estado;
		this.costo_move = costo_move;
	}
	
	public String getMove() {
		return move;
	}
	public void setMove(String move) {
		this.move = move;
	}
	public int[] getEstado() {
		return estado;
	}
	public void setEstado(int[] estado) {
		this.estado = estado;
	}
	public int getCosto_move() {
		return costo_move;
	}
	public void setCosto_move(int costo_move) {
		this.costo_move = costo_move;
	}
	
	
}
