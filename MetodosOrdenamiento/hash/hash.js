function hashSort(arr) {
    if (arr.length === 0) return arr;
    
    const minVal = Math.min(...arr);
    const maxVal = Math.max(...arr);
    
    // Crear tabla hash
    const hashTable = {};
    
    // Insertar elementos en la tabla hash
    for (const num of arr) {
        if (hashTable[num]) {
            hashTable[num]++;
        } else {
            hashTable[num] = 1;
        }
    }
    
    // Reconstruir array ordenado
    const sortedArr = [];
    for (let num = minVal; num <= maxVal; num++) {
        if (hashTable[num]) {
            for (let i = 0; i < hashTable[num]; i++) {
                sortedArr.push(num);
            }
        }
    }
    
    return sortedArr;
}

// Generar arreglo de 10 elementos aleatorios
const arr = Array.from({length: 10}, () => Math.floor(Math.random() * 100) + 1);
console.log("Array original:", arr);

// Ordenar con Hash Sort
const sortedArr = hashSort(arr);
console.log("Array ordenado:", sortedArr);