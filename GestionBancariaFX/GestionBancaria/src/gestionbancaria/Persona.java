package gestionbancaria;

/**
 *
 * @author fando
 */
public class Persona {

  //ATRIBUTOS
  private final String nif;
  private String nombre;
  
  //CONSTRUCTOR

    /**
     *
     * @param nif
     * @param nombre
     */
  public Persona(String nif, String nombre) {
    this.nif = nif;
    this.nombre = nombre;
  }
  
  //GETTERS

    /**
     *
     * @return
     */
  public String getNombre() {
    return nombre;
  }

    /**
     *
     * @return
     */
    public String getNif() {
    return nif;
  }

  //SETTERS

    /**
     *
     * @param nombre
     */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

//COMPARA ESTA PERSONA EN LA QUE ESTAMOS CON OTRO OBJETO PERSON (PARÁMETRO)

    /**
     *
     * @param person
     * @return
     */
  public boolean igual(Persona person) {
    boolean resultado = false;

    if (nif.equalsIgnoreCase(person.getNif())) {
      resultado = true;
    }
    return resultado;

//OTRA FORMA DE HACERLO USANDO EL MÉTODO igual(String nif)
//return this.igual(person.getNif());
  }

//COMPARA NIF DE ESTA PERSONA EN LA QUE ESTAMOS CON OTRO NIF

    /**
     *
     * @param nif
     * @return
     */
  public boolean igual(String nif) {
    boolean resultado = false;

    if (this.nif.equalsIgnoreCase(nif)) {
      resultado = true;
    }
    return resultado;

//OTRA FORMA DE ESCRIBIRLO
// return this.nif.equalsIgnoreCase(dni);
  }

    /**
     *
     * @return
     */
    @Override
  public String toString() {
    return String.format("%s (%s)", nombre, nif);
  }
}
