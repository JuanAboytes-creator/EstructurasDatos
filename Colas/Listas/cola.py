class Nodo:
    def __init__(self, valor):
        self.dato = valor
        self.siguiente = None
        self.anterior = None

class Cola:
    def __init__(self, capacidad):
        self.frente = None
        self.final = None
        self.tamaño = 0
        self.capacidad = capacidad
    
    def insertar(self, elemento):
        if self.tamaño >= self.capacidad:
            print("\nOVERFLOW")
            return
        
        nuevo = Nodo(elemento)
        if self.esta_vacia():
            self.frente = self.final = nuevo
        else:
            self.final.siguiente = nuevo
            nuevo.anterior = self.final
            self.final = nuevo
        self.tamaño += 1
        print("\nElemento insertado correctamente.\n")
    
    def eliminar(self):
        if self.esta_vacia():
            print("\nUnderflow")
            return
        
        elemento = self.frente.dato
        if self.frente == self.final:
            self.frente = self.final = None
        else:
            self.frente = self.frente.siguiente
            self.frente.anterior = None
        self.tamaño -= 1
        print(f"\nElemento eliminado: {elemento}\n")
    
    def mostrar(self):
        if self.esta_vacia():
            print("\nLa cola esta vacia.\n")
        else:
            print("\nElementos en la cola:")
            actual = self.frente
            while actual is not None:
                print(actual.dato)
                actual = actual.siguiente
    
    def esta_vacia(self):
        return self.frente is None

def main():
    cola = Cola(5)
    opcion = 0
    
    while opcion != 4:
        print("\n==========================MENU PRINCIPAL==========================")
        print("====================================================================")
        print("1. Insertar\n2. Eliminar\n3. Mostrar\n4. Salir")
        opcion = int(input("Ingrese su opcion: "))
        
        if opcion == 1:
            elemento = int(input("\nIngrese el elemento: "))
            cola.insertar(elemento)
        elif opcion == 2:
            cola.eliminar()
        elif opcion == 3:
            cola.mostrar()
        elif opcion == 4:
            print("\nSaliendo del programa...\n")
        else:
            print("\nOpcion invalida. Intente nuevamente\n")

if __name__ == "__main__":
    main()