import os
import random
import copy

def mostrarTablero(oceano):
    for fila in oceano:
        print("".join(fila))

def generarCoordenadas():
    return random.randint(1,10), random.randint(1,10), random.randint(1,2)
    #x, y = generarCoordenadas()

def convertirFila(letra):
    letras = {"A": 1, "B": 2, "C": 3, "D": 4, "E": 5,
              "F": 6, "G": 7, "H": 8, "I": 9, "J": 10}
    return letras.get(letra.upper(), -1)#EL -1 regresará si no esta la letha para convertir

def barcoAlAgua(barco, direccion, fila, columna, oceano):
    sizes = {1: 5, 2: 4, 3: 3, 4: 3, 5: 2} #tamaños de los barcos según el que llegue
    size = sizes[barco]
    simbolos ={
        1:"\033[31m■\033[0m",#rojo
        2:"\033[32m■\033[0m",#verde
        3:"\033[33m■\033[0m",#amarillo
        4:"\033[34m■\033[0m",#azul
        5:"\033[35m■\033[0m"#magenta
    }
    simbolo = f"\033[36m[\033[0m{simbolos[barco]}\033[36m]\033[0m"

    try:
        if direccion == 1:#horizontal
            #verifica que quepa: el barco crecerá hacia la derecha empezando desde la cordenada
            if columna + size -1 <= 10:
                for i in range(size):
                    if oceano[fila][columna + i] != "\033[36m[ ]\033[0m":#[ ] de color Cyan
                        print("Error, casilla ocupada o inválida")
                        return False
                #Barca al agua!!!
                for i in range(size):
                    oceano[fila][columna + i] = simbolo
                return True
            else:
                print("Error, el barco no cabe horizontalmente en esa posición")
                return False
        elif direccion == 2:
            #lo mismo que arriba pero vertical
            if fila + size -1 <= 10:
                for i in range(size):
                    if oceano[fila + i][columna] != "\033[36m[ ]\033[0m":
                        print("Error, la casilla está ocupada o es invlálida")
                        return False
                #Barco al agua!!!
                for i in range(size):
                    oceano[fila + i ][columna] = simbolo
                return True

            else:
                print("Error, el barco no cabe verticalmante en esa posición")
                return False
        else:
            print("Error, dirección no válida")
            return False
    except IndexError:
        print("Error, posición fuera del oceano")
        return False

def barcosAleatorios(oceanoRival):
    barcosColocar =[1,2,3,4,5]
    while barcosColocar:
        x,y,direcc = generarCoordenadas()
        if(barcoAlAgua(barcosColocar[0],direcc,x,y,oceanoRival)):
            barcosColocar.remove(barcosColocar[0])

def pedirCoordenadas():
    while True:
        try:
            print("\nEjemplo de coordenadas: A7 , i9")

            fila_letra = input("Fila (A-J): ")
            fila = convertirFila(fila_letra)

            if fila == -1:
                print("Error, entrada no válida")
                input("Presiona Enter para continuar....")
                continue
            
            columna = int(input("Columna (1-10): "))

            if columna < 1 or columna > 10:
                print("Error, columna no válida")
                input("Presiona Enter para continuar....")
                continue

            return fila, columna
        except ValueError:
            print("Entrada inválida, usa números")
            input("Presiona Enter para continuar....")


