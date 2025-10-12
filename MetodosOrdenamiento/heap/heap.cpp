#include <iostream>
#include <vector>
#include <random>
#include <algorithm>

using namespace std;
void heapify(vector<int>& arr, int n, int i){
    int largest = i;
    int left = 2*i+1;
    int right = 2*i+2;

    if (left < n && arr[left] > arr [largest]){
        largest = left;
    }

    if (right < n && arr[right] > arr[largest]){
        largest = right;
    }

    if(largest != i){
        swap(arr[i],arr[largest]);
        heapify(arr,n,largest);
    }
} 
void heapSort(vector<int>& arr){
    int n= arr.size();

    for(int i=n/2-1;i>=0;i--){
        heapify(arr,n,i);
    }
    for(int i=n-1;i>0;i--){
        swap(arr[0], arr[i]);
        heapify(arr,i,0);
    }
}  
int main(){
    system("clear");
    vector<int> numeros;

    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> dis(1,100);

    for (int i=0;i<10;i++){
        numeros.push_back(dis(gen));
    }

    cout << "Arreglo original:\n";
    for(int num : numeros){
        cout << num << " ";
    }
    cout << endl;

    vector<int> numerosOrdenados = numeros;
    heapSort(numerosOrdenados);
    
    for(int num : numerosOrdenados){
        cout << num << " ";
    }
    cout << endl;
}
