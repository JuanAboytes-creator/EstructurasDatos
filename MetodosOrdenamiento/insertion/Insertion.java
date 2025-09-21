import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class Insertion {
    
    public static int[] insertionSort(int[] arr) {
        // Recorremos desde el segundo elemento hasta el final
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];  // Elemento actual a insertar
            int j = i - 1;
            
            // Movemos los elementos mayores que key una posición adelante
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            
            // Insertamos key en la posición correcta
            arr[j + 1] = key;
        }
        
        return arr;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        int tamaño = 0;
        while (true) {
            try {
                System.out.print("Ingrese el tamaño del arreglo: ");
                tamaño = scanner.nextInt();
                
                if (tamaño <= 0) {
                    System.out.println("Por favor, ingrese un número positivo mayor que 0.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next(); // Limpiar buffer
            }
        }
        
        int[] arreglo = new int[tamaño];
        for (int i = 0; i < tamaño; i++) {
            arreglo[i] = random.nextInt(100) + 1;  // Números entre 1 y 100
        }
        
        System.out.println("\nArreglo original: " + Arrays.toString(arreglo));
        
        int[] arregloOrdenado = insertionSort(arreglo.clone());
        
        System.out.println("Arreglo ordenado: " + Arrays.toString(arregloOrdenado));
        
        scanner.close();
    }
}