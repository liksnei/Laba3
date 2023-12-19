package Laba3;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step,
                            Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
// В данной модели два столбца
        return 3;
    }

    public int getRowCount() {
// Вычислить количество точек между началом и концом отрезка
// исходя из шага табулирования
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
// Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step * row;
        if (col == 0) {
// Если запрашивается значение 1-го столбца, то это X
            return x;
        }
        if (col == 2) {
            // Значение целой части многочлена в точке
            int intValue = (int)evaluatePolynomial(x);

            // Проверка, является ли целая часть квадратом целого числа
            boolean isSquare = isSquare(intValue);

            return isSquare;
        } else {

// Вычисление значения в точке по схеме Горнера.
            return evaluatePolynomial(x);
        }
    }

    private boolean isSquare(int number) {
        double sqrt = Math.sqrt(number);
        return sqrt == Math.floor(sqrt);
    }

    private double evaluatePolynomial(double x) {
        Double result = 0.0;

        for (int i = 0; i < coefficients.length; i++) {
            result = result * x + coefficients[i];
        }

        return result;
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
// Название 1-го столбца
                return "Значение X";
            case 1:
                return "Значение многочлена";

            default:
                return "Целая часть является квадратом";
        }
    }

    public Class<?> getColumnClass(int col) {
        if (col == 2)
            return Boolean.class;
// И в 1-ом и во 2-ом столбце находятся значения типа Double
        return Double.class;
    }
}
