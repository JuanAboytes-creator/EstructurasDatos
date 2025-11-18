const readline = require('readline');

const MAXSIZE = 5;
let queue = new Array(MAXSIZE);
let front = -1, rear = -1;

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

function insertar() {
    if (rear === MAXSIZE - 1) {
        console.log("\nOVERFLOW");
        mostrarMenu();
        return;
    }

    rl.question("\nIngrese el elemento: ", (input) => {
        const elemento = parseInt(input);
        
        if (front === -1 && rear === -1) {
            front = rear = 0;
        } else {
            rear++;
        }
        queue[rear] = elemento;
        console.log("\nElemento insertado correctamente.\n");
        mostrarMenu();
    });
}

function eliminar() {
    if (front === -1 || front > rear) {
        console.log("\nUnderflow");
        mostrarMenu();
        return;
    }

    const elemento = queue[front];
    if (front === rear) {
        front = rear = -1;
    } else {
        front++;
    }
    console.log(`\nElemento eliminado: ${elemento}\n`);
    mostrarMenu();
}

function mostrar() {
    if (rear === -1 || front === -1 || front > rear) {
        console.log("\nLa cola esta vacia.\n");
    } else {
        console.log("\nElementos en la cola:");
        for (let i = front; i <= rear; i++) {
            console.log(queue[i]);
        }
    }
    mostrarMenu();
}

function mostrarMenu() {
    console.log("\n==========================MENU PRINCIPAL==========================");
    console.log("====================================================================");
    console.log("1. Insertar\n2. Eliminar\n3. Mostrar\n4. Salir");
    
    rl.question("Ingrese su opcion: ", (opcion) => {
        switch (opcion) {
            case "1": insertar(); break;
            case "2": eliminar(); break;
            case "3": mostrar(); break;
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

// Iniciar el programa
mostrarMenu();