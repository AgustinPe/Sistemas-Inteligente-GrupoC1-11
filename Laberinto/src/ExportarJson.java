import java.util.ArrayList;
import java.util.TreeMap;

public class ExportarJson {

	private int rows;
	private int cols;
	private int max_n;
	ArrayList<ArrayList<String>> mov; 
	private String[] id_mov = new String[4];
	private TreeMap<String, CeldaJson> cells;
	


	public ExportarJson(Celda[][] laberintoj) {
		super();
		this.rows = laberintoj.length;
		this.cols = laberintoj[0].length;
		this.max_n = 4;
		this.mov = new ArrayList<>(); 
		this.id_mov[0] = "N";
		this.id_mov[1] = "E";
		this.id_mov[2] = "S";
		this.id_mov[3] = "O";
		this.cells = new TreeMap<String, CeldaJson>();
	}

	public void rellenarLista(Celda[][] laberintoj) {
		
		ArrayList<String> movimientos = new ArrayList<>();
		movimientos.add(0,"[-1,0]");
		movimientos.add(1,"[0,1]");
		movimientos.add(2,"[1,0]");
		movimientos.add(3,"[0,-1]");
		mov.add(movimientos);
		CeldaJson celda = new CeldaJson();
		String posicion;
		int value;
		boolean[] neighbors = new boolean[4];
		

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				value = 0;
				posicion = "(" + i + "," + j + ")";
				neighbors = laberintoj[i][j].getNeighbors();
				CeldaJson celdahecha = new CeldaJson();
				celdahecha.setNeighbors(neighbors);
				celdahecha.setValue(value);
				cells.put(posicion,celdahecha);
			}
		}
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getMax_n() {
		return max_n;
	}

	public void setMax_n(int max_n) {
		this.max_n = max_n;
	}

	public ArrayList<ArrayList<String>> getMov() {
		return mov;
	}

	public void setMov(ArrayList<ArrayList<String>> mov) {
		this.mov = mov;
	}

	public String[] getId_mov() {
		return id_mov;
	}

	public void setId_mov(String[] id_mov) {
		this.id_mov = id_mov;
	}

	public TreeMap<String, CeldaJson> getCells() {
		return cells;
	}

	public void setCells(TreeMap<String, CeldaJson> cells) {
		this.cells = cells;
	}

}