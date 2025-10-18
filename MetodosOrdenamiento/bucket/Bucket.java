import java.util.*;

public class Bucket {
    public static void bucketSort(List<Integer> arr) {
        if (arr.isEmpty()) return;
        
        // Encontrar valor máximo y mínimo
        int minVal = Collections.min(arr);
        int maxVal = Collections.max(arr);
        
        // Crear buckets
        int bucketCount = arr.size();
        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
        
        // Distribuir elementos en los buckets
        for (int num : arr) {
            int index = (num - minVal) * bucketCount / (maxVal - minVal + 1);
            buckets.get(index).add(num);
        }
        
        // Ordenar cada bucket e insertar en el array original
        arr.clear();
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
            arr.addAll(bucket);
        }
    }
    
    public static void main(String[] args) {
        // Generar arreglo de 10 elementos con números aleatorios del 1 al 100
        List<Integer> arr = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < 10; i++) {
            arr.add(random.nextInt(100) + 1);
        }
        
        System.out.print("Arreglo original: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // Ordenar con bucket sort
        List<Integer> arrOrdenado = new ArrayList<>(arr);
        bucketSort(arrOrdenado);
        
        System.out.print("Arreglo ordenado: ");
        for (int num : arrOrdenado) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}