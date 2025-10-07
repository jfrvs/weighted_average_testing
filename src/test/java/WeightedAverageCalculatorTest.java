import net.jqwik.api.*;
import net.jqwik.api.constraints.Positive;
import net.jqwik.api.constraints.Size;
import net.jqwik.api.constraints.DoubleRange;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

class WeightedAverageCalculatorTest {

    // Initialize directly - this works for both JUnit and jqwik tests
    private WeightedAverageCalculator calculator = new WeightedAverageCalculator();

    // Contract-based tests (JUnit 5)
    @Test
    void calculateWeightedAverage_basicCase_returnsCorrectValue() {
        // Given
        List<Double> values = Arrays.asList(10.0, 20.0, 30.0);
        List<Double> weights = Arrays.asList(1.0, 2.0, 3.0);
        
        // When
        double result = calculator.calculateWeightedAverage(values, weights);
        
        // Then
        double expected = (10.0*1.0 + 20.0*2.0 + 30.0*3.0) / (1.0 + 2.0 + 3.0);
        assertEquals(expected, result, 0.0001);
    }

    @Test
    void calculateWeightedAverage_equalWeights_returnsRegularAverage() {
        // Given
        List<Double> values = Arrays.asList(10.0, 20.0, 30.0);
        List<Double> weights = Arrays.asList(1.0, 1.0, 1.0);
        
        // When
        double result = calculator.calculateWeightedAverage(values, weights);
        
        // Then
        double expected = (10.0 + 20.0 + 30.0) / 3.0;
        assertEquals(expected, result, 0.0001);
    }

    @Test
    void calculateWeightedAverage_singleElement_returnsTheValue() {
        // Given
        List<Double> values = List.of(42.0);
        List<Double> weights = List.of(5.0);
        
        // When
        double result = calculator.calculateWeightedAverage(values, weights);
        
        // Then
        assertEquals(42.0, result, 0.0001);
    }

