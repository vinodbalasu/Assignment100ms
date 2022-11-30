package ms.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    public static final Properties properties = new Properties();



    public static void loadProperties() {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try(InputStream resourceStream = loader.getResourceAsStream("properties/prod_in_region.properties")) {
            properties.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStreamingBaseUrl() {
        return properties.get("streaming_base_url").toString();
    }

    public static String getSessionsBaseUrl() {
        return properties.get("sessions_base_url").toString();
    }
}
