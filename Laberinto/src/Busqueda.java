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
	private long contadorid = 1;

	public Busqueda() {
	}

	public Busqueda(ImportarJsonSucesores sucesores, JsonToObject objeto, Celda[][] laberinto) {
		this.posicionIni = new int[2];
		this.objetivo = new int[2];
		DrawLab cor = new DrawLab(objeto);
		cor.coordenadas(sucesores.getINITIAL());
		this.posicionIni[0] = cor.getCoordenadaFila();
		this.posicionIni[1] = cor.getCoordenadaColumna();
		this.nodoInicial = new Nodo(0, 0, posicionIni, -1, null, 0, heuristica(posicionIni),
				laberinto[posicionIni[0]][posicionIni[1]].getValue());
		this.objeto = objeto;
		cor.coordenadas(sucesores.getOBJETIVE());
		this.objetivo[0] = cor.getCoordenadaFila();
		this.objetivo[1] = cor.getCoordenadaColumna();
		this.profundidadmax = 10000000;
		this.laberinto = laberinto;
		this.frontera = new Frontera();
		this.visitados = new ArrayList<Nodo>();
	}

	public int heuristica(int[] nodo) {
		int h1;
		int h2;
		int h;
		int filasImportado = nodo[0];
		int columnasImportado = nodo[1];

		h1 = Math.abs(objeto.getRows() - filasImportado);
		h2 = Math.abs(objeto.getCols() - columnasImportado);
		h = h1 + h2;
		return h;

	}

	public Stack<Nodo> busqueda(String estrategia) {
		int contador = 0;
		Nodo nodo;
		boolean solucion = false;
		ArrayList<Nodo> nodosHijo = new ArrayList<Nodo>();
		ArrayList<Sucesor> listaSucesores = new ArrayList<Sucesor>();
		PriorityQueue<Nodo> camino = new PriorityQueue<Nodo>();
		frontera.insertar(nodoInicial);
		this.visitados.add(nodoInicial);

		do {
			nodo = frontera.eliminar();
			if (nodo.getId_estado()[0] == this.objetivo[0] && nodo.getId_estado()[1] == this.objetivo[1]) {
				solucion = true;
			} else if (nodo.getProfundidad() < profundidadmax && !pertenece(nodo.getId_estado())) {
				visitados.add(nodo);
				listaSucesores = expandir_Nodo(nodo);
				nodosHijo = CreaListaDeNodos(listaSucesores, nodo, estrategia, contador);
			}
		} while (!this.frontera.estaVacia() && solucion == false);

		if (solucion == true) {
			return crearCamino(nodo);
		} else
			return null;

	}

	public ArrayList<Nodo> CreaListaDeNodos(ArrayList<Sucesor> listaSucesores, Nodo nodo, String estrategia,
			int contador) {
		ArrayList<Nodo> ListaNodos = new ArrayList<Nodo>();
		Nodo nodoAux;
		double valor = 0;
		int prof;
		boolean podar;
		float costo;
		double h;

		for (int i = 0; i < listaSucesores.size(); i++) {
			podar = false;
			contador++;
//			buscarPadre(listaSucesores.get(i).get);
			prof = nodo.getProfundidad() + 1;
			h = heuristica(listaSucesores.get(i).getEstado());
			if (estrategia == "profundidad") {
				valor = (1 / (prof + 1.0));
			} else {
				if (estrategia == "anchura") {
					valor = prof;
				}
				if (estrategia == "voraz") {
					valor = h;
				}
				costo = nodo.getCosto() + listaSucesores.get(i).getCosto_move();
				if (estrategia == "costeUniforme") {
					valor = costo;
				}
				if (estrategia == "A") {
					valor = h + costo;
				}
				nodoAux = new Nodo(contador, costo, listaSucesores.get(i).getEstado(), nodo.getId(),
						listaSucesores.get(i).getAccion(), prof, h, valor);
				if (!pertenece(nodoAux.getId_estado())) {
					ListaNodos.add(nodoAux);
					this.visitados.add(nodoAux);
				}
			}
		}
		return ListaNodos;
	}

	public Stack<Nodo> crearCamino(Nodo fin) {
		Stack<Nodo> camino = new Stack<Nodo>();
		camino.add(fin);
		Nodo actual = buscarPadre(fin.getId_padre());

		while (actual.getId_padre() != -1) {
			camino.add(actual);
			actual = buscarPadre(actual.getId_padre());
		}

		return camino;
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
		int[] posicion = new int[2];

		if (laberinto[f][c].getNeighbors()[0]) {
			posicion[0] = f - 1;
			posicion[1] = c;
			nodoHijo = new Sucesor("N", posicion, 1);
			nodosHijo.add(nodoHijo);
		}
		if (laberinto[f][c].getNeighbors()[1]) {
			posicion[0] = f;
			posicion[1] = c + 1;
			nodoHijo = new Sucesor("E", posicion, 1);
			nodosHijo.add(nodoHijo);
		}
		if (laberinto[f][c].getNeighbors()[2]) {
			posicion[0] = f + 1;
			posicion[1] = c;
			nodoHijo = new Sucesor("S", posicion, 1);
			nodosHijo.add(nodoHijo);
		}
		if (laberinto[f][c].getNeighbors()[3]) {
			posicion[0] = f;
			posicion[1] = c - 1;
			nodoHijo = new Sucesor("O", posicion, 1);
			nodosHijo.add(nodoHijo);
		}

		return nodosHijo;

	}

	public Nodo calcularCosto(long idPadre) {
		Nodo padre = null;
		for (int i = 0; i < this.visitados.size(); i++) {
			if (idPadre == this.visitados.get(i).getId()) {
				padre = this.visitados.get(i);
			}
		}
		return padre;
	}

}
