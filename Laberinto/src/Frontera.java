import java.util.ArrayList;
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
		this.frontera.add(nodo);
	}
	public Nodo eliminar() {
		return this.frontera.remove();
	}
	
	public boolean estaVacia() {
		return this.frontera.isEmpty();
	}
	
	public void insertarNodosHijo(ArrayList<Nodo> nodosHijo) {
		while(!nodosHijo.isEmpty()) {
			this.frontera.add(nodosHijo.remove(0));
		}
	}
}