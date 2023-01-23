package com.paujoseluis.gestionbancaria.Modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CuentaBancaria {

    //VARIABLES DE CONTROL
    private final static int CANTIDAD_MAX = 3000;
    private final static String MENSUAL = "mensual";
    private final static String TRIMESTRAL = "trimestral";
    private final static String ANUAL = "anual";

    //ATRIBUTOS
    private final long numCuenta;
    private final Persona titular;
    private double saldo;
   
    private final Set<Persona> autorizados = new HashSet<>(); //colección para recoger los autorizados
    private final List<Recibo> listaRecibos = new ArrayList<>(); //coleccion para recoger los recibos

    //CONSTRUCTOR
    public CuentaBancaria(long numeroCuenta, Persona titular) {

        this.numCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0;

    }

    //GETTERS
    public long getNumCuenta() {
        return numCuenta;
    }

    public Persona getTitular() {
        return titular;
    }

    public boolean autorizar(Persona autorizado) {
        return autorizados.add(autorizado);
    }
	
	public boolean desautorizar(Persona aDesautorizar) {
        return autorizados.remove(aDesautorizar);
    }

    public double getSaldo() {
        return saldo;
    }

  public Set<Persona> getAutorizados() {
    return autorizados;
  }

  public List<Recibo> getListaRecibos() {
    return listaRecibos;
  }
    
    

    //SETTERS
   
    
    
    //MÉTODOS
    
    //Se mostrara el toString()de cada objeto Persona que hay en autorizados
    public String verAutorizados() {
        return "Personas autorizadas: " + autorizados;
    }

    //Método getSaldoFormateado: muestra el saldo con un formato determinado del tipo ###.###,##€
    public String getSaldoFormateado() {
        return String.format("%1$,.2f", saldo);
    }

    //Método ingresar
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
    public double sacar(double cantidad) {
        if (cantidad > 0 && ((saldo - cantidad) >= -50)) {
            saldo -= cantidad;
        }
        return saldo;
    }

    //método informacion general de Cuenta
    public String informacionCuenta() {

        String informacionCuenta = getNumCuenta() + " - " + getTitular();

        if (saldo >= 0) {
            if (verAutorizados().isEmpty()) {
                informacionCuenta += "\nSaldo actual: " +  getSaldoFormateado() + "€" ;
            } else {
                informacionCuenta += "\n" + verAutorizados() + "\n" + "Saldo actual: " + getSaldoFormateado() + "€" ;
            }
        }

        if (saldo < 0) {
            if (verAutorizados().isEmpty()) {
                informacionCuenta += "\nSaldo actual: "  + getSaldoFormateado() + "€" ;
            } else {
                informacionCuenta += "\n" + verAutorizados() + "\n" + "Saldo actual: "
                        +  getSaldoFormateado() + "€" ;
            }
        }

        return informacionCuenta;
    }

    //Método domiciliar recibos
    public String domiciliar(String cif, String nombreEmpresa, double importe, String concepto, String periodicidad) {

        String reciboDomiciliado =  "CIF: "  + cif + "\nNombre de Empresa: "  + nombreEmpresa
                + "\nImporte: "  + String.format("%1$,.2f", importe) + "€" + "\nConcepto: "
                 + concepto + "\nPeriodicidad: "  + periodicidad;

        boolean reciboOK = true;
        String infoRecibo =  "NO se ha podido domiciliar el recibo" ;

        if (cif.isEmpty()) {
            infoRecibo += "\nEl"  + " CIF "  + "NO se ha introducido \n";
            reciboOK = false;
        }

        if (nombreEmpresa.isEmpty()) {
            infoRecibo += "\nEl"  + " NOMBRE DE LA EMPRESA "  + "NO se ha introducido \n";
            reciboOK = false;
        }

        if (importe <= 0) {
            infoRecibo += "\nEl"  + " IMPORTE "  + "NO se ha introducido \n";
            reciboOK = false;
        }

        if (concepto.isEmpty()) {
            infoRecibo += "\nEl"  + " CONCEPTO "  + "NO se ha introducido \n";
            reciboOK = false;
        }

        if (!(periodicidad.equalsIgnoreCase(MENSUAL) || periodicidad.equalsIgnoreCase(TRIMESTRAL) || periodicidad.equalsIgnoreCase(ANUAL))) {
            infoRecibo += "\nLa"  + " PERIODICIDAD "  + "NO se ha introducido \n";
            reciboOK = false;
        }

        if (reciboOK) {
            Recibo reciboDomiciliadoOK = new Recibo(cif, nombreEmpresa, importe, concepto, periodicidad);
            listaRecibos.add(reciboDomiciliadoOK);
            infoRecibo =  "\nEl recibo se ha creado correctamente"  + "\n" + reciboDomiciliado;
        }

        return infoRecibo;

    }

    //Método conjunto de recibos domiciliados
    public Set<Recibo> listaRecibosDomicialidos(String periodicidad) {

        Set<Recibo> listaRecibosEncontrados = new HashSet<>();
        Set<Recibo> listaRecibosMensuales = new HashSet<>();
        Set<Recibo> listaRecibosTrimestrales = new HashSet<>();
        Set<Recibo> listaRecibosAnuales = new HashSet<>();

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
