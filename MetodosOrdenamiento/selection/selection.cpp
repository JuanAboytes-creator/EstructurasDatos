#include <iostream>
#include <vector>
#include <random>
#include <algorithm>
#include <ctime>

using namespace std;

void selectionSort(vector<int>& arr) {
    int n = arr.size();
    
    for (int i = 0; i < n; i++) {
        int min_idx = i;
        for (int j = i + 1; j < n; j++) {
            if (arr[j] < arr[min_idx]) {
                min_idx = j;
            }
        }
        swap(arr[i], arr[min_idx]);
    }
}

int main() {
    srand(time(0));
    
    int tamaño;
    while (true) {
        cout << "Ingrese el tamaño del arreglo: ";
        cin >> tamaño;
        
        if (cin.fail() || tamaño <= 0) {
            cin.clear();
            cin.ignore(1000, '\n');
            cout << "Por favor, ingrese un número positivo mayor que 0." << endl;
        } else {
            break;
        }
    }
    
    vector<int> arreglo;
    for (int i = 0; i < tamaño; i++) {
        arreglo.push_back(rand() % 100 + 1);
    }
    
    cout << "\nArreglo original: ";
    for (int num : arreglo) {
        cout << num << " ";
    }
    cout << endl;
    
    vector<int> arreglo_ordenado = arreglo;
    selectionSort(arreglo_ordenado);
    
    cout << "Arreglo ordenado: ";
    for (int num : arreglo_ordenado) {
        cout << num << " ";
    }
    cout << endl;
    
    return 0;
}