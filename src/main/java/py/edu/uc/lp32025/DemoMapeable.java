// src/main/java/py/edu/uc/lp32025/demo/DemoMapeable.java
package py.edu.uc.lp32025;

import py.edu.uc.lp32025.util.MapeableFactory;
import py.edu.uc.lp32025.util.MapeablePrinter;

public class DemoMapeable {

    public static void main(String[] args) {
        System.out.println("=== DEMOSTRACIÓN DE LA INTERFAZ MAPEABLE (Polimorfismo TOTAL) ===");
        System.out.println();

        // 1. CREAR DATOS con Factory
        var elementos = MapeableFactory.crearElementosDemo();

        // 2. IMPRIMIR con Printer
        MapeablePrinter.imprimirElementos(elementos);
    }
}