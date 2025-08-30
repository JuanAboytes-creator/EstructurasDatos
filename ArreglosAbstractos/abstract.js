// Clase abstracta (simulada en JavaScript)
class PersonaAbstracta {
    constructor() {
        if (new.target === PersonaAbstracta) {
            throw new Error("No se puede instanciar una clase abstracta");
        }
    }

    // Métodos abstractos (deben ser implementados por las clases hijas)
    calcularIMC() {
        throw new Error("Método abstracto: debe ser implementado");
    }

    esMayorDeEdad() {
        throw new Error("Método abstracto: debe ser implementado");
    }

    mostrarInformacion() {
        throw new Error("Método abstracto: debe ser implementado");
    }
}

// Clase concreta que extiende la clase abstracta
class Persona extends PersonaAbstracta {
    constructor(nombre, edad, peso, altura, email = null, telefono = null) {
        super();
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;    // en kg
        this.altura = altura; // en metros
        this.email = email;
        this.telefono = telefono;
    }

    // Implementación de métodos abstractos
    calcularIMC() {
        return this.peso / (this.altura * this.altura);
    }

    esMayorDeEdad() {
        return this.edad >= 18;
    }

    mostrarInformacion() {
        let info = `${this.nombre}, ${this.edad} años, IMC: ${this.calcularIMC().toFixed(2)}`;
        
        if (this.email) {
            info += `, Email: ${this.email}`;
        }
        if (this.telefono) {
            info += `, Teléfono: ${this.telefono}`;
        }
        
        console.log(info);
    }

    // Método adicional para clasificación del IMC
    clasificarIMC() {
        const imc = this.calcularIMC();
        if (imc < 18.5) return "Bajo peso";
        else if (imc < 25) return "Peso normal";
        else if (imc < 30) return "Sobrepeso";
        else return "Obesidad";
    }

    // Getters (opcionales, pero buena práctica)
    getNombre() { return this.nombre; }
    getEdad() { return this.edad; }
    getPeso() { return this.peso; }
    getAltura() { return this.altura; }
    getEmail() { return this.email; }
    getTelefono() { return this.telefono; }

    // Setters (opcionales)
    setEdad(nuevaEdad) { this.edad = nuevaEdad; }
    setPeso(nuevoPeso) { this.peso = nuevoPeso; }
    setAltura(nuevaAltura) { this.altura = nuevaAltura; }
}

// Función de demostración
function demostrarPersona() {
    console.log("=== Demostración de Persona en JavaScript ===");
    
    // Crear objeto Persona
    const persona1 = new Persona("Juan Pérez", 25, 70.5, 1.75, "juan@email.com");
    persona1.mostrarInformacion();
    console.log("¿Es mayor de edad?", persona1.esMayorDeEdad() ? "Sí" : "No");
    console.log("Clasificación IMC:", persona1.clasificarIMC());
    
    // Crear otra persona con todos los datos
    const persona2 = new Persona("María García", 17, 65.0, 1.68, 
                               "maria@email.com", "+123456789");
    persona2.mostrarInformacion();
    console.log("¿Es mayor de edad?", persona2.esMayorDeEdad() ? "Sí" : "No");
    
    // Intentar instanciar la clase abstracta (debe fallar)
    try {
        const abstracta = new PersonaAbstracta();
    } catch (error) {
        console.log("✓ Correcto: No se puede instanciar clase abstracta -", error.message);
    }
}

// Versión alternativa más simple (sin clases abstractas)
class PersonaSimple {
    constructor(nombre, edad, peso, altura, email = null, telefono = null) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
        this.email = email;
        this.telefono = telefono;
    }

    calcularIMC() {
        return this.peso / (this.altura * this.altura);
    }

    esMayorDeEdad() {
        return this.edad >= 18;
    }

    mostrarInformacion() {
        let info = `${this.nombre}, ${this.edad} años, IMC: ${this.calcularIMC().toFixed(2)}`;
        if (this.email) info += `, Email: ${this.email}`;
        if (this.telefono) info += `, Teléfono: ${this.telefono}`;
        console.log(info);
    }

    clasificarIMC() {
        const imc = this.calcularIMC();
        if (imc < 18.5) return "Bajo peso";
        else if (imc < 25) return "Peso normal";
        else if (imc < 30) return "Sobrepeso";
        else return "Obesidad";
    }
}

// Ejecutar la demostración
demostrarPersona();

// Demo adicional con la versión simple
console.log("\n=== Demostración con PersonaSimple ===");
const persona3 = new PersonaSimple("Carlos López", 30, 80.0, 1.80);
persona3.mostrarInformacion();
console.log("IMC calculado:", persona3.calcularIMC().toFixed(2));