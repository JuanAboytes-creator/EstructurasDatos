// Clase abstracta base
abstract class PersonaAbstracta {
    // Métodos abstractos
    public abstract double calcularIMC();
    public abstract boolean esMayorDeEdad();
    public abstract void mostrarInformacion();
}

// Clase concreta que implementa la interfaz abstracta
class Persona extends PersonaAbstracta {
    // Campos privados
    private String nombre;
    private int edad;
    private double peso;
    private double altura;
    private String email;
    private String telefono;

    // Constructores
    public Persona(String nombre, int edad, double peso, double altura) {
        this(nombre, edad, peso, altura, null, null);
    }

    public Persona(String nombre, int edad, double peso, double altura, 
                  String email, String telefono) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
        this.email = email;
        this.telefono = telefono;
    }

    // Implementación de métodos abstractos
    @Override
    public double calcularIMC() {
        return peso / (altura * altura);
    }

    @Override
    public boolean esMayorDeEdad() {
        return edad >= 18;
    }

    @Override
    public void mostrarInformacion() {
        System.out.print(nombre + ", " + edad + " años, IMC: " + String.format("%.2f", calcularIMC()));
        if (email != null) System.out.print(", Email: " + email);
        if (telefono != null) System.out.print(", Teléfono: " + telefono);
        System.out.println();
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public double getPeso() { return peso; }
    public double getAltura() { return altura; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }

    // Método para clasificación del IMC
    public String clasificarIMC() {
        double imc = calcularIMC();
        if (imc < 18.5) return "Bajo peso";
        else if (imc < 25) return "Peso normal";
        else if (imc < 30) return "Sobrepeso";
        else return "Obesidad";
    }
}

// Clase principal - debe ser pública y coincidir con el nombre del archivo
public class abstracto {
    public static void main(String[] args) {
        System.out.println("=== Demostración de Persona en Java ===");
        
        Persona persona1 = new Persona("Juan Pérez", 25, 70.5, 1.75, "juan@email.com", null);
        persona1.mostrarInformacion();
        System.out.println("¿Es mayor de edad? " + (persona1.esMayorDeEdad() ? "Sí" : "No"));
        System.out.println("Clasificación IMC: " + persona1.clasificarIMC());
        
        Persona persona2 = new Persona("María García", 17, 65.0, 1.68, "maria@email.com", "+123456789");
        persona2.mostrarInformacion();
        System.out.println("¿Es mayor de edad? " + (persona2.esMayorDeEdad() ? "Sí" : "No"));
        
        PersonaAbstracta persona3 = new Persona("Carlos López", 30, 80.0, 1.80);
        persona3.mostrarInformacion();
        System.out.println("IMC calculado: " + String.format("%.2f", persona3.calcularIMC()));
    }
}