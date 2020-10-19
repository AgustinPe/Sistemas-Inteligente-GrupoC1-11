import java.util.ArrayList;
import java.util.Random;

public class Laberinto {

	private Celda[][] laberinto;
	private int filas;
	private int columnas;
	private ArrayList<ArrayList<Celda>> listaCaminos;

	public Laberinto(int filas, int columnas) {
		super();
		this.filas = filas;
		this.columnas = columnas;
		this.laberinto = new Celda[filas][columnas];
		this.listaCaminos = new ArrayList<>();
		rellenar_celdas();
		generar_destino();
	}

	public int getFilas() {
		return filas;
	}

	public void setFilas(int filas) {
		this.filas = filas;
	}

	public int getColumnas() {
		return columnas;
	}

	public Celda[][] getLaberinto() {
		return laberinto;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public ArrayList<ArrayList<Celda>> getListaCaminos() {
		return listaCaminos;
	}

	public void setListaCaminos(ArrayList<ArrayList<Celda>> listaCaminos) {
		this.listaCaminos = listaCaminos;
	}

	public void generar_destino() {
		Random r = new Random();
		int fila = r.nextInt(this.filas);
		int columna = r.nextInt(this.columnas);
		this.laberinto[fila][columna].setVisitada(true);
	}

	public void copiar_laberinto_auxiliar(Celda[][] laberintoAuxiliar) {
		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				this.laberinto[i][j].setPosicion(0, laberintoAuxiliar[i][j].getPosicion()[0]);
				this.laberinto[i][j].setPosicion(1, laberintoAuxiliar[i][j].getPosicion()[1]);
				this.laberinto[i][j].setNeighbors(0, laberintoAuxiliar[i][j].getNeighbors()[0]);
				this.laberinto[i][j].setNeighbors(1, laberintoAuxiliar[i][j].getNeighbors()[1]);
				this.laberinto[i][j].setNeighbors(2, laberintoAuxiliar[i][j].getNeighbors()[2]);
				this.laberinto[i][j].setNeighbors(3, laberintoAuxiliar[i][j].getNeighbors()[3]);
				this.laberinto[i][j].setValue(0);
				this.laberinto[i][j].setVisitada(laberintoAuxiliar[i][j].getVisitada());

			}
		}
	}

	public Celda[][] copiar_laberinto(Celda[][] laberintoAuxiliar) {
		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				Celda c = new Celda();
				laberintoAuxiliar[i][j] = c;
				laberintoAuxiliar[i][j].setPosicion(0, this.laberinto[i][j].getPosicion()[0]);
				laberintoAuxiliar[i][j].setPosicion(1, this.laberinto[i][j].getPosicion()[1]);
				laberintoAuxiliar[i][j].setNeighbors(0, this.laberinto[i][j].getNeighbors()[0]);
				laberintoAuxiliar[i][j].setNeighbors(1, this.laberinto[i][j].getNeighbors()[1]);
				laberintoAuxiliar[i][j].setNeighbors(2, this.laberinto[i][j].getNeighbors()[2]);
				laberintoAuxiliar[i][j].setNeighbors(3, this.laberinto[i][j].getNeighbors()[3]);
				laberintoAuxiliar[i][j].setValue(0);
				laberintoAuxiliar[i][j].setVisitada(this.laberinto[i][j].getVisitada());
			}
		}
		return laberintoAuxiliar;
	}

	public Celda[][] vaciar_laberinto_auxiliar(Celda[][] laberintoAuxiliar) {
		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {

				laberintoAuxiliar[i][j].setNeighbors(0, false);
				laberintoAuxiliar[i][j].setNeighbors(1, false);
				laberintoAuxiliar[i][j].setNeighbors(2, false);
				laberintoAuxiliar[i][j].setNeighbors(3, false);
				laberintoAuxiliar[i][j].setValue(0);
				laberintoAuxiliar[i][j].setVisitada(false);
			}
		}
		return laberintoAuxiliar;
	}

	public boolean celda_repetida(ArrayList<Celda> camino, Celda celda) {
		boolean repetida = false;
		for (int i = 0; i < camino.size(); i++) {
			if (camino.get(i).getPosicion()[0] == celda.getPosicion()[0]
					&& camino.get(i).getPosicion()[1] == celda.getPosicion()[1])
				repetida = true;
		}

		return repetida;
	}

	public boolean sobrepasa_tablero(int fila, int columna, int direccion_salto) {
		boolean sobrepasa = false;
		int limite_filas = this.filas;
		int limite_columnas = this.columnas;

		switch (direccion_salto) {
		case 0:
			if (fila == 0) {
				sobrepasa = true;
			}
			break;

		case 1:
			if (columna == --limite_columnas) {
				sobrepasa = true;
			}
			break;

		case 2:
			if (fila == --limite_filas) {
				sobrepasa = true;
			}
			break;

		case 3:
			if (columna == 0) {
				sobrepasa = true;
			}
			break;
		}

		return sobrepasa;
	}

	public void rellenar_celdas() {

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				Celda celda = new Celda();
				celda.setNeighbors(0, false);
				celda.setNeighbors(1, false);
				celda.setNeighbors(2, false);
				celda.setNeighbors(3, false);
				celda.setValue(0);
				celda.setVisitada(false);
				celda.setPosicion(0, i);
				celda.setPosicion(1, j);
				this.laberinto[i][j] = celda;
			}
		}
	}

	public boolean laberinto_completado() {
		boolean completado = true;
		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				if (!this.laberinto[i][j].getVisitada()) {
					completado = false;
				}
			}
		}
		return completado;
	}

	public void generar_camino() {
		int[] actual = new int[2];
		int fila;
		int columna;
		int direccion_salto;
		boolean bucle_generado = false;
		Celda[][] laberintoAuxiliar = new Celda[filas][columnas];
		ArrayList<Celda> camino = new ArrayList<>();
		Random r = new Random();
		laberintoAuxiliar = copiar_laberinto(laberintoAuxiliar);

		do {
			fila = r.nextInt(this.filas);
			columna = r.nextInt(this.columnas);
		} while (laberintoAuxiliar[fila][columna].getVisitada());

		actual[0] = fila;
		actual[1] = columna;
		laberintoAuxiliar[fila][columna].setVisitada(true);
		camino.add(laberintoAuxiliar[fila][columna]);

		do {
			direccion_salto = r.nextInt(4);
			while (laberintoAuxiliar[actual[0]][actual[1]].getNeighbors()[direccion_salto]
					|| sobrepasa_tablero(actual[0], actual[1], direccion_salto)) {
				direccion_salto = r.nextInt(4);
			}
			switch (direccion_salto) {
			case 0:
				laberintoAuxiliar[actual[0]][actual[1]].setVisitada(true);
				laberintoAuxiliar[actual[0]][actual[1]].setNeighbors(direccion_salto, true);
				laberintoAuxiliar[--actual[0]][actual[1]].setNeighbors(2, true);
				break;

			case 1:
				laberintoAuxiliar[actual[0]][actual[1]].setVisitada(true);
				laberintoAuxiliar[actual[0]][actual[1]].setNeighbors(direccion_salto, true);
				laberintoAuxiliar[actual[0]][++actual[1]].setNeighbors(3, true);
				break;

			case 2:
				laberintoAuxiliar[actual[0]][actual[1]].setVisitada(true);
				laberintoAuxiliar[actual[0]][actual[1]].setNeighbors(direccion_salto, true);
				laberintoAuxiliar[++actual[0]][actual[1]].setNeighbors(0, true);
				break;

			case 3:
				laberintoAuxiliar[actual[0]][actual[1]].setVisitada(true);
				laberintoAuxiliar[actual[0]][actual[1]].setNeighbors(direccion_salto, true);
				laberintoAuxiliar[actual[0]][--actual[1]].setNeighbors(1, true);
				break;
			}

			if (celda_repetida(camino, laberintoAuxiliar[actual[0]][actual[1]])) {
				bucle_generado = true;
			} else {
				camino.add(laberintoAuxiliar[actual[0]][actual[1]]);
			}
		} while (!laberintoAuxiliar[actual[0]][actual[1]].getVisitada() && !bucle_generado);

		if (!bucle_generado) {
			copiar_laberinto_auxiliar(laberintoAuxiliar);
			listaCaminos.add(camino);
		} else {
			camino = new ArrayList<>();
		}
	}
}