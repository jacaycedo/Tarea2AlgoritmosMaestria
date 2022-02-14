package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Ejecutable {

	public static void main(String[] args) throws Exception {
		System.out.println("Ingrese el caso de prueba que quiere probar: \n\t1 \n\t2");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int seleccion = Integer.parseInt(reader.readLine());
		System.out.println("Cargando Caso " + seleccion);
		
		String contenido = "";
		BufferedReader br = new BufferedReader( new FileReader( new File("./Datos/ejercicio_parejas_"+seleccion+".txt" )));
		contenido = br.readLine();
		Solucion sol;
		int numTrabajadores = Integer.parseInt(contenido.split("\t")[0]);
		int numTrabajos = Integer.parseInt(contenido.split("\t")[1]);
		
		sol = new Solucion(numTrabajadores + numTrabajos + 2);
		
		for(int i = 1 ; i<=numTrabajadores; i++)
		{
			sol.addEdge(0, i, 1);
		}
		for(int i = numTrabajos ; i <= numTrabajadores+numTrabajos; i++)
		{
			sol.addEdge(i, numTrabajadores+numTrabajos+1, 1);
		}
		
		while ((contenido = br.readLine())!= null) {
			String[] valores = contenido.split("\t");
			
			sol.addEdge(Integer.parseInt(valores[0]), 
					Integer.parseInt(valores[1]), 1);
		}
		br.close();
		
		sol.getMaxFlow(0, numTrabajos+numTrabajadores+1);
		FileOutputStream os = new FileOutputStream("./Resultados/resultado_archivo"+seleccion+".txt"); 
		PrintStream ps = new PrintStream(os); 
		ps.println("SOLUCION PARA CASO "+seleccion);

		double [][] conexiones = sol.getflows();
		for (int i = 1; i <= numTrabajadores; i++) {
			for (int j = 0; j < conexiones[0].length; j++) {
				if(conexiones[i][j]>0)
				{
					ps.println("Trabajador " + i + " hace trabajo " + j);
					System.out.println("Trabajador " + i + " hace trabajo " + j);
				}
			}
		}
		ps.close();
		os.close();
	}

}
