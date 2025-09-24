# Calculadora MVC con JavaFX

Aplicación simple de calculadora implementada con el patrón MVC usando JavaFX.

## Requisitos
- Java 17 (OpenJDK)
- Maven 3.8+

## Ejecutar

Compilar y ejecutar con el plugin de JavaFX:

```bash
mvn clean javafx:run
```

Si solo desea compilar el JAR:

```bash
mvn -DskipTests package
```

## Estructura MVC
- `com.example.calculator.model.CalculatorModel`: lógica y estado de la calculadora (Modelo)
- `calculator.fxml`: interfaz de usuario (Vista)
- `com.example.calculator.controller.CalculatorController`: maneja eventos y coordina con el Modelo (Controlador)

## Cómo funciona MVC en este proyecto (paso a paso)
1) Vista (FXML)
   - `calculator.fxml` define la UI: un `TextField` (display) y una rejilla de botones.
   - Cada botón dispara un `onAction` (por ejemplo `#onDigit`, `#onAdd`). La vista no contiene lógica de negocio.

2) Controlador (JavaFX Controller)
   - `CalculatorController` recibe los eventos de la vista.
   - Traduce la acción de UI a una llamada sobre el modelo: `inputDigit`, `setOperator`, `evaluate`, `clearAll`.
   - Después de cada acción invoca `updateDisplay()` para reflejar el nuevo estado en el `TextField`.
   - El controlador no realiza cálculos; sólo coordina.

3) Modelo (Dominio)
   - `CalculatorModel` mantiene el estado: `accumulator` (valor actual/resultado), `pendingOperand` y `pendingOperator`.
   - Métodos principales:
     - `inputDigit(String)`: construye el número que el usuario teclea.
     - `setOperator(Operator)`: guarda el primer operando y el operador; prepara el segundo operando.
     - `evaluate()`: aplica la operación pendiente y deja el resultado en el acumulador (10 decimales, `RoundingMode.HALF_UP`).
     - `clearAll()`: reinicia el estado (como pulsar "C").

4) Flujo típico de interacción
   - Usuario pulsa "2" → Vista lanza `#onDigit` → Controlador llama `model.inputDigit("2")` → `updateDisplay()` muestra "2".
   - Pulsa "+" → Controlador llama `model.setOperator(ADD)` y reinicia el display para el segundo número.
   - Pulsa "3" → `inputDigit("3")` → display muestra "3".
   - Pulsa "=" → `model.evaluate()` calcula `2 + 3` → acumulador queda en `5` → `updateDisplay()` muestra "5".

5) Beneficios del MVC aquí
   - Separación clara de responsabilidades: la UI puede cambiar sin tocar la lógica.
   - Testeabilidad: el `CalculatorModel` puede probarse sin JavaFX.
   - Reutilización: la misma lógica serviría para otra vista (CLI, web, etc.).

## Notas
- Usa JavaFX `${javafx.version}` definido en el `pom.xml`.
- La clase principal es `com.example.App`.
