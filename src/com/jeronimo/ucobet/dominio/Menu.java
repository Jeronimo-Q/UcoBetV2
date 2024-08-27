package com.jeronimo.ucobet.dominio;

import java.util.Scanner;

public class Menu {

    private UcoBet ucoBet;
    private Scanner scanner;

    public Menu() {
        ucoBet = new UcoBet();
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;

        do {
            System.out.println("==== Menu Principal ====");
            System.out.println("1. Registro/Inicio sesion Usuario ");
            System.out.println("2. Mostrar Historial");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> registrarUsuario();
                case 2 -> mostrarHistorial();
                case 3 -> System.out.println("Gracias por usar UcoBet. ¡Hasta luego!");
                default -> System.out.println("Opción no válida. Intenta de nuevo.");
            }

        } while (opcion != 3);
    }

    private void registrarUsuario() {
        ucoBet.ingreso();
    }

    private void mostrarHistorial() {
        ucoBet.historial();
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenu();
}
}
