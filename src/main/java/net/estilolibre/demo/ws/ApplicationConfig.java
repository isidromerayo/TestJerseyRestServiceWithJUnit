/**
 * 
 */
package net.estilolibre.demo.ws;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author isidromerayo
 *
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {

	@Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("jersey.config.server.provider.packages", "net.estilolibre.demo.ws.resource");
        return properties;
    }
}
