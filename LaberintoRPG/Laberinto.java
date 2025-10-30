package laberinto;

import java.awt.*;
import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.List;
//import java.util.Queue;

public class Laberinto {
    private int[][] celdas;
    private boolean[][] descubierto;
    private int ancho, alto;
    private Random random;
    private int nivel;
    private int casillasDescubiertas;
    
    // Constantes para tipos de celda
    public static final int VACIO = 0;
    public static final int PARED = 1;
    public static final int ENTRADA = 2;
    public static final int SALIDA = 3;
    public static final int TRAMPA = 4;
    public static final int LLAVE = 5;
    public static final int PUERTA = 6;
    public static final int POCION = 7;
    
    private boolean[][] trampasActivadas;
    private boolean[][] llavesRecogidas;
    private boolean[][] pocionesRecogidas;
    private boolean[][] puertasAbiertas;

    private int numSalasPociones;
    private int numLlavesNecesarias;
    
    public Laberinto(int ancho, int alto, int nivel) {
        this.ancho = ancho;
        this.alto = alto;
        this.nivel = nivel;
        this.random = new Random(System.currentTimeMillis());
        this.celdas = new int[ancho][alto];
        this.descubierto = new boolean[ancho][alto];
        this.trampasActivadas = new boolean[ancho][alto];
        this.llavesRecogidas = new boolean[ancho][alto];
        this.pocionesRecogidas = new boolean[ancho][alto];
        this.puertasAbiertas = new boolean[ancho][alto];
        this.casillasDescubiertas = 0;
        this.numSalasPociones = 1 + random.nextInt(3); // 1-3 salas de poción
        this.numLlavesNecesarias = numSalasPociones + 1; // Salas de poción + salida
        
        generarLaberinto();
    }
    
    public void generarLaberinto() {
    // Inicializar todo como vacío
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {
            celdas[x][y] = VACIO;
            descubierto[x][y] = false;
            trampasActivadas[x][y] = false;
            llavesRecogidas[x][y] = false;
            pocionesRecogidas[x][y] = false;
            puertasAbiertas[x][y] = false;
        }
    }
    
    // 1. Generar laberinto base
    generarLaberintoTradicional();
    
    // 2. Crear salas especiales
    crearSalasEspeciales();
    
    // 3. ASEGURAR CONECTIVIDAD COMPLETA
    asegurarConectividadCompleta();
    
    // 4. Verificar y reparar conexiones específicas
    verificarYRepararConexiones();
    
    // 5. Agregar elementos especiales
    agregarElementosEspeciales();
    
    // 6. Verificación final
    verificarConectividadFinal();
    
    System.out.println("🔧 Laberinto generado - Nivel " + nivel);
    System.out.println("   - Tamaño: " + ancho + "x" + alto);
    System.out.println("   - Salas de poción: " + numSalasPociones + " (generadas: " + contarPociones() + ")");
    System.out.println("   - Llaves necesarias: " + numLlavesNecesarias + " (generadas: " + contarLlaves() + ")");
    System.out.println("   - Trampas: " + contarTrampas() + " (nivel " + nivel + ")");
    System.out.println("   - Puertas: " + contarPuertas());
    System.out.println("   - Salida: " + (tieneSalida() ? "Sí" : "No"));
}
private void verificarConectividadFinal() {
    boolean[][] visitado = new boolean[ancho][alto];
    int componentes = 0;
    
    // Revisar TODO el grid
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {
            if (celdas[x][y] == VACIO && !visitado[x][y]) {
                componentes++;
                floodFill(x, y, visitado, new ArrayList<>());
            }
        }
    }
    
    System.out.println("   - Áreas conectadas: " + componentes + " (ideal: 1)");
    
    // VERIFICACIÓN ESPECIAL: Todas las puertas deben ser accesibles
    int puertasInaccesibles = 0;
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {
            if (celdas[x][y] == PUERTA) {
                if (!estaConectadoAlLaberinto(x, y)) {
                    puertasInaccesibles++;
                    System.out.println("   ⚠️  Puerta en (" + x + "," + y + ") es inaccesible");
                    // Intentar reconectar inmediatamente
                    conectarPuertaDirectamente(x, y);
                }
            }
        }
    }
    
    if (puertasInaccesibles > 0) {
        System.out.println("   🔧 " + puertasInaccesibles + " puertas reconectadas");
    }
    
    if (componentes > 1) {
        System.out.println("   ⚠️  Laberinto tiene " + componentes + " áreas desconectadas");
    }
}
private void generarLaberintoTradicional() {
    // ELIMINAR PAREDES EXTERNAS - todo empieza vacío
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {
            celdas[x][y] = VACIO;
        }
    }
    
    // Asegurar entrada
    celdas[1][1] = ENTRADA;
    
    // Crear laberinto (ahora puede usar TODO el espacio, incluyendo bordes)
    crearLaberintoConBacktracking();
    
    // Crear caminos alternativos
    crearCaminosAlternativos();
}
private void crearLaberintoConBacktracking() {
    // Inicializar TODO como paredes (incluyendo bordes)
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {
            celdas[x][y] = PARED;
        }
    }
    
    // Algoritmo de crecimiento de árbol (Prim's algorithm)
    List<int[]> paredes = new ArrayList<>();
    boolean[][] visitado = new boolean[ancho][alto];
    
    // Empezar desde la entrada (1,1)
    int startX = 1;
    int startY = 1;
    celdas[startX][startY] = VACIO;
    visitado[startX][startY] = true;
    
    // Agregar paredes adyacentes a la lista (AHORA INCLUYENDO BORDES)
    agregarParedes(paredes, startX, startY, visitado);
    
    while (!paredes.isEmpty()) {
        // Elegir una pared aleatoria
        int index = random.nextInt(paredes.size());
        int[] pared = paredes.remove(index);
        int x = pared[0];
        int y = pared[1];
        int direccion = pared[2]; // 0=derecha, 1=abajo, 2=izquierda, 3=arriba
        
        int opuestoX = x, opuestoY = y;
        switch (direccion) {
            case 0: opuestoX = x + 1; break; // Derecha
            case 1: opuestoY = y + 1; break; // Abajo
            case 2: opuestoX = x - 1; break; // Izquierda
            case 3: opuestoY = y - 1; break; // Arriba
        }
        
        // PERMITIR QUE LOS CAMINOS LLEGUEN HASTA LOS BORDES
        if (opuestoX >= 0 && opuestoX < ancho && opuestoY >= 0 && opuestoY < alto) {
            if (!visitado[opuestoX][opuestoY]) {
                // Hacer ambas celdas vacías
                celdas[x][y] = VACIO;
                celdas[opuestoX][opuestoY] = VACIO;
                visitado[opuestoX][opuestoY] = true;
                
                // Agregar nuevas paredes
                agregarParedes(paredes, opuestoX, opuestoY, visitado);
            }
        }
    }
    
    // Fase 2: Asegurar máxima conectividad
    asegurarConectividadCompleta();
}
private void agregarParedes(List<int[]> paredes, int x, int y, boolean[][] visitado) {
    // Derecha - PERMITIR HASTA EL BORDE DERECHO
    if (x + 1 < ancho && celdas[x + 1][y] == PARED) {
        paredes.add(new int[]{x + 1, y, 0});
    }
    // Abajo - PERMITIR HASTA EL BORDE INFERIOR
    if (y + 1 < alto && celdas[x][y + 1] == PARED) {
        paredes.add(new int[]{x, y + 1, 1});
    }
    // Izquierda - PERMITIR HASTA EL BORDE IZQUIERDO
    if (x - 1 >= 0 && celdas[x - 1][y] == PARED) {
        paredes.add(new int[]{x - 1, y, 2});
    }
    // Arriba - PERMITIR HASTA EL BORDE SUPERIOR
    if (y - 1 >= 0 && celdas[x][y - 1] == PARED) {
        paredes.add(new int[]{x, y - 1, 3});
    }
}
private void asegurarConectividadCompleta() {
    System.out.println("   🌐 Asegurando conectividad completa del laberinto...");
    
    // Paso 1: Identificar todas las áreas conectadas
    List<List<Point>> componentes = encontrarComponentesConectadas();
    
    if (componentes.size() <= 1) {
        System.out.println("   ✅ Laberinto ya está completamente conectado");
        return;
    }
    
    System.out.println("   🔍 Encontradas " + componentes.size() + " áreas desconectadas");
    
    // Paso 2: Conectar todas las áreas entre sí
    conectarTodasLasAreas(componentes);
    
    // Paso 3: Verificación final
    verificarConectividadFinal();
}
private List<List<Point>> encontrarComponentesConectadas() {
    boolean[][] visitado = new boolean[ancho][alto];
    List<List<Point>> componentes = new ArrayList<>();
    
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {
            // Solo considerar celdas transitables (VACIO, ENTRADA, etc.)
            if (esCeldaTransitableParaConectividad(x, y) && !visitado[x][y]) {
                List<Point> componente = new ArrayList<>();
                floodFillConectividad(x, y, visitado, componente);
                componentes.add(componente);
            }
        }
    }
    
    return componentes;
}
private boolean esCeldaTransitableParaConectividad(int x, int y) {
    // Considerar todas las celdas por las que se puede caminar
    return celdas[x][y] == VACIO || 
           celdas[x][y] == ENTRADA || 
           celdas[x][y] == SALIDA ||
           celdas[x][y] == TRAMPA ||
           celdas[x][y] == LLAVE ||
           celdas[x][y] == POCION ||
           (celdas[x][y] == PUERTA && puertasAbiertas[x][y]);
}
private void floodFillConectividad(int startX, int startY, boolean[][] visitado, List<Point> componente) {
    Stack<Point> stack = new Stack<>();
    stack.push(new Point(startX, startY));
    
    while (!stack.isEmpty()) {
        Point p = stack.pop();
        int x = p.x;
        int y = p.y;
        
        if (x < 0 || x >= ancho || y < 0 || y >= alto || visitado[x][y] || !esCeldaTransitableParaConectividad(x, y)) {
            continue;
        }
        
        visitado[x][y] = true;
        componente.add(p);
        
        // Agregar vecinos en 4 direcciones (no diagonales)
        stack.push(new Point(x + 1, y));
        stack.push(new Point(x - 1, y));
        stack.push(new Point(x, y + 1));
        stack.push(new Point(x, y - 1));
    }
}
private void conectarTodasLasAreas(List<List<Point>> componentes) {
    // Estrategia: conectar cada área con la siguiente en una cadena
    for (int i = 1; i < componentes.size(); i++) {
        System.out.println("   🔗 Conectando área " + i + " con área 0");
        
        List<Point> areaPrincipal = componentes.get(0);
        List<Point> areaSecundaria = componentes.get(i);
        
        // Encontrar el par de puntos más cercano entre las áreas
        Point[] puntosMasCercanos = encontrarPuntosMasCercanos(areaPrincipal, areaSecundaria);
        
        if (puntosMasCercanos != null) {
            Point puntoPrincipal = puntosMasCercanos[0];
            Point puntoSecundario = puntosMasCercanos[1];
            
            System.out.println("   📍 Puntos más cercanos: (" + puntoPrincipal.x + "," + puntoPrincipal.y + ") -> (" + puntoSecundario.x + "," + puntoSecundario.y + ")");
            
            // Crear camino seguro entre los puntos
            crearCaminoSeguroEntrePuntos(puntoPrincipal, puntoSecundario);
            
            // Después de conectar, actualizar el área principal para incluir la nueva área
            areaPrincipal.addAll(areaSecundaria);
        }
    }
}
private void crearCaminoSeguroEntrePuntos(Point p1, Point p2) {
    System.out.println("   🛣️  Creando camino seguro entre (" + p1.x + "," + p1.y + ") y (" + p2.x + "," + p2.y + ")");
    
    int x = p1.x;
    int y = p1.y;
    
    // Estrategia: moverse en forma de L (primero horizontal, luego vertical)
    // para evitar problemas diagonales y facilitar la protección de salas
    
    // Paso 1: Movimiento horizontal
    while (x != p2.x) {
        if (x < p2.x) x++;
        else x--;
        
        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            if (celdas[x][y] == PARED && esCeldaSeguraParaConexion(x, y)) {
                celdas[x][y] = VACIO;
                System.out.println("   ➡️  Roto pared en (" + x + "," + y + ") - Horizontal");
            }
        }
    }
    
    // Paso 2: Movimiento vertical
    while (y != p2.y) {
        if (y < p2.y) y++;
        else y--;
        
        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            if (celdas[x][y] == PARED && esCeldaSeguraParaConexion(x, y)) {
                celdas[x][y] = VACIO;
                System.out.println("   ⬇️  Roto pared en (" + x + "," + y + ") - Vertical");
            }
        }
    }
}

