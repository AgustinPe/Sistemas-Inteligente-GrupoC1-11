import java.util.ArrayList;
import java.util.PriorityQueue;

public class Busqueda {

	private ArrayList<Nodo> visitados;
	private Frontera frontera;
	private String nodoIni;
	private Nodo nodo;
	private JsonToObject objeto = new JsonToObject();
	private int[] objetivo;
	private int profundidadmax;
	private Celda[][] laberinto;
	private int contadorid=0;		
	
	
	public Busqueda() {
		visitados=new ArrayList<Nodo>();
		frontera=new Frontera();
	}
	public Busqueda(ImportarJsonSucesores sucesores, JsonToObject objeto,Celda[][] laberinto ) {
		this.nodoIni = sucesores.getINITIAL();
		this.nodo= new Nodo(0, 0, null, 0, null, 0, 0,0);
		this.objeto= objeto;
		DrawLab cor = new DrawLab(objeto);
		cor.coordenadas(sucesores.getOBJETIVE());
		this.objetivo[0]=cor.getCoordenadaFila();
		this.objetivo[1]=cor.getCoordenadaColumna();
		this.profundidadmax= 10000000;
		this.laberinto=laberinto;
	}
	
	public int heurística(int[] nodo) {
		int h1;
		int h2;
		int h;
		int filasImportado = nodo[0];
		int columnasImportado = nodo[1];
		
		h1=Math.abs(objeto.getRows()-filasImportado);
		h2=Math.abs(objeto.getCols()-columnasImportado);
		h=h1+h2;
		return h;
		
	}
	public ArrayList<int[]> insertar(){
//		solución = Falso
	//
//		Mientras (frontera no es vacia) y (no hay solución) hacer
	//
//		    nodo = frontera.primer_elemento()
//		    
//		    Si Problema.objetivo(nodo.estado) entonces
//		        solución = Verdad
//		    Sino Si (nodo.estado no está en visitado) y (nodo.profundidad < profundidad) entonces
//		        insertar nodo.estado en visitados
//		        lista_de_nodos_hijos = EXPANDIR_NODO(Problema, nodo, estrategia)
//		        Para cada nodo_hijo en lista_de_nodos hacer
//		            insertar nodo_hijo en frontera
//		Si solución entonces
//		    devolver camino(nodo)
//		si no
//		    devolver no hay solución
			
		boolean solucion = false;
		ArrayList<Nodo> nodosHijo;
		
		do {
			nodo = frontera.eliminar();
			if(nodo.getId_estado()[0] == this.objetivo[0] && nodo.getId_estado()[1] == this.objetivo[1]) {
				solucion = true;
			}else if (nodo.isVisitado()==false && nodo.getProfundidad() < profundidadmax) {
				visitados.add(nodo);
				nodosHijo = expandir_Nodo(nodo);
				
			}
			
			
			
		}while(!this.frontera.estaVacia()  && solucion== false);
		return null;
	}
	
	
	public ArrayList<Nodo> expandir_Nodo(Nodo nodo){
		ArrayList<Nodo> nodosHijo = new ArrayList<Nodo>();
		Nodo nodoHijo;
		int f= nodo.getId_estado()[0];
		int c= nodo.getId_estado()[1];
		int[] posicion = new int[2];
		
		
		if(laberinto[f][c].getNeighbors()[0]) {
			posicion[0]=f-1;
			posicion[1]=c;
			nodoHijo= new Nodo(contadorid++, 0, posicion, nodo.getId(), "N", nodo.getProfundidad()+1, heurística(nodo.getId_estado()),laberinto[f-1][c].getValue());
			nodosHijo.add(nodoHijo);
		}
		if(laberinto[f][c].getNeighbors()[1]) {
			posicion[0]=f;
			posicion[1]=c+1;
			nodoHijo= new Nodo(contadorid++, 0, posicion, nodo.getId(), "E", nodo.getProfundidad()+1, heurística(nodo.getId_estado()),laberinto[f][c+1].getValue());
			nodosHijo.add(nodoHijo);
		}
		if(laberinto[f][c].getNeighbors()[2]) {
			posicion[0]=f+1;
			posicion[1]=c;
			nodoHijo= new Nodo(contadorid++, 0, posicion, nodo.getId(), "S", nodo.getProfundidad()+1, heurística(nodo.getId_estado()),laberinto[f+1][c].getValue());
			nodosHijo.add(nodoHijo);
		}
		if(laberinto[f][c].getNeighbors()[3]) {
			posicion[0]=f;
			posicion[1]=c-1;
			nodoHijo= new Nodo(contadorid++, 0, posicion, nodo.getId(), "O", nodo.getProfundidad()+1, heurística(nodo.getId_estado()),laberinto[f][c-1].getValue());
			nodosHijo.add(nodoHijo);
		}
		
		return nodosHijo;
		
	}
	
	
	
}
