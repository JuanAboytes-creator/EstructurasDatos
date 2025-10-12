import random
import os

def heapify(arr,n,i):
    largest = i
    left = 2*i+1
    right = 2*i+2

    if left < n and arr[left]>arr[largest]:
        largest = left

    if right < n and arr[right] > arr[largest]:
        largest = right

    if largest != i:
        arr[i],arr[largest] = arr[largest],arr[i]
        heapify(arr, n, largest)

def heap_sort(arr):
    n = len(arr)

    for i in range(n // 2 -1, -1,-1):
        heapify(arr,n,i)

    for i in range (n-1,0,-1):
        arr[i],arr[0] = arr[0],arr[i]
        heapify(arr,i,0)

def main():
    os.system('clear')
    numeros = [random.randint(1,100) for _ in range(10)]

    print("Arreglo original:")
    print(numeros)

    numeros_ordenados = numeros.copy()

    heap_sort(numeros_ordenados)

    print("Arreglo ordenado")
    print(numeros_ordenados)

if __name__ == "__main__":
    main()