private boolean esCeldaSeguraParaConexion(int x, int y) {
    // Verificación MÁS ESTRICTA para conexiones entre áreas
    // Solo permitir romper paredes que NO estén cerca de salas
    
    // 1. No puede ser parte de una sala
    if (esParteDeSala(x, y)) {
        return false;
    }
    
    // 2. No puede estar adyacente a una sala (radio 2)
    for (int dx = -2; dx <= 2; dx++) {
        for (int dy = -2; dy <= 2; dy++) {
            int nx = x + dx;
            int ny = y + dy;
            
            if (nx >= 0 && nx < ancho && ny >= 0 && ny < alto) {
                if (celdas[nx][ny] == POCION || celdas[nx][ny] == SALIDA) {
                    return false;
                }
            }
        }
    }
    
    // 3. Verificar que no sea una pared crítica entre salas
    if (esParedCriticaEntreSalas(x, y)) {
        return false;
    }
    
    return true;
}

private boolean esParedCriticaEntreSalas(int x, int y) {
    // Verificar si esta pared es la única separación entre dos salas importantes
    int salasCercanas = 0;
    
    for (int dx = -3; dx <= 3; dx++) {
        for (int dy = -3; dy <= 3; dy++) {
            int nx = x + dx;
            int ny = y + dy;
            
            if (nx >= 0 && nx < ancho && ny >= 0 && ny < alto) {
                if (celdas[nx][ny] == POCION || celdas[nx][ny] == SALIDA) {
                    salasCercanas++;
                    if (salasCercanas >= 2) {
                        return true; // Demasiadas salas cerca, podría ser crítica
                    }
                }
            }
        }
    }
    
    return false;
}
private void verificarYRepararConexiones() {
    System.out.println("   🔎 Verificando conexiones finales...");
    
    // Verificar que todas las puertas sean accesibles
    int puertasInaccesibles = 0;
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {
            if (celdas[x][y] == PUERTA && !estaConectadoAlLaberinto(x, y)) {
                puertasInaccesibles++;
                System.out.println("   ⚠️  Puerta inaccesible en (" + x + "," + y + ")");
                repararConexionPuerta(x, y);
            }
        }
    }
    
    if (puertasInaccesibles > 0) {
        System.out.println("   🔧 Reparadas " + puertasInaccesibles + " puertas inaccesibles");
    }
    
    // Verificar que todas las salas sean accesibles
    int salasInaccesibles = 0;
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {
            if ((celdas[x][y] == POCION || celdas[x][y] == SALIDA) && !estaConectadoAlLaberinto(x, y)) {
                salasInaccesibles++;
                System.out.println("   ⚠️  Sala inaccesible en (" + x + "," + y + ")");
                repararConexionSala(x, y);
            }
        }
    }
    
    if (salasInaccesibles > 0) {
        System.out.println("   🔧 Reparadas " + salasInaccesibles + " salas inaccesibles");
    }
}

private void repararConexionPuerta(int puertaX, int puertaY) {
    // Encontrar el camino más cercano y conectar
    Point caminoCercano = encontrarCaminoMasCercano(puertaX, puertaY);
    
    if (caminoCercano != null) {
        crearCaminoSeguroEntrePuntos(new Point(puertaX, puertaY), caminoCercano);
    }
}

private void repararConexionSala(int salaX, int salaY) {
    // Encontrar la puerta de la sala y reconectarla
    Point puerta = encontrarPuertaDeSala(salaX, salaY);
    
    if (puerta != null) {
        repararConexionPuerta(puerta.x, puerta.y);
    } else {
        // Si no hay puerta, crear una
        System.out.println("   🚪 Creando puerta de emergencia para sala en (" + salaX + "," + salaY + ")");
        crearPuertaEmergencia(salaX, salaY);
    }
}

