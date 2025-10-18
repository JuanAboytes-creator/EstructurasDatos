import random

def shell_sort(arr):
    n = len(arr)
    gap = n // 2
    
    while gap > 0:
        for i in range(gap, n):
            temp = arr[i]
            j = i
            while j >= gap and arr[j - gap] > temp:
                arr[j] = arr[j - gap]
                j -= gap
            arr[j] = temp
        gap //= 2

# Generar arreglo de 10 elementos con números aleatorios del 1 al 100
arr = [random.randint(1, 100) for _ in range(10)]
print("Arreglo original:", arr)

shell_sort(arr)
print("Arreglo ordenado:", arr)