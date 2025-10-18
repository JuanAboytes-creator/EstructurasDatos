using System;
using System.Collections.Generic;
using System.Linq;

class Program
{
    static void BucketSort(List<int> arr)
    {
        if (arr.Count == 0) return;
        
        // Encontrar valor máximo y mínimo
        int minVal = arr.Min();
        int maxVal = arr.Max();
        
        // Crear buckets
        int bucketCount = arr.Count;
        List<List<int>> buckets = new List<List<int>>();
        for (int i = 0; i < bucketCount; i++)
        {
            buckets.Add(new List<int>());
        }
        
        // Distribuir elementos en los buckets
        foreach (int num in arr)
        {
            int index = (num - minVal) * bucketCount / (maxVal - minVal + 1);
            buckets[index].Add(num);
        }
        
        // Ordenar cada bucket e insertar en el array original
        arr.Clear();
        foreach (var bucket in buckets)
        {
            bucket.Sort();
            arr.AddRange(bucket);
        }
    }
    
    static void Main()
    {
        // Generar arreglo de 10 elementos con números aleatorios del 1 al 100
        List<int> arr = new List<int>();
        Random random = new Random();
        
        for (int i = 0; i < 10; i++)
        {
            arr.Add(random.Next(1, 101));
        }
        
        Console.WriteLine("Arreglo original: " + string.Join(" ", arr));
        
        // Ordenar con bucket sort
        List<int> arrOrdenado = new List<int>(arr);
        BucketSort(arrOrdenado);
        
        Console.WriteLine("Arreglo ordenado: " + string.Join(" ", arrOrdenado));
    }
}