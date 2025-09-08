package com.on.healthypersonapp;

import com.on.utils.Environment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Follow Arrange Act Assert (Given/When/Then) style w/o inlining the steps
 */
public class BMICalculatorTest extends BaseTest {


    @Nested
    class IsDiedRecommendedTests {


        @ParameterizedTest(name = "weight={0}, height={1}")
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void shouldReturnTrueWhenDietRecommended(double weight, double height) {
            boolean recommended = BMICalculator.isDietRecommended(weight, height);
            assertTrue(recommended);
        }


        @Test
        void shouldReturnFalseWhenDietNotRecommended() {
            double weight = 50;
            double height = 1.92;
            boolean recommended = BMICalculator.isDietRecommended(weight, height);
            assertFalse(recommended);
        }


        @Test
        void shouldThrowArithmeticExceptionWhenHeightZero() {
            double weight = 50;
            double height = 0.0;

            @SuppressWarnings({"ResultOfMethodCallIgnored"})
            Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

            assertThrows(ArithmeticException.class, executable);
        }


    }


    @Nested
    class FindPersonWithWorstBMITests {


        @Test
        void shouldReturnPersonWithWorstBMIWhenPersonListNotEmpty() {
            List<Person> personList = List.of(
                    new Person(1.80, 60.0),
                    new Person(1.82, 98.0),
                    new Person(1.82, 64.7)
            );

            Person personWorstBMI = BMICalculator.findPersonWithWorstBMI(personList);

            assertAll(
                    () -> assertEquals(1.82, personWorstBMI.getHeight()),
                    () -> assertEquals(98.0, personWorstBMI.getWeight())
            );
        }


        @Test
        void shouldReturnNullWhenPersonListEmpty() {
            List<Person> personList = Collections.emptyList();

            Person personWorstBMI = BMICalculator.findPersonWithWorstBMI(personList);

            assertNull(personWorstBMI);
        }


        @Test
        void shouldReturnPersonWithWorstBMIIn500MsWhenPersonListHas10000Elements() {
            assumeTrue(environment == Environment.PROD);

            List<Person> personList = new ArrayList<>();
            for (int i = 0; i < 10_000; i++) {
                personList.add(new Person(1 + i, 10 + i));
            }

            Executable executable = () -> BMICalculator.findPersonWithWorstBMI(personList);

            assertTimeout(Duration.ofMillis(500), executable);
        }


    }


    @Nested
    class GetBMIScoresTests {


        @Test
        void shouldReturnCorrectBMIScoreWhenPersonListNotEmpty() {
            List<Person> personList = List.of(
                    new Person(1.80, 60.0),
                    new Person(1.82, 98.0),
                    new Person(1.82, 64.7)
            );
            double[] expected = {18.52, 29.59, 19.53};

            double[] bmiScores = BMICalculator.getBMIScores(personList);

            assertArrayEquals(expected, bmiScores);
        }


    }


}
