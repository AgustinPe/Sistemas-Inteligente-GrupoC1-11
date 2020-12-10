
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Busqueda {
	
	private ArrayList<Nodo> visitados;
	private Frontera frontera;
	private int[] posicionIni;
	private Nodo nodoInicial;
	private JsonToObject objeto = new JsonToObject();
	private int[] objetivo;
	private int profundidadmax;
	private Celda[][] laberinto;
	private long contadorId;

	public Busqueda() {
	}

	public Busqueda(ImportarJsonSucesores sucesores, JsonToObject objeto, Celda[][] laberinto) {
		
		this.contadorId = 1;
		this.posicionIni = new int[2];
		this.objetivo = new int[2];
		DrawLab cor = new DrawLab(objeto);
		cor.coordenadas(sucesores.getINITIAL());
		this.posicionIni[0] = cor.getCoordenadaFila();
		this.posicionIni[1] = cor.getCoordenadaColumna();
		cor.coordenadas(sucesores.getOBJETIVE());
		this.objetivo[0] = cor.getCoordenadaFila();
		this.objetivo[1] = cor.getCoordenadaColumna();
		this.objeto = objeto;
		this.nodoInicial = new Nodo(0, 0, this.posicionIni, -1, null, 0, heuristica(this.posicionIni), 0);
		this.profundidadmax = 1000000;
		this.laberinto = laberinto;
		this.frontera = new Frontera();
		this.visitados = new ArrayList<Nodo>();
	}

	public int heuristica(int[] nodo) {
		int h1;
		int h2;
		int h;
		int filasNodo = nodo[0] + 1;
		int columnasNodo = nodo[1] + 1;

		h1 = Math.abs(objeto.getRows() - filasNodo);
		h2 = Math.abs(objeto.getCols() - columnasNodo);
		h = h1 + h2;
		return h;
	}

	public ArrayList<Stack<Nodo>> busqueda(String estrategia) {		
		Nodo nodo;
		boolean solucion = false;
		ArrayList<Nodo> nodosHijo = new ArrayList<Nodo>();
		ArrayList<Sucesor> listaSucesores = new ArrayList<Sucesor>();
		PriorityQueue<Nodo> camino = new PriorityQueue<Nodo>();
		this.nodoInicial.setValor(calcularValueInicial(estrategia));
		this.frontera.insertar(this.nodoInicial);
		do {
			nodo = frontera.eliminar();
			if (nodo.getId_estado()[0] == this.objetivo[0] && nodo.getId_estado()[1] == this.objetivo[1]) {
				solucion = true;
			} else if (nodo.getProfundidad() < profundidadmax && pertenece(nodo.getId_estado()) == false) {
				
				visitados.add(nodo);
				listaSucesores = expandir_Nodo(nodo);
				nodosHijo = CreaListaDeNodos(listaSucesores, nodo, estrategia);
				this.frontera.insertarNodosHijo(nodosHijo);
			}
		} while (!this.frontera.estaVacia() && solucion == false);

		if (solucion == true) {			
			return crearCamino(nodo);
		} else
			return null;

	}

	public ArrayList<Nodo> CreaListaDeNodos(ArrayList<Sucesor> listaSucesores, Nodo nodo, String estrategia) {
		ArrayList<Nodo> ListaNodos = new ArrayList<Nodo>();
		Nodo nodoAux;
		double value = 0;
		int prof;
		double h;
		double costo;

		for (int i = 0; i < listaSucesores.size(); i++) {
			costo = nodo.getCosto() + laberinto[nodo.getId_estado()[0]][nodo.getId_estado()[1]].getValue()
					+ listaSucesores.get(i).getCosto_move();
			prof = nodo.getProfundidad() + 1;
			h = heuristica(listaSucesores.get(i).getEstado());
			if (estrategia == "DEPTH") {
				value = (1 / (prof + 1.0));
			}
			if (estrategia == "BREADTH") {
				value = prof;
			}
			if (estrategia == "GREEDY") {
				value = h;
			}

			if (estrategia == "UNIFORM") {
				value = costo;
			}
			if (estrategia == "A") {
				value = h + costo;
			}
			nodoAux = new Nodo(this.contadorId++, costo, listaSucesores.get(i).getEstado(), nodo.getId(),
					listaSucesores.get(i).getAccion(), prof, h, value);
			if (!pertenece(nodoAux.getId_estado())) {
				ListaNodos.add(nodoAux);
			}
		}
		return ListaNodos;

	}

	public ArrayList<Stack<Nodo>> crearCamino(Nodo fin) {
		Stack<Nodo> camino = new Stack<Nodo>();
		Stack<Nodo> caminoDibujar = new Stack<Nodo>();
		ArrayList<Stack<Nodo>> caminos = new ArrayList<Stack<Nodo>>();
		camino.add(fin);
		caminoDibujar.add(fin);
		Nodo actual = buscarPadre(fin.getId_padre());
		while (actual.getId_padre() != -1) {
			camino.add(actual);
			caminoDibujar.add(actual);
			actual = buscarPadre(actual.getId_padre());
		}
		camino.add(actual);
		caminoDibujar.add(actual);
		caminos.add(camino);
		caminos.add(caminoDibujar);
		return caminos;
	}

	public Nodo buscarPadre(long idPadre) {
		Nodo padre = null;
		for (int i = 0; i < this.visitados.size(); i++) {
			if (idPadre == this.visitados.get(i).getId()) {
				padre = this.visitados.get(i);
			}
		}
		return padre;
	}

	public boolean pertenece(int[] x) {
		boolean pertenece = false;

		for (int i = 0; i < visitados.size(); i++) {
			if (visitados.get(i).getId_estado()[0] == x[0] && visitados.get(i).getId_estado()[1] == x[1]) {
				pertenece = true;
			}
		}
		return pertenece;
	}

	public ArrayList<Sucesor> expandir_Nodo(Nodo nodo) {
		ArrayList<Sucesor> nodosHijo = new ArrayList<Sucesor>();
		Sucesor nodoHijo;
		int f = nodo.getId_estado()[0];
		int c = nodo.getId_estado()[1];
		int[] posicionN = new int[2];
		int[] posicionE = new int[2];
		int[] posicionS = new int[2];
		int[] posicionO = new int[2];

		if (laberinto[f][c].getNeighbors()[0]) {
			posicionN[0] = f - 1;
			posicionN[1] = c;
			nodoHijo = new Sucesor("N", posicionN, 1);
			nodosHijo.add(nodoHijo);
		}
		if (laberinto[f][c].getNeighbors()[1]) {
			posicionE[0] = f;
			posicionE[1] = c + 1;
			nodoHijo = new Sucesor("E", posicionE, 1);
			nodosHijo.add(nodoHijo);
		}
		if (laberinto[f][c].getNeighbors()[2]) {
			posicionS[0] = f + 1;
			posicionS[1] = c;
			nodoHijo = new Sucesor("S", posicionS, 1);
			nodosHijo.add(nodoHijo);
		}
		if (laberinto[f][c].getNeighbors()[3]) {
			posicionO[0] = f;
			posicionO[1] = c - 1;
			nodoHijo = new Sucesor("O", posicionO, 1);
			nodosHijo.add(nodoHijo);
		}

		return nodosHijo;

	}

	public double calcularCosto(Sucesor nodo, Nodo nodoPadre) {

		double costo = 0;
		costo = nodoPadre.getCosto();
		double costoBueno =0;
		costoBueno = costo + this.laberinto[nodo.getEstado()[0]][nodo.getEstado()[1]].getValue();

		return costoBueno;
	}

	public double calcularValueInicial(String estrategia) {
		double valueInicial = 0;
		if (estrategia == "DEPTH") {
			valueInicial = 1;
		} else {
			if (estrategia == "BREADTH") {
				valueInicial = 0;
			}
			if (estrategia == "GREEDY") {
				valueInicial = this.nodoInicial.getHeuristica();
			}
			if (estrategia == "UNIFORM") {
				valueInicial = 0;
			}
			if (estrategia == "A") {
				valueInicial = this.nodoInicial.getHeuristica();
			}
		}
		return valueInicial;
	}

	public Frontera getFrontera() {
		return frontera;
	}

	public void setFrontera(Frontera frontera) {
		this.frontera = frontera;
	}

	public ArrayList<Nodo> getVisitados() {
		return visitados;
	}

	public void setVisitados(ArrayList<Nodo> visitados) {
		this.visitados = visitados;
	}

}

