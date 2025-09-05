import java.util.Arrays;
import java.util.ArrayList;

public class desplaza {
    
    public static ArrayList<Integer> insertarYDesplazar(ArrayList<Integer> arreglo, int indice, int valor) {
        if (indice < 0 || indice >= arreglo.size()) {
            throw new IllegalArgumentException("Índice fuera de rango");
        }
        
        ArrayList<Integer> resultado = new ArrayList<>();
        
        // Agregar elementos antes del índice
        for (int i = 0; i < indice; i++) {
            resultado.add(arreglo.get(i));
        }
        
        // Insertar el nuevo valor
        resultado.add(valor);
        
        // Agregar elementos desde el índice original hasta el penúltimo
        for (int i = indice; i < arreglo.size() - 1; i++) {
            resultado.add(arreglo.get(i));
        }
        
        return resultado;
    }
    
    public static void main(String[] args) {
        ArrayList<Integer> arreglo = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        
        System.out.println("Arreglo original: " + arreglo);
        System.out.println("Inserta el 99 en el indice 5");
        
        try {
            ArrayList<Integer> resultado = insertarYDesplazar(arreglo, 5, 99);
            System.out.println("Resultado: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}