private void crearPuertaEmergencia(int salaX, int salaY) {
    // Crear una puerta en la dirección más segura
    int[][] direcciones = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    for (int[] dir : direcciones) {
        int puertaX = salaX + dir[0];
        int puertaY = salaY + dir[1];
        
        if (puertaX >= 0 && puertaX < ancho && puertaY >= 0 && puertaY < alto) {
            if (celdas[puertaX][puertaY] == PARED && esCeldaSeguraParaConexion(puertaX, puertaY)) {
                celdas[puertaX][puertaY] = PUERTA;
                System.out.println("   ✅ Puerta de emergencia creada en (" + puertaX + "," + puertaY + ")");
                repararConexionPuerta(puertaX, puertaY);
                return;
            }
        }
    }
}
private Point[] encontrarPuntosMasCercanos(List<Point> area1, List<Point> area2) {
    Point mejorPunto1 = null;
    Point mejorPunto2 = null;
    int mejorDistancia = Integer.MAX_VALUE;
    
    for (Point p1 : area1) {
        for (Point p2 : area2) {
            int distancia = Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y); // Distancia Manhattan
            
            // Preferir conexiones más cortas que no pasen por áreas protegidas
            if (distancia < mejorDistancia && esConexionPosible(p1, p2)) {
                mejorDistancia = distancia;
                mejorPunto1 = p1;
                mejorPunto2 = p2;
            }
        }
    }
    
    if (mejorPunto1 != null && mejorPunto2 != null) {
        return new Point[]{mejorPunto1, mejorPunto2};
    }
    
    return null;
}
private boolean esConexionPosible(Point p1, Point p2) {
    // Verificar que se pueda crear un camino sin destruir salas
    int x = p1.x;
    int y = p1.y;
    
    // Simular el camino y verificar si pasa por áreas protegidas
    while (x != p2.x || y != p2.y) {
        if (x < p2.x) x++;
        else if (x > p2.x) x--;
        else if (y < p2.y) y++;
        else if (y > p2.y) y--;
        
        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            if (esParteDeSala(x, y)) {
                return false; // No se puede pasar por una sala
            }
        }
    }
    
    return true;
}
private boolean conectaAreasSeparadas(int x, int y) {
    if (celdas[x][y] != PARED) return false;
    
    // Contar celdas vacías en las 4 direcciones
    int vacios = 0;
    int[][] direcciones = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
    for (int[] dir : direcciones) {
        int nx = x + dir[0];
        int ny = y + dir[1];
        if (nx >= 0 && nx < ancho && ny >= 0 && ny < alto && celdas[nx][ny] == VACIO) {
            vacios++;
        }
    }
    
    // Si hay al menos 2 celdas vacías adyacentes, probablemente conecte áreas
    return vacios >= 2;
}
private void conectarAreasDesconectadas() {
    boolean[][] visitado = new boolean[ancho][alto];
    List<List<Point>> componentes = new ArrayList<>();
    
    // Encontrar todas las componentes conectadas
    for (int x = 1; x < ancho - 1; x++) {
        for (int y = 1; y < alto - 1; y++) {
            if (celdas[x][y] == VACIO && !visitado[x][y]) {
                List<Point> componente = new ArrayList<>();
                floodFill(x, y, visitado, componente);
                componentes.add(componente);
            }
        }
    }
    
    // Si hay más de una componente, conectarlas
    if (componentes.size() > 1) {
        System.out.println("   🔗 Conectando " + componentes.size() + " áreas desconectadas");
        
        for (int i = 1; i < componentes.size(); i++) {
            conectarComponentes(componentes.get(0), componentes.get(i));
        }
    }
}
private void conectarComponentes(List<Point> comp1, List<Point> comp2) {
    // Encontrar el par de puntos más cercano entre las dos componentes
    Point punto1 = null;
    Point punto2 = null;
    int minDistancia = Integer.MAX_VALUE;
    
    for (Point p1 : comp1) {
        for (Point p2 : comp2) {
            int dist = Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
            if (dist < minDistancia) {
                minDistancia = dist;
                punto1 = p1;
                punto2 = p2;
            }
        }
    }
    
    if (punto1 != null && punto2 != null) {
        // Crear camino entre los dos puntos
        crearCaminoEntrePuntos(punto1, punto2);
    }
}
private void crearCaminoEntrePuntos(Point p1, Point p2) {
    int x = p1.x;
    int y = p1.y;
    
    // Moverse hacia p2 rompiendo paredes PERO PROTEGIENDO SALAS
    while (x != p2.x || y != p2.y) {
        if (x < p2.x) {
            x++;
        } else if (x > p2.x) {
            x--;
        } else if (y < p2.y) {
            y++;
        } else if (y > p2.y) {
            y--;
        }
        
        if (x >= 1 && x < ancho - 1 && y >= 1 && y < alto - 1) {
            // SOLO romper si es seguro
            if (celdas[x][y] == PARED && esCeldaSeguraParaRomper(x, y)) {
                celdas[x][y] = VACIO;
            }
        }
    }
}
private void floodFill(int startX, int startY, boolean[][] visitado, List<Point> componente) {
    Stack<Point> stack = new Stack<>();
    stack.push(new Point(startX, startY));
    
    while (!stack.isEmpty()) {
        Point p = stack.pop();
        int x = p.x;
        int y = p.y;
        
        if (x < 1 || x >= ancho - 1 || y < 1 || y >= alto - 1 || visitado[x][y] || celdas[x][y] != VACIO) {
            continue;
        }
        
        visitado[x][y] = true;
        componente.add(new Point(x, y));
        
        // Agregar vecinos
        stack.push(new Point(x + 1, y));
        stack.push(new Point(x - 1, y));
        stack.push(new Point(x, y + 1));
        stack.push(new Point(x, y - 1));
    }
}
private void crearSalasEspeciales() {
    System.out.println("   - Intentando crear " + numSalasPociones + " salas de poción");
    
    List<Point> posicionesSalas = new ArrayList<>();
    int salasPocionesCreadas = 0;
    int intentosMaximos = 50;
    int intentos = 0;
    
    // Crear salas de poción
    while (salasPocionesCreadas < numSalasPociones && intentos < intentosMaximos) {
        Point posicionSala = crearSala(POCION, "poción");
        if (posicionSala != null) {
            salasPocionesCreadas++;
            posicionesSalas.add(posicionSala);
            System.out.println("   ✅ Sala de poción " + salasPocionesCreadas + " creada");
        }
        intentos++;
    }
    
    if (salasPocionesCreadas < numSalasPociones) {
        System.out.println("   ⚠️  Solo se crearon " + salasPocionesCreadas + " de " + numSalasPociones + " salas de poción");
    }
    
    // Crear sala de salida
    System.out.println("   - Intentando crear sala de salida");
    intentos = 0;
    Point posicionSalida = null;
    while (intentos < intentosMaximos) {
        posicionSalida = crearSala(SALIDA, "salida");
        if (posicionSalida != null) {
            posicionesSalas.add(posicionSalida);
            System.out.println("   ✅ Sala de salida creada");
            break;
        }
        intentos++;
    }
    
    if (posicionSalida == null) {
        System.out.println("   ❌ No se pudo crear sala de salida, usando fallback");
        crearSalidaFallback();
    }
    
    // FASE CRÍTICA: Asegurar que todas las salas estén conectadas
    asegurarConexionSalas(posicionesSalas);
}
private void asegurarConexionSalas(List<Point> posicionesSalas) {
    if (posicionesSalas.isEmpty()) return;
    
    System.out.println("   🔗 Asegurando conexión de " + posicionesSalas.size() + " salas...");
    
    // Verificar y conectar cada sala
    for (Point sala : posicionesSalas) {
        if (!estaConectadoAlLaberinto(sala.x, sala.y)) {
            System.out.println("   ❌ Sala en (" + sala.x + "," + sala.y + ") NO está conectada");
            
            // Encontrar la puerta de esta sala y reconectarla
            reconectarPuertaDeSala(sala.x, sala.y);
        } else {
            System.out.println("   ✅ Sala en (" + sala.x + "," + sala.y + ") está conectada");
        }
    }
    
    // Verificación final
    int salasConectadas = 0;
    for (Point sala : posicionesSalas) {
        if (estaConectadoAlLaberinto(sala.x, sala.y)) {
            salasConectadas++;
        }
    }
    System.out.println("   📊 " + salasConectadas + "/" + posicionesSalas.size() + " salas conectadas");
}
private void reconectarPuertaDeSala(int salaX, int salaY) {
    // Encontrar la puerta de esta sala
    Point puerta = encontrarPuertaDeSala(salaX, salaY);
    
    if (puerta != null) {
        System.out.println("   🔧 Reconectando puerta en (" + puerta.x + "," + puerta.y + ")");
        
        // Conectar la puerta directamente al laberinto
        conectarPuertaDirectamente(puerta.x, puerta.y);
    } else {
        System.out.println("   ⚠️  No se encontró puerta para la sala en (" + salaX + "," + salaY + ")");
        // Crear una nueva puerta
        crearPuertaParaSala(salaX, salaY);
    }
}
private Point encontrarPuertaDeSala(int salaX, int salaY) {
    // Buscar en las 4 direcciones alrededor de la sala
    int[][] direcciones = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    for (int[] dir : direcciones) {
        int x = salaX + dir[0];
        int y = salaY + dir[1];
        
        if (x >= 0 && x < ancho && y >= 0 && y < alto && celdas[x][y] == PUERTA) {
            return new Point(x, y);
        }
    }
    return null;
}

private void conectarPuertaDirectamente(int puertaX, int puertaY) {
    // Conectar la puerta al camino más cercano
    Point caminoCercano = encontrarCaminoMasCercano(puertaX, puertaY);
    
    if (caminoCercano != null) {
        System.out.println("   🛣️  Conectando puerta a camino en (" + caminoCercano.x + "," + caminoCercano.y + ")");
        crearCaminoCortoYSeguro(puertaX, puertaY, caminoCercano.x, caminoCercano.y);
    } else {
        // Conectar directamente a la entrada
        System.out.println("   🏠 Conectando puerta directamente a entrada");
        crearCaminoCortoYSeguro(puertaX, puertaY, 1, 1);
    }
}
private void crearCaminoCortoYSeguro(int x1, int y1, int x2, int y2) {
    // Crear camino más corto posible, evitando salas
    System.out.println("   📍 Creando camino corto de (" + x1 + "," + y1 + ") a (" + x2 + "," + y2 + ")");
    
    // Estrategia simple: primero horizontal, luego vertical
    int currentX = x1;
    int currentY = y1;
    
    // Movimiento horizontal
    while (currentX != x2) {
        if (currentX < x2) currentX++;
        else currentX--;
        
        if (currentX >= 0 && currentX < ancho && currentY >= 0 && currentY < alto) {
            if (celdas[currentX][currentY] == PARED && esCeldaSeguraParaRomper(currentX, currentY)) {
                celdas[currentX][currentY] = VACIO;
            }
        }
    }
    
    // Movimiento vertical
    while (currentY != y2) {
        if (currentY < y2) currentY++;
        else currentY--;
        
        if (currentX >= 0 && currentX < ancho && currentY >= 0 && currentY < alto) {
            if (celdas[currentX][currentY] == PARED && esCeldaSeguraParaRomper(currentX, currentY)) {
                celdas[currentX][currentY] = VACIO;
            }
        }
    }
}
private void crearRutaSeguraHaciaSala(int startX, int startY, int salaX, int salaY) {
    // Crear ruta que evite dañar la sala
    System.out.println("   🛡️  Creando ruta segura hacia sala en (" + salaX + "," + salaY + ")");
    
    // Encontrar el punto más cercano fuera del área protegida de la sala
    Point puntoSeguro = encontrarPuntoSeguroCercano(salaX, salaY);
    
    if (puntoSeguro != null) {
        // Conectar desde la entrada hasta el punto seguro
        crearCaminoSimpleSeguro(startX, startY, puntoSeguro.x, puntoSeguro.y);
        
        // Conectar desde el punto seguro hasta la puerta de la sala
        // (esto ya debería estar hecho por crearPuertaParaSala)
    } else {
        // Fallback: conectar directamente pero con protección
        crearCaminoSimpleSeguro(startX, startY, salaX, salaY);
    }
}

