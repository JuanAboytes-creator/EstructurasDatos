public class busqueda {

    public static int busquedaLineal(int[] arr, int objetivo) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == objetivo) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arreglo = {5, 2, 8, 1, 9, 3};
        int objetivo = 8;
        
        int resultado = busquedaLineal(arreglo, objetivo);
        
        if (resultado != -1) {
            System.out.println("Elemento " + objetivo + " encontrado en el índice " + resultado);
        } else {
            System.out.println("Elemento " + objetivo + " no encontrado");
        }
    }
}
