MAXSIZE = 5
queue = [0] * MAXSIZE
front = -1
rear = -1

def insertar():
    global front, rear
    if rear == MAXSIZE - 1:
        print("\nOVERFLOW")
        return
    
    elemento = int(input("\nIngrese el elemento: "))
    
    if front == -1 and rear == -1:
        front = rear = 0
    else:
        rear += 1
    queue[rear] = elemento
    print("\nElemento insertado correctamente.\n")

def eliminar():
    global front, rear
    if front == -1 or front > rear:
        print("\nUnderflow")
        return
    
    elemento = queue[front]
    if front == rear:
        front = rear = -1
    else:
        front += 1
    print(f"\nElemento eliminado: {elemento}\n")

def mostrar():
    if rear == -1 or front == -1 or front > rear:
        print("\nLa cola esta vacia.\n")
    else:
        print("\nElementos en la cola:")
        for i in range(front, rear + 1):
            print(queue[i])

def main():
    opcion = 0
    while opcion != 4:
        print("\n==========================MENU PRINCIPAL==========================")
        print("====================================================================")
        print("1. Insertar\n2. Eliminar\n3. Mostrar\n4. Salir")
        opcion = int(input("Ingrese su opcion: "))
        
        if opcion == 1:
            insertar()
        elif opcion == 2:
            eliminar()
        elif opcion == 3:
            mostrar()
        elif opcion == 4:
            print("\nSaliendo del programa...\n")
        else:
            print("\nOpcion invalida. Intente nuevamente\n")

if __name__ == "__main__":
    main()