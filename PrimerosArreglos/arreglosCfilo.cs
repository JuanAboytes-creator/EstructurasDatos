using System;

class Program
{
    static void Main()
    {
        // Versión simple de arreglos en C#
        int[] arreglo = {1, 2, 3, 4, 5};
        
        Console.Write("Arreglo inicial: ");
        Console.WriteLine(string.Join(" ", arreglo));
        
        Console.WriteLine($"Remplazando el numero {arreglo[1]} por 99");
        arreglo[1] = 99;
        
        Console.Write("Arreglo después del reemplazo: ");
        Console.WriteLine(string.Join(" ", arreglo));
    }
}