import random

def insertion_sort(arr):
    # Recorremos desde el segundo elemento hasta el final
    for i in range(1, len(arr)):
        key = arr[i]  # Elemento actual a insertar
        j = i - 1
        
        # Movemos los elementos mayores que key una posición adelante
        while j >= 0 and arr[j] > key:
            arr[j + 1] = arr[j]
            j -= 1
        
        # Insertamos key en la posición correcta
        arr[j + 1] = key
    
    return arr

def main():
    while True:
        try:
            tamaño = int(input("Ingrese el tamaño del arreglo: "))
            if tamaño <= 0:
                print("Por favor, ingrese un número positivo mayor que 0.")
                continue
            break
        except ValueError:
            print("Por favor, ingrese un número válido.")
    
    arreglo = [random.randint(1, 100) for _ in range(tamaño)]
    
    print(f"\nArreglo original: {arreglo}")
    
    arreglo_ordenado = insertion_sort(arreglo.copy())
    
    print(f"Arreglo ordenado: {arreglo_ordenado}")

if __name__ == "__main__":
    main()