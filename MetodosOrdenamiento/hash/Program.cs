using System;
using System.Collections.Generic;
using System.Linq;

class Program
{
    static List<int> HashSort(List<int> arr)
    {
        if (arr.Count == 0) return arr;
        
        int minVal = arr.Min();
        int maxVal = arr.Max();
        
        // Crear tabla hash
        Dictionary<int, int> hashTable = new Dictionary<int, int>();
        
        // Insertar elementos en la tabla hash
        foreach (int num in arr)
        {
            if (hashTable.ContainsKey(num))
            {
                hashTable[num]++;
            }
            else
            {
                hashTable[num] = 1;
            }
        }
        
        // Reconstruir lista ordenada
        List<int> sortedArr = new List<int>();
        for (int num = minVal; num <= maxVal; num++)
        {
            if (hashTable.ContainsKey(num))
            {
                sortedArr.AddRange(Enumerable.Repeat(num, hashTable[num]));
            }
        }
        
        return sortedArr;
    }
    
    static void Main()
    {
        // Generar arreglo de 10 elementos aleatorios
        Random random = new Random();
        List<int> arr = new List<int>();
        
        for (int i = 0; i < 10; i++)
        {
            arr.Add(random.Next(1, 101));
        }
        
        Console.WriteLine("Array original: " + string.Join(" ", arr));
        
        // Ordenar con Hash Sort
        List<int> sortedArr = HashSort(arr);
        
        Console.WriteLine("Array ordenado: " + string.Join(" ", sortedArr));
    }
}