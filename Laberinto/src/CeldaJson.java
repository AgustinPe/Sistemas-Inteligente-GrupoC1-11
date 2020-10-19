public class CeldaJson {
	String posicion;
	private int value;
    private boolean [] neighbors;

    public CeldaJson() {
    }

    public CeldaJson(int value, boolean[] neighbors) {
    	this.posicion="";
        this.value = value;
        this.neighbors = neighbors;
    }

    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }

    public boolean[] getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(boolean[] neighbors) {
        this.neighbors = neighbors;
    }
    public String getPosicion() {
        return posicion;
    }
    public void setPosicion(String posicion) {
    	this.posicion=posicion;
    }

    @Override
    public String toString() {
        return "CeldaJson{" + ", value=" + value + ", neighbors=" + neighbors + '}';
    }
}