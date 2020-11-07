import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


public class Frontera {
	
	private Nodo inicial;
	private Estado objetivo;
	private ArrayList<Nodo> frontera;
	private ArrayList<int[]> visitados;
	
	private int fila;
	private int columna;
	
	private int columnas;
	private int filas;
	private Celda[][] laberinto;
	
	
	
	
	public Frontera(ImportarJsonSucesores sucesores, Celda[][] laberinto, JsonToObject json) {
		String ini = sucesores.getINITIAL();
		String objetiv = sucesores.getOBJETIVE();
		
		
		
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
		this.visitados = new ArrayList<int[]>();
		this.laberinto= laberinto;
		this.columnas = json.cols;
		this.filas = json.rows;
		
		objetivo();
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
	

	
	public int[] generarNodos() {
		int[]posicion = new int[2];
		Random r = new Random();
		int fila = r.nextInt(this.filas);
		int columna = r.nextInt(this.columnas);
		this.laberinto[fila][columna].setVisitada(true);
		
		posicion[0] = fila;
		posicion[1] = columna;
		
		return posicion;
		
	}
	
	public void objetivo() {
		int[]ini = new int[2];
		ini = inicial.getId_estado();
		int[]obj = new int[2];
		obj=objetivo.getId();
		int[]actual = new int[2];
		this.laberinto[ini[0]][ini[1]].setVisitada(true);
		
		
		if(actual[0] != obj[0] && actual[1] != obj[1]  && this.laberinto[actual[0]][actual[1]].getVisitada() == false) {
			actual = generarNodos();
		}
		
		
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

}