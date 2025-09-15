package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatMatrixTest {

    @Test
    void transpose() {
        FloatMatrix matrix = new FloatMatrix(new float[][]{
                {1, 2},
                {3, 4},
                {5, 6},
        });

        FloatMatrix transposed = matrix.transpose();

        assertEquals(new FloatMatrix(new float[][]{
                {1, 3, 5},
                {2, 4, 6},
        }), transposed);
    }

    @Test
    void multiply() {
        FloatMatrix a = new FloatMatrix(new float[][]{
                {1, 2},
                {3, 4},
                {5, 6},
        });
        FloatMatrix b = new FloatMatrix(new float[][]{
                {1},
                {2},
        });

        FloatMatrix product = a.multiply(b);

        assertEquals(new FloatMatrix(new float[][]{
                {5},
                {11},
                {17},
        }), product);
    }
}