const readline = require('readline');

function insertionSort(arr) {
    // Recorremos desde el segundo elemento hasta el final
    for (let i = 1; i < arr.length; i++) {
        let key = arr[i];  // Elemento actual a insertar
        let j = i - 1;
        
        // Movemos los elementos mayores que key una posición adelante
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        
        // Insertamos key en la posición correcta
        arr[j + 1] = key;
    }
    
    return arr;
}

function main() {
    const rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout
    });
    
    function askTamaño() {
        rl.question('Ingrese el tamaño del arreglo: ', (input) => {
            const tamaño = parseInt(input);
            
            if (isNaN(tamaño) || tamaño <= 0) {
                console.log('Por favor, ingrese un número positivo mayor que 0.');
                askTamaño();
                return;
            }
            
            // Generar arreglo con números aleatorios
            const arreglo = [];
            for (let i = 0; i < tamaño; i++) {
                arreglo.push(Math.floor(Math.random() * 100) + 1);  // Números entre 1 y 100
            }
            
            console.log(`\nArreglo original: [${arreglo.join(', ')}]`);
            
            const arregloOrdenado = insertionSort([...arreglo]);  // Copia del arreglo
            
            console.log(`Arreglo ordenado: [${arregloOrdenado.join(', ')}]`);
            
            rl.close();
        });
    }
    
    askTamaño();
}

main();