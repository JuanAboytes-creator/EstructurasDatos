using System;
using System.Collections.Generic;
using System.Linq;

class Radix
{
    static int GetDigit(int num, int position)
    {
        for (int i = 0; i < position; i++)
        {
            num /= 10;
        }
        return num % 10;
    }
    static void RadixSort(int[] arr)
    {
        if (arr.Length == 0) return;

        int maxNum = arr.Max();
        int maxDigits = 0;

        while (maxNum > 0)
        {
            maxDigits++;
            maxNum /= 10;
        }
        for (int digit = 0; digit < maxDigits; digit++)
        {
            List<int>[] buckets = new List<int>[10];
            for (int i = 0; i < 10; i++)
            {
                buckets[i] = new List<int>();
            }
            foreach (int num in arr)
            {
                int currentDigit = GetDigit(num, digit);
                buckets[currentDigit].Add(num);
            }
            int index = 0;
            for (int i = 0; i < 10; i++)
            {
                foreach (int num in buckets[i])
                {
                    arr[index++] = num;
                }
            }
        }
    }
    static void Main()
    {
        Random rand = new Random();
        int[] arr = new int[10];
        Console.Write("Arreglo original: ");
        for (int i = 0; i < 10; i++)
        {
            arr[i] = rand.Next(1, 101);
            Console.Write(arr[i] + " ");
        }
        Console.WriteLine();

        RadixSort(arr);

        Console.Write("Arreglo ordenado: ");
        foreach (int num in arr)
        {
            Console.Write(num + " ");
        }
        Console.WriteLine();
    }
}