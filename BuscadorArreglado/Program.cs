using System;

class Busqueda
{
    static int BusquedaLineal(int[] arr, int objetivo)
    {
        for (int i = 0; i < arr.Length; i++)
        {
            if (arr[i] == objetivo)
            {
                return i;
            }
        }
        return -1;
    }

    static void Main()
    {
        int[] arreglo = { 5, 2, 8, 1, 9, 3 };
        int objetivo = 8;
        
        int resultado = BusquedaLineal(arreglo, objetivo);
        
        if (resultado != -1)
        {
            Console.WriteLine($"Elemento {objetivo} encontrado en el índice {resultado}");
        }
        else
        {
            Console.WriteLine($"Elemento {objetivo} no encontrado");
        }
    }
}
