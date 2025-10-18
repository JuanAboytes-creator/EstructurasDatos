using System;

class Program {
    static void ShellSort(int[] arr) {
        int n = arr.Length;
        
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
    
    static void Main() {
        Random rand = new Random();
        int[] arr = new int[10];
        
        // Generar arreglo de 10 elementos con números aleatorios del 1 al 100
        for (int i = 0; i < 10; i++) {
            arr[i] = rand.Next(1, 101);
        }
        
        Console.Write("Arreglo original: ");
        Console.WriteLine(string.Join(" ", arr));
        
        ShellSort(arr);
        
        Console.Write("Arreglo ordenado: ");
        Console.WriteLine(string.Join(" ", arr));
    }
}