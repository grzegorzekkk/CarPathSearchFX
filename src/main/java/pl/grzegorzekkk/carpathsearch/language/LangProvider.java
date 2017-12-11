package pl.grzegorzekkk.carpathsearch.language;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LangProvider {

    private static LangProvider instance;
    private Properties properties;

    public LangProvider() {
        properties = new Properties();
        try (InputStream in = getClass().getResourceAsStream("/language.properties")) {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LangProvider getInstance() {
        if (instance == null) {
            instance = new LangProvider();
        }
        return instance;
    }

    public String getMessage(String key) {
        return properties.getProperty(key);
    }
}
