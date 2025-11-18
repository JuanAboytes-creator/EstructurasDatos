#include <iostream>
#define MAX_SIZE 100

using namespace std;

struct Node {
    int data;
    Node* prev;
    Node* next;
};

Node* top = nullptr;
int currentSize = 0;

void push(int item) {
    if (currentSize == MAX_SIZE) {
        cout << "Stack Overflow\n";
        return;
    }
    
    Node* newNode = new Node;
    newNode->data = item;
    newNode->prev = nullptr;
    newNode->next = nullptr;
    
    if (top == nullptr) {
        top = newNode;
    } else {
        newNode->prev = top;
        top->next = newNode;
        top = newNode;
    }
    currentSize++;
}

int pop() {
    if (top == nullptr) {
        cout << "Stack Underflow\n";
        return -1;
    }
    
    int item = top->data;
    Node* temp = top;
    top = top->prev;
    if (top != nullptr) {
        top->next = nullptr;
    }
    delete temp;
    currentSize--;
    return item;
}

int peek() {
    if (top == nullptr) {
        cout << "Pila vacia\n";
        return -1;
    }
    return top->data;
}

bool isEmpty() {
    return top == nullptr;
}

bool isFull() {
    return currentSize == MAX_SIZE;
}

int main() {
    push(10);
    push(20);
    push(30);

    cout << "Elemento Superior: " << peek() << endl;
    cout << "Extrae elemento: " << pop() << endl;
    cout << "Elemento Superior: " << peek() << endl;
}