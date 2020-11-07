public class ImportarJsonSucesores {

	String INITIAL;
	String OBJETIVE;
	String MAZE;
	
	public ImportarJsonSucesores() {
		
	}
	
	
	public ImportarJsonSucesores(String iNITIAL, String oBJETIVE, String mAZE) {
		super();
		INITIAL = iNITIAL;
		OBJETIVE = oBJETIVE;
		MAZE = mAZE;
	}


	public String getINITIAL() {
		return INITIAL;
	}


	public void setINITIAL(String iNITIAL) {
		INITIAL = iNITIAL;
	}


	public String getOBJETIVE() {
		return OBJETIVE;
	}


	public void setOBJETIVE(String oBJETIVE) {
		OBJETIVE = oBJETIVE;
	}


	public String getMAZE() {
		return MAZE;
	}


	public void setMAZE(String mAZE) {
		MAZE = mAZE;
	}


	@Override
	public String toString() {
		return "ImportarJsonSucesores [INITIAL=" + INITIAL + ", OBJETIVE=" + OBJETIVE + ", MAZE=" + MAZE + "]";
	}
	
}
