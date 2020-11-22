import java.util.PriorityQueue;

public class Frontera{

	private PriorityQueue<Nodo> frontera;
	private PriorityQueue<Nodo> visitados;
	
	public Frontera() {
		this.frontera = new PriorityQueue<Nodo>(new ComparadorNodos());
		this.visitados= new PriorityQueue<Nodo>(new ComparadorNodos());
	}
	public Frontera(PriorityQueue<Nodo> frontera, PriorityQueue<Nodo> visitados) {
		this.frontera = frontera;
		this.visitados =visitados;
	}
	
	public void insertar(Nodo nodo) {
		frontera.add(nodo);
	}
	public Nodo eliminar() {
		return frontera.remove();
	}
	
	public boolean estaVacia() {
		return frontera.isEmpty();
	}
	
	public void insertarV(Nodo nodo) {
		visitados.add(nodo);
	}
	public Nodo eliminarV() {
		return visitados.remove();
	}
	
	public boolean estaVaciaV() {
		return visitados.isEmpty();
	}
}