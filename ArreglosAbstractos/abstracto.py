from abc import ABC, abstractmethod
from dataclasses import dataclass
from typing import Optional, List

# Tipo abstracto base
class PersonaAbstracta(ABC):
    @abstractmethod
    def calcular_imc(self) -> float:
        """Calcula el Índice de Masa Corporal"""
        pass
    
    @abstractmethod
    def es_mayor_de_edad(self) -> bool:
        """Verifica si es mayor de edad"""
        pass

# Implementación con dataclass (Python 3.7+)
@dataclass
class Persona(PersonaAbstracta):
    nombre: str
    edad: int
    peso: float  # en kg
    altura: float  # en metros
    email: Optional[str] = None
    telefono: Optional[str] = None
    
    def calcular_imc(self) -> float:
        return self.peso / (self.altura ** 2)
    
    def es_mayor_de_edad(self) -> bool:
        return self.edad >= 18
    
    def __str__(self) -> str:
        return f"{self.nombre}, {self.edad} años, IMC: {self.calcular_imc():.2f}"

# Uso
persona1 = Persona("Juan Pérez", 25, 70.5, 1.75, "juan@email.com")
print(persona1)
print(f"¿Es mayor de edad? {persona1.es_mayor_de_edad()}")