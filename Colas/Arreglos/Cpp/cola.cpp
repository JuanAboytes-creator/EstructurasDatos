#include <iostream>
using namespace std;

#define MAXSIZE 5

int queue[MAXSIZE];
int front = -1, rear = -1;
void insertar(){
    int elemento;
    cout << "\nIngrese el elemento: ";
    cin >> elemento;
    if(rear == MAXSIZE -1){
        cout << "\nOVERFLOW";
        return;
    }
    if(front == -1  && rear == -1){
        front = rear = 0;
    }else{
        rear++;
    }
    queue[rear] = elemento;
    cout << "\nElemento insertado correctamente.\n";
}
void eliminar(){
    if(front == -1 || front > rear){
        cout << "\nUnderflow";
        return;
    }
    int elemento = queue[front];
    if(front == rear){
        front = rear = -1;
    }else{
        front++;
    }
    cout << "\nElemento eliminado: " << elemento << endl;
}
void mostrar(){
    if(rear == -1 || front == -1 || front > rear){
        cout << "\nLa cola esta vacia.\n";
    }else{
        cout << "\nElementos en la cola:\n";
        for (int i = front; i <= rear; i++){
            cout << queue[i] << endl;
        }
    }
}
int main(){
    int opcion = 0;
    while (opcion != 4){
        cout << "\n==========================MENU PRINCIPAL==========================\n";
        cout << "====================================================================\n";
        cout << "1. Insertar\n2. Eliminar\n3. Mostrar\n4. Salir\nIngrese su opcion: ";
        cin >> opcion;
        switch(opcion){
            case 1:insertar();break;
            case 2:eliminar();break;
            case 3:mostrar();break;
            case 4:cout << "\nSaliendo del programa...\n";break;
            default: cout << "\nOpcion invalida. Intente nuevamente\n";
        }
    }
}