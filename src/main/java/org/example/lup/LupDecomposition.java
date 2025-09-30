package org.example.lup;

import org.example.FloatMatrix;

public record LupDecomposition(FloatMatrix lower, FloatMatrix upper, FloatMatrix permutation) { }
