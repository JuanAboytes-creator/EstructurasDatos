import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class burbuja {
    
    public static int[] ordenamientoBurbuja(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Intercambiar elementos
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        System.out.print("Ingrese el tamaño del arreglo: ");
        int tamaño = scanner.nextInt();
        
        // Crear arreglo con valores aleatorios
        int[] arreglo = new int[tamaño];
        for (int i = 0; i < tamaño; i++) {
            arreglo[i] = random.nextInt(100) + 1;
        }
        
        System.out.println("Arreglo original: " + Arrays.toString(arreglo));
        
        // Ordenar el arreglo (creando una copia)
        int[] arregloOrdenado = ordenamientoBurbuja(arreglo.clone());
        
        System.out.println("Arreglo ordenado: " + Arrays.toString(arregloOrdenado));
        
        scanner.close();
    }
}