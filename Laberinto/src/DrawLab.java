import java.util.*;

public class DrawLab {
	private int Columnas;
	private int Filas; // dimensi�n del laberinto
	private boolean[][] norte; // existe pared hacia el norte de la casilla i,j
	private boolean[][] este;
	private boolean[][] sur;
	private boolean[][] oeste;
	TreeMap<String, CeldaJson> celdas;
	private int coordenadaFila;
	private int coordenadaColumna;
	private Celda[][] laberinto;

	public DrawLab(JsonToObject r) {

		this.celdas = r.cells;
		this.Columnas = r.cols;
		this.Filas = r.rows;
		inicializar();
		inicializarLab();
		insetaValores();
		Paredes();

	}

	private void inicializar() {
		// inicializar todas las paredes como presentes
		// n�tese que cada pared se almacena 2 veces
		norte = new boolean[Columnas + 2][Filas + 2];
		este = new boolean[Columnas + 2][Filas + 2];
		sur = new boolean[Columnas + 2][Filas + 2];
		oeste = new boolean[Columnas + 2][Filas + 2];
		for (int x = 0; x < Columnas + 2; x++) {
			for (int y = 0; y < Filas + 2; y++) {
				norte[x][y] = este[x][y] = oeste[x][y] = sur[x][y] = true;
			}
		}
	}
	private void inicializarLab() {
		laberinto = new Celda[Filas][Columnas];
		for (int i = 0; i < Filas; i++) {
			for (int j = 0; j < Columnas; j++) {
				Celda celda = new Celda();
				celda.setNeighbors(0, false);
				celda.setNeighbors(1, false);
				celda.setNeighbors(2, false);
				celda.setNeighbors(3, false);
				celda.setValue(0);
				celda.setVisitada(false);
				celda.setPosicion(0, i);
				celda.setPosicion(1, j);
				laberinto[i][j] = celda;
			}
		}
	}
	
	public void insetaValores() {
		
		for (Map.Entry<String, CeldaJson> entry : celdas.entrySet()) {	
			String key = entry.getKey();
			coordenadas(key);
			int filasImportado = this.coordenadaFila;
			int columnasImportado = this.coordenadaColumna;
			CeldaJson celdaj = new CeldaJson();
			celdaj = entry.getValue();
			laberinto[filasImportado][columnasImportado].setNeighbors(0, celdaj.getNeighbors()[0]);
			laberinto[filasImportado][columnasImportado].setNeighbors(1, celdaj.getNeighbors()[1]);
			laberinto[filasImportado][columnasImportado].setNeighbors(2, celdaj.getNeighbors()[2]);
			laberinto[filasImportado][columnasImportado].setNeighbors(3, celdaj.getNeighbors()[3]);
			laberinto[filasImportado][columnasImportado].setValue(celdaj.getValue());
			laberinto[filasImportado][columnasImportado].setVisitada(false);
			laberinto[filasImportado][columnasImportado].setPosicion(0, filasImportado);
			laberinto[filasImportado][columnasImportado].setPosicion(1, columnasImportado);
		}

	}

	// Metodo para sacar la posici�n de las celdas que tienen paredes a False.

	public void Paredes() {

		// x es columna
		// y es fila

		CeldaJson kk = new CeldaJson();
		boolean[] vecinos;
		String key;
		int cambio = 0;

		for (Map.Entry<String, CeldaJson> entry : celdas.entrySet()) {
			key = entry.getKey();
			kk = entry.getValue();
			vecinos = kk.getNeighbors();

			coordenadas(key);
			int coordenadaF = this.coordenadaFila;
			int corColumna = this.coordenadaColumna + 1;
			
			int corFila = Filas - coordenadaF;// por que borra empezando desde abajo y desde la izquierda

			if (vecinos[0] == true && corFila < Filas) { // Celda norte
				norte[corColumna][corFila] = sur[corColumna][corFila + 1] = false;
			}
			if (vecinos[1] == true && corColumna < Columnas) { // Pared este
				este[corColumna][corFila] = oeste[corColumna + 1][corFila] = false;
			}
			if (vecinos[2] == true && corFila > 1) { // Pared sur
				sur[corColumna][corFila] = norte[corColumna][corFila - 1] = false;
			}
			if (vecinos[3] == true && corColumna > 1) { // Pared Oeste
				oeste[corColumna][corFila] = este[corColumna - 1][corFila] = false;
			}

		}
	}

	public void coordenadas(String key) {

		char[] aCaracteres = key.toCharArray();
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
				this.coordenadaFila = Integer.parseInt(actual);
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
				this.coordenadaColumna = Integer.parseInt(actual);
				j = aCaracteres.length;
			}
		}
	}

	// dibuja el laberinto
	public void dibujar() {

		StdDraw.setYscale(0, Filas + 2);
		StdDraw.setXscale(0, Columnas + 2);

//	      StdDraw.setPenColor(StdDraw.RED);
//	      StdDraw.filledCircle(Columnas + 0.5, Filas + 0.5, 0.375);
//	      StdDraw.filledCircle(1.5, 1.5, 0.375);
		for (int x = 0; x < Filas; x++) {
			for (int y = 0; y < Columnas; y++) {
				if (laberinto[x][y].getValue() == 1) {
					StdDraw.setPenColor(StdDraw.ORANGE);
					StdDraw.filledSquare(y + 1.5 , (Filas-(x+1)) + 1.5 , 0.5);
				} else if (laberinto[x][y].getValue() == 2) {
					StdDraw.setPenColor(StdDraw.GREEN);
					StdDraw.filledSquare(y + 1.5, (Filas-(x+1)) + 1.5, 0.5);
				} else if (laberinto[x][y].getValue() == 3) {
					StdDraw.setPenColor(StdDraw.BLUE);
					StdDraw.filledSquare( y + 1.5, (Filas-(x+1)) + 1.5, 0.5);
				}
			}
			
			}
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int x = 1; x <= Columnas; x++) { // mas 1 por que dejo margen en la ventana
			for (int y = 1; y <= Filas; y++) {
				if (sur[x][y])
					StdDraw.line(x, y, x + 1, y);
				if (norte[x][y])
					StdDraw.line(x, y + 1, x + 1, y + 1);
				if (oeste[x][y])
					StdDraw.line(x, y, x, y + 1);
				if (este[x][y])
					StdDraw.line(x + 1, y, x + 1, y + 1);
			}
		}
		
		StdDraw.show(0);
	}
	
	public int getCoordenadaFila() {
		return coordenadaFila;
	}

	public void setCoordenadaFila(int coordenadaFila) {
		this.coordenadaFila = coordenadaFila;
	}

	public int getCoordenadaColumna() {
		return coordenadaColumna;
	}

	public void setCoordenadaColumna(int coordenadaColumna) {
		this.coordenadaColumna = coordenadaColumna;
	}
}
