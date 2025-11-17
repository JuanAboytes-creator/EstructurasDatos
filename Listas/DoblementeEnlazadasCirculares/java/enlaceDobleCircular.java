import java.util.Scanner;

class Node {
    int data;
    Node prev;
    Node next;
}

public class enlaceDobleCircular {
    private Node head;
    private Scanner scanner = new Scanner(System.in);

    public void beginInsert() {
        System.out.print("\nIngrese valor: ");
        int item = scanner.nextInt();

        Node ptr = new Node();
        ptr.data = item;

        if (head == null) {
            head = ptr;
            ptr.next = ptr;
            ptr.prev = ptr;
        } else {
            Node last = head.prev;
            
            ptr.next = head;
            ptr.prev = last;
            
            head.prev = ptr;
            last.next = ptr;
            
            head = ptr;
        }
        System.out.println("\nNodo insertado");
    }

    public void lastInsert() {
        System.out.print("\nIngrese valor: ");
        int item = scanner.nextInt();

        Node ptr = new Node();
        ptr.data = item;

        if (head == null) {
            ptr.prev = ptr;
            ptr.next = ptr;
            head = ptr;
        } else {
            Node last = head.prev;
            
            ptr.next = head;
            ptr.prev = last;
            
            last.next = ptr;
            head.prev = ptr;
        }
        System.out.println("\nNodo insertado");
    }

    public void randomInsert() {
        System.out.print("\nIntroduzca el valor del elemento: ");
        int item = scanner.nextInt();
        System.out.print("\nIntroduce la ubicacion despues de la cual deseas insertar: ");
        int loc = scanner.nextInt();

        Node ptr = new Node();
        ptr.data = item;

        if (head == null) {
            System.out.println("\nLa lista esta vacia, insertando al inicio");
            ptr.prev = ptr;
            ptr.next = ptr;
            head = ptr;
            return;
        }

        Node temp = head;
        for (int i = 0; i < loc; i++) {
            temp = temp.next;
            if (temp == head) {
                System.out.println("\nNo se puede insertar\n");
                return;
            }
        }

        ptr.prev = temp;
        ptr.next = temp.next;
        temp.next.prev = ptr;
        temp.next = ptr;
        
        System.out.println("\nNodo insertado");
    }

    public void beginDelete() {
        if (head == null) {
            System.out.println("\nLa lista esta vacia\n");
        } else if (head.next == head) {
            head = null;
            System.out.println("\nNodo eliminado desde el principio\n");
        } else {
            Node last = head.prev;
            Node newHead = head.next;
            
            last.next = newHead;
            newHead.prev = last;
            
            head = newHead;
            System.out.println("\nNodo eliminado desde el principio\n");
        }
    }

    public void lastDelete() {
        if (head == null) {
            System.out.println("\nLa lista esta vacia");
        } else if (head.next == head) {
            head = null;
            System.out.println("\nSolo se elimino el unico nodo de la lista\n");
        } else {
            Node last = head.prev;
            Node newLast = last.prev;
            
            newLast.next = head;
            head.prev = newLast;
            
            System.out.println("\nUltimo nodo eliminado\n");
        }
    }

    public void randomDelete() {
        System.out.print("\nIntroduzca la ubicacion del nodo a eliminar: ");
        int loc = scanner.nextInt();

        if (head == null) {
            System.out.println("\nLa lista esta vacia");
            return;
        }

        if (loc == 0) {
            beginDelete();
            return;
        }

        Node ptr = head;
        for (int i = 0; i < loc; i++) {
            ptr = ptr.next;
            if (ptr == head) {
                System.out.println("\nError: fuera de rango");
                return;
            }
        }

        ptr.prev.next = ptr.next;
        ptr.next.prev = ptr.prev;
        
        System.out.println("\nNodo eliminado\n");
    }

    public void search() {
        if (head == null) {
            System.out.println("\nLista vacia\n");
        } else {
            System.out.print("\nIntroduce el elemento que deseas buscar: ");
            int item = scanner.nextInt();
            Node ptr = head;
            int i = 0;
            boolean flag = false;

            do {
                if (ptr.data == item) {
                    System.out.println("Elemento encontrado en la ubicacion " + i);
                    flag = true;
                }
                i++;
                ptr = ptr.next;
            } while (ptr != head);

            if (!flag) {
                System.out.println("\nElemento no encontrado\n");
            }
        }
    }

    public void display() {
        Node ptr = head;

        if (ptr == null) {
            System.out.println("Nada que imprimir");
        } else {
            System.out.println("\nImprimiendo valores.......\n");
            do {
                System.out.println(ptr.data);
                ptr = ptr.next;
            } while (ptr != head);
        }
    }

    public static void main(String[] args) {
        enlaceDobleCircular list = new enlaceDobleCircular();
        int choice = 0;
        
        while (choice != 9) {
            System.out.println("\n\n=======================MENU PRINCIPAL=======================\n");
            System.out.println("\nElige una opcion de la siguiente lista ...\n");
            System.out.println("\n==============================================================\n");
            System.out.println("\n1. Insertar al inicio\n2. Insertar al final\n3. Insertar\n4. Eliminar el primero\n5. Eliminar el ultimo\n6. Eliminar nodo\n7. Buscar\n8. Mostrar\n9. Salir");
            System.out.print("\nIngrese su opcion: ");
            choice = list.scanner.nextInt();
            
            switch (choice) {
                case 1: list.beginInsert(); break;
                case 2: list.lastInsert(); break;
                case 3: list.randomInsert(); break;
                case 4: list.beginDelete(); break;
                case 5: list.lastDelete(); break;
                case 6: list.randomDelete(); break;
                case 7: list.search(); break;
                case 8: list.display(); break;
                case 9: System.exit(0); break;
                default: System.out.println("Introdusca una opcion valida...."); break;
            }
        }
    }
}
