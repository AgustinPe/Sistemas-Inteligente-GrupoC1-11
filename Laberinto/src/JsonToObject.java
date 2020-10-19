import java.util.ArrayList;
import java.util.TreeMap;

public class JsonToObject {

	int rows;
	int cols;
	int max_n;
	ArrayList<ArrayList<String>> mov;
	String[] id_mov;
	TreeMap<String, CeldaJson> cells;

	public JsonToObject() {
	}

	public JsonToObject(int rows, int cols, int max_n, ArrayList<ArrayList<String>> mov, String[] id_mov,
			TreeMap<String, CeldaJson> cells) {
		this.rows = rows;
		this.cols = cols;
		this.max_n = max_n;
		this.mov = mov;
		this.id_mov = id_mov;
		this.cells = cells;
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

	@Override
	public String toString() {
		return "Lab{" + "rows=" + rows + ", cols=" + cols + ", max_n=" + max_n + ", mov=" + mov + ", id_mov=" + id_mov
				+ ", cells=" + cells + '}';
	}

}