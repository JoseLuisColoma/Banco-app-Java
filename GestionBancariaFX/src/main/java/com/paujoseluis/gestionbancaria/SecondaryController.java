package com.paujoseluis.gestionbancaria;

import com.paujoseluis.gestionbancaria.Modelo.Banco;
import com.paujoseluis.gestionbancaria.Modelo.CuentaBancaria;
import com.paujoseluis.gestionbancaria.Modelo.Persona;
import com.paujoseluis.gestionbancaria.Modelo.Recibo;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;

public class SecondaryController implements Initializable {

    @FXML
    private Label saldo;

    @FXML
    private Label numCuenta;

    @FXML
    private Spinner<Double> cantidadIngresar;

    @FXML
    private ChoiceBox<String> motivoIngreso;

    @FXML
    private Spinner<Double> cantidadRetirar;

    @FXML
    private ChoiceBox<String> motivoRetirada;

    @FXML
    private ListView<Persona> listaAutorizados;
    private ObservableList<Persona> listaPersonas;

    @FXML
    private TextField nombreAutorizado;

    @FXML
    private TextField nifAutorizado;

    @FXML
    private TextField nifAutorizadoEliminar;

    @FXML
    private TextField cif;

    @FXML
    private TextField nombreEmpresa;

    @FXML
    private TextField conceptoRecibo;

    @FXML
    private Spinner<Double> importeRecibo;

    @FXML
    private ToggleGroup periodicidad;

    @FXML
    private RadioButton rb_mensual;

    @FXML
    private RadioButton rb_trimestral;

    @FXML
    private RadioButton rb_anual;

    @FXML
    private ListView<Recibo> listaRecibos;
    private ObservableList<Recibo> recibo;

    @FXML
    private ProgressBar barraDonacionONG;

    @FXML
    private TextArea historialMovimientos;

    @FXML
    private Label fechaActual;

    @FXML
    private Label titularCuenta;

    @FXML
    private CheckBox casillaDonacion;

    @FXML
    private ProgressIndicator progresoAutorizados;

    @FXML
    private Label cantidadDonada;

    @FXML
    private TextField nCuentaNuevo;

    @FXML
    private TextField titularNuevo;

    @FXML
    private TextField nifNuevo;

    @FXML
    private ListView<String> listaCuentas;
    private ObservableList<String> cuentas = FXCollections.observableArrayList();

    ;
  @FXML
  private Button botonIngresar;
  @FXML
  private Button botonRetirar;
  @FXML
  private Button botonlimpiar;
  @FXML
  private Button botonAutorizar;
  @FXML
  private Button botonEliminar;
  @FXML
  private Button botonDomiciliar;
  @FXML
  private Button botonFiltrar;

    //Enum 
    enum Motivo {
        ELIGE, NOMINA, DONACION, REGALO, OTROS
    }

    enum filtrarRecibos {
        TODOS, MENSUAL, TRIMESTRAL, ANUAL
    }

    //Variables globales
    private static Banco banco = new Banco();
    private static CuentaBancaria cuenta;
    private Persona titular;
    private Persona autorizado;
    private static Alert alerta;
    private double totalDonacion = 0;
    private Optional<ButtonType> confirmacionDesautorizar;
    private Optional<ButtonType> confirmacionDonacion;