private Point encontrarPuntoSeguroCercano(int salaX, int salaY) {
    // Buscar un punto que no esté en el área protegida de la sala
    for (int radio = 3; radio < 10; radio++) {
        for (int dx = -radio; dx <= radio; dx++) {
            for (int dy = -radio; dy <= radio; dy++) {
                int x = salaX + dx;
                int y = salaY + dy;
                
                if (x >= 0 && x < ancho && y >= 0 && y < alto) {
                    if (celdas[x][y] == VACIO && !estaEnAreaProtegida(x, y, salaX, salaY)) {
                        return new Point(x, y);
                    }
                }
            }
        }
    }
    return null;
}

private boolean estaEnAreaProtegida(int x, int y, int salaX, int salaY) {
    // Área protegida: 3x3 alrededor de la sala (incluye paredes)
    return Math.abs(x - salaX) <= 2 && Math.abs(y - salaY) <= 2;
}

private void crearCaminoSimpleSeguro(int x1, int y1, int x2, int y2) {
    // Versión ultra-segura que nunca toca áreas protegidas
    int x = x1;
    int y = y1;
    
    // Primero moverse horizontalmente, luego verticalmente (o viceversa)
    // para evitar pasar a través de salas
    
    // Estrategia: moverse primero lejos de posibles salas
    if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
        // Primero horizontal
        while (x != x2) {
            if (x < x2) x++;
            else x--;
            
            if (esCeldaSeguraParaRomper(x, y)) {
                celdas[x][y] = VACIO;
            }
        }
        // Luego vertical
        while (y != y2) {
            if (y < y2) y++;
            else y--;
            
            if (esCeldaSeguraParaRomper(x, y)) {
                celdas[x][y] = VACIO;
            }
        }
    } else {
        // Primero vertical
        while (y != y2) {
            if (y < y2) y++;
            else y--;
        
            if (esCeldaSeguraParaRomper(x, y)) {
                celdas[x][y] = VACIO;
            }
        }
        // Luego horizontal
        while (x != x2) {
            if (x < x2) x++;
            else x--;
            
            if (esCeldaSeguraParaRomper(x, y)) {
                celdas[x][y] = VACIO;
            }
        }
    }
}


private boolean esCeldaSeguraParaRomper(int x, int y) {
    // Verificación más estricta para proteger salas
    if (celdas[x][y] != PARED) {
        return false; // Solo podemos romper paredes
    }
    
    // Verificar si es parte de una sala
    if (esParteDeSala(x, y)) {
        return false;
    }
    
    // Verificar si está cerca de una sala (radio más amplio)
    if (estaCercaDeSala(x, y)) {
        return false;
    }
    
    // Verificar adicionalmente si está en el área protegida de cualquier sala
    for (int sx = 0; sx < ancho; sx++) {
        for (int sy = 0; sy < alto; sy++) {
            if (celdas[sx][sy] == POCION || celdas[sx][sy] == SALIDA) {
                // Área protegida extendida: 4x4 alrededor de cada sala
                if (Math.abs(x - sx) <= 3 && Math.abs(y - sy) <= 3) {
                    return false;
                }
            }
        }
    }
    
    return true;
}

private void conectarSalaAlLaberinto(int salaX, int salaY) {
    // Estrategia simple: conectar con el punto más cercano del laberinto principal
    Point puntoCercano = encontrarPuntoCercanoConectadoSimple(salaX, salaY);
    
    if (puntoCercano != null) {
        crearRutaAlternativaSimple(puntoCercano.x, puntoCercano.y, salaX, salaY);
    } else {
        // Si no hay punto cercano, conectar directamente con la entrada
        crearRutaAlternativaSimple(1, 1, salaX, salaY);
    }
}
private Point encontrarPuntoCercanoConectadoSimple(int targetX, int targetY) {
    // Buscar en un radio creciente hasta encontrar un punto conectado
    for (int radio = 1; radio < Math.max(ancho, alto); radio++) {
        for (int dx = -radio; dx <= radio; dx++) {
            for (int dy = -radio; dy <= radio; dy++) {
                int x = targetX + dx;
                int y = targetY + dy;
                
                if (x >= 0 && x < ancho && y >= 0 && y < alto) {
                    if (celdas[x][y] == VACIO && estaConectadoAlLaberinto(x, y)) {
                        return new Point(x, y);
                    }
                }
            }
        }
    }
    return null;
}

private void conectarSalas(Point sala1, Point sala2) {
    // Crear camino directo entre dos salas
    crearCaminoDirecto(sala1.x, sala1.y, sala2.x, sala2.y);
}

private Point encontrarPuntoCercanoConectado(int targetX, int targetY) {
    // Buscar el punto transitable más cercano que esté conectado al laberinto principal
    Point mejorPunto = null;
    int mejorDistancia = Integer.MAX_VALUE;
    
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {
            if (esCeldaTransitable(x, y) && estaConectadoAlLaberinto(x, y)) {
                int distancia = Math.abs(x - targetX) + Math.abs(y - targetY);
                if (distancia < mejorDistancia) {
                    mejorDistancia = distancia;
                    mejorPunto = new Point(x, y);
                }
            }
        }
    }
    
    return mejorPunto;
}
private void crearCaminoDirecto(int x1, int y1, int x2, int y2) {
    // Crear camino en línea recta, pero PROTEGIENDO LAS SALAS
    int x = x1;
    int y = y1;
    
    while (x != x2 || y != y2) {
        if (x < x2) x++;
        else if (x > x2) x--;
        else if (y < y2) y++;
        else if (y > y2) y--;
        
        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            // NO romper si es parte de una sala (pared de sala o elemento interno)
            if (esParteDeSala(x, y)) {
                System.out.println("   🛡️  Protegiendo sala en (" + x + "," + y + ")");
                // Buscar ruta alrededor de la sala
                crearRutaAlrededorDeSala(x1, y1, x2, y2, x, y);
                return;
            }
            
            // Solo romper paredes normales del laberinto
            if (celdas[x][y] == PARED) {
                celdas[x][y] = VACIO;
            }
        }
    }
}
private boolean esParteDeSala(int x, int y) {
    // Verificar si la celda es parte de una sala especial
    int tipoCelda = celdas[x][y];
    
    // Si es un elemento especial de sala
    if (tipoCelda == POCION || tipoCelda == SALIDA) {
        return true;
    }
    
    // Si es una pared que pertenece a una sala (verificar en un radio mayor)
    if (tipoCelda == PARED) {
        return esParedDeSala(x, y);
    }
    
    // Si es una puerta de sala
    if (tipoCelda == PUERTA) {
        return true; // Proteger las puertas también
    }
    
    return false;
}
private boolean esParedDeSala(int x, int y) {
    // Verificar si esta pared rodea un elemento de sala en un radio de 2 celdas
    for (int dx = -2; dx <= 2; dx++) {
        for (int dy = -2; dy <= 2; dy++) {
            int nx = x + dx;
            int ny = y + dy;
            
            if (nx >= 0 && nx < ancho && ny >= 0 && ny < alto) {
                if (celdas[nx][ny] == POCION || celdas[nx][ny] == SALIDA) {
                    return true;
                }
            }
        }
    }
    return false;
}
private void crearRutaAlrededorDeSala(int x1, int y1, int x2, int y2, int obstaculoX, int obstaculoY) {
    System.out.println("   🚧 Creando ruta alrededor de sala en (" + obstaculoX + "," + obstaculoY + ")");
    
    // Estrategia: rodear la sala por el lado más corto
    int distanciaArriba = calcularDistanciaSinSala(x1, y1, x2, y2, obstaculoX, obstaculoY, 0);
    int distanciaDerecha = calcularDistanciaSinSala(x1, y1, x2, y2, obstaculoX, obstaculoY, 1);
    int distanciaAbajo = calcularDistanciaSinSala(x1, y1, x2, y2, obstaculoX, obstaculoY, 2);
    int distanciaIzquierda = calcularDistanciaSinSala(x1, y1, x2, y2, obstaculoX, obstaculoY, 3);
    
    int[] distancias = {distanciaArriba, distanciaDerecha, distanciaAbajo, distanciaIzquierda};
    int mejorDireccion = 0;
    int menorDistancia = Integer.MAX_VALUE;
    
    for (int i = 0; i < distancias.length; i++) {
        if (distancias[i] < menorDistancia && distancias[i] >= 0) {
            menorDistancia = distancias[i];
            mejorDireccion = i;
        }
    }
    
    // Crear ruta en la dirección seleccionada
    switch (mejorDireccion) {
        case 0: // Rodear por arriba
            crearRutaPorArriba(x1, y1, x2, y2, obstaculoX, obstaculoY);
            break;
        case 1: // Rodear por derecha
            crearRutaPorDerecha(x1, y1, x2, y2, obstaculoX, obstaculoY);
            break;
        case 2: // Rodear por abajo
            crearRutaPorAbajo(x1, y1, x2, y2, obstaculoX, obstaculoY);
            break;
        case 3: // Rodear por izquierda
            crearRutaPorIzquierda(x1, y1, x2, y2, obstaculoX, obstaculoY);
            break;
    }
}
private void crearRutaPorAbajo(int x1, int y1, int x2, int y2, int obstaculoX, int obstaculoY) {
    int desvioY = obstaculoY + 2;
    if (desvioY >= alto) desvioY = alto - 1;
    
    // Bajar del obstáculo
    crearCaminoSimple(x1, y1, x1, desvioY);
    // Moverse horizontalmente
    crearCaminoSimple(x1, desvioY, x2, desvioY);
    // Subir al destino (si es necesario)
    crearCaminoSimple(x2, desvioY, x2, y2);
}

