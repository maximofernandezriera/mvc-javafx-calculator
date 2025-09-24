package com.example.calculator.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Modelo de la calculadora.
 *
 * Encapsula el estado y la lógica aritmética sin conocer nada de la UI
 * (JavaFX) ni de cómo se muestran los datos. Esto permite probar la lógica
 * de forma aislada y reutilizarla con cualquier vista/controlador.
 */
public class CalculatorModel {

    /**
     * Valor que se está escribiendo o el último resultado calculado.
     */
    private BigDecimal accumulator = BigDecimal.ZERO;

    /**
     * Operando almacenado cuando el usuario elige un operador (+, -, *, /).
     */
    private BigDecimal pendingOperand = null;

    /**
     * Operador seleccionado que se aplicará cuando el usuario pulse '='.
     */
    private Operator pendingOperator = null;

    /** Operadores soportados por la calculadora. */
    public enum Operator { ADD, SUBTRACT, MULTIPLY, DIVIDE }

    /**
     * Restablece el estado a su valor inicial (como si se pulsara 'C').
     */
    public void clearAll() {
        accumulator = BigDecimal.ZERO;
        pendingOperand = null;
        pendingOperator = null;
    }

    /**
     * Texto listo para mostrarse en la vista. Elimina ceros a la derecha
     * y usa representación decimal convencional.
     */
    public String getDisplay() {
        return accumulator.stripTrailingZeros().toPlainString();
    }

    /**
     * Introduce un dígito o el punto decimal.
     *
     * - Si se recibe '.', lo añade solo si aún no hay parte decimal.
     * - Si el valor actual es 0 y llega un dígito, se reemplaza.
     */
    public void inputDigit(String digit) {
        if (digit.equals(".")) {
            if (!accumulator.toPlainString().contains(".")) {
                accumulator = new BigDecimal(accumulator.toPlainString() + ".");
            }
            return;
        }

        String current = accumulator.toPlainString();
        if (current.equals("0")) {
            current = digit;
        } else {
            current += digit;
        }
        accumulator = new BigDecimal(current);
    }

    /**
     * Guarda el operador elegido y prepara el siguiente operando.
     */
    public void setOperator(Operator operator) {
        pendingOperand = accumulator;
        pendingOperator = operator;
        accumulator = BigDecimal.ZERO;
    }

    /**
     * Evalúa la expresión pendiente y actualiza el estado del modelo.
     *
     * Funcionamiento paso a paso:
     * 1) Si no hay operador u operando pendiente, no hace nada (idempotente).
     * 2) Aplica el operador pendiente entre:
     *    - {@code pendingOperand} (primer operando, el que se tecleó antes de elegir el operador)
     *    - {@code accumulator}   (segundo operando, lo tecleado tras elegir el operador)
     * 3) El resultado se guarda en {@code accumulator} para poder encadenar operaciones
     *    o mostrarlo directamente en la vista mediante {@link #getDisplay()}.
     * 4) El estado temporal ({@code pendingOperator} y {@code pendingOperand}) se limpia.
     *
     * Detalles de implementación y decisiones:
     * - División: si el divisor es 0 se devuelve 0 por simplicidad (evita lanzar
     *   una excepción en la UI). Si no es 0 se divide con 10 decimales y
     *   {@link RoundingMode#HALF_UP} que es el redondeo "convencional".
     * - Precisión: se eligieron 10 decimales pensando en una calculadora básica;
     *   puedes ajustar fácilmente este valor según sea necesario.
     */
    public void evaluate() {
        if (pendingOperator == null || pendingOperand == null) {
            return;
        }
        switch (pendingOperator) {
            case ADD -> accumulator = pendingOperand.add(accumulator);
            case SUBTRACT -> accumulator = pendingOperand.subtract(accumulator);
            case MULTIPLY -> accumulator = pendingOperand.multiply(accumulator);
            case DIVIDE -> {
                if (accumulator.compareTo(BigDecimal.ZERO) == 0) {
                    // División por cero: reseteamos a 0 por simplicidad.
                    accumulator = BigDecimal.ZERO;
                } else {
                    // 10 decimales de precisión con redondeo estándar.
                    accumulator = pendingOperand.divide(
                        accumulator, 10, RoundingMode.HALF_UP
                    );
                }
            }
        }
        pendingOperator = null;
        pendingOperand = null;
    }
}


