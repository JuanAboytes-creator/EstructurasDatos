
import java.util.Random;

public class Shell {
    public static void shellSort(int[] arr) {
        int n = arr.length;
        
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
    }
    
    public static void main(String[] args) {
        Random rand = new Random();
        int[] arr = new int[10];
        
        // Generar arreglo de 10 elementos con números aleatorios del 1 al 100
        for (int i = 0; i < 10; i++) {
            arr[i] = rand.nextInt(100) + 1;
        }
        
        System.out.print("Arreglo original: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        shellSort(arr);
        
        System.out.print("Arreglo ordenado: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