private void crearRutaPorIzquierda(int x1, int y1, int x2, int y2, int obstaculoX, int obstaculoY) {
    int desvioX = obstaculoX - 2;
    if (desvioX < 0) desvioX = 0;
    
    // Ir a la izquierda del obstáculo
    crearCaminoSimple(x1, y1, desvioX, y1);
    // Moverse verticalmente
    crearCaminoSimple(desvioX, y1, desvioX, y2);
    // Ir a la derecha al destino
    crearCaminoSimple(desvioX, y2, x2, y2);
}
private int calcularDistanciaSinSala(int x1, int y1, int x2, int y2, int obstaculoX, int obstaculoY, int direccion) {
    // Calcular distancia de la ruta alternativa en una dirección específica
    // Retorna -1 si la ruta no es posible
    try {
        int tempX = x1, tempY = y1;
        int distancia = 0;
        
        switch (direccion) {
            case 0: // Arriba
                while (tempY > Math.min(y1, y2) - 2) {
                    tempY--;
                    distancia++;
                    if (tempY < 0 || esParteDeSala(tempX, tempY)) return -1;
                }
                break;
            case 1: // Derecha
                while (tempX < Math.max(x1, x2) + 2) {
                    tempX++;
                    distancia++;
                    if (tempX >= ancho || esParteDeSala(tempX, tempY)) return -1;
                }
                break;
            case 2: // Abajo
                while (tempY < Math.max(y1, y2) + 2) {
                    tempY++;
                    distancia++;
                    if (tempY >= alto || esParteDeSala(tempX, tempY)) return -1;
                }
                break;
            case 3: // Izquierda
                while (tempX > Math.min(x1, x2) - 2) {
                    tempX--;
                    distancia++;
                    if (tempX < 0 || esParteDeSala(tempX, tempY)) return -1;
                }
                break;
        }
        
        // Calcular distancia desde el punto de desvío hasta el destino
        distancia += Math.abs(tempX - x2) + Math.abs(tempY - y2);
        return distancia;
        
    } catch (Exception e) {
        return -1;
    }
}

private void crearRutaPorArriba(int x1, int y1, int x2, int y2, int obstaculoX, int obstaculoY) {
    int desvioY = obstaculoY - 2;
    if (desvioY < 0) desvioY = 0;
    
    // Ir arriba del obstáculo
    crearCaminoSimple(x1, y1, x1, desvioY);
    // Moverse horizontalmente
    crearCaminoSimple(x1, desvioY, x2, desvioY);
    // Bajar al destino
    crearCaminoSimple(x2, desvioY, x2, y2);
}

private void crearRutaPorDerecha(int x1, int y1, int x2, int y2, int obstaculoX, int obstaculoY) {
    int desvioX = obstaculoX + 2;
    if (desvioX >= ancho) desvioX = ancho - 1;
    
    crearCaminoSimple(x1, y1, desvioX, y1);
    crearCaminoSimple(desvioX, y1, desvioX, y2);
    crearCaminoSimple(desvioX, y2, x2, y2);
}

private void crearCaminoSimple(int x1, int y1, int x2, int y2) {
    // Versión mejorada que protege completamente las salas
    int x = x1;
    int y = y1;
    
    while (x != x2 || y != y2) {
        if (x < x2) x++;
        else if (x > x2) x--;
        else if (y < y2) y++;
        else if (y > y2) y--;
        
        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            // NO romper si es parte de una sala o está muy cerca de una
            if (celdas[x][y] == PARED && !esParteDeSala(x, y) && !estaCercaDeSala(x, y)) {
                celdas[x][y] = VACIO;
            }
        }
    }
}

private boolean estaCercaDeSala(int x, int y) {
    // Verificar si está cerca (radio 2) de cualquier sala especial - MÁS ESTRICTO
    for (int dx = -2; dx <= 2; dx++) {
        for (int dy = -2; dy <= 2; dy++) {
            int nx = x + dx;
            int ny = y + dy;
            
            if (nx >= 0 && nx < ancho && ny >= 0 && ny < alto) {
                if (celdas[nx][ny] == POCION || celdas[nx][ny] == SALIDA) {
                    return true;
                }
            }
        }
    }
    return false;
}
private void crearRutaAlternativaSimple(int x1, int y1, int x2, int y2) {
    // Estrategia simple sin recursión: crear camino en forma de L
    System.out.println("   🗺️  Creando ruta alternativa simple");
    
    // Primero moverse horizontalmente, luego verticalmente
    int currentX = x1;
    int currentY = y1;
    
    // Movimiento horizontal
    while (currentX != x2) {
        if (currentX < x2) currentX++;
        else currentX--;
        
        if (currentX >= 0 && currentX < ancho && currentY >= 0 && currentY < alto) {
            if (celdas[currentX][currentY] == PARED) {
                celdas[currentX][currentY] = VACIO;
            }
        }
    }
    
    // Movimiento vertical
    while (currentY != y2) {
        if (currentY < y2) currentY++;
        else currentY--;
        
        if (currentX >= 0 && currentX < ancho && currentY >= 0 && currentY < alto) {
            if (celdas[currentX][currentY] == PARED) {
                celdas[currentX][currentY] = VACIO;
            }
        }
    }
}
private void crearRutaAlternativa(int x1, int y1, int x2, int y2, int obstaculoX, int obstaculoY) {
    // Crear ruta que evite el obstáculo (puerta cerrada)
    System.out.println("   🗺️  Creando ruta alternativa para evitar obstáculo");
    
    // Estrategia simple: rodear el obstáculo
    int distanciaVertical = Math.abs(obstaculoY - y1);
    int distanciaHorizontal = Math.abs(obstaculoX - x1);
    
    if (distanciaVertical < distanciaHorizontal) {
        // Rodear verticalmente
        crearCaminoDirecto(x1, y1, x1, obstaculoY + 1);
        crearCaminoDirecto(x1, obstaculoY + 1, x2, y2);
    } else {
        // Rodear horizontalmente
        crearCaminoDirecto(x1, y1, obstaculoX + 1, y1);
        crearCaminoDirecto(obstaculoX + 1, y1, x2, y2);
    }
}
private boolean estaConectadoAlLaberinto(int salaX, int salaY) {
    // Verificar si la sala está conectada a la entrada (1,1) SIN PASAR POR PUERTAS CERRADAS
    boolean[][] visitado = new boolean[ancho][alto];
    return buscarConexionValida(1, 1, salaX, salaY, visitado);
}
private boolean buscarConexionValida(int startX, int startY, int targetX, int targetY, boolean[][] visitado) {
    if (startX == targetX && startY == targetY) {
        return true;
    }
    
    if (startX < 0 || startX >= ancho || startY < 0 || startY >= alto || 
        visitado[startX][startY] || !esCeldaTransitable(startX, startY)) {
        return false;
    }
    
    visitado[startX][startY] = true;
    
    // Buscar en las 4 direcciones
    return buscarConexionValida(startX + 1, startY, targetX, targetY, visitado) ||
           buscarConexionValida(startX - 1, startY, targetX, targetY, visitado) ||
           buscarConexionValida(startX, startY + 1, targetX, targetY, visitado) ||
           buscarConexionValida(startX, startY - 1, targetX, targetY, visitado);
}
private boolean esCeldaTransitable(int x, int y) {
    // Una celda es transitable si es VACIO o es una PUERTA ABIERTA
    // NO es transitable si es PARED o PUERTA CERRADA
    if (celdas[x][y] == PARED) {
        return false;
    }
    
    if (celdas[x][y] == PUERTA && !puertasAbiertas[x][y]) {
        return false; // Puerta cerrada - NO transitable
    }
    
    return true; // VACIO, SALIDA, ENTRADA, POCION, LLAVE, TRAMPA, o PUERTA ABIERTA
}

