package org.example.lup;

import org.example.FloatMatrix;

public class PartialPivotingLupDecompositionAlgorithm implements LupDecompositionAlgorithm {

     @Override
     public LupDecomposition decompose(FloatMatrix a) {
         int n = a.rows();
         // Initialize matrices
         FloatMatrix working = a.copy();  // Will become U
         FloatMatrix lower = FloatMatrix.identity(n);  // L matrix
         FloatMatrix permutation = FloatMatrix.identity(n);  // P matrix
         // For each column/row
         for (int k = 0; k < n - 1; k++) {
             // Find the row with maximum absolute value in column k (partial pivoting)
             int pivotRow = findPivotRow(working, k);
             // Swap rows if needed
             if (pivotRow != k) {
                 working = working.swapRows(k, pivotRow);
                 permutation = permutation.swapRows(k, pivotRow);
                 // Also swap the already computed part of L matrix
                 lower = swapLowerRows(lower, k, pivotRow);
             }
             // Check for zero pivot (matrix is singular)
             if (Math.abs(working.get(k, k)) < 1e-10) {
                 throw new IllegalArgumentException("Matrix is singular and cannot be decomposed");
             }
             // Perform elimination for rows below the pivot
             for (int i = k + 1; i < n; i++) {
                 // Calculate the multiplier
                 float multiplier = working.get(i, k) / working.get(k, k);
                 // Store the multiplier in L matrix
                 lower.set(i, k, multiplier);
                 // Eliminate the column entries below the pivot
                 for (int j = k; j < n; j++) {
                     float newValue = working.get(i, j) - multiplier * working.get(k, j);
                     working.set(i, j, newValue);
                 }
             }
         }
         return new LupDecomposition(lower, working, permutation);
     }
     /**
      * Find the row with the largest absolute value in column k, starting from row k
      */
     private int findPivotRow(FloatMatrix matrix, int k) {
         int n = matrix.rows();
         int pivotRow = k;
         float maxValue = Math.abs(matrix.get(k, k));
         for (int i = k + 1; i < n; i++) {
             float value = Math.abs(matrix.get(i, k));
             if (value > maxValue) {
                 maxValue = value;
                 pivotRow = i;
             }
         }
         return pivotRow;
     }
     /**
      * Swap rows in the L matrix, but only for the already computed lower triangular part
      */
     private FloatMatrix swapLowerRows(FloatMatrix lower, int row1, int row2) {
         FloatMatrix result = lower.copy();
         // Only swap the lower triangular part (columns 0 to min(row1,row2)-1)
         int maxCol = Math.min(row1, row2);
         for (int j = 0; j < maxCol; j++) {
             float temp = result.get(row1, j);
             result.set(row1, j, result.get(row2, j));
             result.set(row2, j, temp);
         }
         return result;
     }
 }