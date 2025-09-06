#include <iostream>
#include <vector>
using namespace std;

int busquedaLineal(const vector<int>& arr, int objetivo) {

    for (int i = 0; i < arr.size(); i++) {
        if (arr[i] == objetivo) {
            return i;
        }
    }
    return -1;
}

int main() {
    vector<int> arreglo = {5, 2, 8, 1, 9, 3};
    int objetivo = 8;
    
    int resultado = busquedaLineal(arreglo, objetivo);
    
    if (resultado != -1) {
        cout << "Elemento " << objetivo << " encontrado en el indice " << resultado << endl;
    } else {
        cout << "Elemento " << objetivo << " no encontrado" << endl;
    }
    
    return 0;
}