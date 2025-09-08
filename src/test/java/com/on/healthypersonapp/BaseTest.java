package com.on.healthypersonapp;

import com.on.utils.Environment;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {


    protected static Environment environment;


    @BeforeAll
    public static void prepareEnv() {
        String env = System.getenv("env");
        switch (env == null ? "" : env.toLowerCase()) {
            case "p":
            case "prod":
            case "production":
                environment = Environment.PROD;
                break;
            case "t":
            case "test":
                environment = Environment.TEST;
                break;
            case "d":
            case "dev":
            case "development":
                environment = Environment.DEV;
                break;
            default:
                environment = Environment.DEV;
        }
    }


}
