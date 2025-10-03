#include <iostream>
#include <string>

using namespace std;

int main(){
    int arreglo[3][3]={
        {2,3,6},
        {8,0,4},
        {9,7,5}
    };
    int c1=0,c2=0,c3=0;
    cout << "Arreglo 3x3:\n";
    for (int i = 0;i<3;i++){
        for(int j =0;j<3;j++){
            cout << "[" << arreglo[i][j] << "]";
            
        }
        cout << endl;
    }
        for(int j =0;j<3;j++){
            c1+=arreglo[j][0];
            c2+=arreglo[j][1];
            c3+=arreglo[j][2];
        }
    
    cout << "La suma de la columna 1 es: "<< c1<< endl;
    cout << "La suma de la columna 2 es: "<<c2<<endl;
    cout << "La suma de la columna 3 es: "<<c3<<endl;
}