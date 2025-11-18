using System;

public class Stack
{
    private const int MAX_SIZE = 100;
    private int[] stack = new int[MAX_SIZE];
    private int top = -1;

    public void Push(int item)
    {
        if (top == MAX_SIZE - 1)
        {
            Console.WriteLine("Stack Overflow");
            return;
        }
        stack[++top] = item;
    }

    public int Pop()
    {
        if (top == -1)
        {
            Console.WriteLine("Stack Underflow");
            return -1;
        }
        return stack[top--];
    }

    public int Peek()
    {
        if (top == -1)
        {
            Console.WriteLine("Pila vacia");
            return -1;
        }
        return stack[top];
    }

    public bool IsEmpty()
    {
        return top == -1;
    }

    public bool IsFull()
    {
        return top == MAX_SIZE - 1;
    }

    public static void Main(string[] args)
    {
        Stack stack = new Stack();
        stack.Push(10);
        stack.Push(20);
        stack.Push(30);

        Console.WriteLine("Elemento Superior: " + stack.Peek());
        Console.WriteLine("Extrae elemento: " + stack.Pop());
        Console.WriteLine("Elemento Superior: " + stack.Peek());
    }
}