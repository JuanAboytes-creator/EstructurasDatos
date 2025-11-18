using System;

class Program
{
    const int MAXSIZE = 5;
    static int[] queue = new int[MAXSIZE];
    static int front = -1, rear = -1;

    static void Insertar()
    {
        if (rear == MAXSIZE - 1)
        {
            Console.WriteLine("\nOVERFLOW");
            return;
        }

        Console.Write("\nIngrese el elemento: ");
        int elemento = Convert.ToInt32(Console.ReadLine());

        if (front == -1 && rear == -1)
        {
            front = rear = 0;
        }
        else
        {
            rear++;
        }
        queue[rear] = elemento;
        Console.WriteLine("\nElemento insertado correctamente.\n");
    }

    static void Eliminar()
    {
        if (front == -1 || front > rear)
        {
            Console.WriteLine("\nUnderflow");
            return;
        }

        int elemento = queue[front];
        if (front == rear)
        {
            front = rear = -1;
        }
        else
        {
            front++;
        }
        Console.WriteLine($"\nElemento eliminado: {elemento}\n");
    }

    static void Mostrar()
    {
        if (rear == -1 || front == -1 || front > rear)
        {
            Console.WriteLine("\nLa cola esta vacia.\n");
        }
        else
        {
            Console.WriteLine("\nElementos en la cola:");
            for (int i = front; i <= rear; i++)
            {
                Console.WriteLine(queue[i]);
            }
        }
    }

    static void Main()
    {
        int opcion = 0;
        while (opcion != 4)
        {
            Console.WriteLine("\n==========================MENU PRINCIPAL==========================");
            Console.WriteLine("====================================================================");
            Console.WriteLine("1. Insertar\n2. Eliminar\n3. Mostrar\n4. Salir");
            Console.Write("Ingrese su opcion: ");
            opcion = Convert.ToInt32(Console.ReadLine());

            switch (opcion)
            {
                case 1: Insertar(); break;
                case 2: Eliminar(); break;
                case 3: Mostrar(); break;
                case 4: Console.WriteLine("\nSaliendo del programa...\n"); break;
                default: Console.WriteLine("\nOpcion invalida. Intente nuevamente\n"); break;
            }
        }
    }
}