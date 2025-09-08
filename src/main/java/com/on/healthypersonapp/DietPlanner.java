package com.on.healthypersonapp;

public class DietPlanner {


    private int proteinPercentage;
    private int fatPercentage;
    private int carbohydratePercentage;


    public DietPlanner(int proteinPercentage, int fatPercentage, int carbohydratePercentage) {
        if (proteinPercentage + fatPercentage + carbohydratePercentage != 100) {
            throw new RuntimeException("protein, fat and carbohydrate percentages must add up to 100!");
        }

        this.proteinPercentage = proteinPercentage;
        this.fatPercentage = fatPercentage;
        this.carbohydratePercentage = carbohydratePercentage;
    }


    public DietPlan calculateDiet(Person person) {
        int calories = this.calculateBMR(person);
        int protein = this.calculateProtein(calories);
        int fat = this.calculateFat(calories);
        int carbohydrate = this.calculateCarbohydrate(calories);

        return new DietPlan(calories, protein, fat, carbohydrate);
    }


    private int calculateProtein(int bmr) {
        return (int) Math.round(bmr * proteinPercentage / 400.0);
    }


    private int calculateFat(int bmr) {
        return (int) Math.round(bmr * fatPercentage / 900.0);
    }


    private int calculateCarbohydrate(int bmr) {
        return (int) Math.round(bmr * carbohydratePercentage / 400.0);
    }


    private int calculateBMR(Person person) {
        if (person.getGender() == Gender.MALE) {
            return (int) Math.round(
                    (66.5 + 13.8 * person.getWeight()
                            + 5.0 * person.getHeight() * 100
                            - 6.8 * person.getAge()) * 1.2
            );
        }

        return (int) Math.round(
                (655.1 + 9.6 * person.getWeight()
                        + 1.9 * person.getHeight() * 100
                        - 4.7 * person.getAge()) * 1.2
        );
    }


}