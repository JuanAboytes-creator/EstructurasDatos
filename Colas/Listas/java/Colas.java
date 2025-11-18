import java.util.Scanner;

class Nodo {
    int dato;
    Nodo siguiente;
    Nodo anterior;
    
    public Nodo(int valor) {
        dato = valor;
        siguiente = null;
        anterior = null;
    }
}

class Cola {
    private Nodo frente;
    private Nodo fin;
    private int tamaño;
    private int capacidad;
    
    public Cola(int cap) {
        frente = null;
        fin = null;
        tamaño = 0;
        capacidad = cap;
    }
    
    public void insertar(int elemento) {
        if (tamaño >= capacidad) {
            System.out.println("\nOVERFLOW");
            return;
        }
        
        Nodo nuevo = new Nodo(elemento);
        if (estaVacia()) {
            frente = fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            nuevo.anterior = fin;
            fin = nuevo;
        }
        tamaño++;
        System.out.println("\nElemento insertado correctamente.\n");
    }
    
    public void eliminar() {
        if (estaVacia()) {
            System.out.println("\nUnderflow");
            return;
        }
        
        int elemento = frente.dato;
        if (frente == fin) {
            frente = fin = null;
        } else {
            frente = frente.siguiente;
            frente.anterior = null;
        }
        tamaño--;
        System.out.println("\nElemento eliminado: " + elemento + "\n");
    }
    
    public void mostrar() {
        if (estaVacia()) {
            System.out.println("\nLa cola esta vacia.\n");
        } else {
            System.out.println("\nElementos en la cola:");
            Nodo actual = frente;
            while (actual != null) {
                System.out.println(actual.dato);
                actual = actual.siguiente;
            }
        }
    }
    
    public boolean estaVacia() {
        return frente == null;
    }
}

public class Colas {
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        Cola cola = new Cola(5);
        int opcion = 0;
        
        while (opcion != 4) {
            System.out.println("\n==========================MENU PRINCIPAL==========================");
            System.out.println("====================================================================");
            System.out.println("1. Insertar\n2. Eliminar\n3. Mostrar\n4. Salir");
            System.out.print("Ingrese su opcion: ");
            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    System.out.print("\nIngrese el elemento: ");
                    int elemento = scanner.nextInt();
                    cola.insertar(elemento);
                    break;
                case 2:
                    cola.eliminar();
                    break;
                case 3:
                    cola.mostrar();
                    break;
                case 4:
                    System.out.println("\nSaliendo del programa...\n");
                    break;
                default:
                    System.out.println("\nOpcion invalida. Intente nuevamente\n");
                    break;
            }
        }
        scanner.close();
    }
}