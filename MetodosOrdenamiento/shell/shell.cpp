#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>
using namespace std;

void shell_sort(vector<int>& arr) {
    int n = arr.size();
    
    for (int gap = n / 2; gap > 0; gap /= 2) {
        for (int i = gap; i < n; i++) {
            int temp = arr[i];
            int j;
            for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                arr[j] = arr[j - gap];
            }
            arr[j] = temp;
        }
    }
}

int main() {
    srand(time(0));
    vector<int> arr(10);
    
    // Generar arreglo de 10 elementos con números aleatorios del 1 al 100
    for (int i = 0; i < 10; i++) {
        arr[i] = rand() % 100 + 1;
    }
    
    cout << "Arreglo original: ";
    for (int num : arr) {
        cout << num << " ";
    }
    cout << endl;
    
    shell_sort(arr);
    
    cout << "Arreglo ordenado: ";
    for (int num : arr) {
        cout << num << " ";
    }
    cout << endl;
    
    return 0;
}