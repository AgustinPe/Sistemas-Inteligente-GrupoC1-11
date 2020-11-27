import java.util.PriorityQueue;

public class Frontera {

	private PriorityQueue<Nodo> frontera;
	
	public Frontera() {
		this.frontera = new PriorityQueue<Nodo>(new ComparadorNodos());
	}
	public Frontera(PriorityQueue<Nodo> frontera, PriorityQueue<Nodo> visitados) {
		this.frontera = frontera;
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
	
}