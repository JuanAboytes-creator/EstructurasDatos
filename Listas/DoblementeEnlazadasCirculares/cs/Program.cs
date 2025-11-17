using System;

class Node
{
    public int data;
    public Node prev;
    public Node next;
}

class CircularDoublyLinkedList
{
    private Node head;

    public void BeginInsert()
    {
        Console.Write("\nIngrese valor: ");
        int item = int.Parse(Console.ReadLine());

        Node ptr = new Node();
        ptr.data = item;

        if (head == null)
        {
            head = ptr;
            ptr.next = ptr;
            ptr.prev = ptr;
        }
        else
        {
            Node last = head.prev;
            
            ptr.next = head;
            ptr.prev = last;
            
            head.prev = ptr;
            last.next = ptr;
            
            head = ptr;
        }
        Console.WriteLine("\nNodo insertado");
    }

    public void LastInsert()
    {
        Console.Write("\nIngrese valor: ");
        int item = int.Parse(Console.ReadLine());

        Node ptr = new Node();
        ptr.data = item;

        if (head == null)
        {
            ptr.prev = ptr;
            ptr.next = ptr;
            head = ptr;
        }
        else
        {
            Node last = head.prev;
            
            ptr.next = head;
            ptr.prev = last;
            
            last.next = ptr;
            head.prev = ptr;
        }
        Console.WriteLine("\nNodo insertado");
    }

    public void RandomInsert()
    {
        Console.Write("\nIntroduzca el valor del elemento: ");
        int item = int.Parse(Console.ReadLine());
        Console.Write("\nIntroduce la ubicacion despues de la cual deseas insertar: ");
        int loc = int.Parse(Console.ReadLine());

        Node ptr = new Node();
        ptr.data = item;

        if (head == null)
        {
            Console.WriteLine("\nLa lista esta vacia, insertando al inicio");
            ptr.prev = ptr;
            ptr.next = ptr;
            head = ptr;
            return;
        }

        Node temp = head;
        for (int i = 0; i < loc; i++)
        {
            temp = temp.next;
            if (temp == head)
            {
                Console.WriteLine("\nNo se puede insertar\n");
                return;
            }
        }

        ptr.prev = temp;
        ptr.next = temp.next;
        temp.next.prev = ptr;
        temp.next = ptr;
        
        Console.WriteLine("\nNodo insertado");
    }

    public void BeginDelete()
    {
        if (head == null)
        {
            Console.WriteLine("\nLa lista esta vacia\n");
        }
        else if (head.next == head)
        {
            head = null;
            Console.WriteLine("\nNodo eliminado desde el principio\n");
        }
        else
        {
            Node last = head.prev;
            Node newHead = head.next;
            
            last.next = newHead;
            newHead.prev = last;
            
            head = newHead;
            Console.WriteLine("\nNodo eliminado desde el principio\n");
        }
    }

    public void LastDelete()
    {
        if (head == null)
        {
            Console.WriteLine("\nLa lista esta vacia");
        }
        else if (head.next == head)
        {
            head = null;
            Console.WriteLine("\nSolo se elimino el unico nodo de la lista\n");
        }
        else
        {
            Node last = head.prev;
            Node newLast = last.prev;
            
            newLast.next = head;
            head.prev = newLast;
            
            Console.WriteLine("\nUltimo nodo eliminado\n");
        }
    }

    public void RandomDelete()
    {
        Console.Write("\nIntroduzca la ubicacion del nodo a eliminar: ");
        int loc = int.Parse(Console.ReadLine());

        if (head == null)
        {
            Console.WriteLine("\nLa lista esta vacia");
            return;
        }

        if (loc == 0)
        {
            BeginDelete();
            return;
        }

        Node ptr = head;
        for (int i = 0; i < loc; i++)
        {
            ptr = ptr.next;
            if (ptr == head)
            {
                Console.WriteLine("\nError: fuera de rango");
                return;
            }
        }

        ptr.prev.next = ptr.next;
        ptr.next.prev = ptr.prev;
        
        Console.WriteLine("\nNodo eliminado\n");
    }

    public void Search()
    {
        if (head == null)
        {
            Console.WriteLine("\nLista vacia\n");
        }
        else
        {
            Console.Write("\nIntroduce el elemento que deseas buscar: ");
            int item = int.Parse(Console.ReadLine());
            Node ptr = head;
            int i = 0;
            bool flag = false;

            do
            {
                if (ptr.data == item)
                {
                    Console.WriteLine($"Elemento encontrado en la ubicacion {i}");
                    flag = true;
                }
                i++;
                ptr = ptr.next;
            } while (ptr != head);

            if (!flag)
            {
                Console.WriteLine("\nElemento no encontrado\n");
            }
        }
    }

    public void Display()
    {
        Node ptr = head;

        if (ptr == null)
        {
            Console.WriteLine("Nada que imprimir");
        }
        else
        {
            Console.WriteLine("\nImprimiendo valores.......\n");
            do
            {
                Console.WriteLine(ptr.data);
                ptr = ptr.next;
            } while (ptr != head);
        }
    }

    public static void Main(string[] args)
    {
        CircularDoublyLinkedList list = new CircularDoublyLinkedList();
        int choice = 0;
        
        while (choice != 9)
        {
            Console.WriteLine("\n\n=======================MENU PRINCIPAL=======================\n");
            Console.WriteLine("\nElige una opcion de la siguiente lista ...\n");
            Console.WriteLine("\n==============================================================\n");
            Console.WriteLine("\n1. Insertar al inicio\n2. Insertar al final\n3. Insertar\n4. Eliminar el primero\n5. Eliminar el ultimo\n6. Eliminar nodo\n7. Buscar\n8. Mostrar\n9. Salir");
            Console.Write("\nIngrese su opcion: ");
            choice = int.Parse(Console.ReadLine());
            
            switch (choice)
            {
                case 1: list.BeginInsert(); break;
                case 2: list.LastInsert(); break;
                case 3: list.RandomInsert(); break;
                case 4: list.BeginDelete(); break;
                case 5: list.LastDelete(); break;
                case 6: list.RandomDelete(); break;
                case 7: list.Search(); break;
                case 8: list.Display(); break;
                case 9: Environment.Exit(0); break;
                default: Console.WriteLine("Introdusca una opcion valida...."); break;
            }
        }
    }
}