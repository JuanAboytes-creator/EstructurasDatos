#include <iostream>
#include <cstdlib>

using namespace std;

struct node {
    struct node *prev;
    int data;
    struct node *next;
};
struct node *head;

void begin_Insert();
void last_Insert();
void random_Insert();
void begin_Delete();
void last_Delete();
void random_Delete();
void display();
void search();

int main(){
    int choice = 0;
    while(choice != 9){
        cout << "\n\n=======================MENU PRINCIPAL=======================\n";
        cout << "\nElige una opcion de la siguiente lista ...\n";
        cout << "\n==============================================================\n";
        cout << "\n1. Insertar al inicio\n2. Insertar al final\n3. Insertar\n4. Eliminar el primero\n5. Eliminar el ultimo\n6. Eliminar nodo\n7. Buscar\n8. Mostrar\n9. Salir";
        cout << "\nIngrese su opcion: ";
        cin >> choice;
        switch (choice){
            case 1:begin_Insert();break;
            case 2:last_Insert();break;
            case 3:random_Insert();break;
            case 4:begin_Delete();break;
            case 5:last_Delete();break;
            case 6:random_Delete();break;
            case 7:search();break;
            case 8:display();break;
            case 9:exit(0);break;
            default:cout << "Introdusca una opcion valida....";
        }
    }
}
void begin_Insert(){
    struct node *ptr, *temp;
    int item;

    ptr = (struct node*)malloc(sizeof(struct node*));
    if (ptr == NULL){
        cout << "\nOverflow";
    }
    else{
        cout << "\nIngrese valor: ";
        cin >> item;

        ptr->data= item;
        if (head == NULL){
            head = ptr;
        }
        ptr->next= head;
        ptr->next->prev = ptr;
        temp = head;
        while (temp->next != head){
            temp = temp->next;
        }
        temp->next = ptr;
        ptr->prev =temp;
        head = ptr;
        cout << "\nNodo insertado";
    }
}
void last_Insert(){
    struct node *ptr, *temp;
    int item;

    ptr = (struct node*)malloc(sizeof(struct node*));

    if(ptr == NULL){
        cout << "\nOverflow";
    }else{
        cout << "\nIngrese valor: "; cin >> item;
        ptr->data = item;

        if(head == NULL){
            ptr->prev = ptr;
            ptr->next = ptr;
            head = ptr;
            cout << "\nNodo insertado";
        }else{
            temp = head;
            temp->prev = ptr;
            while (temp->next != head){
                temp = temp->next;
            }
            temp->next = ptr;
            ptr->next = head;
            ptr->prev = temp;
            cout << "\nNodo insertado";
        }
    }
}
void random_Insert(){
    int i, loc, item;
    struct node *ptr, *temp;

    ptr = (struct node*)malloc(sizeof(struct node*));
    if(ptr == NULL){
        cout << "\nOverflow";
    }else{
        cout << "\nIntroduzta el valor del elemento: ";
        cin >> item;
        ptr->data = item;
        cout << "\nintroduce la ubicacion despues de la cual deseas insertar: "; cin >> loc;
        temp = head;
        for(i=0;i<loc;i++){
            temp = temp->next;
            if(temp == head){
                cout << "\nNo se puede insertar\n";
                return;
            }
        }
        ptr->prev = temp;
        ptr->next = temp->next;
        temp->next = ptr;
        ptr->next->prev = ptr;
        cout << "\nNodo insertado";
    }
}
void begin_Delete(){
    struct node *ptr, *temp;
    if(head == NULL){
        cout << "\nLa lista esta vacia\n";
    }else{
        ptr = head;
        head = ptr->next;
        temp = head;
        while (temp->next != ptr){
            temp = temp->next;
        }
        temp->next = head;
        ptr->next->prev = temp;
        delete ptr;
        cout << "\nNodo eliminado desde el principo ....\n";
    }
}
void last_Delete(){
    struct node *ptr, *temp;
    if(head == NULL){
        cout << "\nLa lista esta vacia";
    }else if(head->next == NULL){
        delete head;
        head = NULL;
        cout << "\nSolo se elimino el unico nodo de la lista\n";
    }else{
        ptr = head;
        ptr->prev = ptr->prev->prev;
        while(ptr->next != head){
            temp = ptr;
            ptr = ptr->next;
        }
        temp->next = head;
        delete ptr;
        cout << "\nUltimo nodo eliminado\n";
    }
}
void random_Delete(){
    struct node *ptr, *temp;
    int loc;
    cout << "\nIntroduzca la ubicacion del nodo a eliminar: ";
    cin >> loc;

    ptr = head;

    for (int i = 0;i<loc; i++){
        temp = ptr;
        ptr = ptr->next;

        if(ptr == head){
            cout << "\nError: fuera de rango";
            return;
        }
    }
    temp->next = ptr->next;
    ptr->next->prev = temp;
    delete ptr;
    cout << "\nNodo eliminado\n";
}
void search(){
    struct node *ptr;
    int item, i= 0;
    bool flag = false;
    ptr = head;
    if(ptr == NULL){
        cout << "\nLusta vacia\n";
    }else{
        cout << "\nInroduce el elemento que deseas buscar: "; cin >> item;
        do{
            if (ptr->data == item){
                cout <<"Elemento encontrado en la ubicacion "<< i;
                flag = true;
            }
            i++;
            ptr = ptr->next;
        }while(ptr != head);
        if (!flag){
            cout << "\nElemento no encontrado\n";
        }
    }
}
void display(){
    struct node *ptr;
    ptr = head;

    if(ptr == NULL){
        cout << "Nada que imprimir";
    }else{
        cout << "\nImprimiendo valores.......\n";
        do{
            cout << endl << ptr->data;
            ptr = ptr->next;
        }while(ptr != head);
    }
}