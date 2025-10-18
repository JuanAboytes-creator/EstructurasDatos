function bucketSort(arr) {
    if (arr.length === 0) return arr;
    
    // Encontrar valor máximo y mínimo
    let minVal = Math.min(...arr);
    let maxVal = Math.max(...arr);
    
    // Crear buckets
    let bucketCount = arr.length;
    let buckets = Array.from({length: bucketCount}, () => []);
    
    // Distribuir elementos en los buckets
    for (let num of arr) {
        let index = Math.floor((num - minVal) / (maxVal - minVal + 1) * bucketCount);
        buckets[index].push(num);
    }
    
    // Ordenar cada bucket e insertar en el array original
    let result = [];
    for (let bucket of buckets) {
        bucket.sort((a, b) => a - b);
        result.push(...bucket);
    }
    
    return result;
}

// Generar arreglo de 10 elementos con números aleatorios del 1 al 100
let arr = Array.from({length: 10}, () => Math.floor(Math.random() * 100) + 1);
console.log("Arreglo original:", arr);

// Ordenar con bucket sort
let arrOrdenado = bucketSort([...arr]);
console.log("Arreglo ordenado:", arrOrdenado);