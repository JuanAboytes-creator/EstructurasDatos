import java.util.Arrays;
import java.util.Random;

public class merge{
    public static void mergeSort(int[] arr){
        if(arr.length > 1){
            int mid = arr.length / 2;
            int[] leftHalf =Arrays.copyOfRange(arr, 0, mid);
            int[] rightHalf = Arrays.copyOfRange(arr, mid, arr.length);

            mergeSort(leftHalf);
            mergeSort(rightHalf);

            int i=0,j=0,k=0;

            while(i< leftHalf.length && j< rightHalf.length){
                if (leftHalf[i] < rightHalf[j]){
                    arr[k]=leftHalf[i];
                    i++;
                }else{
                    arr[k] = rightHalf[j];
                    j++;
                }
                k++;
            }
            while (i < leftHalf.length){
                arr[k] = leftHalf[i];
                i++;
                k++;
            }
            while (j < rightHalf.length){
                arr[k] = rightHalf[j];
                j++;
                k++;
            }
        }
    }
    public static void main(String[] args){
        Random random = new Random();
        int[]arreglo = new int[10];
        for(int i = 0;i<10;i++){
            arreglo[i] = random.nextInt(101);
        }
        System.out.println("Arreglo original:");
        System.out.println(Arrays.toString(arreglo));

        mergeSort(arreglo);

        System.out.println("\nArreglo ordenado:");
        System.out.println(Arrays.toString(arreglo));
    }
}