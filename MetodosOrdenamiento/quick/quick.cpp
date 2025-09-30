#include <iostream>
#include <cstdlib>
#include <vector>
#include <ctime>

using namespace std;

vector<int> quick_sort(const vector<int>& arr){
    if (arr.size() <= 1){
        return arr;
    }

    int pivot = arr[arr.size() / 2];

    vector<int> left;
    vector<int> middle;
    vector<int> right;

    for(int x : arr){
        if (x < pivot){
            left.push_back(x);
        }else if (x == pivot){
            middle.push_back(x);
        }else if (x > pivot){
            right.push_back(x);
        }
    }

    vector<int> sorted_left = quick_sort(left);
    vector<int> sorted_right = quick_sort(right);

    vector<int> result;
    result.reserve(sorted_left.size() + middle.size() + right.size());

    result.insert(result.end(), sorted_left.begin(), sorted_left.end());
    result.insert(result.end(), middle.begin(),middle.end());
    result.insert(result.end(), sorted_right.begin(),sorted_right.end());

    return result;
}

int main(){
    srand(time(0));
    system("clear");

    vector<int> arreglo;
    for(int i = 0; i < 10 ; i++){
        arreglo.push_back(rand()%101);
    }

    cout << "Arreglo original:\n";
    for (int num : arreglo){
        cout << num << " ";
    }
    cout << endl;

    vector <int> arreglo_ordenado = quick_sort(arreglo);

    cout << "\nVector ordenado:\n";
    for (int num : arreglo_ordenado){
        cout << num << " ";
    }
    cout << endl;

    return 0;
}