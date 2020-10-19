import java.io.*;
import java.util.*;
import com.google.gson.Gson;

public class Principal {

	public static void main(String[] args) {
  
		try {

			Scanner teclado = new Scanner(System.in);
			Gson gson = new Gson();
			int eleccion = 0;


			do {
				System.out.println(" 0. Salir \n 1. Crear y Exportar \n 2. Importar y Dibujar");
				eleccion = teclado.nextInt();
				
				switch (eleccion) {
				case 0:
					break;
				
				case 1:
					crearExportar(teclado, gson);
					System.out.println("Ha sido exportado en la ruta indicada");
					JsonToObject objetocreado = new JsonToObject();
					objetocreado = importar(gson);
					importarACeldas(objetocreado);
					DrawLab laberintocreado = new DrawLab(objetocreado);
					StdDraw.show(0);
					laberintocreado.dibujar();
					
					break;

				case 2:
					JsonToObject objeto = new JsonToObject();
					objeto = importar(gson);
					importarACeldas(objeto);
					DrawLab laberinto1 = new DrawLab(objeto);
					StdDraw.show(0);
					laberinto1.dibujar();
					System.out.println("Ha sido importado y dibujado correctamente");
					
					break;

				default:
					break;

				}

			} while (eleccion!=0);
			
		} catch (ExcepcionSemantica e) {
			System.out.println("Semantica incorrecta");
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error al crear el archivo Json");
		} catch (Exception e) {
			System.out.println("Ocurrio un error inesperado" + e);
		}
	}

	public static void crearExportar(Scanner teclado, Gson gson) throws IOException {

		System.out.println("Introducir numero de filas");
		int filas = teclado.nextInt();
		System.out.println("Introducir numero de columnas");
		int columnas = teclado.nextInt();

		Laberinto laberinto = new Laberinto(filas, columnas);
		while (!laberinto.laberinto_completado()) {
			laberinto.generar_camino();
		}

		ExportarJson exportarJson = new ExportarJson(laberinto.getLaberinto());
		exportarJson.rellenarLista(laberinto.getLaberinto());
		String jsonlaberinto = gson.toJson(exportarJson);

		FileWriter exportarJson1 = new FileWriter(
				"D:\\agustin\\Documentos\\Ingenieria Informatica\\CIUDAD REAL\\Reporsitorio GIT\\Sistemas-Inteligente-GrupoC1-11\\Laberinto\\laberinto.json");
		exportarJson1.write(jsonlaberinto);
		exportarJson1.flush();
		exportarJson1.close();

	}

	public static JsonToObject importar(Gson gson) throws IOException {

		String json = "";

		BufferedReader br = new BufferedReader(new FileReader("laberinto.json"));

		String linea;
		while ((linea = br.readLine()) != null) {

			json += linea;
		}

		br.close();

		JsonToObject r = gson.fromJson(json, JsonToObject.class);
		return r;
	}

	public static Celda[][] importarACeldas(JsonToObject r) throws ExcepcionSemantica {

		TreeMap<String, CeldaJson> kk = r.getCells();

		int filasImportado;
		int columnasImportado;

		int nfila = r.getRows();
		int ncolumna = r.getCols();
		boolean semantica = true;

		Celda[][] laberinto = new Celda[nfila][ncolumna];
		for (int i = 0; i < nfila; i++) {
			for (int j = 0; j < ncolumna; j++) {
				Celda celda = new Celda();
				celda.setNeighbors(0, false);
				celda.setNeighbors(1, false);
				celda.setNeighbors(2, false);
				celda.setNeighbors(3, false);
				celda.setValue(0);
				celda.setVisitada(false);
				celda.setPosicion(0, i);
				celda.setPosicion(1, j);
				laberinto[i][j] = celda;
			}
		}

		for (Map.Entry<String, CeldaJson> entry : kk.entrySet()) {
			DrawLab cor = new DrawLab(r);
			String key = entry.getKey();
			cor.coordenadas(key);
			filasImportado = cor.getCoordenadaFila();
			columnasImportado = cor.getCoordenadaColumna();
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

		for (int i = 0; i < laberinto.length && semantica; i++) {
			for (int j = 0; j < laberinto[0].length && semantica; j++) {
				semantica = semantica_correcta(laberinto, i, j, nfila, ncolumna);
			}
		}

		if (!semantica) {
			
			throw new ExcepcionSemantica("La semántica es incorrecta");	
			
		}

		return laberinto;
	}

	public static boolean semantica_correcta(Celda[][] laberinto, int fila, int columna, int limite_filas,
			int limite_columnas) {
		boolean correcto = true;
		if (fila != 0) {
			if (laberinto[fila][columna].getNeighbors()[0] != laberinto[fila - 1][columna].getNeighbors()[2])
				correcto = false;

		}

		if (columna != --limite_columnas) {
			if (laberinto[fila][columna].getNeighbors()[1] != laberinto[fila][columna + 1].getNeighbors()[3])
				correcto = false;
		}

		if (fila != --limite_filas) {
			if (laberinto[fila][columna].getNeighbors()[2] != laberinto[fila + 1][columna].getNeighbors()[0])
				correcto = false;
		}

		if (columna != 0) {
			if (laberinto[fila][columna].getNeighbors()[3] != laberinto[fila][columna - 1].getNeighbors()[1])
				correcto = false;
		}
		return correcto;
	}
}

