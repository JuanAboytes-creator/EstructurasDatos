package laberinto;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        System.out.println("🚀 Iniciando Laberinto Inteligente Mejorado...");
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Laberinto Inteligente");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            
            Juego juego = new Juego();
            frame.add(juego);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            juego.iniciar();
        });
    }
}