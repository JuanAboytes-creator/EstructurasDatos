using System;

class Nodo {
    public int Dato { get; set; }
    public Nodo Siguiente { get; set; }
    public Nodo Anterior { get; set; }
    
    public Nodo(int valor) {
        Dato = valor;
        Siguiente = null;
        Anterior = null;
    }
}

class Cola {
    private Nodo frente;
    private Nodo final;
    private int tamaño;
    private int capacidad;
    
    public Cola(int cap) {
        frente = null;
        final = null;
        tamaño = 0;
        capacidad = cap;
    }
    
    public void Insertar(int elemento) {
        if (tamaño >= capacidad) {
            Console.WriteLine("\nOVERFLOW");
            return;
        }
        
        Nodo nuevo = new Nodo(elemento);
        if (EstaVacia()) {
            frente = final = nuevo;
        } else {
            final.Siguiente = nuevo;
            nuevo.Anterior = final;
            final = nuevo;
        }
        tamaño++;
        Console.WriteLine("\nElemento insertado correctamente.\n");
    }
    
    public void Eliminar() {
        if (EstaVacia()) {
            Console.WriteLine("\nUnderflow");
            return;
        }
        
        int elemento = frente.Dato;
        Nodo temp = frente;
        
        if (frente == final) {
            frente = final = null;
        } else {
            frente = frente.Siguiente;
            frente.Anterior = null;
        }
        
        tamaño--;
        Console.WriteLine($"\nElemento eliminado: {elemento}\n");
    }
    
    public void Mostrar() {
        if (EstaVacia()) {
            Console.WriteLine("\nLa cola esta vacia.\n");
        } else {
            Console.WriteLine("\nElementos en la cola:");
            Nodo actual = frente;
            while (actual != null) {
                Console.WriteLine(actual.Dato);
                actual = actual.Siguiente;
            }
        }
    }
    
    public bool EstaVacia() {
        return frente == null;
    }
}

class Program {
    static void Main() {
        Cola cola = new Cola(5);
        int opcion = 0;
        
        while (opcion != 4) {
            Console.WriteLine("\n==========================MENU PRINCIPAL==========================");
            Console.WriteLine("====================================================================");
            Console.WriteLine("1. Insertar\n2. Eliminar\n3. Mostrar\n4. Salir");
            Console.Write("Ingrese su opcion: ");
            opcion = Convert.ToInt32(Console.ReadLine());
            
            switch (opcion) {
                case 1:
                    Console.Write("\nIngrese el elemento: ");
                    int elemento = Convert.ToInt32(Console.ReadLine());
                    cola.Insertar(elemento);
                    break;
                case 2:
                    cola.Eliminar();
                    break;
                case 3:
                    cola.Mostrar();
                    break;
                case 4:
                    Console.WriteLine("\nSaliendo del programa...\n");
                    break;
                default:
                    Console.WriteLine("\nOpcion invalida. Intente nuevamente\n");
                    break;
            }
        }
    }
}