package com.paujoseluis.gestionbancaria;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

public class PrimaryController {

    @FXML
    private ImageView otrasCuentas;

    @FXML
    private void switchToSecondary() throws IOException {
        SecondaryController.muestraAlerta(Alert.AlertType.INFORMATION, "Las cuentas disponibles para realizar pruebas son: \n"
                + "Cuenta 1: 123456789" + "\nCuenta 2: 234567891" + "\nCuenta 3: 345678912");
        SecondaryController.elegirCuenta();
    }
}
