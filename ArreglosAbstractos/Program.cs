using System;

namespace PersonaDemo
{
    // Clase abstracta base
    public abstract class PersonaAbstracta
    {
        public abstract double CalcularIMC();
        public abstract bool EsMayorDeEdad();
        public abstract void MostrarInformacion();
    }

    // Clase concreta
    public class Persona : PersonaAbstracta
    {
        private string nombre;
        private int edad;
        private double peso;
        private double altura;
        private string email;
        private string telefono;

        public Persona(string nombre, int edad, double peso, double altura, 
                      string email = null, string telefono = null)
        {
            this.nombre = nombre;
            this.edad = edad;
            this.peso = peso;
            this.altura = altura;
            this.email = email;
            this.telefono = telefono;
        }

        public override double CalcularIMC() => peso / (altura * altura);
        
        public override bool EsMayorDeEdad() => edad >= 18;

        public override void MostrarInformacion()
        {
            Console.Write($"{nombre}, {edad} años, IMC: {CalcularIMC():F2}");
            if (!string.IsNullOrEmpty(email)) Console.Write($", Email: {email}");
            if (!string.IsNullOrEmpty(telefono)) Console.Write($", Teléfono: {telefono}");
            Console.WriteLine();
        }

        public string ClasificarIMC()
        {
            double imc = CalcularIMC();
            if (imc < 18.5) return "Bajo peso";
            else if (imc < 25) return "Peso normal";
            else if (imc < 30) return "Sobrepeso";
            else return "Obesidad";
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("=== Demostración de Persona en C# ===");
            
            Persona persona1 = new Persona("Juan Pérez", 25, 70.5, 1.75, "juan@email.com");
            persona1.MostrarInformacion();
            Console.WriteLine("¿Es mayor de edad? " + (persona1.EsMayorDeEdad() ? "Sí" : "No"));
            Console.WriteLine("Clasificación IMC: " + persona1.ClasificarIMC());
        }
    }
}