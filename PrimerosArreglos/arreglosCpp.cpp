#include <iostream>
using namespace std;

int main() {
    // Versión simple de arreglos en C++
    int arreglo[] = {1, 2, 3, 4, 5};
    
    cout << "Arreglo inicial: ";
    for(int i = 0; i < 5; i++) {
        cout << arreglo[i] << " ";
    }
    cout << endl;
    
    cout << "Remplazando el numero " << arreglo[1] << " por 99" << endl;
    arreglo[1] = 99;
    
    cout << "Arreglo despues del reemplazo: ";
    for(int i = 0; i < 5; i++) {
        cout << arreglo[i] << " ";
    }
    cout << endl;
    
    return 0;
}