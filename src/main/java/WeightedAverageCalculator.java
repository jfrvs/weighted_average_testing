import java.util.List;
import java.util.ArrayList;

/**
 * A calculator for computing weighted averages of lists using corresponding weights.
 * This class provides methods to calculate weighted averages with comprehensive input validation.
 */
public class WeightedAverageCalculator {

    /**
     * Calculates the weighted average of a list of values using corresponding weights.
     *
     * @param values the list of values to average
     * @param weights the list of weights corresponding to each value
     * @return the weighted average
     * @throws IllegalArgumentException if lists are null, empty, different sizes, 
     *         contain invalid values, or if total weight is zero
     */
    public double calculateWeightedAverage(List<Double> values, List<Double> weights) {
        // Input validation
        validateInput(values, weights);
        
        // Handle single element case efficiently
        if (values.size() == 1) {
            return values.get(0);
        }
        
        double sumProduct = 0.0;
        double sumWeights = 0.0;
        
        for (int i = 0; i < values.size(); i++) {
            double value = values.get(i);
            double weight = weights.get(i);
            
            sumProduct += value * weight;
            sumWeights += weight;
        }
        
        // This should not happen due to validation, but added for safety
        if (sumWeights == 0.0) {
            throw new IllegalArgumentException("Total weight cannot be zero");
        }
        
        return sumProduct / sumWeights;
    }
    
    private void validateInput(List<Double> values, List<Double> weights) {
        // Check for null lists
        if (values == null || weights == null) {
            throw new IllegalArgumentException("Values and weights lists cannot be null");
        }
        
        // Check for empty lists
        if (values.isEmpty() || weights.isEmpty()) {
            throw new IllegalArgumentException("Values and weights lists cannot be empty");
        }
        
        // Check for size mismatch
        if (values.size() != weights.size()) {
            throw new IllegalArgumentException(
                String.format("Values and weights lists must have same size. Got %d values and %d weights", 
                    values.size(), weights.size()));
        }
        
        // Check for invalid values and weights
        for (int i = 0; i < values.size(); i++) {
            Double value = values.get(i);
            Double weight = weights.get(i);
            
            // Check for null values
            if (value == null || weight == null) {
                throw new IllegalArgumentException("Values and weights cannot contain null elements");
            }
            
            // Check for special double values
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                throw new IllegalArgumentException("Values cannot be NaN or infinite");
            }
            
            if (Double.isNaN(weight) || Double.isInfinite(weight)) {
                throw new IllegalArgumentException("Weights cannot be NaN or infinite");
            }
            
            // Check for negative weights
            if (weight < 0) {
                throw new IllegalArgumentException("Weights cannot be negative");
            }
        }
        
        // Check if all weights are zero
        boolean allWeightsZero = weights.stream().allMatch(weight -> weight == 0.0);
        if (allWeightsZero) {
            throw new IllegalArgumentException("At least one weight must be non-zero");
        }
    }
    
    /**
     * Convenience method for calculating weighted average of integer values.
     *
     * @param values the list of integer values to average
     * @param weights the list of weights corresponding to each value
     * @return the weighted average
     */
    public double calculateWeightedAverageWithIntValues(List<Integer> values, List<Double> weights) {
        List<Double> doubleValues = values.stream()
            .map(Integer::doubleValue)
            .toList();
        return calculateWeightedAverage(doubleValues, weights);
    }
    
    /**
     * Convenience method for calculating weighted average with integer weights.
     *
     * @param values the list of values to average
     * @param weights the list of integer weights corresponding to each value
     * @return the weighted average
     */
    public double calculateWeightedAverageWithIntWeights(List<Double> values, List<Integer> weights) {
        List<Double> doubleWeights = weights.stream()
            .map(Integer::doubleValue)
            .toList();
        return calculateWeightedAverage(values, doubleWeights);
    }
    
    /**
     * Convenience method for calculating weighted average with both integer values and weights.
     *
     * @param values the list of integer values to average
     * @param weights the list of integer weights corresponding to each value
     * @return the weighted average
     */
    public double calculateWeightedAverageWithInts(List<Integer> values, List<Integer> weights) {
        List<Double> doubleValues = values.stream()
            .map(Integer::doubleValue)
            .toList();
        List<Double> doubleWeights = weights.stream()
            .map(Integer::doubleValue)
            .toList();
        return calculateWeightedAverage(doubleValues, doubleWeights);
    }
    
    /**
     * Main method for demonstration and testing from command line.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        WeightedAverageCalculator calculator = new WeightedAverageCalculator();
        
        // Example usage
        List<Double> values = List.of(10.0, 20.0, 30.0);
        List<Double> weights = List.of(1.0, 2.0, 3.0);
        
        try {
            double result = calculator.calculateWeightedAverage(values, weights);
            System.out.printf("Weighted average of %s with weights %s is: %.2f%n", 
                values, weights, result);
        } catch (IllegalArgumentException e) {
            System.err.println("Error calculating weighted average: " + e.getMessage());
        }
        
        // Demonstrate with integers
        List<Integer> intValues = List.of(5, 10, 15);
        List<Integer> intWeights = List.of(1, 3, 1);
        
        try {
            double intResult = calculator.calculateWeightedAverageWithInts(intValues, intWeights);
            System.out.printf("Weighted average of %s with weights %s is: %.2f%n", 
                intValues, intWeights, intResult);
        } catch (IllegalArgumentException e) {
            System.err.println("Error calculating weighted average: " + e.getMessage());
        }
    }
}