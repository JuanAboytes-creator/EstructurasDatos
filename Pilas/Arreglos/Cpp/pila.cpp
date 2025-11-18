#include <iostream>
#define MAX_SIZE 100

using namespace std;

int stack[MAX_SIZE];
int top = -1;

void push(int item){
    if(top == MAX_SIZE - 1){
        cout << "Stack Overflow\n";
        return;
    }
    stack[++top] = item;
}
int pop(){
    if (top ==  -1){
        cout << "Stack Underflow\n";
        return -1;
    }
    return stack[top--];
}
int peek(){
    if (top == -1){
        cout << "Pila vacia\n";
        return -1;
    }
    return stack[top];
}
bool isEmpty(){
    return top == -1;
}
bool isFull(){
    return top == MAX_SIZE - 1;
}
int main(){
    push(10);
    push(20);
    push(30);

    cout << "Elemento Superior: " << peek() << endl;
    cout << "Extrae elemento: " << pop() << endl;
    cout << "Elemento Superior: " << peek() << endl;
}