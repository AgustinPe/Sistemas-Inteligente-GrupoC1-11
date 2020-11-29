import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Principal {

	public static void main(String[] args) {

		try {

			Scanner teclado = new Scanner(System.in);
			Gson gson = new Gson();
			int eleccion = 0;
			ImportarJsonSucesores sucesores = new ImportarJsonSucesores();
			JsonToObject objeto = importar(gson);
			Celda[][] laberinto = importarACeldas(objeto);
			DrawLab laberintoDibujado;
			Stack<Nodo> solucion = new Stack<Nodo>();
			String nombreJson;

			do {
				System.out.println(
						" 0. Salir \n 1. Crear y Exportar \n 2. Importar y Dibujar \n 3. Anchura \n 4. Profundidad Acotada"
								+ "\n 5. Coste Uniforme \n 6. A* \n 7. Voraz");
				eleccion = teclado.nextInt();
				switch (eleccion) {
				case 0:
					break;

				case 1:
					crearExportar(teclado, gson);
					System.out.println("Ha sido exportado en la ruta indicada");
					objeto = importar(gson);
					importarACeldas(objeto);
					laberintoDibujado = new DrawLab(objeto);
					StdDraw.show(0);
					laberintoDibujado.dibujar();

					break;

				case 2:
					objeto = importar(gson);
					importarACeldas(objeto);
					laberintoDibujado = new DrawLab(objeto);
					StdDraw.show(0);
					laberintoDibujado.dibujar();
					System.out.println("Ha sido importado y dibujado correctamente");
					break;

				case 3:
					sucesores = importarSucesores(gson);
					nombreJson = sucesores.getMAZE();
					objeto = importarMaze(gson, nombreJson);
					Busqueda busquedaCaminoAnchura = new Busqueda(sucesores, objeto, laberinto);
					solucion = busquedaCaminoAnchura.busqueda("BREADTH");
					mostrarCamino(solucion, "BREADTH", objeto);
					break;

				case 4:
					sucesores = importarSucesores(gson);
					nombreJson = sucesores.getMAZE();
					objeto = importarMaze(gson, nombreJson);
					Busqueda busquedaCaminoProfundidad = new Busqueda(sucesores, objeto, laberinto);
					solucion = busquedaCaminoProfundidad.busqueda("DEPTH");
					mostrarCamino(solucion, "DEPTH", objeto);
					break;

				case 5:
					sucesores = importarSucesores(gson);
					nombreJson = sucesores.getMAZE();
					objeto = importarMaze(gson, nombreJson);
					Busqueda busquedaCaminoCostoUniforme = new Busqueda(sucesores, objeto, laberinto);
					solucion = busquedaCaminoCostoUniforme.busqueda("UNIFORM");
					mostrarCamino(solucion, "UNIFORM", objeto);
					break;

				case 6:
					sucesores = importarSucesores(gson);
					nombreJson = sucesores.getMAZE();
					objeto = importarMaze(gson, nombreJson);
					Busqueda busquedaCaminoA = new Busqueda(sucesores, objeto, laberinto);
					solucion = busquedaCaminoA.busqueda("A");
					mostrarCamino(solucion, "A", objeto);
					break;

				case 7:
					sucesores = importarSucesores(gson);
					nombreJson = sucesores.getMAZE();
					objeto = importarMaze(gson, nombreJson);
					Busqueda busquedaCaminoVoraz = new Busqueda(sucesores, objeto, laberinto);
					solucion = busquedaCaminoVoraz.busqueda("GREEDY");
					mostrarCamino(solucion, "GREEDY", objeto);
					break;

				default:
					break;

				}

			} while (eleccion != 0);

		} catch (ExcepcionSemantica e) {
			System.out.println("Semantica incorrecta");
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error al crear el archivo Json");
		}

		catch (Exception e) {
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
				"C:\\Users\\elpel\\Desktop\\FullUniversidad\\PrimerCuatri\\Iso2\\Practicas\\PruebasGit\\Sistemas-Inteligente-GrupoC1-11\\Laberinto\\problema_10x10_maze.json");
		exportarJson1.write(jsonlaberinto);
		exportarJson1.flush();
		exportarJson1.close();

	}

	public static JsonToObject importar(Gson gson) throws IOException {

		String json = "";

		BufferedReader br = new BufferedReader(new FileReader("problema_10x10_maze.json"));

		String linea;
		while ((linea = br.readLine()) != null) {

			json += linea;
		}

		br.close();

		JsonToObject r = gson.fromJson(json, JsonToObject.class);
		return r;
	}

	public static JsonToObject importarMaze(Gson gson, String nombreJson) throws IOException {

		String json = "";

		BufferedReader br = new BufferedReader(new FileReader(nombreJson));

		String linea;
		while ((linea = br.readLine()) != null) {

			json += linea;
		}

		br.close();

		JsonToObject r = gson.fromJson(json, JsonToObject.class);
		return r;
	}

	public static ImportarJsonSucesores importarSucesores(Gson gson) throws IOException {

		String jsonSucesores = "";

		BufferedReader bri = new BufferedReader(new FileReader("problema_10x10.json"));

		String linea;
		while ((linea = bri.readLine()) != null) {

			jsonSucesores += linea;
		}

		bri.close();

		ImportarJsonSucesores iJ = gson.fromJson(jsonSucesores, ImportarJsonSucesores.class);
		return iJ;
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
			laberinto[filasImportado][columnasImportado].setVisitada(false);
			laberinto[filasImportado][columnasImportado].setPosicion(0, filasImportado);
			laberinto[filasImportado][columnasImportado].setPosicion(1, columnasImportado);
		}

		for (int i = 0; i < laberinto.length && semantica; i++) {
			for (int j = 0; j < laberinto[0].length && semantica; j++) {
				semantica = semantica_correcta(laberinto, i, j, nfila, ncolumna);
			}
		}

		if (!semantica) {

			throw new ExcepcionSemantica("La sem�ntica es incorrecta");

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

	public static void mostrarCamino(Stack<Nodo> solucion, String estrategia, JsonToObject r) throws IOException {

		if (!solucion.empty()) {
			FileWriter escribirFichero;
			PrintWriter pwriter;
			int filas = r.getRows();
			int columnas = r.getCols();
			Nodo nodo;
			long id;
			double costo;
			String accion;
			int profundidad;
			double heuristica;
			double value;

			File ficheroSolucion = new File("solution_" + filas + "X" + columnas + "_" + estrategia + ".txt");
			ficheroSolucion.delete();
			escribirFichero = new FileWriter("solution_" + filas + "X" + columnas + "_" + estrategia + ".txt", true);
			pwriter = new PrintWriter(escribirFichero);
			pwriter.print(estrategia + "\n");

			while (!solucion.empty()) {
				nodo = solucion.pop();
				id = nodo.getId();
				accion = nodo.getAccion();
				costo = nodo.getCosto();
				profundidad = nodo.getProfundidad();
				heuristica = nodo.getHeuristica();
				value = nodo.getValor();
				pwriter.print(nodo.toString() + "\n");
			}
			pwriter.close();
		} else {
			System.out.println("No se ha encontrado una soluci�n");
		}
	}

}
