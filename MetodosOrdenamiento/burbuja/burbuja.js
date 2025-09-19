const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
});

function ordenamientoBurbuja(arr) {
    let n = arr.length;
    for (let i = 0; i < n; i++) {
        for (let j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                [arr[j], arr[j + 1]] = [arr[j + 1], arr[j]];
            }
        }
    }
    return arr;
}

readline.question('Ingrese el tamaño del arreglo: ', (tamaño) => {
    // Crear arreglo con valores aleatorios
    let arreglo = [];
    for (let i = 0; i < tamaño; i++) {
        arreglo.push(Math.floor(Math.random() * 100) + 1);
    }

    console.log("Arreglo original:", arreglo);

    // Ordenar el arreglo (usando copia)
    let arregloOrdenado = ordenamientoBurbuja([...arreglo]);

    console.log("Arreglo ordenado:", arregloOrdenado);

    readline.close();
});