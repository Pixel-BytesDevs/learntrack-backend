package org.tesis.modulodiagnostico.helpers;

public class LatexValidator {
    // Método que valida y corrige LaTeX
    public static String validateAndFix(String expression) {
        String fixedExpression = expression;

        // Realizamos la corrección LaTeX aquí (simplificado)
        fixedExpression = fixedExpression.replaceAll("sqrt\\(([^)]+)\\)", "\\\\sqrt{\\1}"); // Ejemplo de corrección de sqrt
        fixedExpression = fixedExpression.replaceAll("frac\\s+(\\S+)\\s+(\\S+)", "\\\\frac{\\1}{\\2}"); // Correción de fracciones

        // Agregar llaves en exponentes/subíndices sin llaves
        fixedExpression = fixedExpression.replaceAll("(\\w)\\^(\\w+)", "$1^{\\2}");

        // Verificación simple de que el LaTeX es válido (puedes usar un validador más robusto aquí, como Sympy)
        if (fixedExpression.contains("{") && fixedExpression.contains("}")) {
            return fixedExpression; // Si parece válido, lo devolvemos corregido
        } else {
            // Si no es válido, devolver la expresión corregida (aunque esto depende de tus necesidades)
            return fixedExpression; // Aquí se podría lanzar una excepción si fuera necesario
        }
    }
}
