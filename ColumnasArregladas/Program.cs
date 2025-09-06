using System;

class Columna {
    static void RecorrerPorColumnas(int[,] matriz) {
        // Validación de seguridad
        if (matriz == null || matriz.Length == 0) {
            Console.WriteLine("La matriz está vacía");
            return;
        }
        
        int filas = matriz.GetLength(0);
        int columnas = matriz.GetLength(1);
        
        Console.WriteLine("Recorrido por columnas:");
        for (int j = 0; j < columnas; j++) {      // Primero por columnas
            for (int i = 0; i < filas; i++) {     // Luego por filas
                Console.WriteLine($"Columna {j}, Fila {i}: {matriz[i, j]}");
            }
            Console.WriteLine();  // Separador entre columnas
        }
    }
    
    static void Main() {
        // Matriz rectangular (tamaño fijo)
        int[,] matrizRectangular = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        RecorrerPorColumnas(matrizRectangular);
    }
}