function selectionSort(arr) {
    const n = arr.length;
    
    for (let i = 0; i < n; i++) {
        let min_idx = i;
        for (let j = i + 1; j < n; j++) {
            if (arr[j] < arr[min_idx]) {
                min_idx = j;
            }
        }
        [arr[i], arr[min_idx]] = [arr[min_idx], arr[i]];
    }
    return arr;
}

function main() {
    const readline = require('readline').createInterface({
        input: process.stdin,
        output: process.stdout
    });
    
    function askQuestion(question) {
        return new Promise((resolve) => {
            readline.question(question, resolve);
        });
    }
    
    async function run() {
        let tamaño;
        while (true) {
            const input = await askQuestion('Ingrese el tamaño del arreglo: ');
            tamaño = parseInt(input);
            
            if (!isNaN(tamaño) && tamaño > 0) {
                break;
            } else {
                console.log('Por favor, ingrese un número positivo mayor que 0.');
            }
        }
        
        const arreglo = [];
        for (let i = 0; i < tamaño; i++) {
            arreglo.push(Math.floor(Math.random() * 100) + 1);
        }
        
        console.log(`\nArreglo original: [${arreglo.join(', ')}]`);
        
        const arreglo_ordenado = [...arreglo];
        selectionSort(arreglo_ordenado);
        
        console.log(`Arreglo ordenado: [${arreglo_ordenado.join(', ')}]`);
        
        readline.close();
    }
    
    run().catch(err => {
        console.error('Error:', err);
        readline.close();
    });
}

// Ejecutar el programa
if (require.main === module) {
    main();
}