package gestionbancaria;

/**
 *
 * @author fando
 */
public class Recibos {
  
  //ATRIBUTOS
  private String cif;
  private String nombreEmpresa;
  private double importe;
  private String concepto;
  private String periodicidad; //"mensual", "trimestral", "anual"};


  //CONSTRUCTOR

    /**
     *
     * @param cif
     * @param nombreEmpresa
     * @param importe
     * @param concepto
     * @param periodicidad
     */
  public Recibos (String cif, String nombreEmpresa, double importe, String concepto, String periodicidad) {
    this.cif = cif;
    this.nombreEmpresa = nombreEmpresa;
    this.importe = importe;
    this.concepto = concepto;
    this.periodicidad = periodicidad;
  }
  

  //GETTERS

    /**
     *
     * @return
     */
   public String getImporteFormateado() {
    return String.format("%1$,.2f", importe);
  }

    /**
     *
     * @return
     */
    public String getCif() {
        return cif;
    }

    /**
     *
     * @return
     */
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    /**
     *
     * @return
     */
    public double getImporte() {
        return importe;
    }

    /**
     *
     * @return
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     *
     * @return
     */
    public String getPeriodicidad() {
        return periodicidad;
    }
    

    //SETTERS

    /**
     *
     * @param cif
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     *
     * @param nombreEmpresa
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     *
     * @param importe
     */
    public void setImporte(double importe) {
        this.importe = importe;
    }

    /**
     *
     * @param concepto
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     *
     * @param periodicidad
     */
    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }
    
    
   

   
   
   
}