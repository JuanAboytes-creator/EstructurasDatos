import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class selection {
    
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[min_idx]) {
                    min_idx = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[min_idx];
            arr[min_idx] = temp;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int tamaño = 0;
        
        while (true) {
            System.out.print("Ingrese el tamaño del arreglo: ");
            try {
                tamaño = Integer.parseInt(scanner.nextLine());
                if (tamaño > 0) {
                    break;
                } else {
                    System.out.println("Por favor, ingrese un número positivo mayor que 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
        
        int[] arreglo = new int[tamaño];
        for (int i = 0; i < tamaño; i++) {
            arreglo[i] = random.nextInt(100) + 1;
        }
        
        System.out.println("\nArreglo original: " + Arrays.toString(arreglo));
        
        int[] arreglo_ordenado = arreglo.clone();
        selectionSort(arreglo_ordenado);
        
        System.out.println("Arreglo ordenado: " + Arrays.toString(arreglo_ordenado));
        
        scanner.close();
    }
}