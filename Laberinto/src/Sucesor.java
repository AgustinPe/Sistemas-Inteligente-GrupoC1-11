
public class Sucesor {
	String accion;
	int[] estado;
	int costo_move;
	
	
	public Sucesor() {
		
	}
	
	public Sucesor(String accion, int[] estado, int costo_move) {
		this.accion = accion;
		this.estado = estado;
		this.costo_move = costo_move;
	}
	
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) { 
		this.accion = accion;
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
