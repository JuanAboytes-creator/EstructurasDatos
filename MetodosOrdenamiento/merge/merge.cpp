#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>

using namespace std;
void merge_sort(vector<int>& arr){
    if(arr.size() > 1){
        size_t mid = arr.size()/2;
        vector<int> left_half(arr.begin(), arr.begin() + mid);
        vector<int> right_half(arr.begin() + mid, arr.end());

        merge_sort(left_half);
        merge_sort(right_half);

        size_t i=0, j=0, k=0;

        while (i < left_half.size() && j < right_half.size()){
            if (left_half[i] < right_half[j]){
                arr[k] = left_half[i];
                i++;
            }else{
                arr[k] = right_half[j];
                j++;
            }
            k++;
        }
        while (i < left_half.size()){
            arr[k] = left_half[i];
            i++;
            k++;
        }
        while (j < right_half.size()){
            arr[k] = right_half[j];
            j++;
            k++;
        }
    }
}

int main(){
    srand(time(0));
    system("clear");

    vector<int> arreglo;
    for(int i=0; i<10;i++){
        arreglo.push_back(rand() % 101);
    }

    cout << "Arreglo original:\n";
    for (size_t i =0; i < arreglo.size();i++){
        cout << arreglo[i] << " ";
    }
    cout << endl;

    merge_sort(arreglo);

    cout << "Arreglo ordenado\n";
    for (size_t i = 0; i < 10; i++){
        cout << arreglo[i]<< " ";
    }
    cout << endl;

    return 0;

}