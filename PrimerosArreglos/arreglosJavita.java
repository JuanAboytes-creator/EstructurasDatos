public class arreglosJavita {
    public static void main(String[] args) {
        // Versión simple de arreglos en Java
        int[] arreglo = {1, 2, 3, 4, 5};
        
        System.out.print("Arreglo inicial: ");
        for (int i = 0; i < arreglo.length; i++) {
            System.out.print(arreglo[i] + " ");
        }
        System.out.println();
        
        System.out.println("Remplazando el numero " + arreglo[1] + " por 99");
        arreglo[1] = 99;
        
        System.out.print("Arreglo después del reemplazo: ");
        for (int i = 0; i < arreglo.length; i++) {
            System.out.print(arreglo[i] + " ");
        }
        System.out.println();
    }
}
