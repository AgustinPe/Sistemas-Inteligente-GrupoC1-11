import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Frontera {

	private Nodo inicial;
	private Estado objetivo;
	private List<Nodo> frontera;
	private List<Nodo> visitados;
	private int[] padre;

	private int fila;
	private int columna;

	private int contador;
	private int columnas;
	private int filas;
	private Celda[][] laberinto;

	public Frontera(ImportarJsonSucesores sucesores, Celda[][] laberinto, JsonToObject json) {
		String ini = sucesores.getINITIAL();
		String objetiv = sucesores.getOBJETIVE();
		this.contador = 0;
		padre = new int[2];

		coordenadas(ini);
		int coor[] = new int[2];
		coor[0] = this.fila;
		coor[1] = this.columna;
		this.inicial = new Nodo(0, 1, coor, null, 0, 1, 7);

		coordenadas(objetiv);

		int coor2[] = new int[2];
		coor2[0] = this.fila;
		coor2[1] = this.columna;
		this.objetivo = new Estado(coor2);

		this.frontera = new ArrayList<Nodo>();
		this.visitados = new ArrayList<Nodo>();
		this.laberinto = laberinto;
		this.columnas = json.cols;
		this.filas = json.rows;

		objetivo();
		this.frontera = ordenar(visitados);
		mostrarlista();
		System.out.println("\n");
		
		mostrarlistaOrdenada();
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

	public List<Nodo> getFrontera() {
		return frontera;
	}

	public void setFrontera(List<Nodo> frontera) {
		this.frontera = frontera;
	}

	public List<Nodo> getVisitados() {
		return visitados;
	}

	public void setVisitados(List<Nodo> visitados) {
		this.visitados = visitados;
	}

	public Nodo generarNodos() {
		int[] posicion = new int[2];
		Random r = new Random();
		int fila = r.nextInt(this.filas);
		int columna = r.nextInt(this.columnas);

		posicion[0] = fila;
		posicion[1] = columna;

		Nodo nodo = new Nodo(++this.contador, 1, posicion, padre, this.contador, 1, r.nextInt(15));
		return nodo;

	}
	
	public void mostrarlista() {
		for (int i = 0; i <= this.visitados.size() - 1; i++) { 
			System.out.println("id:" + visitados.get(i).getId()+ " " +visitados.get(i).getValor()+ " " +  visitados.get(i).getId_estado()[0] + " " + visitados.get(i).getId_estado()[1]);
		}
	}
	
	public void mostrarlistaOrdenada() {
		for (int i = 0; i <= this.frontera.size() - 1; i++) { 
			System.out.println("id:" + frontera.get(i).getId()+ " " +frontera.get(i).getValor()+ " " +  frontera.get(i).getId_estado()[0] + " " + frontera.get(i).getId_estado()[1]);
		}
	}
	public void objetivo() {
		Nodo nodo;
		int[] ini = new int[2];
		ini = inicial.getId_estado();
		int[] obj = new int[2];
		obj = objetivo.getId();
		int[] actual = new int[2];
		this.laberinto[ini[0]][ini[1]].setVisitada(true);

		do {
			do {
				padre[0] = actual[0];
				padre[1] = actual[1];
				nodo = generarNodos();
				actual = nodo.getId_estado();
			} while (this.laberinto[actual[0]][actual[1]].getVisitada() == true);
			this.laberinto[actual[0]][actual[1]].setVisitada(true);
			visitados.add(nodo);
		} while (actual[0] != obj[0] && actual[1] != obj[1]);
	}

	public void coordenadas(String coordenada) {

		char[] aCaracteres = coordenada.toCharArray();
		char anterior = 'X';
		String actual = " ";
		int contador = 0;

		for (int i = 0; i < aCaracteres.length; i++) {
			if (Character.isDigit(aCaracteres[i])) {

				if (Character.isDigit(anterior)) {
					actual = String.valueOf(anterior) + String.valueOf(aCaracteres[i]);

				} else {
					actual = String.valueOf(aCaracteres[i]);
				}
				anterior = aCaracteres[i];
			} else if (Character.isDigit(anterior) && !(Character.isDigit(aCaracteres[i]))) {
				this.fila = Integer.parseInt(actual);
				contador = i + 1;
				i = aCaracteres.length;
				anterior = ' ';
			}
		}
		for (int j = contador; j < aCaracteres.length; j++) {
			if (Character.isDigit(aCaracteres[j])) {

				if (Character.isDigit(anterior)) {
					actual = String.valueOf(anterior) + String.valueOf(aCaracteres[j]);

				} else {
					actual = String.valueOf(aCaracteres[j]);
				}
				anterior = aCaracteres[j];
			} else if (Character.isDigit(anterior) && !(Character.isDigit(aCaracteres[j]))) {
				this.columna = Integer.parseInt(actual);
				j = aCaracteres.length;
			}
		}
	}

	public static List<Nodo> ordenar(List<Nodo> ejemploLista) {
		List<Nodo> ordenada = new ArrayList<Nodo>();
		boolean entra = false;
		for (int i = 0; i <= ejemploLista.size() - 1; i++) {
			if (ordenada.isEmpty()) {
				ordenada.add(ejemploLista.get(i));

			} else {
				if (ordenada.size() == 1) {

					if (ejemploLista.get(i).getValor() > ordenada.get(0).getValor()) {
						ordenada.add(1, ejemploLista.get(i));
					} else if (ejemploLista.get(i).getValor() < ordenada.get(0).getValor()) {
						ordenada.add(0, ejemploLista.get(i));
					} else {
						if (primeroMayorQueSegundo(ejemploLista.get(i), ordenada.get(0))) {
							ordenada.add(1, ejemploLista.get(i));
						} else {
							ordenada.add(0, ejemploLista.get(i));
						}
					}
				} else {
					for (int j = 0; j <= (ordenada.size() - 1) && !entra; j++) {

						if (ejemploLista.get(i).getValor() < ordenada.get(j).getValor()) {
							ordenada.add(j, ejemploLista.get(i));
							entra = true;
						} else if (ejemploLista.get(i).getValor() == ordenada.get(j).getValor()) {
							if (primeroMayorQueSegundo(ejemploLista.get(i), ordenada.get(j))) {
								ordenada.add(j + 1, ejemploLista.get(i));
							} else {
								ordenada.add(j, ejemploLista.get(i));
							}
							entra = true;
						}
					}
					if (!entra)
						ordenada.add(ordenada.size(), ejemploLista.get(i));
				}
			}
			entra = false;
		}
		
		
		return ordenada;
	}

	public static boolean primeroMayorQueSegundo(Nodo n1, Nodo n2) {
		boolean primeroMayor = true;
		if (n1.getId_estado()[0] > n2.getId_estado()[0]) {
			primeroMayor = true;
		} else if (n1.getId_estado()[0] < n2.getId_estado()[0]) {
			primeroMayor = false;
		} else if (n1.getId_estado()[0] == n2.getId_estado()[0]) {
			if (n1.getId_estado()[1] > n2.getId_estado()[1]) {
				primeroMayor = true;
			} else if (n1.getId_estado()[1] < n2.getId_estado()[1]) {
				primeroMayor = false;
			} else {
				primeroMayor = true;
			}
		}
		return primeroMayor;
	}

}