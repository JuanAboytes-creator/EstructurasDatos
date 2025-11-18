const readline = require('readline');

class Nodo {
    constructor(valor) {
        this.dato = valor;
        this.siguiente = null;
        this.anterior = null;
    }
}

class Cola {
    constructor(capacidad) {
        this.frente = null;
        this.final = null;
        this.tamaño = 0;
        this.capacidad = capacidad;
    }
    
    insertar(elemento) {
        if (this.tamaño >= this.capacidad) {
            console.log("\nOVERFLOW");
            return false;
        }
        
        const nuevo = new Nodo(elemento);
        if (this.estaVacia()) {
            this.frente = this.final = nuevo;
        } else {
            this.final.siguiente = nuevo;
            nuevo.anterior = this.final;
            this.final = nuevo;
        }
        this.tamaño++;
        console.log("\nElemento insertado correctamente.\n");
        return true;
    }
    
    eliminar() {
        if (this.estaVacia()) {
            console.log("\nUnderflow");
            return false;
        }
        
        const elemento = this.frente.dato;
        if (this.frente === this.final) {
            this.frente = this.final = null;
        } else {
            this.frente = this.frente.siguiente;
            this.frente.anterior = null;
        }
        this.tamaño--;
        console.log(`\nElemento eliminado: ${elemento}\n`);
        return true;
    }
    
    mostrar() {
        if (this.estaVacia()) {
            console.log("\nLa cola esta vacia.\n");
        } else {
            console.log("\nElementos en la cola:");
            let actual = this.frente;
            while (actual !== null) {
                console.log(actual.dato);
                actual = actual.siguiente;
            }
        }
    }
    
    estaVacia() {
        return this.frente === null;
    }
}

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

function main() {
    const cola = new Cola(5);
    
    function mostrarMenu() {
        console.log("\n==========================MENU PRINCIPAL==========================");
        console.log("====================================================================");
        console.log("1. Insertar\n2. Eliminar\n3. Mostrar\n4. Salir");
        
        rl.question("Ingrese su opcion: ", (opcion) => {
            switch (opcion) {
                case "1":
                    rl.question("\nIngrese el elemento: ", (input) => {
                        const elemento = parseInt(input);
                        cola.insertar(elemento);
                        mostrarMenu();
                    });
                    break;
                case "2":
                    cola.eliminar();
                    mostrarMenu();
                    break;
                case "3":
                    cola.mostrar();
                    mostrarMenu();
                    break;
                case "4":
                    console.log("\nSaliendo del programa...\n");
                    rl.close();
                    break;
                default:
                    console.log("\nOpcion invalida. Intente nuevamente\n");
                    mostrarMenu();
                    break;
            }
        });
    }
    
    mostrarMenu();
}

main();