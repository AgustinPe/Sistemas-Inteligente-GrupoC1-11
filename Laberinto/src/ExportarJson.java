
import java.util.ArrayList;

public class ExportarJson {

	private int filas;
	private int columnas;
	private int nvecinosmax;
	private String[] movimiento = new String[4];
	private String[] id_movimiento = new String[4];
	private ArrayList<CeldaJson> listaCeldas;

	public ExportarJson(Celda[][] laberintoj) {
		super();
		this.filas = laberintoj.length;
		this.columnas = laberintoj[0].length;
		this.nvecinosmax = 4;
		this.movimiento[0] = "[-1,0]";
		this.movimiento[1] = "[0,1]";
		this.movimiento[2] = "[1,0]";
		this.movimiento[3] = "[0,-1]";
		this.id_movimiento[0] = "N";
		this.id_movimiento[1] = "E";
		this.id_movimiento[2] = "S";
		this.id_movimiento[3] = "O";
		this.listaCeldas = new ArrayList<CeldaJson>();
	}

	public void rellenarLista(Celda[][] laberintoj) {

		CeldaJson celda = new CeldaJson();
		String posicion;
		int value;
		boolean[] neighbors = new boolean[4];

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {
				value = 0;
				posicion = "(" + i + "," + j + ")";
				neighbors = laberintoj[i][j].getNeighbors();
				CeldaJson celdahecha = new CeldaJson();
				celdahecha.setPosicion(posicion);
				celdahecha.setValue(value);
				celdahecha.setNeighbors(neighbors);
				listaCeldas.add(celdahecha);
			}
		}
	}

	public ArrayList<CeldaJson> getListaCeldas() {
		return listaCeldas;
	}

	public void setListaCeldas(ArrayList<CeldaJson> listaCeldas) {
		this.listaCeldas = listaCeldas;
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

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public int getNvecinos() {
		return nvecinosmax;
	}

	public void setNvecinos(int nvecinos) {
		this.nvecinosmax = nvecinos;
	}

	public String[] getId_movimiento() {
		return id_movimiento;
	}

	public void setId_movimiento(String[] id_movimiento) {
		this.id_movimiento = id_movimiento;
	}

	public String[] getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(String[] movimiento) {
		this.movimiento = movimiento;
	}

}