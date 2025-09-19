#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <algorithm>

using namespace std;

vector<int> ordenamiento_burbuja(vector<int> arr) {
    int n = arr.size();
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                swap(arr[j], arr[j + 1]);
            }
        }
    }
    return arr;
}

int main() {
    // Semilla para números aleatorios
    srand(time(0));
    
    int tamaño;
    cout << "Ingrese el tamaño del arreglo: ";
    cin >> tamaño;
    
    // Crear arreglo con valores aleatorios
    vector<int> arreglo;
    for (int i = 0; i < tamaño; i++) {
        arreglo.push_back(rand() % 100 + 1);
    }
    
    cout << "Arreglo original: ";
    for (int num : arreglo) {
        cout << num << " ";
    }
    cout << endl;
    
    vector<int> arreglo_ordenado = ordenamiento_burbuja(arreglo);
    
    cout << "Arreglo ordenado: ";
    for (int num : arreglo_ordenado) {
        cout << num << " ";
    }
    cout << endl;
    
    return 0;
}