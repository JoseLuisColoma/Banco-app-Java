package com.paujoseluis.gestionbancaria.Modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Banco {

  //ATRIBUTOS
  private String nombreBanco = "BANCO DE MISLATA";
  private Map<Long, CuentaBancaria> cuentasBancarias = new HashMap<>();

  //CONSTRUCTOR
  public Banco() {
    cargaDatosIniciales();
  }

  //GETTERS
  public String getNombreBanco() {
    return nombreBanco;
  }

  //SETTERS
  public void setNombreBanco(String nombreBanco) {
    this.nombreBanco = nombreBanco;

  }

  //MÉTODOS  
  private void cargaDatosIniciales() {

    //Titulares de cuentas y personas autorizadas para pruebas
    Persona titular1 = new Persona("11111111A", "Pau Fandos");
    Persona titular2 = new Persona("22222222B", "Jose Luis Coloma");
    Persona titular3 = new Persona("33333333C", "Raquel López");

    Persona autorizado1 = new Persona("44444444D", "Esteban Adarme");
    Persona autorizado2 = new Persona("55555555E", "Daniel Cano");
    Persona autorizado3 = new Persona("66666666F", "Alvaro Garcia");

    //cuentas bancarias
    CuentaBancaria cuenta1 = new CuentaBancaria(123456789, titular1);
    CuentaBancaria cuenta2 = new CuentaBancaria(234567891, titular2);
    CuentaBancaria cuenta3 = new CuentaBancaria(345678912, titular3);

    //autorizaciones
    cuenta1.autorizar(autorizado1);
    cuenta1.autorizar(autorizado2);

    cuenta2.autorizar(autorizado2);

    cuenta3.autorizar(autorizado3);

    //ingresos
    cuenta1.ingresar(100);
    cuenta2.ingresar(500);
    cuenta3.ingresar(1000);

    //retiros
    cuenta1.sacar(50);
    cuenta2.sacar(100);
    cuenta2.sacar(150);
    cuenta3.sacar(100);
    cuenta3.sacar(200);
    cuenta3.sacar(500);

    //conjunto cuentas
    cuentasBancarias.put(cuenta1.getNumCuenta(), cuenta1);
    cuentasBancarias.put(cuenta2.getNumCuenta(), cuenta2);
    cuentasBancarias.put(cuenta3.getNumCuenta(), cuenta3);

    //domiciliacion recibos en cuentas
    cuenta1.domiciliar("1000000A", "EMIVASA", 45.00, "Agua", "MENSUAL");
    
    cuenta2.domiciliar("2000000A", "EMIVASA", 40.00, "Agua", "TRIMESTRAL");
    cuenta2.domiciliar("3000000A", "Telefonica", 60.00, "Teléfono", "ANUAL");
    cuenta2.domiciliar("4000000A", "Línea directa", 250.00, "Seguro coche", "MENSUAL");
    
    cuenta3.domiciliar("5000000A", "EMIVASA", 50.00, "Agua", "TRIMESTRAL");
    cuenta3.domiciliar("6000000A", "Yoigo", 65.00, "Teléfono", "ANUAL");
    cuenta3.domiciliar("7000000A", "Mutua Madrileña", 300.00, "Seguro moto", "MENSUAL");
  }

  
  
  //Método para encontrar una cuenta bancaria por su número de cuenta
  public CuentaBancaria localizaCC(long ncuenta) {
    if (cuentasBancarias.containsKey(ncuenta)) {
      return cuentasBancarias.get(ncuenta);
    } else {
      return null;
    }
  }
  
  
  //ALMACENA LA CUENTA CC PASADA POR PARAMETRO 
  public CuentaBancaria almacenarCC(CuentaBancaria cc){
    return cuentasBancarias.put(cc.getNumCuenta(), cc);
  }
  
  
  
  //DEVUELVE LOS NUMEROS DE CUENTA ALMACENADOS
  public Set<Long> listaNumcuenta(){
    return cuentasBancarias.keySet();
  }

}
