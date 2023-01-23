package gestionbancaria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase que almacena los datos de las cuentas bancarias.
 * @author Pau Fandos
 * @version 1
 */
public class CuentaBancaria {

    //COLORES POR CONSOLA
    /**Atributo para estblecer el color verde, que se mostrará por consola posteriormente.*/
    private final static String GREEN = "\033[32m";
    /**Atributo para estblecer el color rojo, que se mostrará por consola posteriormente.*/
    private final static String RED = "\033[31m";
    /**Atributo para restablecer el color por defecto de la consola.*/
    private final static String RESET = "\u001B[0m";
    /**Atributo para estblecer el color morado, que se mostrará por consola posteriormente.*/
    private final static String PURPLE = "\033[35m";

    //VARIABLES DE CONTROL
    /**Atributo que establece la cantidad máxima de dinero que se pude ingresar.*/
    private final static int CANTIDAD_MAX = 3000;
    /**Atributo que indica el tipo de periodicidad mensual para domiciliar recibos.*/
    private final static String MENSUAL = "mensual";
    /**Atributo que indica el tipo de periodicidad trimestral para domiciliar recibos.*/
    private final static String TRIMESTRAL = "trimestral";
    /**Atributo que indica el tipo de periodicidad anual para domiciliar recibos.*/
    private final static String ANUAL = "anual";

    //ATRIBUTOS
    /**Atributo que indica el numero de cuenta.*/
    private final long numCuenta;
    /**Atributo de tipo Persona que indica el titular de la cuenta.*/
    private Persona titular;
    /**Atributo que establece la cantidad de saldo.*/
    private double saldo;
    /**Atributo de tipo Recibo que representa un recibo domiciliario de la cuenta bancaria.*/
    private Recibos recibo;
    /**Colección que recoge las personas que estan autorizadas en la cuenta.*/
    private Set<Persona> autorizados = new HashSet<>();
    /**Colección que recoge todos los recibos domiciliarios de la cuenta.*/
    private List<Recibos> listaRecibos = new ArrayList<>(); //coleccion para recoger los recibos

    //CONSTRUCTOR

    /**
     * Constructor que instancia la cuenta bancaria con el numero de cuenta y 
     * el titular pasados como parámetos y establece el saldo de la cuenta a 0.
     * @param numeroCuenta Número de la cuenta bancaría.
     * @param titular Nombre del titular de la cuenta bancaría.
     */
    public CuentaBancaria(long numeroCuenta, Persona titular) {

        this.numCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0;

    }

    //GETTERS

    /**
     * 
     * Método que devuele el numero de cuenta.
     * @return long Devuelve el numero de la cuenta bancaria.
     */
    public long getNumCuenta() {
        return numCuenta;
    }

    /**
     * 
     * Método que devuelve el titular de la cuenta.
     * @return Persona Devuelve el objeto Persona que es titular de la cuenta bancaría.
     */
    public Persona getTitular() {
        return titular;
    }

    /**
     * 
     * Método que añade la persona pasada como parámetro a la lista de autorizados.
     * @param autorizado Persona autorizada en la cuenta bancaria.
     * @return boolean Devuelve true si la Persona se ha podido añadir, sino devuelve false.
     */
    public boolean autorizar(Persona autorizado) {
        return autorizados.add(autorizado);
    }

    /**
     * 
     * Método que devuelve el saldo de la cuenta.
     * @return double
     */
    public double getSaldo() {
        return saldo;
    }

    //SETTERS
    //NO HAY METODOS SET PORQUE NO SE DEBE PERMITIR MODIFICAR DATOS DESPUES DE CREAR EL OBJETO
    
    
    //MÉTODOS
    
    //Se mostrara el toString()de cada objeto Persona que hay en autorizados

    /**
     * 
     * Método que devuelve un string con todas las personas autorizas de la cuenta.
     * @return String
     */
    public String verAutorizados() {
        return "Personas autorizadas: " + autorizados;
    }

    //Método getSaldoFormateado: muestra el saldo con un formato determinado del tipo ###.###,##€

    /**
     * 
     * Método que devuelve un String con el saldo de la cuenta en formato de 2 decimales.
     * @return String 
     */
    public String getSaldoFormateado() {
        return String.format("%1$,.2f", saldo);
    }

    //Método ingresar

    /**
     * 
     * Método que ingresa la cantidad pasada como parámetro y devuelve 0, 1 o -1 
     * si la operación se ha relizado corrtamente, superando la cantidad máxima o no se ha realizado.
     * @param cantidad
     * @return int
     */
    public int ingresar(double cantidad) {

        final int CANTIDAD_CORRECTA = 0;
        final int CANTIDAD_MAX_SUPERADA = 1;
        final int CANTIDAD_INCORRECTA = -1;
        int resultadoIngreso = CANTIDAD_INCORRECTA;

        if (cantidad > 0) {
            if (cantidad <= CANTIDAD_MAX) {
                saldo += cantidad;
                resultadoIngreso = CANTIDAD_CORRECTA;
            } else if (cantidad >= CANTIDAD_MAX) {
                saldo += cantidad;
                resultadoIngreso = CANTIDAD_MAX_SUPERADA;
            }
        }

        return resultadoIngreso;
    }

    //Método retirar dinero

    /**
     * 
     * Método para retirar la cantidad pasada como parámetro del saldo de la cuenta bancaria, 
     * devolviendo el saldo resultante de la operación.
     * @param cantidad
     * @return double
     */
    public double sacar(double cantidad) {
        if (cantidad > 0 && ((saldo - cantidad) >= -50)) {
            saldo -= cantidad;
        }
        return saldo;
    }

