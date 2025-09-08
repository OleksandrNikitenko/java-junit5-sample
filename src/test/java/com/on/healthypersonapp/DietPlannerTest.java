package com.on.healthypersonapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DietPlannerTest extends BaseTest {


    private DietPlanner dietPlanner;


    @BeforeEach
    void setup() {
        this.dietPlanner = new DietPlanner(20, 30, 50);
    }


    @Test
    void shouldReturnCorrectDietPlanWhenCorrectPerson() {
        Person person = new Person(1.82, 75.0, 26, Gender.MALE);
        DietPlan expected = new DietPlan(2202, 110, 73, 275);

        DietPlan actual = this.dietPlanner.calculateDiet(person);

        assertAll(() -> {
            assertEquals(expected.getCalories(), actual.getCalories());
            assertEquals(expected.getProtein(), actual.getProtein());
            assertEquals(expected.getFat(), actual.getFat());
            assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate());
        });
    }


}