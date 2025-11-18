const MAX_SIZE = 100;
let stack = new Array(MAX_SIZE);
let top = -1;

function push(item) {
    if (top === MAX_SIZE - 1) {
        console.log("Stack Overflow");
        return;
    }
    stack[++top] = item;
}

function pop() {
    if (top === -1) {
        console.log("Stack Underflow");
        return -1;
    }
    return stack[top--];
}

function peek() {
    if (top === -1) {
        console.log("Pila vacia");
        return -1;
    }
    return stack[top];
}

function isEmpty() {
    return top === -1;
}

function isFull() {
    return top === MAX_SIZE - 1;
}

// Ejemplo de uso
push(10);
push(20);
push(30);

console.log("Elemento Superior: " + peek());
console.log("Extrae elemento: " + pop());
console.log("Elemento Superior: " + peek());