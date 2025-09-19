import random

def ordenamiento_burbuja(arr):
    n = len(arr)
    for i in range(n):
        for j in range(0, n-i-1):
            if arr[j] > arr[j+1]:
                arr[j], arr[j+1] = arr[j+1], arr[j]
    return arr

tamaño = int(input("Ingrese el tamaño del arreglo: "))

# Crear arreglo con valores aleatorios
arreglo = [random.randint(1, 100) for _ in range(tamaño)]

print(f"Arreglo original: {arreglo}")

# Ordenar el arreglo (usando copia para no modificar el original)
arreglo_ordenado = ordenamiento_burbuja(arreglo.copy())

print(f"Arreglo ordenado: {arreglo_ordenado}")