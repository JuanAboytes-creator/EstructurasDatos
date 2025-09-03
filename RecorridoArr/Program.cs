using System;

class Recorrido
{
    static void Main()
    {
        // Arreglo de 5 números enteros
        int[] numeros = {10, 25, 37, 42, 58};
        
        // Recorrer el arreglo e imprimir cada valor
        Console.WriteLine("Recorrido del arreglo:");
        foreach(int numero in numeros)
        {
            Console.WriteLine(numero);
        }
    }
}