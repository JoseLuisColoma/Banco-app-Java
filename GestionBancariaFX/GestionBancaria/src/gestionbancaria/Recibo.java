package gestionbancaria;

/**
 * Clase para crear recibos domiciliaos.
 * @author Pau Fandos
 * @version 1.0
 */
public class Recibo {

    //ATRIBUTOS
    /**
     * Atributo que indica el CIF de la empresa a la que pertenece el recibo.
     */
    private String cif;
    /**
     * Atributo que indica el nombre de la empresa a la que pertenece el recibo.
     */
    private String nombreEmpresa;
    /**
     * Atributo que indica total a pagar en el recibo.
     */
    private double importe;
    /**
     * Atributo que indica el motivo del recibo.
     */
    private String concepto;
    /**
     * Atributo que indica la frecuencia de tiempo en la que se tiene que pagar
     * el recibo. "MENUAL"/"TRIMESTRAL"/"ANUAL"
     */
    private String periodicidad;

    //CONSTRUCTOR
    /**
     * Constructor de la clase Recibos.
     *
     * @param cif CIF de la empresa que tiene que pagar el recibo.
     * @param nombreEmpresa Nombre de la empresa que tiene que pagar el recibo.
     * @param importe Cantidad que tiene que abonar para pagar el recibo.
     * @param concepto Motivo o asunto del recibo.
     * @param periodicidad Frecuencia con la que se tiene que pagar el recibo.
     */
    public Recibo(String cif, String nombreEmpresa, double importe, String concepto, String periodicidad) {
        this.cif = cif;
        this.nombreEmpresa = nombreEmpresa;
        this.importe = importe;
        this.concepto = concepto;
        this.periodicidad = periodicidad;
    }

    //GETTERS
    /**
     * Método que devuelve el importe del recibo en formato ###.###,##€
     *
     * @return String
     */
    public String getImporteFormateado() {
        return String.format("%1$,.2f", importe);
    }

    /**
     * Método que devuelve el CIF de la empresa a la que pertence el recibo.
     *
     * @return String
     */
    public String getCif() {
        return cif;
    }

    /**
     * Método que devuelve el nombre de la empresa a la que pertence el recibo.
     *
     * @return String
     */
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    /**
     * Método que devuelve la cantidad que se tiene que abonar para pagar el
     * recibo.
     *
     * @return double
     */
    public double getImporte() {
        return importe;
    }

    /**
     * Método que devuelve el motivo del recibo.
     *
     * @return String
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * Método que devuelve la periodiciad del recibo.
     *
     * @return String
     */
    public String getPeriodicidad() {
        return periodicidad;
    }

    //SETTERS
    /**
     * Método para modificar el CIF de la empresa a la que pertenece el recibo.
     *
     * @param cif CIF de la empresa a la que pertenece el recibo.
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * Método para modificar el nombre de la empresa a la que pertenece el
     * recibo.
     *
     * @param nombreEmpresa Nombre de la empresa a la que pertenece el recibo.
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * Método que modifica el importe a pagar en el recibo.
     *
     * @param importe Cantidad que se debe abonar para saldar el recibo.
     */
    public void setImporte(double importe) {
        this.importe = importe;
    }

    /**
     * Método que modifica el concepto del recibo.
     *
     * @param concepto Motivo o asunto del recibo.
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * Método para modificar la periodicidad del recibo.
     *
     * @param periodicidad Frecuencia de tiempo en la que se tiene que pagar el
     * importe del recibo.
     */
    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

}
