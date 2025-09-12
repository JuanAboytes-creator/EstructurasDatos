using System;

class Program
{
    static void Main()
    {
        int[] tablero = new int[9];
        Console.Write("Nomble del jugador 1(O): ");
        string jugador1 = Console.ReadLine();
        Console.Write("Nomble del jugador 2(X): ");
        string jugador2 = Console.ReadLine();
        int jugadorActual = 1;
        bool juegoTerminado = false;

        Console.WriteLine("=== JUEGO DEL GATO ===");

        while (!juegoTerminado)
        {
            MostrarTablero(tablero);
            
            Console.Write($"Turno de {(jugadorActual == 1 ? jugador1 : jugador2)}: ");
            string input = Console.ReadLine();
            
            if (int.TryParse(input, out int posicion) && posicion >= 1 && posicion <= 9)
            {
                if (tablero[posicion - 1] == 0)
                {
                    tablero[posicion - 1] = jugadorActual;
                    
                    if (VerificarGanador(tablero, jugadorActual))
                    {
                        MostrarTablero(tablero);
                        Console.WriteLine($"¡{(jugadorActual == 1 ? jugador1 : jugador2)} GANA!");
                        juegoTerminado = true;
                    }
                    else if (TableroLleno(tablero))
                    {
                        MostrarTablero(tablero);
                        Console.WriteLine("¡EMPATE!");
                        juegoTerminado = true;
                    }
                    else
                    {
                        jugadorActual = jugadorActual == 1 ? 2 : 1;
                    }
                }
                else
                {
                    Console.WriteLine("Casilla ocupada. Intenta otra.");
                }
            }
            else
            {
                Console.WriteLine("Entrada inválida. Usa números 1-9.");
            }
        }
    }

    static void MostrarTablero(int[] tablero)
    {
        Console.WriteLine("\nTablero:");
        for (int i = 0; i < 9; i++)
        {
            char simbolo = tablero[i] switch
            {
                1 => 'O',
                2 => 'X',
                _ => (char)('1' + i) // Muestra el número de posición
            };
            
            Console.Write($"[{simbolo}]");
            if ((i + 1) % 3 == 0) Console.WriteLine();
        }
    }

    static bool VerificarGanador(int[] tablero, int jugador)
    {
        // Líneas horizontales
        if (tablero[0] == jugador && tablero[1] == jugador && tablero[2] == jugador) return true;
        if (tablero[3] == jugador && tablero[4] == jugador && tablero[5] == jugador) return true;
        if (tablero[6] == jugador && tablero[7] == jugador && tablero[8] == jugador) return true;
        
        // Líneas verticales
        if (tablero[0] == jugador && tablero[3] == jugador && tablero[6] == jugador) return true;
        if (tablero[1] == jugador && tablero[4] == jugador && tablero[7] == jugador) return true;
        if (tablero[2] == jugador && tablero[5] == jugador && tablero[8] == jugador) return true;
        
        // Diagonales
        if (tablero[0] == jugador && tablero[4] == jugador && tablero[8] == jugador) return true;
        if (tablero[2] == jugador && tablero[4] == jugador && tablero[6] == jugador) return true;
        
        return false;
    }

    static bool TableroLleno(int[] tablero)
    {
        foreach (int casilla in tablero)
        {
            if (casilla == 0) return false;
        }
        return true;
    }
}