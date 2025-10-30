package laberinto;

import java.awt.*;

public class Jugador {
    private int x, y;
    private int vidas;
    private int llavesObtenidas;
    private int puertasAbiertas;
    private int pasos;
    
    public Jugador() {
        reiniciarParaNuevoNivel();
    }
    
    public void reiniciarParaNuevoNivel() {
        this.x = 1;
        this.y = 1;
        this.llavesObtenidas = 0;
        this.puertasAbiertas = 0;
        this.pasos = 0;
        // Las vidas se mantienen entre niveles
        if (this.vidas == 0) {
            this.vidas = 5; // Vidas iniciales
        }
    }
    
    public boolean mover(int dx, int dy, Laberinto laberinto) {
        int nuevoX = x + dx;
        int nuevoY = y + dy;
        
        if (laberinto.puedeMoverse(nuevoX, nuevoY)) {
            this.x = nuevoX;
            this.y = nuevoY;
            this.pasos++;
            return true;
        }
        return false;
    }
    
    public void dibujar(Graphics g, int tamanioCelda) {
        // Dibujar sombra
        g.setColor(new Color(100, 0, 0));
        g.fillOval(x * tamanioCelda + 2, y * tamanioCelda + 2, 
                  tamanioCelda - 4, tamanioCelda - 4);
        
        // Dibujar jugador
        g.setColor(Color.RED);
        g.fillOval(x * tamanioCelda + 5, y * tamanioCelda + 5, 
                  tamanioCelda - 10, tamanioCelda - 10);
        
        // Contorno
        g.setColor(Color.YELLOW);
        g.drawOval(x * tamanioCelda + 5, y * tamanioCelda + 5, 
                  tamanioCelda - 10, tamanioCelda - 10);
    }
    
    public void perderVida() {
        if (vidas > 0) {
            vidas--;
        }
    }
    
    public void recogerLlave() {
        if (llavesObtenidas < 3) {
            llavesObtenidas++;
        }
    }
    
    public void usarLlave() {
        if (llavesObtenidas > 0) {
            llavesObtenidas--;
            puertasAbiertas++;
        }
    }
    
    public void recogerPocion() {
        // POCIONES AHORA SUMAN 1 VIDA SIN LÍMITE
        vidas++;
        System.out.println("🧪 ¡Poción recogida! Vidas: " + vidas + " (sin límite)");
    }
    
    public boolean tieneLlaves() {
        return llavesObtenidas > 0;
    }
    
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getVidas() { return vidas; }
    public int getLlavesObtenidas() { return llavesObtenidas; }
    public int getPuertasAbiertas() { return puertasAbiertas; }
    public int getPasos() { return pasos; }
    
    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }
}