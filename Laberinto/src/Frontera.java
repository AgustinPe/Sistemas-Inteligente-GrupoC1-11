import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;

public class Frontera {

	private PriorityQueue<Nodo> frontera;
	private PriorityQueue<Nodo> camino;
	private PriorityQueue<Nodo> visatados;
	
	public Frontera() {
		
	}
	public Frontera(PriorityQueue<Nodo> frontera) {
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