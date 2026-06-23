import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Clase Reina que implementa la solucion al problema de las n-reinas utilizando
 * backtracking.
 */
public class Reina {

    // Lista enlazada para almacenar los diferentes estados del tablero durante la
    // busqueda de soluciones.
    private LinkedList<Tablero> L = new LinkedList<Tablero>();

    /**
     * Constructor por defecto de la clase Reina.
     */
    public Reina() {
        // Inicializacion vacia.
    }

    /**
     * Constructor que inicializa la lista con un tablero inicial.
     * 
     * @param t Tablero inicial con el que se comienza la solucion.
     */
    public Reina(Tablero t) {
        L.add(t);
    }

    /**
     * Verifica si una reina en la columna especificada seria comida por otra reina
     * ya colocada en el tablero.
     * 
     * @param col     Columna donde se intenta colocar la reina.
     * @param tablero El estado actual del tablero.
     * @return true si la reina seria comida, false en caso contrario.
     */
    boolean esComida(int col, Tablero tablero) {
        boolean comida = true; // Inicialmente asumimos que la reina seria comida.
        int i, j = 0;
        int reng = tablero.getRenglon(); // Obtener el renglon actual del tablero.
        i = reng - 1;
        int[] tab = tablero.getTablero(); // Obtener el arreglo que representa el tablero.

        // Verificar si hay una reina en la misma columna en filas anteriores.
        while ((i >= 0) && (tab[i] != col)) {
            i--;
        }

        if (i < 0) {
            // Verificar la diagonal izquierda hacia arriba.
            i = reng - 1;
            j = col - 1;
            while ((i >= 0) && (j >= 0) && (tab[i] != j)) {
                i--;
                j--;
            }

            if ((i < 0) || (j < 0)) {
                // Verificar la diagonal derecha hacia arriba.
                i = reng - 1;
                j = col + 1;
                while ((i >= 0) && (j < Tablero.TAM) && (tab[i] != j)) {
                    i--;
                    j++;
                }

                if ((i < 0) || (j >= Tablero.TAM)) {
                    comida = false; // Si no hay ninguna amenaza, la reina no seria comida.
                }
            }
        }

        return comida;
    }

    /**
     * Calcula el numero de soluciones posibles para el problema de las n-reinas.
     * 
     * @return El numero de soluciones encontradas.
     */
    int calcularReinas() {
        int num_sol = 0; // Contador para el numero de soluciones encontradas.
        Tablero elem;
        Tablero nuevo;
        int r;
        int[] tab;

        try {
            // Mientras haya tableros en la lista, seguir procesando.
            while ((elem = L.removeLast()) != null) {
                r = elem.getRenglon(); // Obtener el renglon actual.
                tab = elem.getTablero(); // Obtener el estado actual del tablero.

                // Si el tablero no esta completo, intentar colocar una reina en cada columna.
                if (r < Tablero.TAM) {
                    for (int col = 0; col < Tablero.TAM; col++) {
                        if (!esComida(col, elem)) { // Si no hay amenazas, colocar la reina.
                            tab[r] = col; // Colocar la reina en la columna actual.
                            nuevo = new Tablero(tab.clone(), r + 1); // Crear un nuevo tablero con esta colocacion.
                            tab[r] = -1; // Restaurar el tablero original.
                            L.add(nuevo); // Agregar el nuevo tablero a la lista.
                        }
                    }
                } else {
                    // Si el tablero esta completo y valido, incrementa el contador de soluciones.
                    num_sol += 1;
                }
            }
        } catch (NoSuchElementException e) {
            // Capturar la excepcion si la lista esta vacia, finalizando la busqueda.
        }

        return num_sol;
    }

    public static class TrabajadorReinas implements Runnable {

        private int idHIlo;// identifica al hilo actual
        private int totalHilos;// indica cuantos hilos se crearon
        private int soluciones;// almacena las soluciones encontradas por ese hilo

        public TrabajadorReinas() {
        }

        public TrabajadorReinas(int idHilo, int totalHilos) {
            this.idHIlo = idHilo;
            this.totalHilos = totalHilos;
            this.soluciones = 0;
        }

        public int getSoluciones() {
            return soluciones;
        }

        boolean esComida(int col, Tablero tablero) {
            boolean comida = true; // Inicialmente asumimos que la reina seria comida.
            int i, j = 0;
            int reng = tablero.getRenglon(); // Obtener el renglon actual del tablero.
            i = reng - 1;
            int[] tab = tablero.getTablero(); // Obtener el arreglo que representa el tablero.

            // Verificar si hay una reina en la misma columna en filas anteriores.
            while ((i >= 0) && (tab[i] != col)) {
                i--;
            }

            if (i < 0) {
                // Verificar la diagonal izquierda hacia arriba.
                i = reng - 1;
                j = col - 1;
                while ((i >= 0) && (j >= 0) && (tab[i] != j)) {
                    i--;
                    j--;
                }

                if ((i < 0) || (j < 0)) {
                    // Verificar la diagonal derecha hacia arriba.
                    i = reng - 1;
                    j = col + 1;
                    while ((i >= 0) && (j < Tablero.TAM) && (tab[i] != j)) {
                        i--;
                        j++;
                    }

                    if ((i < 0) || (j >= Tablero.TAM)) {
                        comida = false; // Si no hay ninguna amenaza, la reina no seria comida.
                    }
                }
            }

            return comida;
        }

