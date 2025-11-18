class Node:
    def __init__(self, data):
        self.data = data
        self.prev = None
        self.next = None

class Stack:
    def __init__(self):
        self.top = None
        self.size = 0
        self.MAX_SIZE = 100
    
    def push(self, item):
        if self.size == self.MAX_SIZE:
            print("Stack Overflow")
            return
        
        new_node = Node(item)
        if self.top is None:
            self.top = new_node
        else:
            new_node.prev = self.top
            self.top.next = new_node
            self.top = new_node
        self.size += 1
    
    def pop(self):
        if self.top is None:
            print("Stack Underflow")
            return -1
        
        item = self.top.data
        self.top = self.top.prev
        if self.top is not None:
            self.top.next = None
        self.size -= 1
        return item
    
    def peek(self):
        if self.top is None:
            print("Pila vacia")
            return -1
        return self.top.data
    
    def is_empty(self):
        return self.top is None
    
    def is_full(self):
        return self.size == self.MAX_SIZE

# Ejemplo de uso
if __name__ == "__main__":
    stack = Stack()
    stack.push(10)
    stack.push(20)
    stack.push(30)

    print(f"Elemento Superior: {stack.peek()}")
    print(f"Extrae elemento: {stack.pop()}")
    print(f"Elemento Superior: {stack.peek()}")