    //Constantes
    final static double SEXTA_PARTE_AUTORIZADOS = (double) 1 / 6;
    final static double PORCENTAJE_DONACION = (double) 5 / 100;
    double UNIDAD_BARRA_PROGRESO = (double) 1 / 100;
    final static int MAXIMO_AUTORIZADOS = 6;
    final static double MINIMO_DESCUBIERTO = -50.0;
    final static double TOPE_DONACION = 100.0;
    private final double CERO = 0.0;
    private final double CANTIDAD_MAX_MOVIMIENTO = 1000000.0;
    private final String PATRON_DNI = "^[0-9]{8}[a-zA-Z]{1}$";
    private final String PATRON_CIF = "^[a-zA-Z]{1}[0-9]{8}$";

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        titular = new Persona("123456789A", "Jose Luis Coloma");
//        cuenta = new CuentaBancaria(123456789, titular);
//        autorizado = new Persona("66666666F", "Pau Fandos Gorris");
        cargarDatosIniciales();
    }

    //Método que recopila todos los métodos, datos y pruebas iniciales para la app
    private void cargarDatosIniciales() {
        //saldo inicial en la cuenta
        String saldoInicial = cuenta.getSaldoFormateado();
        muestraAlerta(Alert.AlertType.INFORMATION, "¡Bienvenido " + cuenta.getTitular() + "!\nTu cuenta tiene un saldo inicial de " + saldoInicial);
        historialMovimientos.appendText("\n --> SALDO INICIAL: " + saldoInicial + "€\n");

        //cargamos el progressIndicator
        progresoAutorizados.setProgress(cuenta.getAutorizados().size() * SEXTA_PARTE_AUTORIZADOS);

        //llamadas a métodos iniciales de la app
        mostrarInfo();
        mostrarMotivoIngreso();
        mostrarMotivoRetirada();
        recogidaCantidades();
        listarAutorizados();
        listarRecibos();
        listarCuentas();
        mostrarFechaActual();
    }

    //Método para configurar los spinners y recoger la cantidad introducida.
    private void recogidaCantidades() {
        try {
            //Spinner de ingresar
            SpinnerValueFactory.DoubleSpinnerValueFactory ingreso = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 1000000, 0, 10);
            cantidadIngresar.setValueFactory(ingreso);
            //Spinner de sacar
            SpinnerValueFactory.DoubleSpinnerValueFactory retirada = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000000, 0, 10);
            cantidadRetirar.setValueFactory(retirada);

            //Spinner de recibo
            SpinnerValueFactory.DoubleSpinnerValueFactory cobroRecibo = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000000, 0, 10);
            importeRecibo.setValueFactory(cobroRecibo);

        } catch (Exception e) {
            muestraAlerta(Alert.AlertType.ERROR, "ERROR\nDebes introducir un valor númerico");
        }

    }

    //Método para listar personas en un ListView
    private void listarAutorizados() {
        listaPersonas = FXCollections.observableArrayList(cuenta.getAutorizados());
        listaAutorizados.setItems(listaPersonas);
    }

    //Método para listar recibos en un ListView
    private void listarRecibos() {
        //ListView recibos
        recibo = FXCollections.observableArrayList(cuenta.getListaRecibos());
        listaRecibos.setItems(recibo);
    }

    //Método para listar las cuentas en un ListView
    private void listarCuentas() {
        cuentas.clear();
        for (Long nCuenta : banco.listaNumcuenta()) {
            CuentaBancaria c1 = banco.localizaCC(nCuenta);
            String info = c1.getNumCuenta() + " - " + c1.getTitular();
            cuentas.add(info);
        }
        listaCuentas.setItems(cuentas);
    }

    //Método para mostrar la información general de la cuenta
    private void mostrarInfo() {
        numCuenta.setText(cuenta.getNumCuenta() + "");
        titularCuenta.setText(cuenta.getTitular() + "");
        mostrarSaldo();
    }

    //Método que muestra el saldo de la cuenta (por colores)
    private void mostrarSaldo() {
        saldo.setText(cuenta.getSaldoFormateado() + " €");
        if (cuenta.getSaldo() < 0) {
            saldo.setStyle("-fx-text-fill: red;");
        } else if (cuenta.getSaldo() > 0) {
            saldo.setStyle("-fx-text-fill: green;");
        }
    }

    //Método que recoge el Motivo del ingreso
    private void mostrarMotivoIngreso() {
        recogidaCantidades();
        motivoIngreso.setValue(Motivo.ELIGE.name());
        motivoIngreso.getItems().add("NÓMINA");
        motivoIngreso.getItems().add("REGALO");
        motivoIngreso.getItems().add("DONACIÓN");
        motivoIngreso.getItems().add("OTROS");
    }

    //Método que recoge el Motivo de la retirada
    private void mostrarMotivoRetirada() {
        recogidaCantidades();
        motivoRetirada.setValue(Motivo.ELIGE.name());
        motivoRetirada.getItems().add("NÓMINA");
        motivoRetirada.getItems().add("REGALO");
        motivoRetirada.getItems().add("DONACIÓN");
        motivoRetirada.getItems().add("OTROS");
    }

    //Método que muestra la fecha actual al entrar al banco
    private void mostrarFechaActual() {
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter fechaAccesoBanco = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        fechaActual.setText(hoy.format(fechaAccesoBanco));
    }

    //Metódo que genera distintas alertas y se invocan según se necesiten
    //Así se tienen todas las ventanas de diálogo en un solo método
    public static void muestraAlerta(Alert.AlertType tipoAlerta, String contenidoAlerta) {

        alerta = new Alert(tipoAlerta);
        alerta.setTitle(tipoAlerta.name());
        alerta.setHeaderText(null);
        alerta.setContentText(contenidoAlerta);
        if (alerta.getAlertType() != Alert.AlertType.CONFIRMATION) {
            alerta.showAndWait();
        }
    }

    //Método para limpiar todos los campos
    private void limpiar() {
        //limpiar ingreso
        cantidadIngresar.getValueFactory().setValue(CERO);
        motivoIngreso.setValue(Motivo.ELIGE.name());

        //limpiar retirada
        cantidadRetirar.getValueFactory().setValue(CERO);
        motivoRetirada.setValue(Motivo.ELIGE.name());
        casillaDonacion.setSelected(false);

        //limpiar autorizados
        nombreAutorizado.setText("");
        nifAutorizado.setText("");
        nifAutorizadoEliminar.setText("");

        //limpiar recibos
        cif.setText("");
        nombreEmpresa.setText("");
        importeRecibo.getValueFactory().setValue(CERO);
        conceptoRecibo.setText("");
        periodicidad.selectToggle(null);

        //limpiar cuentas
        nCuentaNuevo.setText("");
        titularNuevo.setText("");
        nifNuevo.setText("");
    }

    //Método que limpia el historial de movimientos
    @FXML
    private void limpiarMovimientos(ActionEvent event) {
        historialMovimientos.clear();
        historialMovimientos.appendText("\n");
    }

    //Método que gestiona todos los ingresos a la cuenta
    @FXML
    private void ingresar(ActionEvent event) {
        double cantidad = (double) (cantidadIngresar.getValue());
        String motivo = motivoIngreso.getValue();

        if (cantidad < CANTIDAD_MAX_MOVIMIENTO) {
            switch (cuenta.ingresar(cantidad)) {
                case 0:
                    mostrarSaldo();
                    historialMovimientos.appendText(historialMovimientos("INGRESO", cantidad, motivo));
                    break;
                case 1:
                    muestraAlerta(Alert.AlertType.WARNING, "AVISO: NOTIFICACIÓN A HACIENDA\nSe ha realizado un ingreso superior a 3.000,00€");
                    mostrarSaldo();
                    break;
                case -1:
                    muestraAlerta(Alert.AlertType.ERROR, "ERROR\nNo se ha podido realizar la operación\nDebes introducir un número positivo");
                    mostrarSaldo();
                    break;
            }
        } else {
            muestraAlerta(Alert.AlertType.ERROR, "ERROR\nNo se ha podido realizar la operación\nNo puedes ingresar tanto dinero.");
        }

        limpiar();
    }

    //Método que gestiona todos las retiradas de la cuenta
    //Incluye la donación
    @FXML
    private void retirar(ActionEvent event) {
        double retirada = (double) cantidadRetirar.getValue();
        String motivo = motivoRetirada.getValue();
        double donacion;

        if (retirada <= CANTIDAD_MAX_MOVIMIENTO) {

            if (retirada > 0) {

                if ((cuenta.getSaldo() - retirada) > MINIMO_DESCUBIERTO) {
                    cuenta.sacar(retirada);
                    historialMovimientos.appendText(historialMovimientos("RETIRADA", retirada, motivo));
                    mostrarSaldo();

                    if (casillaDonacion.isSelected() && totalDonacion <= TOPE_DONACION) {
                        muestraAlerta(Alert.AlertType.CONFIRMATION, "Vas a realizar una donación del 5% del retiro\nEstás seguro?");
                        confirmacionDonacion = alerta.showAndWait();

                        if (confirmacionDonacion.get() == ButtonType.OK) {
                            donacion = retirada * PORCENTAJE_DONACION;
                            double temp = totalDonacion;
                            temp += donacion;

                            if ((cuenta.getSaldo() - donacion) < MINIMO_DESCUBIERTO) {
                                muestraAlerta(Alert.AlertType.ERROR, "ERROR SALDO DESCUBIERTO\nNo se puede realizar la retirada"
                                        + "\nporque habría un descubierto menor a -50,00€");
                            } else {
                                if (temp * UNIDAD_BARRA_PROGRESO <= 1) {
                                    totalDonacion += donacion;
                                    barraDonacionONG.setProgress(totalDonacion * UNIDAD_BARRA_PROGRESO);
                                    cantidadDonada.setText(String.format("%1$,.2f", totalDonacion) + " €");
                                    cuenta.sacar(donacion);
                                    mostrarSaldo();

                                } else {
                                    muestraAlerta(Alert.AlertType.ERROR, "ERROR EN LA DONACIÓN\nHas llegado al tope de la donación\nYa no se pueden realizar más donaciones");
                                }

                            }

                        }
                    } else if (casillaDonacion.isSelected() && totalDonacion >= TOPE_DONACION) {
                        muestraAlerta(Alert.AlertType.ERROR, "ERROR EN LA DONACIÓN\nHas llegado al tope de la donación\nYa no se pueden realizar más donaciones");
                    }

                } else if ((cuenta.getSaldo() - retirada) < TOPE_DONACION) {
                    muestraAlerta(Alert.AlertType.ERROR, "ERROR AL REALIZAR EL RETIRO\nNo es posible realizar esta retirada\nya que sobrepasaría el máximo descubierto");
                }

            } else {
                muestraAlerta(Alert.AlertType.ERROR, "ERROR\nNo se ha podido realizar la operación\nDebes introducir un número positivo");
            }

        } else {
            muestraAlerta(Alert.AlertType.ERROR, "ERROR\nNo se ha podido realizar la operación\nNo puede retirar tanto dinero.");
        }

        if (cuenta.getSaldo() < CERO) {

            muestraAlerta(Alert.AlertType.WARNING, "WARNING!\nSaldo negativo");

        }

        limpiar();
    }

    //Método para mostrar todos los movimientos
    private String historialMovimientos(String tipo, double cantidad, String motivo) {
        String movimiento = " --> " + tipo + " de " + cantidad + "€ ";
        String fechaMovimiento = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();

        if (!motivo.equals(Motivo.ELIGE.name())) {
            movimiento += "por '" + motivo + "' realizado a las " + fechaMovimiento + " horas \n";
        } else {
            movimiento += "realizado a las " + fechaMovimiento + " horas \n";
        }

        return movimiento;
    }

    //Método que autoriza a personas a utilizar la cuenta
    @FXML
    private void autorizar(ActionEvent event) {
        int contadorAutorizados = cuenta.getAutorizados().size();
        String nombre = nombreAutorizado.getText();
        String nif = nifAutorizado.getText();
        Persona personaAutorizada = new Persona(nif, nombre);

        if ((nif.matches(PATRON_DNI) && !nombre.isBlank() && !nif.isBlank())) {
            if (contadorAutorizados < MAXIMO_AUTORIZADOS) {
                cuenta.autorizar(personaAutorizada);
                if (!listaPersonas.contains(personaAutorizada)) {
                    personaAutorizada.setNombre(personaAutorizada.getNombre());
                    listaPersonas.add(personaAutorizada);
                    contadorAutorizados++;
                    progresoAutorizados.setProgress(contadorAutorizados * SEXTA_PARTE_AUTORIZADOS);
                } else {
                    muestraAlerta(Alert.AlertType.ERROR, "ERROR AL AÑADIR AUTORIZADO\nLa persona ya esta autorizada.");
                }
            } else {
                muestraAlerta(Alert.AlertType.ERROR, "ERROR AUTORIZACIÓN\nNo se pueden incluir más autorizados en esta cuenta\nYa existen 6 personas autorizadas.");
            }
        } else {
            muestraAlerta(Alert.AlertType.ERROR, "ERROR AL INTRODUCIR LOS DATOS\nEl DNI o el nombre son incorrectos.");

        }

        limpiar();

    }

    //Método que desautoriza a persona a utilizar tu cuenta
    @FXML
    private void desautorizar(ActionEvent event) {
        String nif = nifAutorizadoEliminar.getText();
        int numeroAutorizados = cuenta.getAutorizados().size();
        Set<Persona> desautorizados = new HashSet<>();

        //Creación de alerta para confirmar la acción de desautorizar.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DESAUTORIZAR");
        ButtonType aceptar = new ButtonType("Aceptar");
        ButtonType cancelar = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(aceptar, cancelar);

        for (Persona autorizada : cuenta.getAutorizados()) {
            if (autorizada.getNif().equalsIgnoreCase(nif)) {
                desautorizados.add(autorizada);
                numeroAutorizados--;
                alert.setContentText("DESAUTORIZAR\n¿Esta seguro de desautorizar a " + autorizada.getNombre() + "?");
                confirmacionDesautorizar = alert.showAndWait();
            }
        }

        if (!nif.isBlank()) {
            if (numeroAutorizados != cuenta.getAutorizados().size()) {
                if (confirmacionDesautorizar.get() == aceptar) {
                    cuenta.getAutorizados().removeAll(desautorizados);
                    listarAutorizados();
                    progresoAutorizados.setProgress(numeroAutorizados * SEXTA_PARTE_AUTORIZADOS);
                } else {

                }
            } else {
                muestraAlerta(Alert.AlertType.ERROR, "ERROR AL ELIMINAR UN AUTORIZADO\nNo se ha encontrado a ningún autorizado\ncon este nif");
            }
        } else {
            muestraAlerta(Alert.AlertType.ERROR, "ERROR AL INTRODUCIR LOS DATOS\nDebes indicar un NIF.");
        }

        limpiar();
    }

    //Método que domicilia recibos a la cuenta
    @FXML
    private void domiciliar(ActionEvent event
    ) {

        boolean esCierto = true;
        boolean cifCorrecto = true;
        String cifEmpresa = cif.getText();
        String nombre = nombreEmpresa.getText();
        String concepto = conceptoRecibo.getText();
        double importeDelRecibo = importeRecibo.getValue();
        String periodoRecibo = "";

        if (nombre.isBlank() || concepto.isBlank()) {
            esCierto = false;
        }
        if ((!cifEmpresa.matches(PATRON_CIF) || cifEmpresa.isBlank())) {
            cifCorrecto = false;
            muestraAlerta(Alert.AlertType.ERROR, "FALLO AL INTRODUCIR EL CIF\nEl CIF no tiene el formato deseado\nFormato correcto: letra + 8 dígitos");
        }
        if (esCierto == false) {
            muestraAlerta(Alert.AlertType.ERROR, "ERROR AL DOCIMILIAR EL RECIBO\nFalta rellenar algunos campos del recibo");
        }

        if (cifCorrecto) {
            if (rb_mensual.isSelected()) {
                periodoRecibo = "MENSUAL";
            }
            if (rb_trimestral.isSelected()) {
                periodoRecibo = "TRIMESTRAL";
            }
            if (rb_anual.isSelected()) {
                periodoRecibo = "ANUAL";
            }
        }

        cuenta.domiciliar(cifEmpresa, nombre, importeDelRecibo, concepto, periodoRecibo);

        recibo = FXCollections.observableArrayList(cuenta.getListaRecibos());
        listaRecibos.setItems(recibo);

        limpiar();
    }

    //Método que filtra en el listado los recibos domicialiados según la periodicidad elegida
    //Creamos una ventana de diálogo personalizada
    @FXML
    private void filtrar(ActionEvent event
    ) {
        ChoiceDialog<String> eligePeriodicidad = new ChoiceDialog<>("TODOS", "MENSUAL", "TRIMESTRAL", "ANUAL");
        eligePeriodicidad.setTitle("FILTRAR RECIBOS");
        eligePeriodicidad.setHeaderText("Filtrar por periodicidad");
        eligePeriodicidad.setContentText("Elije una opción");
        Optional<String> resultado = eligePeriodicidad.showAndWait();

        if ("MENSUAL".equals(filtrarRecibos.MENSUAL.name())) {
            recibo = FXCollections.observableArrayList(cuenta.listaRecibosDomicialidos("MENSUAL"));
            listaRecibos.setItems(recibo);
        }
        if ("TRIMESTRAL".equals(filtrarRecibos.TRIMESTRAL.name())) {
            recibo = FXCollections.observableArrayList(cuenta.listaRecibosDomicialidos("TRIMESTRAL"));
            listaRecibos.setItems(recibo);
        }
        if ("ANUAL".equals(filtrarRecibos.ANUAL.name())) {
            recibo = FXCollections.observableArrayList(cuenta.listaRecibosDomicialidos("ANUAL"));
            listaRecibos.setItems(recibo);
        }
        if ("TODOS".equals(filtrarRecibos.TODOS.name())) {
            recibo = FXCollections.observableArrayList(cuenta.getListaRecibos());
            listaRecibos.setItems(recibo);
        }

        if (resultado.isPresent()) {
            Set<Recibo> recibosFiltrados = cuenta.listaRecibosDomicialidos(resultado.get());
            if (!recibosFiltrados.isEmpty()) {
                recibo.setAll(recibosFiltrados);
            } else {
                muestraAlerta(Alert.AlertType.INFORMATION, "BÚSQUEDA DE RECIBOS\nNo se ha encontrado ningun recibo en el periodo indicado");
            }
        }
    }

    //Método para crear cuentas 
    @FXML
    private void crearCuenta(ActionEvent event) {
        try {
            Long numeroCuenta = Long.parseLong(nCuentaNuevo.getText());
            String nombreTitular = titularNuevo.getText();
            String nifTitular = nifNuevo.getText();

            if ((nifTitular.matches(PATRON_DNI) && !nombreTitular.isBlank() && !nCuentaNuevo.getText().isBlank())) {
                Persona nuevoTitular = new Persona(nifTitular, nombreTitular);
                CuentaBancaria nuevaCuenta = new CuentaBancaria(numeroCuenta, nuevoTitular);

                if (banco.almacenarCC(nuevaCuenta) == null) {
                    listarCuentas();
                } else {
                    muestraAlerta(Alert.AlertType.ERROR, "ERROR.\nLa cuenta que quiere crear ya existe.");
                }
            } else {
                muestraAlerta(Alert.AlertType.ERROR, "ERROR AL INTRODUCIR LOS DATOS\nEl DNI o el Nombre son incorrectos.");

            }
        } catch (NumberFormatException e) {
            muestraAlerta(Alert.AlertType.ERROR, "ERROR AL INTRODUCIR LOS DATOS\nEl dato introducido de número de cuenta bancaria no es válido.");
        }

        limpiar();
    }

    //Método para elegir cuenta para entrar a la aplicación
    public static void elegirCuenta() {
        try {
            TextInputDialog elegirCuenta = new TextInputDialog("Elegir cuenta");
            elegirCuenta.setHeaderText("");
            elegirCuenta.setTitle("Cuentas Bancarias");
            elegirCuenta.setContentText("Introduce tu número de cuenta: ");

            Optional<String> result = elegirCuenta.showAndWait();

            Long resultado = Long.parseLong(result.get());

            cuenta = banco.localizaCC(resultado);
            System.out.println(cuenta);

            if (cuenta != null) {
                App.setRoot("secondary");
            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            muestraAlerta(Alert.AlertType.ERROR, "ERROR\nEl dato introducido no es válido.");
        }
    }
}
