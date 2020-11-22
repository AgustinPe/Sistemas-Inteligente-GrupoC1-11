import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;

public class Busqueda {

	private ArrayList<int[]> visitados;
	private Frontera frontera;
	private int[] posicionIni;
	private Nodo nodoInicial;
	private Nodo nodo;
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
		this.nodo = new Nodo(0, 0, null, 0, null, 0, 0, 0);
		this.objeto = objeto;
		cor.coordenadas(sucesores.getOBJETIVE());
		this.objetivo[0] = cor.getCoordenadaFila();
		this.objetivo[1] = cor.getCoordenadaColumna();
		this.profundidadmax = 10000000;
		this.laberinto = laberinto;
		this.frontera = new Frontera();
		this.visitados = new ArrayList<int[]>();
		busqueda();
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
	
	

	public PriorityQueue<Nodo> busqueda() {

		boolean solucion = false;
		ArrayList<Nodo> nodosHijo = new ArrayList<Nodo>();
		PriorityQueue<Nodo> camino = new PriorityQueue<Nodo>();
		frontera.insertar(nodoInicial);
		frontera.insertarV(nodoInicial);

		do {
			// Estrategia
			nodo = frontera.eliminar();
			//)
			if (nodo.getId_estado()[0] == this.objetivo[0] && nodo.getId_estado()[1] == this.objetivo[1]) {
				solucion = true;
			//} else if (!pertenece(nodo.getId_estado()) && nodo.getProfundidad() < profundidadmax) {
			} else if ( nodo.getProfundidad() < profundidadmax) {
				visitados.add(nodo.getId_estado());
				frontera.insertarV(nodo);
				nodosHijo = expandir_Nodo(nodo);

				for (int i = 0; i <= nodosHijo.size(); i++) {
					frontera.insertar(nodosHijo.get(i));
				}
			}
		} while (!this.frontera.estaVacia() && solucion == false);

		if (solucion == true) {

			camino = damePadres(nodo);
			return camino;
		} else
			return null;

	}
	
	public ArrayList<Nodo> CreaListaDeNodos (ArrayList<Sucesor> listaSucesores, Nodo nodo, String estrategia, int contador, Map<String, Double> podas, boolean poda) throws NoSuchAlgorithmException{
		ArrayList<Nodo> ListaNodos = new ArrayList<Nodo>();
		Nodo nodoAux;
		double valor=0;
		int prof;
		boolean podar;
		double h;
		for(int i=0; i<listaSucesores.size();i++) {
			podar=false;
			contador++;
			prof=nodo.getProfundidad()+1;
			h=heuristica(listaSucesores.get(i).getEstado());
			if (estrategia=="profundidad") {
				valor=(1/(prof+1.0));
				if(poda==true) {
					if(podas.containsKey(listaSucesores.get(i).getCubo().md5())){
						if(podas.get(listaSucesores.get(i).getCubo().md5())>=valor) {
							podar = true;
						}
					}
				}
			}
			else {
				if(estrategia=="anchura") {
					valor = prof;
				}			
				if(estrategia=="voraz") {
					valor = h;
				}
				float costo = nodo.getCosto() + listaSucesores.get(i).getCosto_move();
				if (estrategia=="costeUniforme") {
					valor = costo;
				}
				if (estrategia=="A") {					
					valor = h + costo;
				}
				if(poda==true) {
					if(podas.containsKey(listaSucesores.get(i).getCubo().md5())){
						if(podas.get(listaSucesores.get(i).getCubo().md5())<=valor) {
							podar = true;
						}
					}
				}
			}
			if(podar==false) {
				nodoAux=new Nodo(contador,nodo,listaSucesores.get(i).getCubo(),
						nodo.getCosto()+listaSucesores.get(i).getCostAcci(),listaSucesores.get(i).getAcci(),prof,h,valor);
				ListaNodos.add(nodoAux);
				if(poda==true) {
					podas.put(nodoAux.getCubo().md5(),valor);
				}
			}
		}
		return ListaNodos;
	}

	public PriorityQueue<Nodo> damePadres(Nodo fin) {
		PriorityQueue<Nodo> solucion = new PriorityQueue<Nodo>();
		solucion.add(fin);
		Nodo actual = new Nodo(0, 0, null, 0, null, 0, 0, 0);

		do {
			actual = frontera.eliminarV();
			if (fin.getId_padre() == actual.getId()) {
				solucion.add(actual);
				fin = nodo;
			}
		} while (fin.getId() != 0);

		return solucion;
	}

	public boolean pertenece(int[] x) {
		boolean pertenece = false;

		for (int i = 0; i < visitados.size(); i++) {
			if (visitados.isEmpty()) {
				i = visitados.size();
			} else if (visitados.get(i)[0] == x[0]) {
				if (visitados.get(i)[1] == x[1]) {
					pertenece = true;
				}
			}
		}
		return pertenece;
	}

	public ArrayList<Nodo> expandir_Nodo(Nodo nodo) {
		ArrayList<Nodo> nodosHijo = new ArrayList<Nodo>();
		Nodo nodoHijo;
		int f = nodo.getId_estado()[0];
		int c = nodo.getId_estado()[1];
		int[] posicion = new int[2];

		if (laberinto[f][c].getNeighbors()[0]) {
			posicion[0] = f - 1;
			posicion[1] = c;
			nodoHijo = new Nodo(contadorid++, 0, posicion, nodo.getId(), "N", nodo.getProfundidad() + 1,
					heuristica(nodo.getId_estado()), laberinto[f - 1][c].getValue());
			nodosHijo.add(nodoHijo);
		}
		if (laberinto[f][c].getNeighbors()[1]) {
			posicion[0] = f;
			posicion[1] = c + 1;
			nodoHijo = new Nodo(contadorid++, 0, posicion, nodo.getId(), "E", nodo.getProfundidad() + 1,
					heuristica(nodo.getId_estado()), laberinto[f][c + 1].getValue());
			nodosHijo.add(nodoHijo);
		}
		if (laberinto[f][c].getNeighbors()[2]) {
			posicion[0] = f + 1;
			posicion[1] = c;
			nodoHijo = new Nodo(contadorid++, 0, posicion, nodo.getId(), "S", nodo.getProfundidad() + 1,
					heuristica(nodo.getId_estado()), laberinto[f + 1][c].getValue());
			nodosHijo.add(nodoHijo);
		}
		if (laberinto[f][c].getNeighbors()[3]) {
			posicion[0] = f;
			posicion[1] = c - 1;
			nodoHijo = new Nodo(contadorid++, 0, posicion, nodo.getId(), "O", nodo.getProfundidad() + 1,
					heuristica(nodo.getId_estado()), laberinto[f][c - 1].getValue());
			nodosHijo.add(nodoHijo);
		}

		return nodosHijo;

	}

}
