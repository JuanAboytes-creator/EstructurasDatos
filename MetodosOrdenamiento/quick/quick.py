import random

def quick_sort(arr):
    if len(arr) <= 1:
        return arr
    
    pivot = arr[len(arr) // 2] #El doble // trunquea el resultado un 3.9 pasa a ser 3

    left = [x for x in arr if x < pivot]
    middle = [x for x in arr if x == pivot]
    right = [x for x in arr if x > pivot]

    return quick_sort(left) + middle + quick_sort(right)

def main():
    arreglo = [random.randint(1, 100) for _ in range(10)]
    print("Arreglo original:")
    print(arreglo)

    arreglo_ordenado = quick_sort(arreglo)

    print("\nArreglo ordenado:")
    print(arreglo_ordenado)

if __name__ == "__main__":
    main()