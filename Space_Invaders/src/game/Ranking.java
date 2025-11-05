package game;

//empieza mi codigo -fati 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ranking {
    private Map<String, Integer> puntajes = new HashMap<>();
    private final String ARCHIVO = "ranking.txt";

    public Ranking() {
        cargarDesdeArchivo();
    }

    public void guardarPuntajeBest(String nombre, int puntos) {
        if (puntajes.containsKey(nombre)) {
            if (puntos > puntajes.get(nombre)) {
                puntajes.put(nombre, puntos);
            }
        } else {
            puntajes.put(nombre, puntos);
        }
        guardarEnArchivo();
    }

    public List<String> topN(int n) {
        return puntajes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(n)
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.toList());
    }

    private void cargarDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 2) {
                    puntajes.put(partes[0], Integer.parseInt(partes[1]));
                }
            }
        } catch (IOException e) {
            // Si el archivo no existe, empieza vacío
        }
    }

    private void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Map.Entry<String, Integer> entry : puntajes.entrySet()) {
                pw.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//termino mi codigo -fati