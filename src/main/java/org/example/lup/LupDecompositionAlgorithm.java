package org.example.lup;

import org.example.FloatMatrix;

public interface LupDecompositionAlgorithm {

    LupDecomposition decompose(FloatMatrix a);
}
