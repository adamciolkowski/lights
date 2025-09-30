package org.example;

import org.example.lu.DoolittleLuDecompositionAlgorithm;
import org.example.lu.LuDecomposition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoolittleLuDecompositionAlgorithmTest {

    @Nested
    class Decomposition {

        @Test
        void matrix3x3() {
            FloatMatrix A = new FloatMatrix(new float[][]{
                    {5, 3, 2},
                    {1, 2, 0},
                    {3, 0, 4},
            });
            LuDecomposition result = new DoolittleLuDecompositionAlgorithm().decompose(A);

            assertEquals(result.lower(), new FloatMatrix(new float[][]{
                    {1, 0, 0},
                    {1 / 5f, 1, 0},
                    {3 / 5f, -9 / 7f, 1},
            }));
            assertEquals(result.upper(), new FloatMatrix(new float[][]{
                    {5, 3, 2},
                    {0, 7 / 5f, -2 / 5f},
                    {0, 0, 16 / 7f},
            }));
        }
    }

    @Test
    void matrix3x3() {
        FloatMatrix A = new FloatMatrix(new float[][]{
                {5, 3, 2},
                {1, 2, 0},
                {3, 0, 4},
        });

        // input
        FloatMatrix y = new FloatMatrix(new float[][]{
                {10},
                {5},
                {-2},
        });

        FloatMatrix solution = new DoolittleLuDecompositionAlgorithm().solve(A, y);
        assertEquals(A.multiply(solution), y);
    }

    @Test
    void matrix4x4() {
        FloatMatrix A = new FloatMatrix(new float[][]{
                { 1, 0,  4, -6},
                { 2, 5,  0,  3},
                {-1, 2,  3,  5},
                { 2, 1, -2,  3},
        });

        // input
        FloatMatrix y = new FloatMatrix(new float[][]{
                {10},
                {5},
                {-2},
                {-3},
        });

        FloatMatrix solution = new DoolittleLuDecompositionAlgorithm().solve(A, y);
        assertEquals(A.multiply(solution), y);
    }

    @Test
    void matrix3x2() {
        FloatMatrix A = new FloatMatrix(new float[][]{
                {3, 4},
                {-5, 3},
                {5, 4},
        });

        // input
        FloatMatrix y = new FloatMatrix(new float[][]{
                {14},
                {-9},
                {23},
        });

        FloatMatrix solution = new DoolittleLuDecompositionAlgorithm().solve(A, y);
        assertEquals(A.multiply(solution), y);
    }



    @Disabled("work in progress - ignore")
    @Test
    void bigNonSquare() {
        FloatMatrix A = new FloatMatrix(new float[][]{
                {3, 1, -4},
                {6, -3, 10},
                {-9, 5, -11},
                {-3, 0, -7},
                {6, -4, 2},
        });

        FloatMatrix expectedL = new FloatMatrix(new float[][]{
                {1, 0, 0, 0, 0},
                {2, 1, 0, 0, 0},
                {-3, -8/5f, 1, 0, 0},
                {-1, -1/5f, -37/29f, 1, 0},
                {2, 6/5f, -2, 0, 1},
        });
        FloatMatrix expectedU = new FloatMatrix(new float[][]{
                {3, 1, -4},
                {0, -5, 18},
                {0, 0, 29 / 5f},
                {0, 0, 0},
                {0, 0, 0},
        });
//
//        // input
//        FloatMatrix y = new FloatMatrix(new float[][]{
//                {5},
//                {11},
//                {17},
//        });

//        FloatMatrix shouldEqualY = A.multiply(expectedResult);
//
        assertEquals(A, expectedL.multiply(expectedU));
        LuDecomposition result = new DoolittleLuDecompositionAlgorithm().decompose(A);
        assertEquals(expectedL, result.lower());
        assertEquals(expectedU, result.upper());
//        FloatMatrix multiply = A.multiply(solution);
//        assertEquals(multiply, y);
    }
}