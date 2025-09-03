#include <iostream>
#include <vector>
using namespace std;

int main() {
    // Vector de 5 números enteros (similar a lista en Python)
    vector<int> numeros = {10, 25, 37, 42, 58};
    
    // Recorrer el vector e imprimir cada valor
    cout << "Recorrido del arreglo:" << endl;
    for(int numero : numeros) {
        cout << numero << endl;
    }
    
    return 0;
}