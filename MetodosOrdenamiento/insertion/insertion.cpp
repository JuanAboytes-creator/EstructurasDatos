#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <algorithm>

using namespace std;

vector<int> insertion_sort(vector<int> arr) {
    // Recorremos desde el segundo elemento hasta el final
    for (int i = 1; i < arr.size(); i++) {
        int key = arr[i];  // Elemento actual a insertar
        int j = i - 1;
        
        // Movemos los elementos mayores que key una posición adelante
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        
        // Insertamos key en la posición correcta
        arr[j + 1] = key;
    }
    
    return arr;
}

int main() {
    srand(time(0));  // Semilla para números aleatorios
    
    int tamaño;
    while (true) {
        cout << "Ingrese el tamaño del arreglo: ";
        cin >> tamaño;
        
        if (cin.fail() || tamaño <= 0) {
            cout << "Por favor, ingrese un número positivo mayor que 0." << endl;
            cin.clear();
            cin.ignore(10000, '\n');
            continue;
        }
        break;
    }
    
    vector<int> arreglo;
    for (int i = 0; i < tamaño; i++) {
        arreglo.push_back(rand() % 100 + 1);  // Números entre 1 y 100
    }
    
    cout << "\nArreglo original: ";
    for (int num : arreglo) {
        cout << num << " ";
    }
    cout << endl;
    
    vector<int> arreglo_ordenado = insertion_sort(arreglo);
    
    cout << "Arreglo ordenado: ";
    for (int num : arreglo_ordenado) {
        cout << num << " ";
    }
    cout << endl;
    
    return 0;
}