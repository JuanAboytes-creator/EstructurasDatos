import random

def selection_sort(arr):
    n = len(arr)
    
    # Recorremos todo el arreglo
    for i in range(n):
        # Encontramos el índice del elemento mínimo en la parte no ordenada
        min_idx = i
        for j in range(i + 1, n):
            if arr[j] < arr[min_idx]:
                min_idx = j
        
        # Intercambiamos el elemento mínimo encontrado con el primer elemento
        arr[i], arr[min_idx] = arr[min_idx], arr[i]
    
    return arr

def main():
    while True:
        try:
            tamaño = int(input("Ingrese el tamaño del arreglo: "))
            if tamaño > 0:
                break
            else:
                print("Por favor, ingrese un número positivo mayor que 0.")
        except ValueError:
            print("Por favor, ingrese un número válido.")
    
    arreglo = [random.randint(1, 100) for _ in range(tamaño)]
    
    print(f"\nArreglo original: {arreglo}")
    
    arreglo_ordenado = selection_sort(arreglo.copy())
    
    print(f"Arreglo ordenado: {arreglo_ordenado}")

if __name__ == "__main__":
    main()