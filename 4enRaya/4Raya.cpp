#include <iostream>
#include <string>

using namespace std;

static void MostrarTablero(char tablero[6][7]){
    cout << "\n\n[1][2][3][4][5][6][7]"<< "\n\n";
    //cout << "\n\n|1||2||3||4||5||6||7|"<< "\n\n";
    for (int i = 0;i<6;i++){
        for(int j =0;j<7;j++){
            cout << "[" << tablero[i][j] << "]";
        }
        cout << endl;
    }
}
int validarNumero(const string& message){
    string entrada;
    int column;

    while(true){
        cout << "\n" << message;
        getline(cin, entrada);

        try {
            size_t pos;
            column=stoi(entrada, &pos);

            if (pos != entrada.length()){
                cout << "Error: La entrada contiene caracteres no númericos";
                continue;
            }
            if (column >= 1 && column <= 7){
                return column;
            }else{
                cout << "Error: El número debe estar entre el 1 y el 7";
                continue;
            }
        }catch(const invalid_argument& e){
            cout << "Error: Entrada invalida";
        }catch(const out_of_range& e){
            cout << "Error: Demasiado grande";
        }
    }
}
static bool VerificarGanador(char tablero[6][7], char jugadorActual){
    for (int i=5;i>2;i--){
        for(int j=0;j<7;j++){
            if(j>=0 && j<=2){
                if(tablero[i][j]==jugadorActual){
                    //arriba
                    if(tablero[i-1][j]==jugadorActual && tablero[i-2][j]==jugadorActual && tablero[i-3][j]==jugadorActual) return true;
                    //diagonal derecha
                    if(tablero[i-1][j+1]==jugadorActual && tablero[i-2][j+2]==jugadorActual && tablero[i-3][j+3]==jugadorActual) return true;
                    //derecha
                    if(tablero[i][j+1]==jugadorActual && tablero[i][j+2]==jugadorActual && tablero[i][j+3]==jugadorActual) return true;
                }
            }
            if(j==3){
                if(tablero[i][j]==jugadorActual){
                    //diaganal izquierda
                    if(tablero[i-1][j-1]==jugadorActual && tablero[i-2][j-2]==jugadorActual && tablero[i-3][j-3]==jugadorActual) return true;
                    //arriba
                    if(tablero[i-1][j]==jugadorActual && tablero[i-2][j]==jugadorActual && tablero[i-3][j]==jugadorActual) return true;
                    //diagonal derecha
                    if(tablero[i-1][j+1]==jugadorActual && tablero[i-2][j+2]==jugadorActual && tablero[i-3][j+3]==jugadorActual) return true;
                    //derecha
                    if(tablero[i][j+1]==jugadorActual && tablero[i][j+2]==jugadorActual && tablero[i][j+3]==jugadorActual) return true;
                }
            }
            if(j>=4 && j<=6){
                if(tablero[i][j]==jugadorActual){
                    //diaganal izquierda
                    if(tablero[i-1][j-1]==jugadorActual && tablero[i-2][j-2]==jugadorActual && tablero[i-3][j-3]==jugadorActual) return true;
                    //arriba
                    if(tablero[i-1][j]==jugadorActual && tablero[i-2][j]==jugadorActual && tablero[i-3][j]==jugadorActual) return true;
                }
            }
        }
    }
    return false;
}
static bool TableroLleno(char tablero[6][7]){
    for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 7; j++) {
            if (tablero[i][j] == ' ') {
                return false;
            }
        }
    }
    return true;
}
int main()
{
    char tablero[6][7] = {
    {' ', ' ', ' ', ' ', ' ', ' ', ' '},
    {' ', ' ', ' ', ' ', ' ', ' ', ' '},
    {' ', ' ', ' ', ' ', ' ', ' ', ' '},
    {' ', ' ', ' ', ' ', ' ', ' ', ' '},
    {' ', ' ', ' ', ' ', ' ', ' ', ' '},
    {' ', ' ', ' ', ' ', ' ', ' ', ' '}
    };

    cout << "Nombre del jugador 1(X): ";
    string jugador1;
    getline(cin, jugador1);
    jugador1 += " (X)";
    cout << "Nombre del jugador 2(O): ";
    string jugador2;
    getline(cin, jugador2);
    jugador2 += " (O)";

    char jugadorActual = 'X';//X es el jugador 1
    bool juegoTerminado = false;
    
    cout << "=== JUEGO 4 EN LINEA ===";
    int columna;
    while (!juegoTerminado){
        MostrarTablero(tablero);
        
        //operador ternario
        columna=validarNumero("Turno de " + (jugadorActual == 'X' ? jugador1:jugador2) + ": ");
        
        if (tablero[0][columna-1] == ' '){
            for (int i=5;i>-1;i--){
                if (tablero[i][columna-1]==' '){

                    tablero[i][columna-1]=jugadorActual;
                    
                    if (VerificarGanador(tablero,jugadorActual)){
                        MostrarTablero(tablero);
                        cout << "\n" << (jugadorActual== 'X' ? jugador1 : jugador2) << " GANA!!\n";
                        juegoTerminado=true;
                    }else if (TableroLleno(tablero)){
                        MostrarTablero(tablero);
                        cout << "\nEMPATE!!\n";
                        juegoTerminado=true;
                    }else{
                        //cambio de jugador
                        jugadorActual=jugadorActual== 'X' ? 'O' : 'X';
                    }
                    break;
                }
            }
        }else{
            cout << "Columna llena, intenta otrra";
        }
    }
    return 0;
}