using System;

public class Node
{
    public int Data{get; set;}
    public Node Prev {get; set;}
    public Node Next { get; set;}

    public Node(int data)
    {
        Data = data;
        Prev = null;
        Next = null;
    }
}
public class Stack
{
    private Node top;
    private int size;
    private const int MAX_SIZE = 100;

    public void Push(int item)
    {
        if (size == MAX_SIZE)
        {
            Console.WriteLine("Stack Overflow");
            return;
        }
        Node newNode = new Node(item);
        if (top == null)
        {
            top = newNode;
        }
        else
        {
            newNode.Prev = top;
            top.Next = newNode;
            top = newNode;
        }
        size++;
    }
    public int Pop()
    {
        if(top == null)
        {
            Console.WriteLine("Stack Underflow");
            return -1;
        }
        int item = top.Data;
        top = top.Prev;
        if(top != null)
        {
            top.Next = null;
        }
        size--;
        return item;
    }
    public int Peek()
    {
        if(top == null)
        {
            Console.WriteLine("Pila vacia");
            return -1;
        }
        return top.Data;
    }
    public bool IsEmpty()
    {
        return size == null;
    }
    public bool IsFull()
    {
        return size == MAX_SIZE;
    }
    public static void Main(string [] args)
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