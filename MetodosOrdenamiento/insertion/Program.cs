using System;
using System.Collections.Generic;

class InsertionMet
{
    static List<int> InsertionSort(List<int> arr)
    {
        // Recorremos desde el segundo elemento hasta el final
        for (int i = 1; i < arr.Count; i++)
        {
            int key = arr[i];  // Elemento actual a insertar
            int j = i - 1;
            
            // Movemos los elementos mayores que key una posición adelante
            while (j >= 0 && arr[j] > key)
            {
                arr[j + 1] = arr[j];
                j--;
            }
            
            // Insertamos key en la posición correcta
            arr[j + 1] = key;
        }
        
        return arr;
    }
    
    static void Main()
    {
        Random random = new Random();
        
        int tamaño;
        while (true)
        {
            Console.Write("Ingrese el tamaño del arreglo: ");
            string input = Console.ReadLine();
            
            if (!int.TryParse(input, out tamaño) || tamaño <= 0)
            {
                Console.WriteLine("Por favor, ingrese un número positivo mayor que 0.");
                continue;
            }
            break;
        }
        
        List<int> arreglo = new List<int>();
        for (int i = 0; i < tamaño; i++)
        {
            arreglo.Add(random.Next(1, 101));  // Números entre 1 y 100
        }
        
        Console.Write("\nArreglo original: ");
        Console.WriteLine(string.Join(" ", arreglo));
        
        List<int> arregloOrdenado = InsertionSort(new List<int>(arreglo));
        
        Console.Write("Arreglo ordenado: ");
        Console.WriteLine(string.Join(" ", arregloOrdenado));
    }
}