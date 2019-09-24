package org.accounting.config;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
    public Properties properties = new Properties();

    public Config() {
        try {
            String file = getClass().getClassLoader().getResource("database.properties").getFile();
            properties.load(new FileInputStream(file));
        } catch (Exception e) {
            System.out.println("Could not find database.properties");
            System.exit(1);
        }
    }
}