private boolean estanConectadas(Point sala1, Point sala2) {
    boolean[][] visitado = new boolean[ancho][alto];
    return buscarConexionValida(sala1.x, sala1.y, sala2.x, sala2.y, visitado);
}
private Point crearSala(int tipo, String nombre) {
    int intentos = 0;
    while (intentos < 30) {
        int salaX = 3 + random.nextInt(ancho - 6);  // Más cerca de bordes
        int salaY = 3 + random.nextInt(alto - 6);   // Más cerca de bordes
        
        if (puedeCrearSala(salaX, salaY)) {
            // Crear sala 3x3 rodeada de paredes
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int x = salaX + dx;
                    int y = salaY + dy;
                    if (dx == 0 && dy == 0) {
                        celdas[x][y] = tipo;
                    } else {
                        celdas[x][y] = PARED;
                    }
                }
            }
            
            // Crear puerta para la sala
            if (crearPuertaParaSala(salaX, salaY)) {
                return new Point(salaX, salaY);  // Retornar posición de la sala
            } else {
                // Si no se pudo crear puerta, deshacer la sala
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int x = salaX + dx;
                        int y = salaY + dy;
                        celdas[x][y] = VACIO;
                    }
                }
            }
        }
        intentos++;
    }
    return null;  // No se pudo crear la sala
}
private boolean crearPuertaParaSala(int salaX, int salaY) {
    List<Integer> direcciones = new ArrayList<>();
    direcciones.add(0); // Arriba
    direcciones.add(1); // Abajo  
    direcciones.add(2); // Izquierda
    direcciones.add(3); // Derecha
    
    // Mezclar direcciones
    for (int i = 0; i < direcciones.size(); i++) {
        int randomIndex = random.nextInt(direcciones.size());
        int temp = direcciones.get(i);
        direcciones.set(i, direcciones.get(randomIndex));
        direcciones.set(randomIndex, temp);
    }
    
    for (int direccion : direcciones) {
        int puertaX = salaX;
        int puertaY = salaY;
        
        switch (direccion) {
            case 0: puertaY--; break; // Arriba
            case 1: puertaY++; break; // Abajo
            case 2: puertaX--; break; // Izquierda
            case 3: puertaX++; break; // Derecha
        }
        
        // Verificar que la puerta esté dentro de los límites
        if (puertaX >= 0 && puertaX < ancho && puertaY >= 0 && puertaY < alto) {
            // Verificar que la posición de la puerta sea válida
            if (esPosicionValidaParaPuerta(puertaX, puertaY)) {
                celdas[puertaX][puertaY] = PUERTA;
                
                // CONECTAR DIRECTAMENTE la puerta al laberinto
                conectarPuertaAlLaberinto(puertaX, puertaY, direccion);
                return true;
            }
        }
    }
    return false;
}
private boolean esPosicionValidaParaPuerta(int puertaX, int puertaY) {
    // La puerta debe estar en una pared y tener al menos un lado transitable
    if (celdas[puertaX][puertaY] != PARED) {
        return false;
    }
    
    // Verificar que al menos un lado de la puerta lleve a un camino
    int[][] direcciones = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    int caminosCercanos = 0;
    
    for (int[] dir : direcciones) {
        int nx = puertaX + dir[0];
        int ny = puertaY + dir[1];
        
        if (nx >= 0 && nx < ancho && ny >= 0 && ny < alto) {
            if (celdas[nx][ny] == VACIO) {
                caminosCercanos++;
            }
        }
    }
    
    return caminosCercanos > 0;
}
private void conectarPuertaAlLaberinto(int puertaX, int puertaY, int direccion) {
    System.out.println("   🔗 Conectando puerta en (" + puertaX + "," + puertaY + ") al laberinto");
    
    // ESTRATEGIA MEJORADA: Buscar conexión en las 4 direcciones, no solo en la dirección de la puerta
    boolean conectada = false;
    
    // Primero intentar en la dirección original de la puerta
    conectada = intentarConexionEnDireccion(puertaX, puertaY, direccion);
    
    // Si no se pudo conectar, intentar en otras direcciones
    if (!conectada) {
        int[] otrasDirecciones = obtenerOtrasDirecciones(direccion);
        for (int otraDir : otrasDirecciones) {
            if (intentarConexionEnDireccion(puertaX, puertaY, otraDir)) {
                conectada = true;
                break;
            }
        }
    }
    
    // Si todavía no está conectada, usar método agresivo
    if (!conectada) {
        conectarPuertaAgresivamente(puertaX, puertaY);
    }
}
private boolean intentarConexionEnDireccion(int puertaX, int puertaY, int direccion) {
    System.out.println("   🧭 Intentando conexión en dirección " + direccion);
    
    int longitudCamino = 3; // Camino más corto
    
    for (int i = 1; i <= longitudCamino; i++) {
        int caminoX = puertaX;
        int caminoY = puertaY;
        
        switch (direccion) {
            case 0: caminoY = puertaY - i; break; // Arriba
            case 1: caminoY = puertaY + i; break; // Abajo
            case 2: caminoX = puertaX - i; break; // Izquierda
            case 3: caminoX = puertaX + i; break; // Derecha
        }
        
        if (caminoX >= 0 && caminoX < ancho && caminoY >= 0 && caminoY < alto) {
            // Si encontramos un camino existente, ¡éxito!
            if (celdas[caminoX][caminoY] == VACIO) {
                System.out.println("   ✅ Conectado a camino existente en (" + caminoX + "," + caminoY + ")");
                // Crear camino entre la puerta y el camino existente
                crearCaminoRecto(puertaX, puertaY, caminoX, caminoY);
                return true;
            }
            
            // Si es una pared segura, romperla
            if (celdas[caminoX][caminoY] == PARED && esCeldaSeguraParaRomper(caminoX, caminoY)) {
                celdas[caminoX][caminoY] = VACIO;
                System.out.println("   🛠️  Rompiendo pared en (" + caminoX + "," + caminoY + ")");
            } else if (esParteDeSala(caminoX, caminoY)) {
                System.out.println("   ⚠️  Encontrada sala, cambiando dirección");
                return false; // Cambiar de dirección
            }
        } else {
            break; // Salir si nos salimos del grid
        }
    }
    
    return false;
}
private int[] obtenerOtrasDirecciones(int direccionOriginal) {
    // Devolver las otras 3 direcciones en orden aleatorio
    List<Integer> direcciones = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
        if (i != direccionOriginal) {
            direcciones.add(i);
        }
    }
    
    // Mezclar
    for (int i = 0; i < direcciones.size(); i++) {
        int randomIndex = random.nextInt(direcciones.size());
        int temp = direcciones.get(i);
        direcciones.set(i, direcciones.get(randomIndex));
        direcciones.set(randomIndex, temp);
    }
    
    int[] resultado = new int[direcciones.size()];
    for (int i = 0; i < direcciones.size(); i++) {
        resultado[i] = direcciones.get(i);
    }
    return resultado;
}
private void crearCaminoRecto(int x1, int y1, int x2, int y2) {
    // Crear camino en línea recta entre dos puntos
    int x = x1;
    int y = y1;
    
    while (x != x2 || y != y2) {
        if (x < x2) x++;
        else if (x > x2) x--;
        else if (y < y2) y++;
        else if (y > y2) y--;
        
        if (x >= 0 && x < ancho && y >= 0 && y < alto) {
            if (celdas[x][y] == PARED && esCeldaSeguraParaRomper(x, y)) {
                celdas[x][y] = VACIO;
            }
        }
    }
}
private void conectarPuertaAgresivamente(int puertaX, int puertaY) {
    System.out.println("   🚀 Usando conexión agresiva para puerta en (" + puertaX + "," + puertaY + ")");
    
    // Método de último recurso: buscar el camino más cercano y conectar directamente
    Point caminoMasCercano = encontrarCaminoMasCercano(puertaX, puertaY);
    
    if (caminoMasCercano != null) {
        System.out.println("   🎯 Camino más cercano en (" + caminoMasCercano.x + "," + caminoMasCercano.y + ")");
        
        // Conectar con el camino más cercano, ignorando protección temporalmente
        // pero evitando destruir salas
        conectarIgnorandoProteccion(puertaX, puertaY, caminoMasCercano.x, caminoMasCercano.y);
    } else {
        // Conectar directamente a la entrada
        System.out.println("   🏠 Conectando directamente a entrada");
        conectarIgnorandoProteccion(puertaX, puertaY, 1, 1);
    }
}
private void conectarIgnorandoProteccion(int x1, int y1, int x2, int y2) {
    // Conectar dos puntos evitando solo las salas, no las áreas protegidas
    int x = x1;
    int y = y1;
    
    // Primero horizontal, luego vertical (o viceversa) para evitar problemas diagonales
    if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
        // Primero horizontal
        while (x != x2) {
            if (x < x2) x++;
            else x--;
            
            if (x >= 0 && x < ancho && y >= 0 && y < alto) {
                if (celdas[x][y] == PARED && !esParteDeSala(x, y)) {
                    celdas[x][y] = VACIO;
                }
            }
        }
        // Luego vertical
        while (y != y2) {
            if (y < y2) y++;
            else y--;
            
            if (x >= 0 && x < ancho && y >= 0 && y < alto) {
                if (celdas[x][y] == PARED && !esParteDeSala(x, y)) {
                    celdas[x][y] = VACIO;
                }
            }
        }
    } else {
        // Primero vertical
        while (y != y2) {
            if (y < y2) y++;
            else y--;
            
            if (x >= 0 && x < ancho && y >= 0 && y < alto) {
                if (celdas[x][y] == PARED && !esParteDeSala(x, y)) {
                    celdas[x][y] = VACIO;
                }
            }
        }
        // Luego horizontal
        while (x != x2) {
            if (x < x2) x++;
            else x--;
            
            if (x >= 0 && x < ancho && y >= 0 && y < alto) {
                if (celdas[x][y] == PARED && !esParteDeSala(x, y)) {
                    celdas[x][y] = VACIO;
                }
            }
        }
    }
}

