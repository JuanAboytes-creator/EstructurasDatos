
function busquedaLineal(arr, objetivo) {
    for (let i = 0; i < arr.length; i++) {
        if (arr[i] === objetivo) {
            return i;
        }
    }
    return -1;
}

const arreglo = [5, 2, 8, 1, 9, 3];
const objetivo = 8;

// Búsqueda
const resultado = busquedaLineal(arreglo, objetivo);

if (resultado !== -1) {
    console.log(`Elemento ${objetivo} encontrado en el índice ${resultado}`);
} else {
    console.log(`Elemento ${objetivo} no encontrado`);
}