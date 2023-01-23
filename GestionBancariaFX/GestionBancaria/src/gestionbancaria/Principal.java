package gestionbancaria;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author fando
 */
public class Principal {

    //COLORES POR CONSOLA
    private final static String RED = "\033[31m";
    private final static String GREEN = "\033[32m";
    private final static String PURPLE = "\033[35m";
    private final static String BLUE = "\u001B[34m";
    private final static String RESET = "\u001B[0m";

    //PERIODICIDADES
    private final static String MENSUAL = "mensual";
    private final static String TRIMESTRAL = "trimestral";
    private final static String ANUAL = "anual";

    //AVISOS IMPORTANTES EN CONSOLA
    final static String AVISO_HACIENDA = RED + "AVISO: NOTIFICAR A HACIENDA" + RESET;
    final static String AVISO_SALDO_NEGATIVO = RED + "AVISO: SALDO NEGATIVO" + RESET;

    //MÉTODO MAIN

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        String salir = "0";
        String opcion = "";

        //MENÚ PRINCIPAL
        System.out.println("Aplicación para la Gestión de tu Cuenta Bancaria");
        Banco bancoSueca = new Banco();
        CuentaBancaria cuenta = menuPrincipal(bancoSueca);

        //MENÚ SECUNDARIO
        while (!(opcion.equals(salir))) {

            String respuesta = menuSecundario();

            switch (respuesta) {

                case "1":
                    ingresar(cuenta);
                    mostrarInformacion(cuenta);
                    break;

                case "2":
                    sacar(cuenta);
                    mostrarInformacion(cuenta);
                    break;

                case "3":
                    recibosDomiciliados(cuenta);
                    mostrarInformacion(cuenta);
                    break;

                case "4":
                    listadoRecibosPorPeriodicidad(cuenta);
                    mostrarInformacion(cuenta);
                    break;

                case "5":
                    menuPrincipal(bancoSueca);
                    break;

                case "0":
                    System.out.println(PURPLE + "\nGracias por confiar en GRAN BANCO DE SUECA"
                            + PURPLE + "\nEsperamos volver a verte muy pronto" + RESET);
                    opcion = salir;
                    break;

                default:
                    System.out.println(RED + "Debes seleccionar una opción correcta\nInténtalo de nuevo" + RESET);

            }
        }
    }

    //MÉTODO MENÚ PRINCIPAL

    /**
     *
     * @param banco
     * @return
     */
    public static CuentaBancaria menuPrincipal(Banco banco) {
        Scanner sc = new Scanner(System.in);
        Long numCuenta;

        while (true) {

            System.out.println(BLUE + "\n----------------------------------" + RESET);
            System.out.println(BLUE + "*****  GRAN BANCO DE SUECA   *****" + RESET);
            System.out.println(BLUE + "----------------------------------" + RESET);
            System.out.print("Introduce tu número de cuenta: ");

            numCuenta = sc.nextLong();

            if (banco.localizaCC(numCuenta) == null) {

                System.out.println(RED + "Cuenta no válida" + RESET);

            } else {

                return banco.localizaCC(numCuenta);

            }
        }

    }

    //MÉTODO MENÚ SECUNDARIO

    /**
     *
     * @return
     */
    public static String menuSecundario() {
        Scanner sc = new Scanner(System.in);

        String respuesta;

        System.out.println(BLUE + "\n----------------------------------" + RESET);
        System.out.println(BLUE + "*****  GRAN BANCO DE SUECA   *****" + RESET);
        System.out.println(BLUE + "----------------------------------\n" + RESET);
        System.out.println(PURPLE + "1 - Ingresar dinero");
        System.out.println(PURPLE + "2 - Sacar dinero");
        System.out.println(PURPLE + "3 - Domiciliar recibos");
        System.out.println(PURPLE + "4 - Listar recibos según periodicidad");
        System.out.println(PURPLE + "5 - Operar con otra cuenta");
        System.out.println(PURPLE + "0 - Salir." + RESET);
        System.out.print(BLUE + "\nSelecciona una opción: " + RESET);
        respuesta = sc.next();

        return respuesta;
    }

    //MÉTODO INGRESAR DINERO

    /**
     *
     * @param cuenta
     */
    public static void ingresar(CuentaBancaria cuenta) {
        Scanner sc = new Scanner(System.in);

        boolean error = true;

        while (error) {

            System.out.println(BLUE + "\n---------------" + RESET);
            System.out.println(BLUE + "INGRESAR DINERO" + RESET);
            System.out.println(BLUE + "---------------" + RESET);
            System.out.print(GREEN + "Número de cuenta: " + cuenta.getNumCuenta());
            System.out.println(" --- Titular: " + cuenta.getTitular() + RESET);
            System.out.print("Cantidad a ingresar(€): ");

            try {

                double cantidadIngresada = sc.nextDouble();

                switch (cuenta.ingresar(cantidadIngresada)) {

                    case 1 -> { //CANTIDAD MÁXIMA SUPERADA -> AVISAR A HACIENDA

                        System.out.printf("Se ha ingresado correctamente la cantidad de:" + GREEN
                                + " %1$,.2f€\n" + RESET, cantidadIngresada);
                        System.out.println(RED + AVISO_HACIENDA + RESET);

                        if (cuenta.getSaldo() >= 0) {
                            System.out.println("Saldo actual: " + GREEN + cuenta.getSaldoFormateado() + "€" + RESET);
                        } else {
                            System.out.println("Saldo actual: " + RED + cuenta.getSaldoFormateado() + "€" + RESET);
                        }

                        break;
                    }
                    case 0 -> { //INGRESO REALIZADO CORRECTAMENTE

                        System.out.printf("Se ha ingresado correctamente la cantidad de:" + GREEN + " %1$,.2f€\n" + RESET, cantidadIngresada);

                        if (cuenta.getSaldo() >= 0) {
                            System.out.println("Saldo actual: " + GREEN + cuenta.getSaldoFormateado() + "€" + RESET);
                        } else {
                            System.out.println("Saldo actual: " + RED + cuenta.getSaldoFormateado() + "€" + RESET);
                        }

                        break;
                    }
                    case -1 -> { //INGRESO FALLIDO (CANTIDAD NEGATIVA)

                        System.out.println(RED + "HAS INTRODUCIDO UNA CANTIDAD NEGATIVA Y NO SE HA PODIDO REALIZAR EL INGRESO" + RESET);
                        System.out.println(RED + "Vuelve a intentarlo" + RESET);

                        break;
                    }
                }

                error = false;

            } catch (InputMismatchException e) { //CONTROL EXCEPCION INPUT MISMATCH

                System.out.println(RED + "Debes ingresar un numero" + RESET);
                sc = new Scanner(System.in);

            }
        }
    }

    //MÉTODO SACAR DINERO

    /**
     *
     * @param cuenta
     */
    public static void sacar(CuentaBancaria cuenta) {
        Scanner sc = new Scanner(System.in);

        boolean error = true;

        while (error) {

            try {

                System.out.println(BLUE + "\n--------------" + RESET);
                System.out.println(BLUE + "RETIRAR DINERO" + RESET);
                System.out.println(BLUE + "--------------" + RESET);
                System.out.print(GREEN + "Número de cuenta: " + cuenta.getNumCuenta());
                System.out.println(" --- Titular: " + cuenta.getTitular() + RESET);
                System.out.print("Cantidad a retirar(€): ");

                double cantidadRetirada = sc.nextDouble();
                double saldoAnterior = cuenta.getSaldo();
                cuenta.sacar(cantidadRetirada);

                if (cantidadRetirada > 0) {

                    if (saldoAnterior == cuenta.getSaldo()) {
                        System.out.println(RED + "NO SE HA PODIDO RETIRAR LA CANTIDAD SOLICITADA. SALDO INSUFICIENTE" + RESET);
                        System.out.println(RED + "Como máximo puedes tener un descubierto de -50,00€\nVuelve a intentarlo" + RESET);

                    } else if (cuenta.getSaldo() < 0) {

                        System.out.println(RED + AVISO_SALDO_NEGATIVO + RESET);
                        System.out.printf(RESET + "Se ha retirado correctamente la cantidad de: "
                                + RED + "%1$,.2f€\n", cantidadRetirada);

                        if (cuenta.getSaldo() >= 0) {

                            System.out.println(RESET + "Saldo actual: " + GREEN + cuenta.getSaldoFormateado() + "€" + RESET);

                        } else {

                            System.out.println(RESET + "Saldo actual: " + RED + cuenta.getSaldoFormateado() + "€" + RESET);

                        }
                    } else {

                        System.out.printf("Se ha retirado correctamente la cantidad de: " + RED + " %1$,.2f€\n", cantidadRetirada);

                        if (cuenta.getSaldo() >= 0) {

                            System.out.println(RESET + "Saldo actual: " + GREEN + cuenta.getSaldoFormateado() + "€" + RESET);

                        } else {

                            System.out.println("Saldo actual: " + RED + cuenta.getSaldoFormateado() + "€" + RESET);

                        }
                    }
                } else {

                    System.out.println(RED + "LA CANTIDAD DEBE SER MAYOR QUE 0" + RESET);
                    System.out.println(RED + "Vuelve a intentarlo" + RESET);

                }

                error = false;

            } catch (InputMismatchException e) {//CONTROL EXCEPCION INPUT MISMATCH
                System.out.println(RED + "Debes ingresar un numero" + RESET);
                sc = new Scanner(System.in);
            }
        }
    }

    //MÉTODOS MOSTRAR INFORMACIÓN DE LA CUENTA

    /**
     *
     * @param cuenta
     */
    public static void mostrarInformacion(CuentaBancaria cuenta) {

        System.out.println(BLUE + "\n-------------------------" + RESET);
        System.out.println(BLUE + "INFORMACIÓN DE LA CUENTA" + RESET);
        System.out.println(BLUE + "-------------------------" + RESET);
        System.out.println(cuenta.informacionCuenta());

    }

    //MÉTODOS DOMICILIAR UN RECIBO

    /**
     *
     * @param cuenta
     */
    public static void recibosDomiciliados(CuentaBancaria cuenta) {
        Scanner sc = new Scanner(System.in);

        boolean error = true;

        while (error) {

            try {

                System.out.println(BLUE + "\n------------------" + RESET);
                System.out.println(BLUE + "DOMICILIAR RECIBOS" + RESET);
                System.out.println(BLUE + "------------------" + RESET);
                System.out.print(GREEN + "Número de cuenta: " + cuenta.getNumCuenta());
                System.out.println(" --- Titular: " + cuenta.getTitular() + RESET);

                //SE PIDEN LOS DATOS DEL RECIBO PARA PODER GUARDARLO Y DOMICILIARLO
                System.out.print("Introduce el número de RECIBOS que quieres domiciliar: ");
                int numeroRecibos = sc.nextInt();

                for (int i = 1; i <= numeroRecibos; i++) {//CONTADOR DE RECIBOS
                    System.out.println("\nDatos del " + GREEN + "RECIBO " + i + RESET + ":");

                    System.out.print("CIF: ");
                    String cif = sc.next().toUpperCase();

                    System.out.print("Nombre de la Empresa: ");
                    String nombreEmpresa = sc.next().toUpperCase();

                    double importe = 0;
                    boolean importeOK = true;

                    while (importeOK == true) {
                        try {

                            System.out.print("Importe: ");
                            importe = sc.nextDouble();
                            importeOK = false;

                        } catch (InputMismatchException e) {

                            System.out.println(RED + "Importe incorrecto. Debes introducir un numero" + RESET);
                            System.out.print("Importe: ");
                            sc = new Scanner(System.in);
                            importe = sc.nextDouble();

                        }
                    }

                    System.out.print("Concepto: ");
                    String concepto = sc.next().toUpperCase();

                    System.out.print("Periodicidad: ");
                    String periodicidad = sc.next().toUpperCase();

                    System.out.println(cuenta.domiciliar(cif, nombreEmpresa, importe, concepto, periodicidad));

                }

                error = false;

            } catch (InputMismatchException e) {//CONTROL EXCEPCION INPUT MISMATCH

                System.out.println(RED + "Debes introducir un numero" + RESET);
                sc = new Scanner(System.in);

            }
        }

    }

    //MÉTODO PARA LISTAR RECIBOS SEGÚN LA PERIOCIDAD

    /**
     *
     * @param cuenta
     */
    public static void listadoRecibosPorPeriodicidad(CuentaBancaria cuenta) {
        Scanner sc = new Scanner(System.in);

        System.out.println(BLUE + "\n----------------------------------" + RESET);
        System.out.println(BLUE + "LISTADO RECIBOS SEGÚN PERIODICIDAD" + RESET);
        System.out.println(BLUE + "----------------------------------" + RESET);
        System.out.print(GREEN + "Número de cuenta: " + cuenta.getNumCuenta());
        System.out.println(" --- Titular: " + cuenta.getTitular() + RESET);

        System.out.print("Elige el periodo de los recibos a revisar (Mensual/Trimestral/Anual): ");

        boolean periodicidadOk = false;
        String periodicidad = sc.next();

        while (periodicidadOk == false) {

            if (!(periodicidad.equalsIgnoreCase(ANUAL) || periodicidad.equalsIgnoreCase(MENSUAL)
                    || periodicidad.equalsIgnoreCase(TRIMESTRAL))) {

                System.out.println(RED + "Debes seleccionar una periodicidad correcta" + RESET);
                System.out.print("Elige el periodo de los recibos a revisar (Mensual/Trimestral/Anual): ");
                periodicidad = sc.next();

            } else {

                periodicidadOk = true;

            }
        }

        System.out.println(BLUE + "RECIBOS");

        for (Recibos recibos : cuenta.listaRecibosDomicialidos(periodicidad)) {

            System.out.println(PURPLE + "CIF: " + RESET + recibos.getCif());
            System.out.println(PURPLE + "Nombre de la Empresa: " + RESET + recibos.getNombreEmpresa());
            System.out.println(PURPLE + "Importe: " + RESET + recibos.getImporteFormateado());
            System.out.println(PURPLE + "Concepto: " + RESET + recibos.getConcepto());
            System.out.println(PURPLE + "Periodicidad: " + RESET + recibos.getPeriodicidad());

        }

    }

}
