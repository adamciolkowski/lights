package org.example;

import java.util.Arrays;

public class FloatMatrix {

    private static final double EPSILON = 1e-5;

    private final float[][] matrix;

    public FloatMatrix(float[][] matrix) {
        this.matrix = matrix;
    }

    public static FloatMatrix zeros(int size) {
        return zeros(size, size);
    }

    public static FloatMatrix zeros(int rows, int cols) {
        return new FloatMatrix(new float[rows][cols]);
    }

    public static FloatMatrix identity(int size) {
        FloatMatrix m = zeros(size);
        for (int i = 0; i < size; i++) {
            m.set(i, i, 1);
        }
        return m;
    }

    public int columns() {
        return matrix[0].length;
    }

    public int rows() {
        return matrix.length;
    }

    public void set(int row, int col, float value) {
        matrix[row][col] = value;
    }

    public float get(int row, int col) {
        return matrix[row][col];
    }

    public FloatMatrix divideBy(float scalar) {
        FloatMatrix result = new FloatMatrix(new float[rows()][columns()]);
        for (int r = 0; r < rows(); r++) {
            for (int c = 0; c < columns(); c++) {
                result.set(r, c, get(r, c) / scalar);
            }
        }
        return result;
    }

    public float determinant() {
        if (columns() != rows()) {
            throw new IllegalArgumentException("Matrix is not square: " + columns() + " != " + rows());
        }
        if (columns() == 2) {
            return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
        }
        throw new UnsupportedOperationException();
    }

    private FloatMatrix adjoint() {
        throw new UnsupportedOperationException();
    }

    public FloatMatrix transpose() {
        float[][] matrix = new float[this.matrix[0].length][this.matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = this.matrix[j][i];
            }
        }
        return new FloatMatrix(matrix);
    }

    public FloatMatrix multiply(FloatMatrix m) {
        FloatMatrix a = this;
        FloatMatrix b = m;
        if (a.columns() != b.rows()) {
            throw new IllegalArgumentException("Incompatible dimensions");
        }
        float[][] matrix = new float[a.rows()][b.columns()];
        for (int i = 0; i < a.rows(); i++) {
            for (int j = 0; j < b.columns(); j++) {
                for (int k = 0; k < a.columns(); k++) {
                    matrix[i][j] += a.get(i, k) * b.get(k, j);
                }
            }
        }
        return new FloatMatrix(matrix);
    }

    public FloatMatrix copy() {
        float[][] copy = matrix.clone();
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return new FloatMatrix(copy);
    }

    public FloatMatrix inverse() {
        FloatMatrix adj = adjoint();
        float det = determinant();
        return adj.divideBy(det);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloatMatrix myMatrix = (FloatMatrix) o;
        int length = matrix.length;
        if (myMatrix.matrix.length != length)
            return false;

        for (int i = 0; i < length; i++) {
            float[] e1 = matrix[i];
            float[] e2 = myMatrix.matrix[i];
            if (!equalsWithEpsilon(e1, e2)) {
                return false;
            }
        }
        return true;
    }

    private static boolean equalsWithEpsilon(float[] a, float[] b) {
        for (int i = 0; i < a.length; i++) {
            if (Math.abs(a[i] - b[i]) > EPSILON) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(matrix);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (float[] ints : matrix) {
            sb.append(Arrays.toString(ints));
            sb.append('\n');
        }
        return sb.toString();
    }

    public FloatMatrix swapRows(int i, int j) {
        if (i == j) {
            return this;
        }
        FloatMatrix copy = copy();
        swap(copy.matrix, i, j);
        return copy;
    }

    private static void swap(float[][] array, int i, int j) {
        float[] tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public FloatMatrix submatrix(int fromRow, int toRow, int fromColumn, int toColumn) {
        FloatMatrix subMatrix = new FloatMatrix(new float[toRow - fromRow][toColumn - fromColumn]);
        for (int r = fromRow; r < toRow; r++) {
            for (int c = fromColumn; c < toColumn; c++) {
                float value = get(r, c);
                subMatrix.set(r - fromRow, c - fromColumn, value);
            }
        }
        return subMatrix;
    }

    public FloatMatrix subtract(FloatMatrix m) {
        FloatMatrix result = new FloatMatrix(new float[rows()][columns()]);
        for (int r = 0; r < rows(); r++) {
            for (int c = 0; c < columns(); c++) {
                result.set(r, c, get(r, c) - m.get(r, c));
            }
        }
        return result;
    }
}
