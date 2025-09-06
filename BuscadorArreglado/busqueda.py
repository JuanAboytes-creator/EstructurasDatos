def busqueda_lineal(arr, objetivo):

    for i in range(len(arr)):
        if arr[i] == objetivo:
            return i
    return -1

arreglo = [5, 2, 8, 1, 9, 3]
objetivo = 8

resultado = busqueda_lineal(arreglo, objetivo)

if resultado != -1:
    print(f"Elemento {objetivo} encontrado en el índice {resultado}")
else:
    print(f"Elemento {objetivo} no encontrado")