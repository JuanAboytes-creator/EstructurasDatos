using System;
using System.Collections.Generic;
using System.Linq;

class Quick
{
    static List<int> QuickSort(List<int> arr)
    {
        if (arr.Count <= 1)
        {
            return arr;
        }

        int pivot = arr[arr.Count / 2];

        List<int> left = new List<int>();
        List<int> middle = new List<int>();
        List<int> right = new List<int>();

        foreach (int x in arr)
        {
            if (x < pivot)
            {
                left.Add(x);
            }
            else if (x == pivot)
            {
                middle.Add(x);
            }
            else if (x > pivot)
            {
                right.Add(x);
            }
        }

        return QuickSort(left).Concat(middle).Concat(QuickSort(right)).ToList();
    }

    static void Main()
    {
        Random random = new Random();
        Console.Clear();

        List<int> arreglo = new List<int>();
        for (int i = 0; i < 10; i++)
        {
            arreglo.Add(random.Next(1, 101));
        }

        Console.WriteLine("Arreglo original");
        Console.WriteLine(string.Join(" ", arreglo));

        List<int> arreglo_ordenado = QuickSort(arreglo);

        Console.WriteLine("\nArreglo ordenado:");
        Console.WriteLine(string.Join(" ", arreglo_ordenado));
    }
}