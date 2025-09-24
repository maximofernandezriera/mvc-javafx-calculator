package com.example.calculator.controller;

import com.example.calculator.model.CalculatorModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controlador de la calculadora.
 *
 * Recibe eventos de la vista definida en {@code calculator.fxml} y delega la
 * lógica de negocio en {@link CalculatorModel}. Su única responsabilidad es:
 * - Traducir acciones de UI (clics de botones) a invocaciones del modelo.
 * - Sincronizar el campo de texto de la vista con el estado del modelo.
 *
 * Nota: el controlador no realiza cálculos por sí mismo; esto mantiene la
 * separación de responsabilidades del patrón MVC.
 */
public class CalculatorController {

    @FXML
    private TextField display;

    /**
     * Instancia de modelo que mantiene el estado y ejecuta las operaciones.
     */
    private final CalculatorModel model = new CalculatorModel();

    @FXML
    /**
     * Método de inicialización llamado automáticamente por JavaFX cuando el
     * {@code FXML} ha sido cargado. Sincroniza el display con el modelo.
     */
    private void initialize() {
        updateDisplay();
    }

    @FXML
    /**
     * Maneja la pulsación de un dígito o del punto decimal.
     * El texto del botón pulsado se envía al modelo.
     */
    public void onDigit(javafx.event.ActionEvent event) {
        String text = ((javafx.scene.control.Button) event.getSource()).getText();
        model.inputDigit(text);
        updateDisplay();
    }

    @FXML
    /** Asigna el operador suma. */
    public void onAdd() {
        model.setOperator(CalculatorModel.Operator.ADD);
        updateDisplay();
    }

    @FXML
    /** Asigna el operador resta. */
    public void onSubtract() {
        model.setOperator(CalculatorModel.Operator.SUBTRACT);
        updateDisplay();
    }

    @FXML
    /** Asigna el operador multiplicación. */
    public void onMultiply() {
        model.setOperator(CalculatorModel.Operator.MULTIPLY);
        updateDisplay();
    }

    @FXML
    /** Asigna el operador división. */
    public void onDivide() {
        model.setOperator(CalculatorModel.Operator.DIVIDE);
        updateDisplay();
    }

    @FXML
    /** Evalúa la operación pendiente y muestra el resultado. */
    public void onEquals() {
        model.evaluate();
        updateDisplay();
    }

    @FXML
    /** Limpia el estado del modelo y reinicia el display. */
    public void onClear() {
        model.clearAll();
        updateDisplay();
    }

    /** Refresca el valor mostrado tomando el estado actual del modelo. */
    private void updateDisplay() {
        display.setText(model.getDisplay());
    }
}


