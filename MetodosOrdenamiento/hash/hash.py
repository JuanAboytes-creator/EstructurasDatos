import random

def hash_sort(arr):
    if len(arr) == 0:
        return arr
    
    min_val = min(arr)
    max_val = max(arr)
    
    # Crear tabla hash
    hash_table = {}
    
    # Insertar elementos en la tabla hash
    for num in arr:
        if num in hash_table:
            hash_table[num] += 1
        else:
            hash_table[num] = 1
    
    # Reconstruir array ordenado
    sorted_arr = []
    for num in range(min_val, max_val + 1):
        if num in hash_table:
            sorted_arr.extend([num] * hash_table[num])
    
    return sorted_arr

# Generar arreglo de 10 elementos aleatorios
arr = [random.randint(1, 100) for _ in range(10)]
print("Array original:", arr)

# Ordenar con Hash Sort
sorted_arr = hash_sort(arr)
print("Array ordenado:", sorted_arr)