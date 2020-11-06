import java.util.ArrayList;

public class Frontera {
	
	private Nodo inicial;
	private Estado objetivo;
	private ArrayList<Nodo> frontera;
	private ArrayList<int[]> visitados;
	
	public Frontera(Nodo inicial, Estado objetivo) {
		this.inicial = inicial;
		this.objetivo = objetivo;
		this.frontera = new ArrayList<Nodo>();
		this.visitados = new ArrayList<int[]>();
	}
	
	public Nodo getInicial() {
		return inicial;
	}
	public void setInicial(Nodo inicial) {
		this.inicial = inicial;
	}
	public Estado getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(Estado objetivo) {
		this.objetivo = objetivo;
	}
	public ArrayList<Nodo> getFrontera() {
		return frontera;
	}
	public void setFrontera(ArrayList<Nodo> frontera) {
		this.frontera = frontera;
	}

	public void generarFrontera() {
		
	}

}