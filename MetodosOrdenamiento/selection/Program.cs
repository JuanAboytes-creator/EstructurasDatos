using System;
using System.Collections.Generic;

class SelectionMet
{
    static void SelectionSort(List<int> arr)
    {
        int n = arr.Count;
        
        for (int i = 0; i < n; i++)
        {
            int min_idx = i;
            for (int j = i + 1; j < n; j++)
            {
                if (arr[j] < arr[min_idx])
                {
                    min_idx = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[min_idx];
            arr[min_idx] = temp;
        }
    }
    
    static void Main()
    {
        Random random = new Random();
        int tamaño;
        
        while (true)
        {
            Console.Write("Ingrese el tamaño del arreglo: ");
            string input = Console.ReadLine();
            
            if (int.TryParse(input, out tamaño) && tamaño > 0)
            {
                break;
            }
            else
            {
                Console.WriteLine("Por favor, ingrese un número positivo mayor que 0.");
            }
        }
        
        List<int> arreglo = new List<int>();
        for (int i = 0; i < tamaño; i++)
        {
            arreglo.Add(random.Next(1, 101));
        }
        
        Console.WriteLine("\nArreglo original: " + string.Join(" ", arreglo));
        
        List<int> arreglo_ordenado = new List<int>(arreglo);
        SelectionSort(arreglo_ordenado);
        
        Console.WriteLine("Arreglo ordenado: " + string.Join(" ", arreglo_ordenado));
    }
}