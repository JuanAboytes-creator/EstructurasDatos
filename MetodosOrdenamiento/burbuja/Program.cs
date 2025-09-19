using System;
using System.Collections.Generic;

class OrdenadoCS
{
    static List<int> OrdenamientoBurbuja(List<int> arr)
    {
        int n = arr.Count;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n - i - 1; j++)
            {
                if (arr[j] > arr[j + 1])
                {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    static void Main()
    {
        Random random = new Random();
        
        Console.Write("Ingrese el tamaño del arreglo: ");
        int tamaño = int.Parse(Console.ReadLine());
        
        // Crear arreglo con valores aleatorios
        List<int> arreglo = new List<int>();
        for (int i = 0; i < tamaño; i++)
        {
            arreglo.Add(random.Next(1, 101));
        }
        
        // Mostrar arreglo original
        Console.Write("Arreglo original: ");
        Console.WriteLine(string.Join(" ", arreglo));
        
        // Ordenar el arreglo (creando una copia)
        List<int> arregloOrdenado = OrdenamientoBurbuja(new List<int>(arreglo));
        
        // Mostrar arreglo ordenado
        Console.Write("Arreglo ordenado: ");
        Console.WriteLine(string.Join(" ", arregloOrdenado));
    }
}