import java.util.*;
public class DrawLab {
    private int Columnas;  
    private int Filas;   // dimensión del laberinto
    private boolean[][] norte;     // existe pared hacia el norte de la casilla i,j
    private boolean[][] este;
    private boolean[][] sur;
    private boolean[][] oeste;
    TreeMap<String, CeldaJson> celdas;
    int coordenadaFila;
    int coordenadaColumna;
  
    public DrawLab(JsonToObject r) {
     
    	this.celdas= r.cells;
    	this.Columnas = r.cols;
        this.Filas = r.rows;
        
        StdDraw.setYscale(0, Filas+2);
		StdDraw.setXscale(0, Columnas+2);
     
        inicializar();
        Paredes();
     
    }

    private void inicializar() {
       // inicializar todas las paredes como presentes
       // nótese que cada pared se almacena 2 veces
        norte = new boolean[Columnas+2][Filas+2];
        este  = new boolean[Columnas+2][Filas+2];
        sur   = new boolean[Columnas+2][Filas+2];
        oeste = new boolean[Columnas+2][Filas+2];
        for (int x = 0; x < Columnas+2; x++) {
            for (int y = 0; y < Filas+2; y++) {
            	norte[x][y] = este[x][y] = oeste[x][y] = sur[x][y] = true;
            }
        }
        
    }
    
    //Metodo para sacar la posición de las celdas que tienen paredes a False.
    
    public void Paredes() { 
    	
    	//x es columna
    	//y es fila
    	
    	CeldaJson kk = new CeldaJson();
    	boolean[] vecinos;
    	String key;
    	int cambio=0;
    	
    	for (Map.Entry<String,CeldaJson> entry : celdas.entrySet()) {
    		key = entry.getKey();
    		kk = entry.getValue();
	    	vecinos = kk.getNeighbors();
	    	
	    	
//	    		int coordenadaF = Integer.parseInt(String.valueOf(key.charAt(1)));
//	    		int corColumna = Integer.parseInt(String.valueOf(key.charAt(4)))+1;	
	    	
	    	coordenadas(key);
	    	int coordenadaF = this.coordenadaFila;
	    	int corColumna = this.coordenadaColumna +1;
	    	
	    		int corFila = Filas - coordenadaF ;// por que borra empezando desde abajo y desde la izquierda
	    	
    				if(vecinos[0]== true && corFila<Filas) { //Celda norte
    					norte[corColumna][corFila] = sur[corColumna][corFila+1] = false;
    				}
    				if(vecinos[1]== true && corColumna<Columnas) { //Pared este
    					este[corColumna][corFila] = oeste[corColumna+1][corFila] = false;		
    				}
    				if(vecinos[2]== true && corFila>1) { //Pared sur
    					sur[corColumna][corFila] = norte[corColumna][corFila-1] = false;
    				}
    				if(vecinos[3]== true && corColumna>1){ //Pared Oeste
    					oeste[corColumna][corFila] = este[corColumna-1][corFila] = false;	
    				}
    		
    	}
    }
    
    public void coordenadas(String key) {
    	
    	char[] aCaracteres = key.toCharArray();
    	char anterior = 'X';
    	String actual = " ";
    	int contador=0;
    	
    	
    	for(int i=0; i<aCaracteres.length;i++) {
    	 try {
			if(Character.isDigit(aCaracteres[i])) {
				
    			if(Character.isDigit(anterior)) {
    				actual = String.valueOf(anterior)+String.valueOf(aCaracteres[i]);
    				
    			}
    			else {
    				actual =String.valueOf(aCaracteres[i]);
    			}
    			anterior = aCaracteres[i];
    		}else if(Character.isDigit(anterior) && !(Character.isDigit(aCaracteres[i]))) {
    			this.coordenadaFila = Integer.parseInt(actual);
    			contador= i+1;
    			i=aCaracteres.length;
    			anterior=' ';
    		}
    	 } catch (NumberFormatException exception) {
    	 }
    	}
    	for(int j=contador;j<aCaracteres.length;j++){
    		try {
    		if(Character.isDigit(aCaracteres[j])) {
				
    			if(Character.isDigit(anterior)) {
    				actual = String.valueOf(anterior)+String.valueOf(aCaracteres[j]);
    				
    			}
    			else {
    				actual =String.valueOf(aCaracteres[j]);
    			}
    			anterior = aCaracteres[j];
    		}else if(Character.isDigit(anterior) && !(Character.isDigit(aCaracteres[j]))) {
    			this.coordenadaColumna = Integer.parseInt(actual);
    			j=aCaracteres.length;
    		}
    		} catch (NumberFormatException exception) {
    		}
    	}
    }
    	
    
	 // dibuja el laberinto
    public void dibujar() {
    	
//        StdDraw.setPenColor(StdDraw.RED);
//        StdDraw.filledCircle(Columnas + 0.5, Filas + 0.5, 0.375);
//        StdDraw.filledCircle(1.5, 1.5, 0.375);
    	
    	
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x = 1; x <= Columnas; x++) { // mas 1 por que dejo margen en la ventana
            for (int y = 1; y <= Filas; y++) {
                if (sur[x][y])   StdDraw.line(x, y, x + 1, y);
                if (norte[x][y]) StdDraw.line(x, y + 1, x + 1, y + 1);
                if (oeste[x][y]) StdDraw.line(x, y, x, y + 1);
                if (este[x][y])  StdDraw.line(x + 1, y, x + 1, y + 1);
            }
        }
        StdDraw.show(0);
    }
    
}

