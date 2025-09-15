package org.example;

public class DoolittleLuDecompositionAlgorithm implements LuDecompositionAlgorithm {

    @Override
    public LuDecomposition decompose(FloatMatrix m) {
        FloatMatrix lower = FloatMatrix.identity(m.rows());
        FloatMatrix upper = new FloatMatrix(new float[m.rows()][m.columns()]);

        for (int i = 0; i < m.rows(); i++) {
            for (int j = i; j < m.columns(); j++) {
                float sum = 0;
                for (int k = 0; k <= i - 1; k++) {
                    sum += lower.get(i, k) * upper.get(k, j);
                }
                float value = m.get(i, j) - sum;
                upper.set(i, j, value);
            }
            for (int j = i + 1; j < m.rows(); j++) {
                float sum = 0;
                for (int k = 0; k <= i - 1; k++) {
                    try {
                        sum += lower.get(j, k) * upper.get(k, i);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        sum += 0;
                    }
                }
                float divisor = upper.get(i, i);
                if (divisor == 0) {
                    throw new IllegalArgumentException("division by zero");
                }
                float value = (m.get(j, i) - sum) / divisor;
                lower.set(j, i, value);
            }
        }

        return new LuDecomposition(lower, upper);
    }

    private static LuDecomposition decomposeV1(FloatMatrix m) {
        if (m.columns() != m.rows()) {
            throw new IllegalArgumentException("Matrix is not square");
        }
        FloatMatrix lower = FloatMatrix.identity(m.rows());
        FloatMatrix upper = new FloatMatrix(new float[m.rows()][m.columns()]);

        for (int i = 0; i < m.rows(); i++) {
            for (int j = i; j < m.columns(); j++) {
                float sum = 0;
                for (int k = 0; k <= i - 1; k++) {
                    sum += lower.get(i, k) * upper.get(k, j);
                }
                float value = m.get(i, j) - sum;
                upper.set(i, j, value);
            }
            for (int j = i + 1; j < m.columns(); j++) {
                float sum = 0;
                for (int k = 0; k <= i - 1; k++) {
                    sum += lower.get(j, k) * upper.get(k, i);
                }
                float divisor = upper.get(i, i);
                if (divisor == 0) {
                    throw new ArithmeticException("division by zero");
                }
                float value = (m.get(j, i) - sum) / divisor;
                lower.set(j, i, value);
            }
        }

        return new LuDecomposition(lower, upper);
    }
}
