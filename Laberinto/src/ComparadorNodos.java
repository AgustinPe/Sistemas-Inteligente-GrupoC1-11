import java.util.Comparator;

public class ComparadorNodos implements Comparator<Nodo> {

	
	public int compare(Nodo nodo1, Nodo nodo2) {
	if (nodo1.getValor()<nodo2.getValor()) return -1;
	if (nodo1.getValor()>nodo2.getValor()) return 1;
	if (nodo1.getId_estado()[0] < nodo2.getId_estado()[0]) return -1;
	if (nodo1.getId_estado()[0] > nodo2.getId_estado()[0]) return 1;
	if (nodo1.getId_estado()[1] < nodo2.getId_estado()[1]) return -1;
	if (nodo1.getId_estado()[1] > nodo2.getId_estado()[1]) return 1;
	if (nodo1.getId() < nodo2.getId()) return -1;
	if (nodo1.getId() > nodo2.getId()) return 1;
	return 0;
	}
	
}