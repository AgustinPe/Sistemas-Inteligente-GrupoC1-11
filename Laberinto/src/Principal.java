
import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Principal {

	public static void main(String[] args) {
		
//	------------------Importar y Dibujar laberinto---------------------------------------------------------------
		
		 JsonToObject Objeto = new JsonToObject();
         Objeto = Importar();
         
         DrawLab laberinto1 = new DrawLab(Objeto);
         StdDraw.show(0); 
         laberinto1.dibujar();
//____________________________________________________________________________________________________________________     
         
            Scanner teclado = new Scanner(System.in);
			Gson gson = new Gson();
			
			try {
			System.out.println("Introducir numero de filas");
			int filas = teclado.nextInt();
			System.out.println("Introducir numero de columnas");
			int columnas = teclado.nextInt();
			
			Laberinto laberinto = new Laberinto(filas, columnas);
			while(!laberinto.laberinto_completado()) {
				laberinto.generar_camino();
			}
		
		
			ExportarJson exportarJson = new ExportarJson(laberinto.getLaberinto());  
			exportarJson.rellenarLista(laberinto.getLaberinto());
			String jsonlaberinto = gson.toJson(exportarJson);				
			
			FileWriter exportarJson1 = new FileWriter ("C:\\Users\\34671\\eclipse-workspace\\Generador_Laberinto(16-10)\\laberinto.json");
			exportarJson1.write(jsonlaberinto);
			exportarJson1.flush();
			exportarJson1.close();
		    
		}	catch (IOException e) {
			System.out.println("Ha ocurrido un error al crear el archivo Json");		
		}	catch(Exception e){
			System.out.println("Ocurrio un error inesperado"+e);
		}
	}

	public  static JsonToObject Importar ( ){
        
	       String json = "";
	       
	            try {
	                BufferedReader br = new BufferedReader(new FileReader("puzzle_5060.json"));
	            
	                String linea;
	            while ((linea = br.readLine()) != null ){
	                
	                json += linea;
	            }
	            
	            br.close();
	            
	            } catch (FileNotFoundException ex) {
	                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
	            } catch (IOException ex) {
	                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
	            }
	            
	            System.out.println(json);
	            Gson gson = new Gson();
	            JsonToObject r = gson.fromJson(json, JsonToObject.class);
	            return r;
	    }
	
    public static Celda[][] importarACeldas (JsonToObject r){
    	TreeMap<String, CeldaJson> kk = r.getCells();
    	
        int filasImportado = 0;
        int columnasImportado = 0;
        int nfila;
        int ncolumna;
        boolean semantica = true;
        
        for(Map.Entry<String,CeldaJson> entry : kk.entrySet()) {
            String key = entry.getKey();          
        	filasImportado = key.charAt(1) - '0';
            columnasImportado = key.charAt(3) - '0';       	
        }
        
        nfila = filasImportado;
        ncolumna = columnasImportado;
        
        Celda [][] laberinto = new Celda [nfila][ncolumna];
        
        for(Map.Entry<String,CeldaJson> entry : kk.entrySet()) {
            String key = entry.getKey();          
        	filasImportado = key.charAt(1) - '0';
            columnasImportado = key.charAt(3) - '0';
        	CeldaJson celdaj = new CeldaJson();
        	celdaj = entry.getValue();
			laberinto[filasImportado][columnasImportado].setNeighbors(0, celdaj.getNeighbors()[0]);
			laberinto[filasImportado][columnasImportado].setNeighbors(1, celdaj.getNeighbors()[1]);
			laberinto[filasImportado][columnasImportado].setNeighbors(2, celdaj.getNeighbors()[2]);
			laberinto[filasImportado][columnasImportado].setNeighbors(3, celdaj.getNeighbors()[3]);
			laberinto[filasImportado][columnasImportado].setValue(celdaj.getValue());
			laberinto[filasImportado][columnasImportado].setVisitada(true);
			laberinto[filasImportado][columnasImportado].setPosicion(0, filasImportado);
			laberinto[filasImportado][columnasImportado].setPosicion(1, columnasImportado);
        }            
        
        for(int i=0; i<laberinto.length && semantica; i++) {
			 for(int j=0; j<laberinto[0].length && semantica; j++) {
					semantica=semantica_correcta(laberinto, i, j, nfila, ncolumna);				
			 }
		 }
        
        if(!semantica) {
        	System.out.println("La semántica es incorrecta");
        }             
                       
             return laberinto;
     }
	          
        public static boolean semantica_correcta(Celda [][] laberinto, int fila, int columna, int limite_filas, int limite_columnas) {
    		boolean correcto = true;		   	
    			if(fila != 0) {
    				if(laberinto[fila][columna].getNeighbors()[0] != laberinto[fila-1][columna].getNeighbors()[2])
    					correcto = false;
    				
    			}

    			if(columna != --limite_columnas) {
    				if(laberinto[fila][columna].getNeighbors()[1] != laberinto[fila][columna+1].getNeighbors()[3])
    					correcto = false;
    			}

    			if(fila != --limite_filas) {
    				if(laberinto[fila][columna].getNeighbors()[2] != laberinto[fila+1][columna].getNeighbors()[0])
    					correcto = false;
    			}

    			if(columna != 0) {
    				if(laberinto[fila][columna].getNeighbors()[3] != laberinto[fila][columna-1].getNeighbors()[1])
    					correcto = false;
    			}										
    		return correcto;
    	}
}