    @Test
    void calculateWeightedAverage_zeroWeights_throwsException() {
        // Given
        List<Double> values = Arrays.asList(10.0, 20.0);
        List<Double> weights = Arrays.asList(0.0, 0.0);
        
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> calculator.calculateWeightedAverage(values, weights));
    }

    @Test
    void calculateWeightedAverage_differentSizeLists_throwsException() {
        // Given
        List<Double> values = Arrays.asList(10.0, 20.0);
        List<Double> weights = Arrays.asList(1.0, 2.0, 3.0);
        
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> calculator.calculateWeightedAverage(values, weights));
    }

    @Test
    void calculateWeightedAverage_emptyLists_throwsException() {
        // Given
        List<Double> values = Collections.emptyList();
        List<Double> weights = Collections.emptyList();
        
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> calculator.calculateWeightedAverage(values, weights));
    }

    @Test
    void calculateWeightedAverage_nullLists_throwsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> calculator.calculateWeightedAverage(null, Arrays.asList(1.0)));
        
        assertThrows(IllegalArgumentException.class, 
            () -> calculator.calculateWeightedAverage(Arrays.asList(1.0), null));
    }

    @Test
    void calculateWeightedAverage_negativeWeights_throwsException() {
        // Given
        List<Double> values = Arrays.asList(10.0, 20.0);
        List<Double> weights = Arrays.asList(1.0, -1.0);
        
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> calculator.calculateWeightedAverage(values, weights));
    }

    @Test
    void calculateWeightedAverage_withNaNValues_throwsException() {
        // Given
        List<Double> values = Arrays.asList(10.0, Double.NaN);
        List<Double> weights = Arrays.asList(1.0, 2.0);
        
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> calculator.calculateWeightedAverage(values, weights));
    }

    @Test
    void calculateWeightedAverage_withInfiniteValues_throwsException() {
        // Given
        List<Double> values = Arrays.asList(10.0, Double.POSITIVE_INFINITY);
        List<Double> weights = Arrays.asList(1.0, 2.0);
        
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> calculator.calculateWeightedAverage(values, weights));
    }

    @Test
    void calculateWeightedAverageWithIntValues_withIntegerValues_worksCorrectly() {
        // Given
        List<Integer> values = Arrays.asList(10, 20, 30);
        List<Double> weights = Arrays.asList(1.0, 2.0, 3.0);
        
        // When
        double result = calculator.calculateWeightedAverageWithIntValues(values, weights);
        
        // Then
        double expected = (10.0*1.0 + 20.0*2.0 + 30.0*3.0) / (1.0 + 2.0 + 3.0);
        assertEquals(expected, result, 0.0001);
    }

    @Test
    void calculateWeightedAverageWithIntWeights_withIntegerWeights_worksCorrectly() {
        // Given
        List<Double> values = Arrays.asList(10.0, 20.0, 30.0);
        List<Integer> weights = Arrays.asList(1, 2, 3);
        
        // When
        double result = calculator.calculateWeightedAverageWithIntWeights(values, weights);
        
        // Then
        double expected = (10.0*1.0 + 20.0*2.0 + 30.0*3.0) / (1.0 + 2.0 + 3.0);
        assertEquals(expected, result, 0.0001);
    }

    @Test
    void calculateWeightedAverageWithInts_withIntegerValuesAndWeights_worksCorrectly() {
        // Given
        List<Integer> values = Arrays.asList(10, 20, 30);
        List<Integer> weights = Arrays.asList(1, 2, 3);
        
        // When
        double result = calculator.calculateWeightedAverageWithInts(values, weights);
        
        // Then
        double expected = (10.0*1.0 + 20.0*2.0 + 30.0*3.0) / (1.0 + 2.0 + 3.0);
        assertEquals(expected, result, 0.0001);
    }

    // Property-based tests (jqwik) - with reasonable value ranges
    @Property(tries = 100)
    void weightedAverageShouldBeBetweenMinAndMax(
            @ForAll @Size(min = 1, max = 10) List<@DoubleRange(min = -1000.0, max = 1000.0) Double> valuesList,
            @ForAll @Size(min = 1, max = 10) List<@DoubleRange(min = 0.1, max = 100.0) @Positive Double> weightsList) {
        
        // Ensure lists have same size
        int size = Math.min(valuesList.size(), weightsList.size());
        List<Double> values = valuesList.subList(0, size);
        List<Double> weights = weightsList.subList(0, size);
        
        if (size > 0) {
            double result = calculator.calculateWeightedAverage(values, weights);
            double minValue = values.stream().min(Double::compareTo).get();
            double maxValue = values.stream().max(Double::compareTo).get();
            
            assertTrue(result >= minValue && result <= maxValue,
                "Weighted average should be between min and max values. Result: " + result + 
                ", min: " + minValue + ", max: " + maxValue + ", values: " + values + ", weights: " + weights);
        }
    }

    @Property(tries = 100)
    void weightedAverageWithEqualWeightsEqualsRegularAverage(
            @ForAll @Size(min = 1, max = 10) List<@DoubleRange(min = -1000.0, max = 1000.0) Double> values) {
        
        List<Double> weights = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            weights.add(1.0);
        }
        
        double weightedAverage = calculator.calculateWeightedAverage(values, weights);
        double regularAverage = values.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
        
        assertEquals(regularAverage, weightedAverage, 0.0001,
            "Weighted average with equal weights should equal regular average. " +
            "Weighted: " + weightedAverage + ", Regular: " + regularAverage + ", values: " + values);
    }

    @Property(tries = 100)
    void scalingWeightsDoesNotChangeResult(
            @ForAll @Size(min = 1, max = 10) List<@DoubleRange(min = -1000.0, max = 1000.0) Double> values,
            @ForAll @Size(min = 1, max = 10) List<@DoubleRange(min = 0.1, max = 100.0) @Positive Double> weights,
            @ForAll @DoubleRange(min = 0.1, max = 10.0) @Positive double scale) {
        
        // Ensure lists have same size
        int size = Math.min(values.size(), weights.size());
        List<Double> sizedValues = values.subList(0, size);
        List<Double> sizedWeights = weights.subList(0, size);
        
        // Scale weights
        List<Double> scaledWeights = new ArrayList<>();
        for (Double weight : sizedWeights) {
            scaledWeights.add(weight * scale);
        }
        
        double originalAvg = calculator.calculateWeightedAverage(sizedValues, sizedWeights);
        double scaledAvg = calculator.calculateWeightedAverage(sizedValues, scaledWeights);
        
        assertEquals(originalAvg, scaledAvg, 0.0001,
            "Scaling weights should not change the weighted average. " +
            "Original: " + originalAvg + ", Scaled: " + scaledAvg + 
            ", values: " + sizedValues + ", weights: " + sizedWeights + ", scale: " + scale);
    }

    @Property(tries = 100)
    void singleElementListReturnsThatElement(
            @ForAll @DoubleRange(min = -1000.0, max = 1000.0) double value, 
            @ForAll @DoubleRange(min = 0.1, max = 100.0) @Positive double weight) {
        
        List<Double> values = List.of(value);
        List<Double> weights = List.of(weight);
        
        double result = calculator.calculateWeightedAverage(values, weights);
        
        assertEquals(value, result, 0.0001,
            "Single element list should return that element. " +
            "Expected: " + value + ", Got: " + result + ", weight: " + weight);
    }

    @Property(tries = 100)
    void weightedAverageRespectsWeightImportance(
            @ForAll @Size(2) List<@DoubleRange(min = -1000.0, max = 1000.0) Double> values,
            @ForAll @Size(2) List<@DoubleRange(min = 0.1, max = 100.0) @Positive Double> weights) {
        
        // Ensure values are distinct for meaningful test
        if (Math.abs(values.get(0) - values.get(1)) > 0.001) {
            double result = calculator.calculateWeightedAverage(values, weights);
            
            // The result should be closer to the value with higher weight
            double value1 = values.get(0);
            double value2 = values.get(1);
            double weight1 = weights.get(0);
            double weight2 = weights.get(1);
            
            double dist1 = Math.abs(result - value1);
            double dist2 = Math.abs(result - value2);
            
            if (weight1 > weight2 && weight1 / weight2 > 1.1) { // Significant weight difference
                assertTrue(dist1 < dist2,
                    "Result should be closer to value with higher weight. " +
                    "Result: " + result + ", value1: " + value1 + " (weight: " + weight1 + 
                    "), value2: " + value2 + " (weight: " + weight2 + "), " +
                    "distance1: " + dist1 + ", distance2: " + dist2);
            } else if (weight2 > weight1 && weight2 / weight1 > 1.1) {
                assertTrue(dist2 < dist1,
                    "Result should be closer to value with higher weight. " +
                    "Result: " + result + ", value1: " + value1 + " (weight: " + weight1 + 
                    "), value2: " + value2 + " (weight: " + weight2 + "), " +
                    "distance1: " + dist1 + ", distance2: " + dist2);
            }
            // If weights are similar (within 10%), no assertion (could be equidistant)
        }
    }
}