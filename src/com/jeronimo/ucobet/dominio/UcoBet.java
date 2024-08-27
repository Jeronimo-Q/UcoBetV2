package com.jeronimo.ucobet.dominio;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UcoBet {
    Scanner scanner = new Scanner(System.in);
    private final List<Usuario> usuarios;
    private final static int LONGITUD_NUMERO_GANADOR = 4;
    private final static LocalTime HORA_CIERRE = LocalTime.of(17, 8);

    public UcoBet() {
        usuarios = new ArrayList<>();
    }

    //Se rectifica la hora de cierre de las apuestas
    private boolean verificadorTiempo() {
        LocalTime horaActual = LocalTime.now();
        return horaActual.isAfter(HORA_CIERRE);
    }

    //Se crean los usuarios ingresados en el main y se incorporan a la lista de usuarios
    public void ingreso(){
        boolean controlNombre;
        boolean controlIdentificacion;
        String numeroGanador;
        Usuario usuario;

        if(!verificadorTiempo()) {
            System.out.print("Ingresa tu nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingresa tu correo: ");
            String correo = scanner.nextLine();
            System.out.print("Ingresa tu identificacion: ");
            String identificacion = scanner.nextLine();
            System.out.print("Ingresa tu celular: ");
            String celular = scanner.nextLine();
            scanner.nextLine();

            //Control de no contener mas de un usuario con la misma identificacion
            controlNombre = usuarios.stream().
                    filter(usuarioV -> usuarioV.getNombre().equalsIgnoreCase(nombre)).toList().isEmpty();

            controlIdentificacion = usuarios.stream().
                    filter(usuarioV -> usuarioV.getIdentificacion().equalsIgnoreCase(identificacion)).toList().isEmpty();

            if (controlIdentificacion) {
                usuario = new Usuario(nombre, correo, identificacion, celular);
                usuarios.add(usuario);
                generarApuesta(usuario);
            } else {
                if (!controlNombre) {
                    usuario = usuarios.stream().
                            filter(usuarioV -> usuarioV.getIdentificacion().equalsIgnoreCase(identificacion)).toList().getFirst();
                    generarApuesta(usuario);
                } else {
                    System.out.println("Identificacion ya registrada");
                }
            }
        }else{
            System.out.println("La hora de apostar ya a terminado");
            //Se realizan la verificacion de los resultados y la obtencion del numero ganador
            numeroGanador = ingresoNumeroGanador();
            System.out.println("El número ganador fue: " + numeroGanador);
            manejoResultado(numeroGanador);
        }
    }

    //Se generan las apuestas de los usuarios
    public void generarApuesta(Usuario usuario){

        System.out.print("Ingresa tu el numero de cifras a las que las vas a apostar: ");
        int cifra = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingresa el numero a apostar: ");
        String numeroApostado = scanner.nextLine();
        System.out.print("Ingresa el dinero a apostar: ");
        long saldo = scanner.nextInt();
        scanner.nextLine();

        Apuesta apuesta = new Apuesta(numeroApostado, saldo);
        usuario.agregarApuesta(apuesta);

    }

    //Se agregan las apuestas y se determina el resultado de esta
    public void manejoResultado(String numeroGanador){
        List<String> numeroLista;

        //Se recorre la lista de usuarios y sus respectivas apuestas
        for(Usuario usuario: usuarios ) {
            List<Apuesta> apuestas = usuario.getApuestas();
            for (Apuesta usuarioA : apuestas) {
                String identificacion = usuario.getIdentificacion();
                double saldo = usuarioA.getMonto();

                //Se crean listas que van a ser transformadas de String a listas
                numeroLista = transformacionALista(usuarioA.getNumeroApostado());
                List<String> numeroGanadorLista = transformacionALista(numeroGanador);
                int largoDelNumero = numeroLista.size();


                //Manejo de las diferentes situaciones dependiendo del numero de cifras del usuario
                switch (largoDelNumero) {
                    case 1 ->
                            manejarCaso(largoDelNumero, 10, numeroLista, numeroGanadorLista, saldo, identificacion);
                    case 2 ->
                            manejarCaso(largoDelNumero, 15, numeroLista, numeroGanadorLista, saldo, identificacion);
                    case 3 ->
                            manejarCaso(largoDelNumero, 50, numeroLista, numeroGanadorLista, saldo, identificacion);
                    case 4 -> {
                        manejarCaso(largoDelNumero, 100, numeroLista, numeroGanadorLista, saldo, identificacion);
                    }
                }
            }
        }
    }

    //Manejo de los casos dependiendo del numero de cifras ingresados
    private void manejarCaso(int numeroDeCifras, int porcetajeDeGanancia, List<String> numeroLista, List<String> numeroGanadorLista, double saldo, String identificacion) {
        List<String> listaAComparar = numeroGanadorLista;

        if(numeroDeCifras != LONGITUD_NUMERO_GANADOR) {
            listaAComparar = numeroGanadorLista.subList(LONGITUD_NUMERO_GANADOR - numeroDeCifras, LONGITUD_NUMERO_GANADOR);
        }

        if (listaAComparar.equals(numeroLista)){
            double finalSaldo = saldo* 1.1 *porcetajeDeGanancia;
            usuarios.stream().filter(usuario -> usuario.getIdentificacion().equals(identificacion))
                    .forEach(usuario -> {
                        usuario.setAcierto();
                                usuario.setSaldo(finalSaldo);
                                usuario.getApuestas().forEach(apuesta -> apuesta.setResultado(true));
                    });
        }
    }

    public void historial(){
        System.out.print("Ingresa tu nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingresa tu identificacion: ");
        String identificacion = scanner.nextLine();
        scanner.nextLine();

        usuarios.stream().filter(usuarioA -> usuarioA.getIdentificacion().equals(identificacion) &&
                        usuarioA.getNombre().equalsIgnoreCase(nombre))
                .forEach(usuarioA ->{System.out.println("El usuario "+usuarioA.getNombre()+" aposto por :");
                        usuarioA.getApuestas().forEach(apuesta -> {
                            System.out.println(apuesta.getNumeroApostado()+ " y su resultado fue : "+ (apuesta.isResultado() ? "Ganó" : "Perdió"));
                        });
                        System.out.println("Saldo: " + usuarioA.getSaldo());
        });

    }

    // Convierte el número seleccionado en una lista
    public List<String> transformacionALista(String numero){
        List<String> listaNumeros = new ArrayList<>();
        for (char digito : numero.toCharArray()) {
            listaNumeros.add(String.valueOf(digito));
        }
        return listaNumeros;
    }

    public String ingresoNumeroGanador(){
        System.out.print("Ingresa el numero que va a ganar : ");
        String numeroGanador = scanner.nextLine();
        scanner.nextLine();

        return numeroGanador;
    }

}
