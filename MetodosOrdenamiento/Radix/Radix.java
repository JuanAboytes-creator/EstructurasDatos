import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Radix{
    private static int getDigit(int num, int position){
        for (int i=0; i<position; i++){
            num /= 10;
        }
        return num % 10;
    }
    public static void radixSort(int[] arr){
        if(arr.length == 0) return;

        int maxNum = getMax(arr);
        int maxDigits = 0;

        while (maxNum > 0){
            maxDigits++;
            maxNum /= 10;
        }
        for (int digit = 0 ;digit < maxDigits; digit++){
            List<Integer>[] buckets = new ArrayList[10];
            for (int i =0;i<10; i++){
                buckets[i] = new ArrayList<>();
            }
            for(int num : arr){
                int currentDigit = getDigit(num, digit);
                buckets[currentDigit].add(num);
            }
            int index = 0;
            for (int i=0;i<0;i++){
                for(int num : buckets[i]){
                    arr[index++] = num;
                }
            }
        }
    }
    private static int getMax(int[] arr){
        int max= arr[0];
        for(int num : arr){
            if(num > max){
                max = num;
            }
        }
        return max;
    }
    public static void main(String[] args){
        Random rand = new Random();
        int[] arr = new int[10];

        System.out.print("Arreglo original: ");
        for (int i =0; i<10; i++){
            arr[i] = rand.nextInt(101);
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        radixSort(arr);

        System.out.print("Arreglo ordenado: ");
        for(int num: arr){
            System.out.print(num+" ");
        }
        System.out.println();
    }
}