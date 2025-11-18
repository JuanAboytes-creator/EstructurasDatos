MAX_SIZE = 100
stack = [0] * MAX_SIZE
top = -1

def push(item):
    global top
    if top == MAX_SIZE - 1:
        print("Stack Overflow")
        return
    top += 1
    stack[top] = item

def pop():
    global top
    if top == -1:
        print("Stack Underflow")
        return -1
    item = stack[top]
    top -= 1
    return item

def peek():
    if top == -1:
        print("Pila vacia")
        return -1
    return stack[top]

def is_empty():
    return top == -1

def is_full():
    return top == MAX_SIZE - 1

# Ejemplo de uso
if __name__ == "__main__":
    push(10)
    push(20)
    push(30)

    print(f"Elemento Superior: {peek()}")
    print(f"Extrae elemento: {pop()}")
    print(f"Elemento Superior: {peek()}")