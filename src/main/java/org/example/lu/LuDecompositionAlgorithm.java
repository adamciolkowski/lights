package org.example.lu;

import org.example.FloatMatrix;

public interface LuDecompositionAlgorithm {

    LuDecomposition decompose(FloatMatrix a);
}
