using System;
using System.Collections.Generic;

class MergeFilo
{
    static void MergeSort(List<int> arr)
    {
        if (arr.Count > 1)
        {
            int mid = arr.Count / 2;
            List<int> leftHalf = arr.GetRange(0, mid);
            List<int> rightHalf = arr.GetRange(mid, arr.Count - mid);

            MergeSort(leftHalf);
            MergeSort(rightHalf);

            int i = 0, j = 0, k = 0;
            while (i < leftHalf.Count && j < rightHalf.Count)
            {
                if (leftHalf[i] < rightHalf[j])
                {
                    arr[k] = leftHalf[i];
                    i++;
                }
                else
                {
                    arr[k] = rightHalf[j];
                    j++;
                }
                k++;
            }
            while (i < leftHalf.Count)
            {
                arr[k] = leftHalf[i];
                i++;
                k++;
            }
            while (j < rightHalf.Count)
            {
                arr[k] = rightHalf[j];
                j++;
                k++;
            }
        }
    }
    static void Main()
    {
        Random random = new Random();
        List<int> arreglo = new List<int>();
        for (int i = 0; i < 10; i++)
        {
            arreglo.Add(random.Next(1, 100));
        }
        Console.WriteLine("Arreglo original:");
        Console.WriteLine(string.Join(" ", arreglo));

        MergeSort(arreglo);

        Console.WriteLine("\nArreglo ordenado: ");
        Console.WriteLine(string.Join(" ", arreglo));
    }
}