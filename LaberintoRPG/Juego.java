package laberinto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Juego extends JPanel implements ActionListener, KeyListener {
    private static final int TAMANIO_CELDA = 40;
    private static final int COLUMNAS_LABERINTO = 20;
    private static final int FILAS_LABERINTO = 20;
    private static final int ANCHO_LABERINTO = COLUMNAS_LABERINTO * TAMANIO_CELDA;
    private static final int ALTO_LABERINTO = FILAS_LABERINTO * TAMANIO_CELDA;
    private static final int MARGEN_UI = 350;
    private static final int ANCHO = ANCHO_LABERINTO + MARGEN_UI;
    private static final int ALTO = ALTO_LABERINTO;
    
    private Laberinto laberinto;
    private Jugador jugador;
    private Timer timer;
    private int nivel;
    private boolean juegoActivo;
    private boolean juegoCompletado;
    
    public Juego() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        
        this.nivel = 1;
        this.juegoActivo = true;
        this.juegoCompletado = false;
        // Usar 20x20 para que coincida con el tamaño visual
        this.laberinto = new Laberinto(COLUMNAS_LABERINTO, FILAS_LABERINTO, nivel);
        this.jugador = new Jugador();
        this.timer = new Timer(16, this);
        
        System.out.println("🎮 Juego inicializado - Nivel " + nivel);
        System.out.println("📐 Ventana: " + ANCHO + "x" + ALTO + " (Laberinto: " + ANCHO_LABERINTO + "x" + ALTO_LABERINTO + ")");
    }
    
    public void iniciar() {
        timer.start();
        requestFocusInWindow();
    }
    
    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    if (juegoCompletado) {
        dibujarVictoria(g2d);
        return;
    }
    
    if (!juegoActivo) {
        dibujarGameOver(g2d);
        return;
    }
    
    // Dibujar área del laberinto (tamaño exacto 20x20 celdas)
    g2d.setColor(Color.DARK_GRAY);
    g2d.fillRect(0, 0, ANCHO_LABERINTO, ALTO_LABERINTO);
    
    // Dibujar laberinto (solo lo visible)
    laberinto.dibujar(g2d, TAMANIO_CELDA, jugador.getX(), jugador.getY());
    
    // Dibujar jugador
    jugador.dibujar(g2d, TAMANIO_CELDA);
    
    // Dibujar UI
    dibujarInterfazUsuario(g2d);
}
    
    private void dibujarInterfazUsuario(Graphics g) {
    int uiX = ANCHO_LABERINTO + 20; // Empezar justo después del laberinto
    
    // Fondo UI
    g.setColor(new Color(40, 40, 40));
    g.fillRect(ANCHO_LABERINTO, 0, MARGEN_UI, ALTO);
    
    // Borde UI
    g.setColor(new Color(100, 100, 100));
    g.drawRect(ANCHO_LABERINTO, 0, MARGEN_UI - 1, ALTO - 1);
    
    // Título
    g.setColor(Color.YELLOW);
    g.setFont(new Font("Arial", Font.BOLD, 24));
    g.drawString("LABERINTO INTELIGENTE", uiX, 40);
    
    // Línea separadora
    g.setColor(new Color(100, 100, 100));
    g.drawLine(uiX - 10, 60, ANCHO - 20, 60);
    
    // Información del juego
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 18));
    g.drawString("INFORMACIÓN DEL JUEGO", uiX, 90);
    
    g.setFont(new Font("Arial", Font.PLAIN, 16));
    g.drawString("Nivel: " + nivel, uiX, 120);
    
    // Vidas con corazones
    g.drawString("Vidas: ", uiX, 150);
    for (int i = 0; i < jugador.getVidas(); i++) {
        g.setColor(Color.RED);
        g.drawString("❤", uiX + 60 + (i * 20), 150);
    }
    
    g.setColor(Color.WHITE);
    g.drawString("Llaves: " + jugador.getLlavesObtenidas() + "/" + laberinto.getLlavesNecesarias(), uiX, 180);
    g.drawString("Puertas Abiertas: " + jugador.getPuertasAbiertas(), uiX, 210);
    g.drawString("Pasos: " + jugador.getPasos(), uiX, 240);
    g.drawString("Casillas Descubiertas: " + laberinto.getCasillasDescubiertas(), uiX, 270);
    
    // Leyendas
    g.setColor(Color.YELLOW);
    g.setFont(new Font("Arial", Font.BOLD, 18));
    g.drawString("LEYENDAS:", uiX, 320);
    
    g.setFont(new Font("Arial", Font.PLAIN, 16));
    g.setColor(Color.GREEN);
    g.drawString("■ Salida", uiX, 350);
    g.setColor(Color.RED);
    g.drawString("■ Trampa (visible después de activar)", uiX, 380);
    g.setColor(Color.ORANGE);
    g.drawString("■ Llave", uiX, 410);
    g.setColor(Color.CYAN);
    g.drawString("■ Poción", uiX, 440);
    g.setColor(Color.MAGENTA);
    g.drawString("■ Puerta", uiX, 470);
    g.setColor(Color.RED);
    g.drawString("● Jugador", uiX, 500);
    
    // Mecánicas explicadas
    g.setColor(Color.YELLOW);
    g.setFont(new Font("Arial", Font.BOLD, 18));
    g.drawString("MECÁNICAS:", uiX, 540);
    
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.PLAIN, 14));
    String[] mecanicas = {
        "- Trampas son invisibles hasta activarlas",
        "- Puertas requieren llaves para abrir",
        "- Pociones recuperan 1 vida",
        "- Salida está tras una puerta",
        "- Radio de visión: 1 casilla alrededor",
        "- Usa WASD/Flechas para moverte"
    };
    
    for (int i = 0; i < mecanicas.length; i++) {
        g.drawString(mecanicas[i], uiX, 570 + (i * 25));
    }
    
    // Controles
    g.setColor(Color.YELLOW);
    g.setFont(new Font("Arial", Font.BOLD, 18));
    g.drawString("CONTROLES:", uiX, 720);
    
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.PLAIN, 14));
    g.drawString("WASD/Flechas: Moverse", uiX, 750);
    g.drawString("R: Reiniciar nivel", uiX, 775);
    //g.drawString("N: Siguiente nivel (test)", uiX, 800);
}
    
    private void dibujarGameOver(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, ANCHO, ALTO);
        
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("PERDISTE", ANCHO/2 - 100, ALTO/2 - 50);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Presiona R para reiniciar", ANCHO/2 - 120, ALTO/2 + 50);
    }
    
    private void dibujarVictoria(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, ANCHO, ALTO);
        
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("¡COMPLETASTE EL JUEGO!", ANCHO/2 - 240, ALTO/2 - 50);
        
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("FELICIDADES", ANCHO/2 - 100, ALTO/2 + 20);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Presiona R para jugar de nuevo", ANCHO/2 - 160, ALTO/2 + 80);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    if (juegoCompletado || !juegoActivo) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            reiniciarJuego();
        }
        return;
    }
    
    int key = e.getKeyCode();
    int dx = 0, dy = 0;
    
    switch (key) {
        case KeyEvent.VK_W:
        case KeyEvent.VK_UP:
            dy = -1;
            break;
        case KeyEvent.VK_S:
        case KeyEvent.VK_DOWN:
            dy = 1;
            break;
        case KeyEvent.VK_A:
        case KeyEvent.VK_LEFT:
            dx = -1;
            break;
        case KeyEvent.VK_D:
        case KeyEvent.VK_RIGHT:
            dx = 1;
            break;
        case KeyEvent.VK_R:
            reiniciarNivel();
            return;
        case KeyEvent.VK_N:
            // Solo para testing
            siguienteNivel();
            return;
    }
    
    if (dx != 0 || dy != 0) {
        int nuevoX = jugador.getX() + dx;
        int nuevoY = jugador.getY() + dy;
        
        // VERIFICAR PUERTAS ANTES DE MOVER
        if (laberinto.esPuerta(nuevoX, nuevoY) && !laberinto.esPuertaAbierta(nuevoX, nuevoY)) {
            if (jugador.tieneLlaves()) {
                // ABRIR PUERTA Y LUEGO MOVER
                jugador.usarLlave();
                laberinto.usarLlave();
                laberinto.abrirPuerta(nuevoX, nuevoY);
                System.out.println("🚪 ¡Puerta abierta con llave! Llaves restantes: " + jugador.getLlavesObtenidas());
                jugador.mover(dx, dy, laberinto); // Moverse después de abrir
                verificarEventosCasilla();
                repaint(); // Redibujar inmediatamente
            } else {
                System.out.println("🔒 Puerta cerrada - Necesitas una llave. Tienes: " + jugador.getLlavesObtenidas());
            }
        } else {
            // Movimiento normal (no es puerta cerrada)
            boolean seMovio = jugador.mover(dx, dy, laberinto);
            if (seMovio) {
                verificarEventosCasilla();
            }
        }
    }
}
    
    private void verificarEventosCasilla() {
    int x = jugador.getX();
    int y = jugador.getY();
    
    // Verificar trampas (siempre primero)
    if (laberinto.esTrampa(x, y) && !laberinto.esTrampaActivada(x, y)) {
        jugador.perderVida();
        laberinto.activarTrampa(x, y);
        System.out.println("💥 ¡Trampa! Vidas restantes: " + jugador.getVidas());
        
        if (jugador.getVidas() <= 0) {
            gameOver();
            return;
        }
    }
    
    // Verificar salida
    if (laberinto.esSalida(x, y)) {
        if (nivel >= 5) {
            juegoCompletado();
        } else {
            siguienteNivel();
        }
        return;
    }
    
    // Verificar llaves
    if (laberinto.esLlave(x, y) && !laberinto.esLlaveRecogida(x, y)) {
        jugador.recogerLlave();
        laberinto.recogerLlave(x, y);
        System.out.println("🔑 ¡Llave recogida! Total: " + jugador.getLlavesObtenidas());
    }
    
    // Verificar pociones
    if (laberinto.esPocion(x, y) && !laberinto.esPocionRecogida(x, y)) {
        jugador.recogerPocion();
        laberinto.recogerPocion(x, y);
        //System.out.println("🧪 ¡Poción recogida! Vidas: " + jugador.getVidas());
    }
    
    // NOTA: Las puertas ahora se manejan en keyPressed() antes del movimiento
}
    
    private void siguienteNivel() {
        nivel++;
        if (nivel > 5) {
            juegoCompletado();
        } else {
            generarNuevoNivel();
            System.out.println("🎉 ¡Nivel " + (nivel-1) + " completado! Avanzando al nivel " + nivel);
        }
    }
    
    private void generarNuevoNivel() {
        laberinto = new Laberinto(20, 20, nivel);
        jugador.reiniciarParaNuevoNivel();
        juegoActivo = true;
        System.out.println("🔄 Generando nivel " + nivel);
        repaint();
        requestFocusInWindow();
    }
    
    private void reiniciarNivel() {
        generarNuevoNivel();
    }
    
    private void reiniciarJuego() {
        nivel = 1;
        juegoCompletado = false;
        juegoActivo = true;
        generarNuevoNivel();
    }
    
    private void gameOver() {
        juegoActivo = false;
        System.out.println("💀 GAME OVER - Reinicia para jugar de nuevo");
        repaint();
    }
    
    private void juegoCompletado() {
        juegoCompletado = true;
        System.out.println("🎊 ¡JUEGO COMPLETADO! - Felicidades");
        repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
}