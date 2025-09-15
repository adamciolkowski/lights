package org.example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DoolittleLuDecompositionAlgorithmTest {

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

    @Disabled("division by 0")
    @Test
    void noDecomposition() {
        FloatMatrix A = new FloatMatrix(new float[][]{
                {2, 8, 4, 1},
                {1, 4, 3, 3},
                {1, 2, 6, 2},
                {1, 3, 4, 2},
        });

        LuDecomposition result = new DoolittleLuDecompositionAlgorithm().decompose(A);
        System.out.println(result.lower());
        System.out.println(result.upper());
    }

    @Test
    void Example_Matrix_for_which_LUP_Decomposition_Succeeds_but_LU_Decomposition_Fails() {
        FloatMatrix A = new FloatMatrix(new float[][]{
                {0, 1},
                {2, 1},
        });

        LuDecomposition result = new DoolittleLuDecompositionAlgorithm().decompose(A);
        System.out.println(result.lower());
        System.out.println(result.upper());
    }

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