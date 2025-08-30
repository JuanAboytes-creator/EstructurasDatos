#include <iostream>
#include <string>
#include <optional>
#include <memory>
#include <cmath>
#include <iomanip>

// Clase abstracta base
class PersonaAbstracta {
public:
    virtual ~PersonaAbstracta() = default;
    
    // Métodos abstractos (puros)
    virtual double calcularIMC() const = 0;
    virtual bool esMayorDeEdad() const = 0;
    
    // Método virtual para mostrar información
    virtual void mostrarInformacion() const = 0;
};

// Clase concreta que implementa la interfaz abstracta
class Persona : public PersonaAbstracta {
private:
    std::string nombre;
    int edad;
    double peso;    // en kg
    double altura;  // en metros
    std::optional<std::string> email;
    std::optional<std::string> telefono;

public:
    // Constructor
    Persona(std::string nombre, int edad, double peso, double altura,
            std::optional<std::string> email = std::nullopt,
            std::optional<std::string> telefono = std::nullopt)
        : nombre(std::move(nombre)), edad(edad), peso(peso), altura(altura),
          email(std::move(email)), telefono(std::move(telefono)) {}
    
    // Implementación de métodos abstractos
    double calcularIMC() const override {
        return peso / (altura * altura);
    }
    
    bool esMayorDeEdad() const override {
        return edad >= 18;
    }
    
    void mostrarInformacion() const override {
        std::cout << nombre << ", " << edad << " años, IMC: " 
                  << std::fixed << std::setprecision(2) << calcularIMC();
        
        if (email.has_value()) {
            std::cout << ", Email: " << email.value();
        }
        if (telefono.has_value()) {
            std::cout << ", Teléfono: " << telefono.value();
        }
        std::cout << std::endl;
    }
    
    // Getters y setters (opcionales, pero buena práctica)
    std::string getNombre() const { return nombre; }
    int getEdad() const { return edad; }
    double getPeso() const { return peso; }
    double getAltura() const { return altura; }
    
    void setEdad(int nuevaEdad) { edad = nuevaEdad; }
    void setPeso(double nuevoPeso) { peso = nuevoPeso; }
    void setAltura(double nuevaAltura) { altura = nuevaAltura; }
    
    // Método para clasificación del IMC
    std::string clasificarIMC() const {
        double imc = calcularIMC();
        if (imc < 18.5) return "Bajo peso";
        else if (imc < 25) return "Peso normal";
        else if (imc < 30) return "Sobrepeso";
        else return "Obesidad";
    }
};

// Función para demostrar el uso
void demostrarUsoPersona() {
    // Crear objeto Persona
    Persona persona1("Juan Pérez", 25, 70.5, 1.75, "juan@email.com");
    
    // Usar métodos
    persona1.mostrarInformacion();
    std::cout << "¿Es mayor de edad? " << (persona1.esMayorDeEdad() ? "Sí" : "No") << std::endl;
    std::cout << "Clasificación IMC: " << persona1.clasificarIMC() << std::endl;
    
    // Crear otra persona con todos los datos
    Persona persona2("María García", 17, 65.0, 1.68, "maria@email.com", "+123456789");
    persona2.mostrarInformacion();
    std::cout << "¿Es mayor de edad? " << (persona2.esMayorDeEdad() ? "Sí" : "No") << std::endl;
}

// Uso con punteros inteligentes (forma más común en C++)
void usoConSmartPointers() {
    std::unique_ptr<PersonaAbstracta> persona = 
        std::make_unique<Persona>("Carlos López", 30, 80.0, 1.80);
    
    persona->mostrarInformacion();
    std::cout << "IMC calculado: " << persona->calcularIMC() << std::endl;
}

int main() {
    std::cout << "=== Demostración de Persona en C++ ===" << std::endl;
    demostrarUsoPersona();
    
    std::cout << "\n=== Uso con Smart Pointers ===" << std::endl;
    usoConSmartPointers();
    
    return 0;
}