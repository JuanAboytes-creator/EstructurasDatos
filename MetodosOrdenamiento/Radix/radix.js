function getDigit(num, position){
    for(let i=0; i<position;i++){
        num=Math.floor(num/10);
    }
    return num % 10;
}

function radixSort(arr){
    if(arr.length === 0)return arr;

    let maxNum = Math.max(...arr);
    let maxDigits = 0;

    while (maxNum > 0){
        maxDigits++;
        maxNum = Math.floor(maxNum / 10);
    }
    for(let digit = 0; digit < maxDigits; digit++){
        let buckets = Array.from({length: 10}, () => []);

        for(let num of arr){
            let currentDigit = getDigit(num, digit);
            buckets[currentDigit].push(num);
        }
        arr = [].concat(...buckets);
    }
    return arr;
}
let arr = Array.from({length:10}, () => Math.floor(Math.random()  * 101));

console.log("Arreglo original: ", arr.join(" "));

let sortedArr = radixSort([...arr]);

console.log("Arreglo ordenado:", sortedArr.join(" "));