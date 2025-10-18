#include <iostream>
#include <vector>
#include <algorithm>
#include <random>
#include <ctime>
using namespace std;

void bucketSort(vector<int>& arr) {
    if (arr.empty()) return;
    
    // Encontrar valor máximo y mínimo
    int min_val = *min_element(arr.begin(), arr.end());
    int max_val = *max_element(arr.begin(), arr.end());
    
    // Crear buckets
    int bucket_count = arr.size();
    vector<vector<int>> buckets(bucket_count);
    
    // Distribuir elementos en los buckets
    for (int num : arr) {
        int index = (num - min_val) * bucket_count / (max_val - min_val + 1);
        buckets[index].push_back(num);
    }
    
    // Ordenar cada bucket e insertar en el array original
    arr.clear();
    for (auto& bucket : buckets) {
        sort(bucket.begin(), bucket.end());
        arr.insert(arr.end(), bucket.begin(), bucket.end());
    }
}

int main() {
    // Generar arreglo de 10 elementos con números aleatorios del 1 al 100
    vector<int> arr(10);
    srand(time(0));
    
    for (int i = 0; i < 10; i++) {
        arr[i] = rand() % 100 + 1;
    }
    
    cout << "Arreglo original: ";
    for (int num : arr) {
        cout << num << " ";
    }
    cout << endl;
    
    // Ordenar con bucket sort
    vector<int> arr_ordenado = arr;
    bucketSort(arr_ordenado);
    
    cout << "Arreglo ordenado: ";
    for (int num : arr_ordenado) {
        cout << num << " ";
    }
    cout << endl;
    
    return 0;
}