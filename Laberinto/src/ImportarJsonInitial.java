
public class ImportarJsonInitial {

	String initial;
	String objetive;
	String maze;
	
	
	public ImportarJsonInitial() {
		
	}
	
	public ImportarJsonInitial(String INITIAL, String OBJETIVE, String MAZE) {
		super();
		this.initial = INITIAL;
		this.objetive = OBJETIVE;
		this.maze = MAZE;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

	public String getObjetive() {
		return objetive;
	}

	public void setObjetive(String objetive) {
		this.objetive = objetive;
	}

	public String getMaze() {
		return maze;
	}

	public void setMaze(String maze) {
		this.maze = maze;
	}

	@Override
	public String toString() {
		return "ImportarJsonInitial [initial=" + initial + ", objetive=" + objetive + ", maze=" + maze + "]";
	}
		
}