    //método informacion general de Cuenta

    /**
     * 
     * Método que devuelve la información de la cuenta, numero de cuenta, titular, 
     * autorizados (si tiene) y el saldo en verde si es positivo y en rojo si es negativo.
     * @return String
     */
    public String informacionCuenta() {

        String informacionCuenta = getNumCuenta() + " - " + getTitular();

        if (saldo >= 0) {

            if (verAutorizados().isEmpty()) {
                informacionCuenta += "\nSaldo actual: " + GREEN + getSaldoFormateado() + "€" + RESET;

            } else {

                informacionCuenta += "\n" + verAutorizados() + "\n" + "Saldo actual: "
                        + GREEN + getSaldoFormateado() + "€" + RESET;

            }
        }

        if (saldo < 0) {

            if (verAutorizados().isEmpty()) {

                informacionCuenta += "\nSaldo actual: " + RED + getSaldoFormateado() + "€" + RESET;

            } else {

                informacionCuenta += "\n" + verAutorizados() + "\n" + "Saldo actual: "
                        + RED + getSaldoFormateado() + "€" + RESET;

            }
        }

        return informacionCuenta;
    }

    //Método domiciliar recibos

    /**
     * 
     * Método para domiciliar recibos.
     * @param cif
     * @param nombreEmpresa
     * @param importe
     * @param concepto
     * @param periodicidad
     * @return String
     */
    public String domiciliar(String cif, String nombreEmpresa, double importe, String concepto, String periodicidad) {

        String reciboDomiciliado = PURPLE + "CIF: " + RESET + cif + PURPLE + "\nNombre de Empresa: " + RESET + nombreEmpresa
                + PURPLE + "\nImporte: " + RESET + String.format("%1$,.2f", importe) + "€" + PURPLE + "\nConcepto: "
                + RESET + concepto + PURPLE + "\nPeriodicidad: " + RESET + periodicidad;

        boolean reciboOK = true;
        String infoRecibo = RED + "No se ha podido domiciliar el recibo" + RESET;

        if (cif.isEmpty()) {
            infoRecibo += "\nEl" + RED + " CIF " + RESET + "no se ha introducido correctamente\nVuelve a intentarlo";
            reciboOK = false;
        }

        if (nombreEmpresa.isEmpty()) {
            infoRecibo += "\nEl" + RED + " NOMBRE DE LA EMPRESA " + RESET + "no se ha introducido correctamente\nVuelve a intentarlo";
            reciboOK = false;
        }

        if (importe <= 0) {
            infoRecibo += "\nEl" + RED + " IMPORTE " + RESET + "no se ha introducido correctamente\nVuelve a intentarlo";
            reciboOK = false;
        }

        if (concepto.isEmpty()) {
            infoRecibo += "\nEl" + RED + " CONCEPTO " + RESET + "no se ha introducido correctamente\nVuelve a intentarlo";
            reciboOK = false;
        }

        if (!(periodicidad.equalsIgnoreCase(MENSUAL) || periodicidad.equalsIgnoreCase(TRIMESTRAL) || periodicidad.equalsIgnoreCase(ANUAL))) {
            infoRecibo += "\nLa" + RED + " PERIODICIDAD " + RESET + "no se ha introducido correctamente\nVuelve a intentarlo";
            reciboOK = false;
        }

        if (reciboOK) {
            Recibos reciboDomiciliadoOK = new Recibos(cif, nombreEmpresa, importe, concepto, periodicidad);
            listaRecibos.add(reciboDomiciliadoOK);
            infoRecibo = GREEN + "\nEl recibo se ha creado correctamente" + RESET + "\n" + reciboDomiciliado;
        }

        return infoRecibo;

    }

    //Método conjunto de recibos domiciliados

    /**
     * 
     * Método para guardar los recibos domiciliarios según la periodicidad pasada como parámetro. 
     * @param periodicidad
     * @return Set<Recibos>
     */
    public Set<Recibos> listaRecibosDomicialidos(String periodicidad) {

        Set<Recibos> listaRecibosEncontrados = new HashSet<>();
        Set<Recibos> listaRecibosMensuales = new HashSet<>();
        Set<Recibos> listaRecibosTrimestrales = new HashSet<>();
        Set<Recibos> listaRecibosAnuales = new HashSet<>();

        listaRecibos.forEach(recibos -> {
            if (recibos.getPeriodicidad().equalsIgnoreCase(MENSUAL)) {
                listaRecibosMensuales.add(recibos);

            } else if (recibos.getPeriodicidad().equalsIgnoreCase(TRIMESTRAL)) {
                listaRecibosTrimestrales.add(recibos);

            } else if (recibos.getPeriodicidad().equalsIgnoreCase(ANUAL)) {
                listaRecibosAnuales.add(recibos);
            }
        });

        if (periodicidad.equalsIgnoreCase(MENSUAL)) {

            listaRecibosEncontrados.addAll(listaRecibosMensuales);

        } else if (periodicidad.equalsIgnoreCase(TRIMESTRAL)) {

            listaRecibosEncontrados.addAll(listaRecibosTrimestrales);

        } else if (periodicidad.equalsIgnoreCase(ANUAL)) {

            listaRecibosEncontrados.addAll(listaRecibosAnuales);

        }

        return listaRecibosEncontrados;
    }
}
