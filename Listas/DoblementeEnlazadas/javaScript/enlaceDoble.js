const readline = require('readline');

class Node {
    constructor(data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

class DoublyLinkedList {
    constructor() {
        this.head = null;
        this.rl = readline.createInterface({
            input: process.stdin,
            output: process.stdout
        });
    }

    async beginInsert() {
        const item = await this.question("\nIngrese valor: ");
        const ptr = new Node(parseInt(item));
        ptr.prev = null;
        ptr.next = this.head;
        
        if (this.head !== null)
            this.head.prev = ptr;
            
        this.head = ptr;
        console.log("\nNodo insertado");
    }

    async lastInsert() {
        const item = await this.question("\nIngrese valor: ");
        const ptr = new Node(parseInt(item));
        ptr.next = null;

        if (this.head === null) {
            ptr.prev = null;
            this.head = ptr;
        } else {
            let temp = this.head;
            while (temp.next !== null) {
                temp = temp.next;
            }
            ptr.prev = temp;
            temp.next = ptr;
        }
        console.log("\nNodo insertado");
    }

    async randomInsert() {
        const item = await this.question("\nIntroduzca el valor del elemento: ");
        const loc = await this.question("\nIntroduce la ubicacion despues de la cual deseas insertar: ");

        const ptr = new Node(parseInt(item));

        let temp = this.head;
        for (let i = 0; i < parseInt(loc); i++) {
            if (temp === null) {
                console.log("\nNo se puede insertar\n");
                return;
            }
            temp = temp.next;
        }

        if (temp !== null) {
            ptr.prev = temp;
            ptr.next = temp.next;
            temp.next = ptr;
            if (ptr.next !== null)
                ptr.next.prev = ptr;
            console.log("\nNodo insertado");
        }
    }

    beginDelete() {
        if (this.head === null) {
            console.log("\nLa lista esta vacia\n");
        } else {
            this.head = this.head.next;
            if (this.head !== null)
                this.head.prev = null;
            console.log("\nPrimer nodo eliminado\n");
        }
    }

    lastDelete() {
        if (this.head === null) {
            console.log("\nLa lista esta vacia");
        } else if (this.head.next === null) {
            this.head = null;
            console.log("\nSolo se elimino el unico nodo de la lista\n");
        } else {
            let ptr = this.head;
            while (ptr.next !== null) {
                ptr = ptr.next;
            }
            ptr.prev.next = null;
            console.log("\nUltimo nodo eliminado\n");
        }
    }

    async randomDelete() {
        const loc = await this.question("\nIntroduzca la ubicacion del nodo a eliminar: ");

        if (this.head === null) {
            console.log("\nLa lista esta vacia");
            return;
        }

        if (parseInt(loc) === 0) {
            this.beginDelete();
            return;
        }

        let ptr = this.head;
        for (let i = 0; i < parseInt(loc); i++) {
            if (ptr === null) {
                console.log("\nError: fuera de rango");
                return;
            }
            ptr = ptr.next;
        }

        if (ptr !== null) {
            ptr.prev.next = ptr.next;
            if (ptr.next !== null)
                ptr.next.prev = ptr.prev;
            console.log("\nNodo eliminado\n");
        }
    }

    async search() {
        if (this.head === null) {
            console.log("\nLista vacia\n");
        } else {
            const item = await this.question("\nIntroduce el elemento que deseas buscar: ");
            let ptr = this.head;
            let i = 0;
            let flag = false;

            while (ptr !== null) {
                if (ptr.data === parseInt(item)) {
                    console.log(`Elemento encontrado en la ubicacion ${i}`);
                    flag = true;
                }
                i++;
                ptr = ptr.next;
            }

            if (!flag) {
                console.log("\nElemento no encontrado\n");
            }
        }
    }

    display() {
        let ptr = this.head;

        if (ptr === null) {
            console.log("Nada que imprimir");
        } else {
            console.log("\nImprimiendo valores.......\n");
            while (ptr !== null) {
                console.log(ptr.data);
                ptr = ptr.next;
            }
        }
    }

    question(prompt) {
        return new Promise((resolve) => {
            this.rl.question(prompt, resolve);
        });
    }

    async close() {
        this.rl.close();
    }

    async main() {
        let choice = 0;
        
        while (choice !== 9) {
            console.log("\n\n=======================MENU PRINCIPAL=======================\n");
            console.log("\nElige una opcion de la siguiente lista ...\n");
            console.log("\n==============================================================\n");
            console.log("\n1. Insertar al inicio\n2. Insertar al final\n3. Insertar\n4. Eliminar el primero\n5. Eliminar el ultimo\n6. Eliminar nodo\n7. Buscar\n8. Mostrar\n9. Salir");
            
            const answer = await this.question("\nIngrese su opcion: ");
            choice = parseInt(answer);
            
            switch (choice) {
                case 1: await this.beginInsert(); break;
                case 2: await this.lastInsert(); break;
                case 3: await this.randomInsert(); break;
                case 4: this.beginDelete(); break;
                case 5: this.lastDelete(); break;
                case 6: await this.randomDelete(); break;
                case 7: await this.search(); break;
                case 8: this.display(); break;
                case 9: 
                    this.close();
                    process.exit(0);
                    break;
                default: console.log("Introdusca una opcion valida...."); break;
            }
        }
    }
}

// Ejecutar el programa
const list = new DoublyLinkedList();
list.main();