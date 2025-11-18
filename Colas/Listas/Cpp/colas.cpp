#include <iostream>
using namespace std;

struct Nodo {
    int dato;
    Nodo* siguiente;
    Nodo* anterior;
    
    Nodo(int valor) : dato(valor), siguiente(nullptr), anterior(nullptr) {}
};

class Cola {
private:
    Nodo* frente;
    Nodo* final;
    int tamaño;
    int capacidad;
    
public:
    Cola(int cap) : frente(nullptr), final(nullptr), tamaño(0), capacidad(cap) {}
    
    ~Cola() {
        while (!estaVacia()) {
            eliminar();
        }
    }
    
    void insertar(int elemento) {
        if (tamaño >= capacidad) {
            cout << "\nOVERFLOW";
            return;
        }
        
        Nodo* nuevo = new Nodo(elemento);
        if (estaVacia()) {
            frente = final = nuevo;
        } else {
            final->siguiente = nuevo;
            nuevo->anterior = final;
            final = nuevo;
        }
        tamaño++;
        cout << "\nElemento insertado correctamente.\n";
    }
    
    void eliminar() {
        if (estaVacia()) {
            cout << "\nUnderflow";
            return;
        }
        
        Nodo* temp = frente;
        int elemento = temp->dato;
        
        if (frente == final) {
            frente = final = nullptr;
        } else {
            frente = frente->siguiente;
            frente->anterior = nullptr;
        }
        
        delete temp;
        tamaño--;
        cout << "\nElemento eliminado: " << elemento << endl;
    }
    
    void mostrar() {
        if (estaVacia()) {
            cout << "\nLa cola esta vacia.\n";
        } else {
            cout << "\nElementos en la cola:\n";
            Nodo* actual = frente;
            while (actual != nullptr) {
                cout << actual->dato << endl;
                actual = actual->siguiente;
            }
        }
    }
    
    bool estaVacia() {
        return frente == nullptr;
    }
};

int main() {
    Cola cola(5);
    int opcion = 0, elemento;
    
    while (opcion != 4) {
        cout << "\n==========================MENU PRINCIPAL==========================\n";
        cout << "====================================================================\n";
        cout << "1. Insertar\n2. Eliminar\n3. Mostrar\n4. Salir\nIngrese su opcion: ";
        cin >> opcion;
        
        switch(opcion) {
            case 1:
                cout << "\nIngrese el elemento: ";
                cin >> elemento;
                cola.insertar(elemento);
                break;
            case 2:
                cola.eliminar();
                break;
            case 3:
                cola.mostrar();
                break;
            case 4:
                cout << "\nSaliendo del programa...\n";
                break;
            default:
                cout << "\nOpcion invalida. Intente nuevamente\n";
        }
    }
    return 0;
}