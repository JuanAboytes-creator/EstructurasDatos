#include <iostream>
#include <vector>
#include <unordered_map>
#include <algorithm>
#include <random>

using namespace std;

vector<int> hash_sort(const vector<int>& arr) {
    if (arr.empty()) return arr;
    
    int min_val = *min_element(arr.begin(), arr.end());
    int max_val = *max_element(arr.begin(), arr.end());

    // Crear tabla hash
    unordered_map<int, int> hash_table;
    
    // Insertar elementos en la tabla hash
    for (int num : arr) {
        hash_table[num]++;
    }
    
    // Reconstruir vector ordenado
    vector<int> sorted_arr;
    for (int num = min_val; num <= max_val; ++num) {
        if (hash_table.find(num) != hash_table.end()) {
            for (int i = 0; i < hash_table[num]; ++i) {
                sorted_arr.push_back(num);
            }
        }
    }
    
    return sorted_arr;
}

int main() {
    // Generar arreglo de 10 elementos aleatorios
    vector<int> arr(10);
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> dis(1, 100);
    
    for (int i = 0; i < 10; ++i) {
        arr[i] = dis(gen);
    }
    
    cout << "Array original: ";
    for (int num : arr) {
        cout << num << " ";
    }
    cout << endl;
    
    // Ordenar con Hash Sort
    vector<int> sorted_arr = hash_sort(arr);
    
    cout << "Array ordenado: ";
    for (int num : sorted_arr) {
        cout << num << " ";
    }
    cout << std::endl;
    
    return 0;
}