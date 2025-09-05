function insertarYDesplazar(arreglo, indice, valor) {
    if (indice < 0 || indice >= arreglo.length) {
        throw new Error("Índice fuera de rango");
    }
    
    // Crear una copia del arreglo hasta el índice
    let resultado = arreglo.slice(0, indice);
    
    // Insertar el nuevo valor
    resultado.push(valor);
    
    // Agregar los elementos desde el índice original hasta el penúltimo
    resultado = resultado.concat(arreglo.slice(indice, arreglo.length - 1));
    
    return resultado;
}

// Uso
const arreglo = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

console.log("Arreglo original:", arreglo);
console.log("Inserta el 99 en el indice 5");

try {
    const resultado = insertarYDesplazar(arreglo, 5, 99);
    console.log("Resultado:", resultado);
} catch (error) {
    console.log("Error:", error.message);
}