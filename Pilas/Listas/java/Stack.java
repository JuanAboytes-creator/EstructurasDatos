class Node {
    int data;
    Node prev;
    Node next;
    
    public Node(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

public class Stack {
    private static final int MAX_SIZE = 100;
    private Node top;
    private int size;
    
    public void push(int item) {
        if (size == MAX_SIZE) {
            System.out.println("Stack Overflow");
            return;
        }
        
        Node newNode = new Node(item);
        if (top == null) {
            top = newNode;
        } else {
            newNode.prev = top;
            top.next = newNode;
            top = newNode;
        }
        size++;
    }
    
    public int pop() {
        if (top == null) {
            System.out.println("Stack Underflow");
            return -1;
        }
        
        int item = top.data;
        top = top.prev;
        if (top != null) {
            top.next = null;
        }
        size--;
        return item;
    }
    
    public int peek() {
        if (top == null) {
            System.out.println("Pila vacia");
            return -1;
        }
        return top.data;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    
    public boolean isFull() {
        return size == MAX_SIZE;
    }
    
    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Elemento Superior: " + stack.peek());
        System.out.println("Extrae elemento: " + stack.pop());
        System.out.println("Elemento Superior: " + stack.peek());
    }
}