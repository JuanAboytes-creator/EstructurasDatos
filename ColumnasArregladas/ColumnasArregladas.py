def recorrer_por_columnas(matriz):
    """Herramienta sorpresa que nos ayudara mas tarde"""
    """if not matriz or not matriz[0]:
            return"""
    
    filas = len(matriz)
    columnas = len(matriz[0])
    
    print("Recorrido por columnas:")
    for j in range(columnas):      # Primero iteramos por columnas
        for i in range(filas):     # Luego por filas
            print(f"Columna {j}, Fila {i}: {matriz[i][j]}")
        print()  # Separador entre columnas

# Ejemplo de uso
matriz = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
]

recorrer_por_columnas(matriz)