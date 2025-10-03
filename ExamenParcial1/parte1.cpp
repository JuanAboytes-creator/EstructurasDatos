#include <iostream>
#include <string>

using namespace std;

int main(){
    cout << "\n";
    int arreglo[] = {1,2,3,4,5,6,7,8,9,10};
    for(int j =0;j<10;j++){
         cout << "[" << arreglo[j] << "]";
    }
    int pares=0,impares=0;
        for(int i = 0;i<10;i++){
            if(arreglo[i] % 2 == 0){
                pares++;
            }else{
                impares++;
            }
        }
        cout << "\nEl numero de pares en de: " << pares<<endl;
        cout << "El numero de impares en de: " << impares<<endl;
}