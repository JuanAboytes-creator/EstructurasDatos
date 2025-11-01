import random

def get_digit(num, position):
    for _ in range(position):
        num //= 10
    return num % 10

def radix_sort(arr):
    if not arr:
        return arr
    
    max_num = max(arr)
    max_digits = 0

    while max_num > 0:
        max_digits += 1
        max_num //= 10

    for digit in range(max_digits):
        buckets = [[] for _ in range(10)]

        for num in arr:
            current_digit = get_digit(num, digit)
            buckets[current_digit].append(num)

        arr = [num for bucket in buckets for num in bucket]

    return arr

arr = [random.randint(1,100) for _ in range(10)]

print("Arreglo original:"," ".join(map(str, arr)))

sorted_arr = radix_sort(arr.copy())

print("Arreglo ordenado:"," ".join(map(str, sorted_arr)))