private void crearRutaAlternativaParaPuerta(int puertaX, int puertaY, int direccionOriginal) {
    // Si el camino directo está bloqueado, buscar direcciones alternativas
    int[][] direccionesAlternativas = {
        {1, 0},   // Derecha
        {-1, 0},  // Izquierda  
        {0, 1},   // Abajo
        {0, -1}   // Arriba
    };
    
    for (int[] dir : direccionesAlternativas) {
        int nuevoX = puertaX + dir[0];
        int nuevoY = puertaY + dir[1];
        
        if (nuevoX >= 0 && nuevoX < ancho && nuevoY >= 0 && nuevoY < alto) {
            if (celdas[nuevoX][nuevoY] == PARED && !esParteDeSala(nuevoX, nuevoY)) {
                // Crear un camino en esta dirección alternativa
                celdas[nuevoX][nuevoY] = VACIO;
                System.out.println("   🔄 Ruta alternativa creada en (" + nuevoX + "," + nuevoY + ")");
                
                // Intentar conectar este nuevo camino al laberinto
                conectarCaminoAlLaberinto(nuevoX, nuevoY);
                return;
            }
        }
    }
}
private void conectarCaminoAlLaberinto(int startX, int startY) {
    // Buscar el camino más cercano y conectar con él
    Point caminoMasCercano = encontrarCaminoMasCercano(startX, startY);
    
    if (caminoMasCercano != null) {
        System.out.println("   🗺️  Conectando a camino en (" + caminoMasCercano.x + "," + caminoMasCercano.y + ")");
        crearCaminoSimpleSeguro(startX, startY, caminoMasCercano.x, caminoMasCercano.y);
    } else {
        // Si no hay camino cercano, conectar directamente a la entrada
        System.out.println("   🏠 Conectando directamente a entrada");
        crearCaminoSimpleSeguro(startX, startY, 1, 1);
    }
}
private Point encontrarCaminoMasCercano(int startX, int startY) {
    // Buscar en un patrón espiral para encontrar el camino más cercano
    
    for (int radio = 1; radio < Math.max(ancho, alto); radio++) {
        // Buscar en forma de cruz primero (más eficiente)
        List<Point> puntos = new ArrayList<>();
        
        // Horizontal
        for (int dx = -radio; dx <= radio; dx++) {
            puntos.add(new Point(startX + dx, startY));
            puntos.add(new Point(startX + dx, startY - radio));
            puntos.add(new Point(startX + dx, startY + radio));
        }
        // Vertical (evitando duplicados)
        for (int dy = -radio + 1; dy < radio; dy++) {
            puntos.add(new Point(startX - radio, startY + dy));
            puntos.add(new Point(startX + radio, startY + dy));
        }
        
        // Verificar todos los puntos en este radio
        for (Point p : puntos) {
            if (p.x >= 0 && p.x < ancho && p.y >= 0 && p.y < alto) {
                if (celdas[p.x][p.y] == VACIO && estaConectadoAlLaberinto(p.x, p.y)) {
                    return p;
                }
            }
        }
    }
    
    return null;
}
private void resolverPatronesDiagonales() {
    System.out.println("   🔍 Buscando y resolviendo patrones diagonales problemáticos...");
    
    int patronesResueltos = 0;
    
    // Buscar patrones del tipo:
    // [VACIO][PARED]
    // [PARED][VACIO]
    for (int x = 1; x < ancho - 1; x++) {
        for (int y = 1; y < alto - 1; y++) {
            if (esPatronDiagonalProblema(x, y)) {
                resolverPatronDiagonal(x, y);
                patronesResueltos++;
            }
        }
    }
    
    if (patronesResueltos > 0) {
        System.out.println("   ✅ Resueltos " + patronesResueltos + " patrones diagonales problemáticos");
    }
}

private boolean esPatronDiagonalProblema(int x, int y) {
    // Verificar si hay un patrón diagonal que impida el acceso
    // Caso 1: [V][P]  Caso 2: [P][V]
    //         [P][V]          [V][P]
    
    boolean caso1 = celdas[x][y] == VACIO && 
                   celdas[x+1][y] == PARED && 
                   celdas[x][y+1] == PARED && 
                   celdas[x+1][y+1] == VACIO;
    
    boolean caso2 = celdas[x][y] == PARED && 
                   celdas[x+1][y] == VACIO && 
                   celdas[x][y+1] == VACIO && 
                   celdas[x+1][y+1] == PARED;
    
    return caso1 || caso2;
}

private void resolverPatronDiagonal(int x, int y) {
    // Romper una de las paredes para conectar las áreas
    if (celdas[x][y] == VACIO && celdas[x+1][y] == PARED) {
        // Romper pared derecha
        if (esCeldaSeguraParaRomper(x+1, y)) {
            celdas[x+1][y] = VACIO;
            System.out.println("   🔓 Roto patrón diagonal en (" + (x+1) + "," + y + ")");
        }
    } else if (celdas[x][y] == PARED && celdas[x+1][y] == VACIO) {
        // Romper pared izquierda
        if (esCeldaSeguraParaRomper(x, y)) {
            celdas[x][y] = VACIO;
            System.out.println("   🔓 Roto patrón diagonal en (" + x + "," + y + ")");
        }
    }
}
private void crearCaminoLargoDesdePuerta(int puertaX, int puertaY, int direccion) {
    // Crear camino más largo (3-4 celdas) desde la puerta PERO PROTEGIENDO SALAS
    int longitudCamino = 3 + random.nextInt(2);
    
    for (int i = 0; i < longitudCamino; i++) {
        int caminoX = puertaX;
        int caminoY = puertaY;
        
        switch (direccion) {
            case 0: caminoY = puertaY - 1 - i; break; // Arriba
            case 1: caminoY = puertaY + 1 + i; break; // Abajo
            case 2: caminoX = puertaX - 1 - i; break; // Izquierda
            case 3: caminoX = puertaX + 1 + i; break; // Derecha
        }
        
        if (caminoX >= 0 && caminoX < ancho && caminoY >= 0 && caminoY < alto) {
            // SOLO romper si es seguro (no parte de sala)
            if (celdas[caminoX][caminoY] == PARED && esCeldaSeguraParaRomper(caminoX, caminoY)) {
                celdas[caminoX][caminoY] = VACIO;
            }
        } else {
            break; // Salir si nos salimos del grid
        }
    }
}
    