        private int calcularDesde(Tablero inicial) {
            int numSol = 0;
            LinkedList<Tablero> lista = new LinkedList<Tablero>();

            lista.add(inicial);

            Tablero elem;
            Tablero nuevo;
            int r;
            int[] tab;

            try {
                // Mientras haya tableros en la lista, seguir procesando.
                while ((elem = lista.removeLast()) != null) {
                    r = elem.getRenglon(); // Obtener el renglon actual.
                    tab = elem.getTablero(); // Obtener el estado actual del tablero.

                    // Si el tablero no esta completo, intentar colocar una reina en cada columna.
                    if (r < Tablero.TAM) {
                        for (int col = 0; col < Tablero.TAM; col++) {
                            if (!esComida(col, elem)) { // Si no hay amenazas, colocar la reina.
                                tab[r] = col; // Colocar la reina en la columna actual.
                                nuevo = new Tablero(tab.clone(), r + 1); // Crear un nuevo tablero con esta colocacion.
                                tab[r] = -1; // Restaurar el tablero original.
                                lista.add(nuevo); // Agregar el nuevo tablero a la lista.
                            }
                        }
                    } else {
                        // Si el tablero esta completo y valido, incrementa el contador de soluciones.
                        numSol += 1;
                    }
                }
            } catch (NoSuchElementException e) {
                // Capturar la excepcion si la lista esta vacia, finalizando la busqueda.
            }
            return numSol;
        }

        @Override
        public void run() {
            int totalLocal = 0;

            for (int col = idHIlo; col < Tablero.TAM; col += totalHilos) {
                int[] tab = new int[Tablero.TAM];
                for (int i = 0; i < Tablero.TAM; i++) {
                    tab[i] = -1;
                }
                tab[0] = col;
                Tablero inicial = new Tablero(tab, 1);
                totalLocal += calcularDesde(inicial);
            }
            soluciones = totalLocal;
        }
    }

    /**
     * Metodo principal para ejecutar el programa.
     * 
     * @param n Dimension del tablero (n x n).
     */
    public static void main(String[] args) {

        /****************Ejecución secuencial******************************************/

        Tablero init = new Tablero(); // Inicializar el tablero.
        int totalSoluciones_sec;
        long inicio_sec, fin_sec;
        double tiempoSegundos_sec;
        Reina reinas = new Reina(init); // Crear la instancia de Reina con el tablero inicial.

        inicio_sec = System.nanoTime();
        ;
        totalSoluciones_sec = reinas.calcularReinas(); // Calcular las soluciones.

        fin_sec = System.nanoTime();
        tiempoSegundos_sec = (fin_sec - inicio_sec) / 1_000_000_000.0;

        System.out.println("\nResultados Algoritmo Secuencial:");
        System.out.println("Tablero: " + Tablero.TAM);
        System.out.println("Soluciones totales: " + totalSoluciones_sec);
        System.out.println("Tiempo: " + tiempoSegundos_sec + " segundos\n");

        /*******************Ejecución paralela***************************************** */

        int procesadores = Runtime.getRuntime().availableProcessors();
        int numHilos = Math.min(procesadores, Tablero.TAM);

        Thread[] hilos = new Thread[numHilos];
        TrabajadorReinas[] trabajadores = new TrabajadorReinas[numHilos];

        for (int i = 0; i < numHilos; i++) {
            trabajadores[i] = new TrabajadorReinas(i, numHilos);
            hilos[i] = new Thread(trabajadores[i], "Hilo-Reinas-" + i);
            hilos[i].start();
        }

        int totalSoluciones_par = 0;

        for (int i = 0; i < numHilos; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalSoluciones_par = totalSoluciones_par + trabajadores[i].getSoluciones();
        }

        Tablero tab = new Tablero();
        TrabajadorReinas worker = new TrabajadorReinas();

        long inicio_par = System.nanoTime();

        // Ejecucion del algoritmo
        worker.calcularDesde(tab);

        long fin_par = System.nanoTime();
        double tiempoSegundos_par = (fin_par - inicio_par) / 1_000_000_000.0;

        System.out.println("Resultados Algoritmo Paralelo:");
        System.out.println("Tablero: " + Tablero.TAM);
        System.out.println("Soluciones totales: " + totalSoluciones_sec);
        System.out.println("Tiempo: " + tiempoSegundos_par + " segundos.\n");

        if(tiempoSegundos_par < tiempoSegundos_sec)
            System.out.println("El algoritmo en paralelo es más rápido para un tablero de tamaño " + Tablero.TAM);
        else
            System.out.println("El algoritmo secuencial es más rápido para un tablero de tamaño " + Tablero.TAM);

    }
}
