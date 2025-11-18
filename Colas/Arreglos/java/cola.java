import java.util.Scanner;

public class cola {
    static final int MAXSIZE = 5;
    static int[] queue = new int[MAXSIZE];
    static int front = -1, rear = -1;
    static Scanner scanner = new Scanner(System.in);

    static void insertar() {
        if (rear == MAXSIZE - 1) {
            System.out.println("\nOVERFLOW");
            return;
        }

        System.out.print("\nIngrese el elemento: ");
        int elemento = scanner.nextInt();

        if (front == -1 && rear == -1) {
            front = rear = 0;
        } else {
            rear++;
        }
        queue[rear] = elemento;
        System.out.println("\nElemento insertado correctamente.\n");
    }

    static void eliminar() {
        if (front == -1 || front > rear) {
            System.out.println("\nUnderflow");
            return;
        }

        int elemento = queue[front];
        if (front == rear) {
            front = rear = -1;
        } else {
            front++;
        }
        System.out.println("\nElemento eliminado: " + elemento + "\n");
    }

    static void mostrar() {
        if (rear == -1 || front == -1 || front > rear) {
            System.out.println("\nLa cola esta vacia.\n");
        } else {
            System.out.println("\nElementos en la cola:");
            for (int i = front; i <= rear; i++) {
                System.out.println(queue[i]);
            }
        }
    }

    public static void main(String[] args) {
        int opcion = 0;
        while (opcion != 4) {
            System.out.println("\n==========================MENU PRINCIPAL==========================");
            System.out.println("====================================================================");
            System.out.println("1. Insertar\n2. Eliminar\n3. Mostrar\n4. Salir");
            System.out.print("Ingrese su opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: insertar(); break;
                case 2: eliminar(); break;
                case 3: mostrar(); break;
                case 4: System.out.println("\nSaliendo del programa...\n"); break;
                default: System.out.println("\nOpcion invalida. Intente nuevamente\n"); break;
            }
        }
        scanner.close();
    }
}