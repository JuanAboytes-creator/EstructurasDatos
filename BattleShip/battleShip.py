import os

def mostrarTablero(oceano):
    
    print("\nOceano:")
    for fila in oceano:
        print("".join(fila))

def convertirFila(letra):
    letras = {"A": 1, "B": 2, "C": 3, "D": 4, "E": 5,
              "F": 6, "G": 7, "H": 8, "I": 9, "J": 10}
    return letras.get(letra.upper(), -1)#EL -1 regresará si no esta la letha para convertir

def barcoAlAgua(barco, direccion, fila, columna, oceano):
    sizes = {1: 5, 2: 4, 3: 3, 4: 3, 5: 2} #tamaños de los barcos según el que llegue
    size = sizes[barco]
    simbolo = "[■]"

    try:
        if direccion == 1:#horizontal
            #verifica que quepa: el barco crecerá hacia la derecha empezando desde la cordenada
            if columna + size -1 <= 10:
                for i in range(size):
                    if oceano[fila][columna + i] != "[ ]":
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
                    if oceano[fila + i][columna] != "[ ]":
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

def main():
    #oceano = [["[ ]"] * 10 for _ in range(10)]
    oceano = [["_ |","|1|","|2|","|3|","|4|","|5|","|6|","|7|","|8|","|9|","|10|"],
              ["A |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["B |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["C |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["D |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["E |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["F |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["G |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["H |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["I |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],
              ["J |","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"],]
   
    barcos_por_colocar = [1,2,3,4,5]

    while barcos_por_colocar: #Se detendrá cuando este vacio
        os.system('clear')
        mostrarTablero(oceano)
        
        print("\n========= Coloca tus Barcos =========")
        print("Barcos por colocar")
        barcos_info = {
            1: "Carrier (5 espacios)",
            2: "Battleship (4 espacios)",
            3: "Cruiser (3 espacios)",
            4: "Submarine (3 espacios)",
            5: "Destroyer (2 espacios)"
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

            mostrarTablero(oceano)
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
    mostrarTablero(oceano)
    print("\nTodos los barcos han sarpado")
    print("Listos para la batalla!!!")
        
if __name__ == "__main__":
    main()