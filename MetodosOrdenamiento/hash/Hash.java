import java.util.*;

public class Hash{
    public static List<Integer> hashSort(List<Integer> arr) {
        if (arr.isEmpty()) return arr;
        
        int minVal = Collections.min(arr);
        int maxVal = Collections.max(arr);
        
        // Crear tabla hash
        Map<Integer, Integer> hashTable = new HashMap<>();
        
        // Insertar elementos en la tabla hash
        for (int num : arr) {
            hashTable.put(num, hashTable.getOrDefault(num, 0) + 1);
        }
        
        // Reconstruir lista ordenada
        List<Integer> sortedArr = new ArrayList<>();
        for (int num = minVal; num <= maxVal; num++) {
            if (hashTable.containsKey(num)) {
                for (int i = 0; i < hashTable.get(num); i++) {
                    sortedArr.add(num);
                }
            }
        }
        
        return sortedArr;
    }
    
    public static void main(String[] args) {
        // Generar arreglo de 10 elementos aleatorios
        Random random = new Random();
        List<Integer> arr = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            arr.add(random.nextInt(100) + 1);
        }
        
        System.out.println("Array original: " + arr);
        
        // Ordenar con Hash Sort
        List<Integer> sortedArr = hashSort(arr);
        
        System.out.println("Array ordenado: " + sortedArr);
    }
}