private boolean puedeCrearSala(int x, int y) {
    // Verificar que la sala 3x3 tenga espacio (AHORA PERMITIENDO BORDES)
    for (int dx = -2; dx <= 2; dx++) {
        for (int dy = -2; dy <= 2; dy++) {
            int checkX = x + dx;
            int checkY = y + dy;
            
            // PERMITIR QUE LAS SALAS ESTÉN CERCA DE LOS BORDES
            if (checkX < 0 || checkY < 0 || checkX >= ancho || checkY >= alto) {
                return false; // Pero no permitir que se salgan del grid
            }
            
            // No crear cerca de la entrada
            if (Math.abs(checkX - 1) <= 4 && Math.abs(checkY - 1) <= 4) {
                return false;
            }
            
            // Para el área interna de la sala (3x3)
            if (dx >= -1 && dx <= 1 && dy >= -1 && dy <= 1) {
                if (celdas[checkX][checkY] != VACIO && celdas[checkX][checkY] != PARED) {
                    return false;
                }
            }
        }
    }
    return true;
}
private void crearCaminosAlternativos() {
    // Romper paredes en TODO el espacio (incluyendo bordes) pero PROTEGIENDO SALAS
    int caminosExtra = (ancho * alto) / 10;
    
    for (int i = 0; i < caminosExtra; i++) {
        int x = random.nextInt(ancho);  // AHORA INCLUYE 0 y ancho-1
        int y = random.nextInt(alto);   // AHORA INCLUYE 0 y alto-1
        
        if (celdas[x][y] == PARED && esCeldaSeguraParaRomper(x, y)) {
            // Romper la pared si tiene al menos 1 celda vacía adyacente Y es segura
            int vaciosAlrededor = 0;
            if (x > 0 && celdas[x-1][y] == VACIO) vaciosAlrededor++;
            if (x < ancho-1 && celdas[x+1][y] == VACIO) vaciosAlrededor++;
            if (y > 0 && celdas[x][y-1] == VACIO) vaciosAlrededor++;
            if (y < alto-1 && celdas[x][y+1] == VACIO) vaciosAlrededor++;
            
            if (vaciosAlrededor >= 1) {
                celdas[x][y] = VACIO;
            }
        }
    }
}
private void agregarElementosEspeciales() {
    // TRAMPAS POR NIVEL - sucesión específica
    int[] trampasPorNivel = {2, 3, 5, 7, 9};
    int numTrampas = trampasPorNivel[Math.min(nivel - 1, trampasPorNivel.length - 1)];
    
    System.out.println("   - Generando " + numTrampas + " trampas para nivel " + nivel);
    for (int i = 0; i < numTrampas; i++) {
        agregarElementoEnPosicionValida(TRAMPA);
    }
    
    // Agregar EXACTAMENTE el número necesario de llaves
    System.out.println("   - Generando " + numLlavesNecesarias + " llaves");
    int llavesCreadas = 0;
    int intentos = 0;
    
    while (llavesCreadas < numLlavesNecesarias && intentos < 100) {
        if (agregarElementoEnPosicionValida(LLAVE)) {
            llavesCreadas++;
        }
        intentos++;
    }
    
    if (llavesCreadas < numLlavesNecesarias) {
        System.out.println("   ⚠️  Solo se crearon " + llavesCreadas + " de " + numLlavesNecesarias + " llaves");
    }
}
private boolean agregarElementoEnPosicionValida(int elemento) {
    int intentos = 0;
    while (intentos < 50) {
        int x = 2 + random.nextInt(ancho - 4);
        int y = 2 + random.nextInt(alto - 4);
        
        if (celdas[x][y] == VACIO && 
            !esPosicionEspecial(x, y) &&
            tieneAccesoValidoDesdeEntrada(x, y)) {  // Cambiado a acceso válido
            celdas[x][y] = elemento;
            return true;
        }
        intentos++;
    }
    return false;
}
private boolean tieneAccesoValidoDesdeEntrada(int x, int y) {
    // Verificar acceso SIN pasar por puertas cerradas
    boolean[][] visitado = new boolean[ancho][alto];
    return buscarConexionValida(1, 1, x, y, visitado);
}
    private void crearSalidaFallback() {
        // Buscar una posición aleatoria para la salida
        for (int intentos = 0; intentos < 50; intentos++) {
            int x = 3 + random.nextInt(ancho - 6);
            int y = 3 + random.nextInt(alto - 6);
            
            if (celdas[x][y] == VACIO) {
                celdas[x][y] = SALIDA;
                System.out.println("   - Salida fallback en (" + x + "," + y + ")");
                return;
            }
        }
        
        // Último recurso: poner en una esquina
        celdas[ancho-2][alto-2] = SALIDA;
        System.out.println("   - Salida en esquina (" + (ancho-2) + "," + (alto-2) + ")");
    }
    
    private boolean esPosicionEspecial(int x, int y) {
        return (x == 1 && y == 1); // Entrada
    }
    
    public boolean puedeMoverse(int x, int y) {
        if (x < 0 || y < 0 || x >= ancho || y >= alto) {
            return false;
        }
        
        if (celdas[x][y] == PARED) {
            return false;
        }
        
        // CORREGIDO: Las puertas cerradas NO permiten movimiento
        if (celdas[x][y] == PUERTA && !puertasAbiertas[x][y]) {
            return false;
        }
        
        return true;
    }
    
    // Método adicional para debugging
    private int contarLlaves() {
        int count = 0;
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                if (celdas[x][y] == LLAVE) count++;
            }
        }
        return count;
    }
    
    public void descubrirArea(int jugadorX, int jugadorY) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int x = jugadorX + dx;
                int y = jugadorY + dy;
                
                if (x >= 0 && x < ancho && y >= 0 && y < alto) {
                    // Las trampas NUNCA se descubren hasta activarse
                    // Se mantienen completamente invisibles
                    //if (celdas[x][y] == TRAMPA && !trampasActivadas[x][y]) {
                      //  continue; // No descubrir trampas invisibles
                    //}
                    
                    if (!descubierto[x][y]) {
                        descubierto[x][y] = true;
                        casillasDescubiertas++;
                    }
                }
            }
        }
    }
    
    public void dibujar(Graphics g, int tamanioCelda, int jugadorX, int jugadorY) {
        descubrirArea(jugadorX, jugadorY);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                if (!descubierto[x][y]) {
                    continue; // No dibujar celdas no descubiertas
                }
                
                int celdaX = x * tamanioCelda;
                int celdaY = y * tamanioCelda;
                
                // Dibujar fondo negro para todas las celdas
                g2d.setColor(Color.BLACK);
                g2d.fillRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                
                switch (celdas[x][y]) {
                    case PARED:
                        g2d.setColor(new Color(0, 0, 150));
                        g2d.fillRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                        g2d.setColor(new Color(0, 0, 100));
                        g2d.drawRect(celdaX + 2, celdaY + 2, tamanioCelda - 4, tamanioCelda - 4);
                        break;
                    case ENTRADA:
                        g2d.setColor(Color.GREEN);
                        g2d.fillRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                        g2d.setColor(Color.WHITE);
                        g2d.setFont(new Font("Arial", Font.BOLD, 14));
                        g2d.drawString("IN", celdaX + 18, celdaY + 30);
                        break;
                    case SALIDA:
                        g2d.setColor(Color.GREEN);
                        g2d.fillRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                        g2d.setColor(Color.WHITE);
                        g2d.setFont(new Font("Arial", Font.BOLD, 12));
                        g2d.drawString("OUT", celdaX + 12, celdaY + 30);
                        break;
                    case TRAMPA:
                        if (trampasActivadas[x][y]) {
                            // TRAMPA ACTIVADA: Se muestra en negro (como el suelo)
                            g2d.setColor(Color.BLACK);
                            g2d.fillRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                            // Pequeña marca sutil para indicar que fue una trampa
                            g2d.setColor(new Color(100, 0, 0));
                            g2d.drawRect(celdaX + 10, celdaY + 10, tamanioCelda - 20, tamanioCelda - 20);
                        }else{
                            g2d.setColor(new Color(30, 30, 30));
                            g2d.drawRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                        }
                        break;
                    case LLAVE:
                        if (!llavesRecogidas[x][y]) {
                            g2d.setColor(Color.ORANGE);
                            g2d.fillRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                            g2d.setColor(Color.WHITE);
                            g2d.setFont(new Font("Arial", Font.BOLD, 20));
                            g2d.drawString("🔑", celdaX + 15, celdaY + 30);
                        }
                        break;
                    case PUERTA:
                        if (!puertasAbiertas[x][y]) {
                            g2d.setColor(Color.MAGENTA);
                            g2d.fillRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                            g2d.setColor(Color.WHITE);
                            g2d.setFont(new Font("Arial", Font.BOLD, 20));
                            g2d.drawString("🚪", celdaX + 15, celdaY + 30);
                        } else {
                            // Puerta abierta - se ve como suelo normal
                            g2d.setColor(Color.BLACK);
                            g2d.fillRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                            // Marca sutil de puerta abierta
                            g2d.setColor(new Color(100, 0, 100));
                            g2d.drawRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                        }
                        break;
                    case POCION:
                        if (!pocionesRecogidas[x][y]) {
                            g2d.setColor(Color.CYAN);
                            g2d.fillRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                            g2d.setColor(Color.WHITE);
                            g2d.setFont(new Font("Arial", Font.BOLD, 20));
                            g2d.drawString("🧪", celdaX + 15, celdaY + 30);
                        }
                        break;
                    default:
                        // VACIO y otros - fondo negro con borde gris muy tenue
                        g2d.setColor(new Color(30, 30, 30));
                        g2d.drawRect(celdaX, celdaY, tamanioCelda, tamanioCelda);
                        break;
                }
            }
        }
    }
    
    // Métodos de conteo para debugging
    private int contarPociones() {
        int count = 0;
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                if (celdas[x][y] == POCION) count++;
            }
        }
        return count;
    }
    private int contarTrampas() {
    int count = 0;
    for (int x = 0; x < ancho; x++) {
        for (int y = 0; y < alto; y++) {
            if (celdas[x][y] == TRAMPA) count++;
        }
    }
    return count;
}
    
    private int contarPuertas() {
        int count = 0;
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                if (celdas[x][y] == PUERTA) count++;
            }
        }
        return count;
    }
    
    private boolean tieneSalida() {
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                if (celdas[x][y] == SALIDA) return true;
            }
        }
        return false;
    }
    
    
    public boolean esSalida(int x, int y) {
        return estaEnRango(x, y) && celdas[x][y] == SALIDA;
    }
    
    public boolean esTrampa(int x, int y) {
        return estaEnRango(x, y) && celdas[x][y] == TRAMPA;
    }
    
    public boolean esTrampaActivada(int x, int y) {
        return estaEnRango(x, y) && trampasActivadas[x][y];
    }
    
    public boolean esLlave(int x, int y) {
        return estaEnRango(x, y) && celdas[x][y] == LLAVE;
    }
    
    public boolean esLlaveRecogida(int x, int y) {
        return estaEnRango(x, y) && llavesRecogidas[x][y];
    }
    
    public boolean esPocion(int x, int y) {
        return estaEnRango(x, y) && celdas[x][y] == POCION;
    }
    
    public boolean esPocionRecogida(int x, int y) {
        return estaEnRango(x, y) && pocionesRecogidas[x][y];
    }
    
    public boolean esPuerta(int x, int y) {
        return estaEnRango(x, y) && celdas[x][y] == PUERTA;
    }
    
    public boolean esPuertaAbierta(int x, int y) {
        return estaEnRango(x, y) && puertasAbiertas[x][y];
    }
    
    public boolean esPared(int x, int y) {
        return !estaEnRango(x, y) || celdas[x][y] == PARED;
    }
    
    private boolean estaEnRango(int x, int y) {
        return x >= 0 && y >= 0 && x < ancho && y < alto;
    }
    
    public void activarTrampa(int x, int y) {
        if (estaEnRango(x, y)) {
            trampasActivadas[x][y] = true;
            // NO se marca como descubierto automáticamente
            // Solo se vuelve visible en el siguiente descubrimiento de área
        }
    }
    
    public void recogerLlave(int x, int y) {
        if (estaEnRango(x, y)) {
            llavesRecogidas[x][y] = true;
        }
    }
    public void usarLlave() {
        if (numLlavesNecesarias > 0) {
            numLlavesNecesarias--;
            System.out.println("🔑 Llave usada. Llaves necesarias restantes: " + numLlavesNecesarias);
        }
    }
    public void recogerPocion(int x, int y) {
        if (estaEnRango(x, y)) {
            pocionesRecogidas[x][y] = true;
        }
    }
    
    public void abrirPuerta(int x, int y) {
        if (estaEnRango(x, y)) {
            puertasAbiertas[x][y] = true;
        }
    }
    public int getLlavesNecesarias() {
    return numLlavesNecesarias;
}
    
    public int getCasillasDescubiertas() {
        return casillasDescubiertas;
    }
}