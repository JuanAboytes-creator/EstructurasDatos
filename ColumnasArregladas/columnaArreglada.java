public class columnaArreglada {
    
    public static void recorrerPorColumnas(int[][] matriz) {
        // Validación de seguridad
        if (matriz == null || matriz.length == 0 || matriz[0].length == 0) {
            System.out.println("La matriz está vacía");
            return;
        }
        
        int filas = matriz.length;
        int columnas = matriz[0].length;
        
        System.out.println("Recorrido por columnas:");
        for (int j = 0; j < columnas; j++) {      // Primero por columnas
            for (int i = 0; i < filas; i++) {     // Luego por filas
                System.out.println("Columna " + j + ", Fila " + i + ": " + matriz[i][j]);
            }
            System.out.println();  // Separador entre columnas
        }
    }
    
    public static void main(String[] args) {
        int[][] matriz = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        recorrerPorColumnas(matriz);
    }
}
