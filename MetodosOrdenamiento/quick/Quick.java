import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Quick {

    public static List<Integer> quickSort(List<Integer> arr){
        if (arr.size() <= 1){
            return arr;
        }

        int pivot = arr.get(arr.size() / 2);

        List<Integer> left = new ArrayList<>();
        List<Integer> middle = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int x : arr){
            if (x < pivot){
                left.add(x);
            }else if (x == pivot){
                middle.add(x);
            }else if (x > pivot){
                right.add(x);
            }
        }

        List<Integer> sortedLeft = quickSort(left);
        List<Integer> sortedRight = quickSort(right);

        List<Integer> result = new ArrayList<>();
        result.addAll(sortedLeft);
        result.addAll(middle);
        result.addAll(sortedRight);

        return result;
    }

    public static void main(String[] args) {
        Random random = new Random();

        List<Integer> arreglo = new ArrayList<>();
        for (int i = 0; i < 10;i++){
            arreglo.add(random.nextInt(101));
        }

        System.out.println("Arreglo original:");
        System.out.println(String.join(" ", arreglo.stream().map(String::valueOf).toArray(String[]::new)));

        List<Integer> arregloOrdenado = quickSort(arreglo);

        System.out.println("\nArreglo ordenado:");
        System.out.println(String.join(" ", arregloOrdenado.stream().map(String::valueOf).toArray(String[]::new)));

    }
}