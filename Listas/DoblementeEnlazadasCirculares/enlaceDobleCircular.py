class Node:
    def __init__(self, data=None):
        self.data = data
        self.prev = None
        self.next = None

class CircularDoublyLinkedList:
    def __init__(self):
        self.head = None

    def begin_insert(self):
        item = int(input("\nIngrese valor: "))
        ptr = Node(item)

        if self.head is None:
            self.head = ptr
            ptr.next = ptr
            ptr.prev = ptr
        else:
            last = self.head.prev
            
            ptr.next = self.head
            ptr.prev = last
            
            self.head.prev = ptr
            last.next = ptr
            
            self.head = ptr
        print("\nNodo insertado")

    def last_insert(self):
        item = int(input("\nIngrese valor: "))
        ptr = Node(item)

        if self.head is None:
            ptr.prev = ptr
            ptr.next = ptr
            self.head = ptr
        else:
            last = self.head.prev
            
            ptr.next = self.head
            ptr.prev = last
            
            last.next = ptr
            self.head.prev = ptr
        print("\nNodo insertado")

    def random_insert(self):
        item = int(input("\nIntroduzca el valor del elemento: "))
        loc = int(input("\nIntroduce la ubicacion despues de la cual deseas insertar: "))

        ptr = Node(item)

        if self.head is None:
            print("\nLa lista esta vacia, insertando al inicio")
            ptr.prev = ptr
            ptr.next = ptr
            self.head = ptr
            return

        temp = self.head
        for i in range(loc):
            temp = temp.next
            if temp == self.head:
                print("\nNo se puede insertar\n")
                return

        ptr.prev = temp
        ptr.next = temp.next
        temp.next.prev = ptr
        temp.next = ptr
        
        print("\nNodo insertado")

    def begin_delete(self):
        if self.head is None:
            print("\nLa lista esta vacia\n")
        elif self.head.next == self.head:
            self.head = None
            print("\nNodo eliminado desde el principio\n")
        else:
            last = self.head.prev
            new_head = self.head.next
            
            last.next = new_head
            new_head.prev = last
            
            self.head = new_head
            print("\nNodo eliminado desde el principio\n")

    def last_delete(self):
        if self.head is None:
            print("\nLa lista esta vacia")
        elif self.head.next == self.head:
            self.head = None
            print("\nSolo se elimino el unico nodo de la lista\n")
        else:
            last = self.head.prev
            new_last = last.prev
            
            new_last.next = self.head
            self.head.prev = new_last
            
            print("\nUltimo nodo eliminado\n")

    def random_delete(self):
        loc = int(input("\nIntroduzca la ubicacion del nodo a eliminar: "))

        if self.head is None:
            print("\nLa lista esta vacia")
            return

        if loc == 0:
            self.begin_delete()
            return

        ptr = self.head
        for i in range(loc):
            ptr = ptr.next
            if ptr == self.head:
                print("\nError: fuera de rango")
                return

        ptr.prev.next = ptr.next
        ptr.next.prev = ptr.prev
        
        print("\nNodo eliminado\n")

    def search(self):
        if self.head is None:
            print("\nLista vacia\n")
        else:
            item = int(input("\nIntroduce el elemento que deseas buscar: "))
            ptr = self.head
            i = 0
            flag = False

            while True:
                if ptr.data == item:
                    print(f"Elemento encontrado en la ubicacion {i}")
                    flag = True
                i += 1
                ptr = ptr.next
                if ptr == self.head:
                    break

            if not flag:
                print("\nElemento no encontrado\n")

    def display(self):
        ptr = self.head

        if ptr is None:
            print("Nada que imprimir")
        else:
            print("\nImprimiendo valores.......\n")
            while True:
                print(ptr.data)
                ptr = ptr.next
                if ptr == self.head:
                    break

    def main(self):
        choice = 0
        
        while choice != 9:
            print("\n\n=======================MENU PRINCIPAL=======================\n")
            print("\nElige una opcion de la siguiente lista ...\n")
            print("\n==============================================================\n")
            print("\n1. Insertar al inicio\n2. Insertar al final\n3. Insertar\n4. Eliminar el primero\n5. Eliminar el ultimo\n6. Eliminar nodo\n7. Buscar\n8. Mostrar\n9. Salir")
            
            try:
                choice = int(input("\nIngrese su opcion: "))
            except ValueError:
                print("Introdusca una opcion valida....")
                continue
            
            if choice == 1:
                self.begin_insert()
            elif choice == 2:
                self.last_insert()
            elif choice == 3:
                self.random_insert()
            elif choice == 4:
                self.begin_delete()
            elif choice == 5:
                self.last_delete()
            elif choice == 6:
                self.random_delete()
            elif choice == 7:
                self.search()
            elif choice == 8:
                self.display()
            elif choice == 9:
                exit(0)
            else:
                print("Introdusca una opcion valida....")

if __name__ == "__main__":
    list = CircularDoublyLinkedList()
    list.main()