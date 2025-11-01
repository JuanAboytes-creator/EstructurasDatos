#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <algorithm>

using namespace std;

// Función para obtener el dígito en una posición específica
int getDigit(int num, int position) {
    for (int i = 0; i < position; i++) {
        num /= 10;
    }
    return num % 10;
}

void radixSort(vector<int>& arr) {
    if (arr.empty()) return;
    
    int maxNum = *max_element(arr.begin(), arr.end());
    int maxDigits = 0;
    
    // Calcular el número máximo de dígitos
    while (maxNum > 0) {
        maxDigits++;
        maxNum /= 10;
    }
    
    // Ordenar por cada dígito
    for (int digit = 0; digit < maxDigits; digit++) {
        vector<vector<int>> buckets(10);
        
        // Distribuir en los buckets según el dígito actual
        for (int num : arr) {
            int currentDigit = getDigit(num, digit);
            buckets[currentDigit].push_back(num);
        }
        
        // Recolectar de los buckets
        int index = 0;
        for (int i = 0; i < 10; i++) {
            for (int num : buckets[i]) {
                arr[index++] = num;
            }
        }
    }
}

int main() {
    srand(time(0));
    system("clear");
    vector<int> arr(10);
    
    cout << "Arreglo original: ";
    for (int i = 0; i < 10; i++) {
        arr[i] = rand() % 100 + 1;
        cout << arr[i] << " ";
    }
    cout << endl;
    
    radixSort(arr);
    
    cout << "Arreglo ordenado: ";
    for (int num : arr) {
        cout << num << " ";
    }
    cout << endl;
    
    return 0;
}