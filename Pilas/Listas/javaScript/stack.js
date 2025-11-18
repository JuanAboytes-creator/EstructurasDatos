class Node {
    constructor(data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

class Stack {
    constructor() {
        this.top = null;
        this.size = 0;
        this.MAX_SIZE = 100;
    }

    push(item) {
        if (this.size === this.MAX_SIZE) {
            console.log("Stack Overflow");
            return;
        }
        
        const newNode = new Node(item);
        if (this.top === null) {
            this.top = newNode;
        } else {
            newNode.prev = this.top;
            this.top.next = newNode;
            this.top = newNode;
        }
        this.size++;
    }

    pop() {
        if (this.top === null) {
            console.log("Stack Underflow");
            return -1;
        }
        
        const item = this.top.data;
        this.top = this.top.prev;
        if (this.top !== null) {
            this.top.next = null;
        }
        this.size--;
        return item;
    }

    peek() {
        if (this.top === null) {
            console.log("Pila vacia");
            return -1;
        }
        return this.top.data;
    }

    isEmpty() {
        return this.top === null;
    }

    isFull() {
        return this.size === this.MAX_SIZE;
    }
}

// Ejemplo de uso
const stack = new Stack();
stack.push(10);
stack.push(20);
stack.push(30);

console.log("Elemento Superior: " + stack.peek());
console.log("Extrae elemento: " + stack.pop());
console.log("Elemento Superior: " + stack.peek());