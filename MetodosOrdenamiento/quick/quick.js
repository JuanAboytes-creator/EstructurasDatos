function quickSort(arr){
    if(arr.length <= 1){
        return arr;
    }

    const pivot = arr[Math.floor(arr.length /2)];

    const left = [];
    const middle = [];
    const right = [];

    for (let x of arr){
        if (x < pivot){
            left.push(x);
        }else if (x == pivot){
            middle.push(x);
        }else if (x > pivot){
            right.push(x);
        }
    }

    return [...quickSort(left), ...middle, ...quickSort(right)];
}

function main(){
    const arreglo = [];
    for (let i = 0; i<10; i++){
        arreglo.push(Math.floor(Math.random() * 100)+1);
    }

    console.log("Arreglo original");
    console.log(arreglo.join(" "));

    const arregloOrdenado = quickSort(arreglo);

    console.log("\nArreglado");
    console.log(arregloOrdenado.join(" "));
}

main();