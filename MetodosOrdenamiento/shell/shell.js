function shellSort(arr) {
    const n = arr.length;
    
    for (let gap = Math.floor(n / 2); gap > 0; gap = Math.floor(gap / 2)) {
        for (let i = gap; i < n; i++) {
            const temp = arr[i];
            let j = i;
            
            while (j >= gap && arr[j - gap] > temp) {
                arr[j] = arr[j - gap];
                j -= gap;
            }
            arr[j] = temp;
        }
    }
}

// Generar arreglo de 10 elementos con números aleatorios del 1 al 100
const arr = Array.from({length: 10}, () => Math.floor(Math.random() * 100) + 1);
console.log("Arreglo original:", arr);

shellSort(arr);
console.log("Arreglo ordenado:", arr);