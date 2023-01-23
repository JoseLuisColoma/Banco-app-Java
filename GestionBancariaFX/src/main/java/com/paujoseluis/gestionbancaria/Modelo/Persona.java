package com.paujoseluis.gestionbancaria.Modelo;

import java.util.Objects;

public class Persona {

  //ATRIBUTOS
  private final String nif;
  private String nombre;
  
  //CONSTRUCTOR
  public Persona(String nif, String nombre) {
    this.nif = nif;
    this.nombre = nombre;
  }
  
  //GETTERS
  public String getNombre() {
    return nombre;
  }

  public String getNif() {
    return nif;
  }

  //SETTERS
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

//COMPARA ESTA PERSONA EN LA QUE ESTAMOS CON OTRO OBJETO PERSON (PARÁMETRO)
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
  public boolean igual(String nif) {
    boolean resultado = false;

    if (this.nif.equalsIgnoreCase(nif)) {
      resultado = true;
    }
    return resultado;

//OTRA FORMA DE ESCRIBIRLO
// return this.nif.equalsIgnoreCase(dni);
  }

  @Override
  public String toString() {
    return String.format("%s", nombre.toUpperCase());
  }

  
  // CON ESTOS MÉTODOS JAVA PUEDE COMPARAR DOS OBJETOS Y SI TIENEN EL MISMO NIF LOS CONSIDERARÁ IGUALES
  // ESTOS MÉTODOS LOS UTILIZA POR EJEMPLO AL AÑADIR UNA PERSONA EN UN SET<PERSONA> Y ASI EVITAR DUPLICADOS
  
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 43 * hash + Objects.hashCode(this.nif);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Persona other = (Persona) obj;
    return Objects.equals(this.nif, other.nif);
  }
  

}
