import random

def bucket_sort(arr):
    if len(arr) == 0:
        return arr
    
    # Encontrar valor máximo y mínimo
    min_val = min(arr)
    max_val = max(arr)
    
    # Crear buckets
    bucket_count = len(arr)
    buckets = [[] for _ in range(bucket_count)]
    
    # Distribuir elementos en los buckets
    for num in arr:
        # Calcular índice del bucket
        index = int((num - min_val) / (max_val - min_val + 1) * bucket_count)
        buckets[index].append(num)
    
    # Ordenar cada bucket e insertar en el array original
    arr.clear()
    for bucket in buckets:
        bucket.sort()  # Puedes usar cualquier algoritmo de ordenación
        arr.extend(bucket)
    
    return arr

# Generar arreglo de 10 elementos con números aleatorios del 1 al 100
arr = [random.randint(1, 100) for _ in range(10)]
print("Arreglo original:", arr)

# Ordenar con bucket sort
arr_ordenado = bucket_sort(arr.copy())
print("Arreglo ordenado:", arr_ordenado)