def main():
    #oceano color cyan
    oceano = [["_ |","|1|","|2|","|3|","|4|","|5|","|6|","|7|","|8|","|9|","|10|"],
              ["A |","\033[36m[ ]\033[0m","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["B |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["C |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["D |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["E |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["F |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["G |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["H |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["I |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["J |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ]
    for i in range(11):
        for j in range(11):
            if oceano[i][j] == "[ ]":
                oceano[i][j] = "\033[36m[ ]\033[0m"

    oceanoRivalVisible = copy.deepcopy(oceano)
    oceanoRival = copy.deepcopy(oceano) #trate de usar oceanoRival = oceano, pero
    barcosAleatorios(oceanoRival)       #parece que python los relaciona, en otras palabras
    barcos_por_colocar = [1,2,3,4,5]    #es un arreglo con dos nombres, para evitarlo uso deepcopy

    while barcos_por_colocar: #Se detendrá cuando este vacio
        os.system('clear')
        print("\nOceano:")
        mostrarTablero(oceano)

        print("\n========= Coloca tus Barcos =========")

        modoAuto = input("Quieres colocar tus barcos aleatoriamente (S/n): ")
        if modoAuto == "S" or modoAuto == "s":
            barcosAleatorios(oceano)
            break

        print("\nBarcos por colocar")
        barcos_info = {
            1: "\033[31mCarrier\033[0m (5 espacios)",#rojo
            2: "\033[32mBattleship\033[0m (4 espacios)",#verde
            3: "\033[33mCruiser\033[0m (3 espacios)",#amarillo
            4: "\033[34mSubmarine\033[0m (3 espacios)",#azul
            5: "\033[35mDestroyer\033[0m (2 espacios)"#magenta
        }

        for barco_id in barcos_por_colocar:
            print(f"{barco_id}.{barcos_info[barco_id]}")#Aquí se muestran los barcos que faltan de ponerse

        try:
            barco_al_agua = int(input("\nSelecciona el barco (1-5): "))

            if barco_al_agua not in barcos_por_colocar:
                print("Error, el barco no existe o ya fué colocado")
                input("Presiona Enter para continuar....")#Uso el input() para dar tiempo de leer el error
                continue                                  #ya que se limpia la terminal al inicio del while
            
            print("1.Horizontal")
            print("2.Vertical")
            direccion = int(input("Dirección (1-2): "))

            if direccion not in [1,2]:
                print("Error, dirección no válida")
                input("Presiona Enter para continuar....")
                continue

            print("\nOceano:")
            mostrarTablero(oceano)
            fila, columna = pedirCoordenadas()

            #se intentará colocar el barco
            if barcoAlAgua(barco_al_agua, direccion, fila, columna, oceano):
                print(f"Barco {barco_al_agua}. {barcos_info[barco_al_agua]} fue colocado exitosamente!")
                barcos_por_colocar.remove(barco_al_agua)
                input("Presiona Enter para continuar....")
            else:
                print("No se pudo colocar el barco. Intentalo de nuevo. jejeje")
                input("Presiona Enter para continuar....")
                

        except ValueError:
            print("Entrada inválida, usa números")
            input("Presiona Enter para continuar....")

    os.system('clear')
    print("\nOceano:")
    mostrarTablero(oceano)
    print("\nTodos los barcos han sarpado")
    print("Listos para la batalla!!!")

    #Modo Batalla
    print("\n=========== Modo Batalla ============\n")
    puntosMaquina = 0
    puntosJugador = 0
    turno = "j"#empieza siendo el turno del jugador
    hit= False
    hit_fila = 0
    hit_columna = 0
    base_fila = 0
    base_columna = 0
    miss_right=False
    miss_left= True
    miss_up=True
    miss_down=True
    while puntosJugador < 17 and puntosMaquina < 17:
        
        if turno == "j":
            print("\nOceano:")
            mostrarTablero(oceano)
            print("\n\033[31mOceano Rival:\033[0m")
            mostrarTablero(oceanoRivalVisible)
            fila, columna = pedirCoordenadas()

            if oceanoRival[fila][columna] == "[X]" or oceanoRival[fila][columna] == "\033[31m[X]\033[0m":
                print("Ya habias atacado esas coordenadas, tontito")
            elif oceanoRival[fila][columna] != "\033[36m[ ]\033[0m":
                oceanoRival[fila][columna] = "\033[31m[X]\033[0m"#la casilla se pone roja
                oceanoRivalVisible[fila][columna] = "\033[31m[X]\033[0m"
                print("\nHIT: Has golpeado un barco enemigo")
                puntosJugador += 1
            else:
                oceanoRival[fila][columna] = "[X]"#la casilla se pone blanca
                oceanoRivalVisible[fila][columna] = "[X]"
                print("\nMISS: No le diste a nada, mejor suerte a la prox")
            turno = "m"
            print (f"\n \033[32mHITS para ganar\033[0m    {puntosJugador}/17 hits")
            input("Presione ENTER para continuar....")
        else:
            while True:
                falla = False
                try:
                    if hit == False:
                        print("\n\033[33mCoordenada ALEATORIA\033[0m")
                        fila = random.randint(1,10)
                        columna = random.randint(1,10)
                    else:
                        print("\n\033[35mCOORDENADA INTELIGENTE\033[0m")
                        #primero se intentara a la derecha
                        if not miss_right:
                            fila=hit_fila
                            columna = hit_columna + 1
                            #print(f"DEBUG: Intentando derecha -> ({fila}, {columna})")
                        elif not miss_left: 
                            fila=hit_fila
                            columna = hit_columna - 1
                            if columna == 0 :
                                oceano[30][30] = "Llamada a funcion inesperada"
                            #print(f"DEBUG: Intentando izquierda -> ({fila}, {columna})")
                        elif not miss_up:
                            fila= hit_fila -1
                            columna = hit_columna
                            if fila == 0:
                                oceano[30][30] = "Llamada a funcion inesperada"
                            #print(f"DEBUG: Intentando arriba -> ({fila}, {columna})")
                        elif not miss_down:
                            fila= hit_fila +1
                            columna = hit_columna
                            #print(f"DEBUG: Intentando abajo -> ({fila}, {columna})")

                
                    if oceano[fila][columna] =="[X]" or oceano[fila][columna] == "\033[31m[X]\033[0m":
                        oceano[30][30] = "Llamada a funcion inesperada"

                    elif oceano[fila][columna] != "\033[36m[ ]\033[0m":
                        oceano[fila][columna] = "\033[31m[X]\033[0m"#la casilla se pone roja
                        print("\nHIT: Han golpeado un barco aliado")
                        if not hit:#Guardo las coordenados del primer hit
                            base_fila=fila
                            base_columna=columna
                            hit = True
                    
                        hit_fila=fila
                        hit_columna = columna
                        puntosMaquina += 1
                        break
                    else:
                        falla = True
                        oceano[30][30] = "Llamada a funcion inesperada"
                except IndexError:
                    if hit:
                        if not miss_right:
                            hit_fila=base_fila
                            hit_columna= base_columna
                            miss_right = True
                            miss_left = False
                        elif not miss_left:
                            hit_fila=base_fila
                            hit_columna= base_columna
                            miss_left = True
                            miss_up = False
                        elif not miss_up:
                            hit_fila=base_fila
                            hit_columna= base_columna
                            miss_up= True
                            miss_down = False
                        elif not miss_down:
                            miss_down = True
                            miss_right = False
                            hit = False
                    if falla:
                        oceano[fila][columna] = "[X]"#la casilla se pone blanca
                        print("\nMISS: Fallaron, se sobrevive un turno a la vez")
                        break

            turno = "j"
            print (f"\n \033[31mHITS para perder en contra\033[0m   {puntosMaquina}/17 hits")
            input("Presione ENTER para continuar....")
    if puntosJugador == 17:
        print ("\n\033[32mFelicidades, Ganaste contra la maquina\033[0m")
    else:
        print ("\n\033[31mGanan las Maquinas: Ni modo, la proxima será\033[0m")


if __name__ == "__main__":
    main()