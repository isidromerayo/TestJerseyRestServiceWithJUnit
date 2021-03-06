package net.estilolibre.demo.ws.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import net.estilolibre.demo.ws.dto.Notification;

public class NotificationRestServiceIT extends JerseyTest {

	@Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(NotificationRestService.class);
    }
	@Test
    public void testFetchAll() {
        Response output = target("/notifications").request().get();
        assertEquals("should return status 200", 200, output.getStatus());
        assertNotNull("Should return list", output.getEntity());
        List<Notification> notification = output.readEntity(new GenericType<List<Notification>>() {});
        assertEquals(2, notification.size());
    }

    @Test
    public void testFetchBy(){
        Response output = target("/notifications/1").request().get();
        assertEquals("Should return status 200", 200, output.getStatus());
        assertNotNull("Should return notification", output.getEntity());
    }

    @Test
    public void testFetchByFail_DoesNotHaveDigit(){
        Response output = target("/notifications/no-id-digit").request().get();
        assertEquals("Should return status 404", 404, output.getStatus());
    }

    @Test
    public void testCreate(){
        Notification notification = new Notification(null, "Invoice was deleted");
        Response output = target("/notifications")
                .request()
                .post(Entity.entity(notification, MediaType.APPLICATION_JSON));

        assertEquals("Should return status 200", 200, output.getStatus());
        assertNotNull("Should return notification", output.getEntity());
    }

    @Test
    public void testUpdate(){
        Notification notification = new Notification(1, "New user created at Antwerp");
        Response output = target("/notifications")
                .request()
                .put(Entity.entity(notification, MediaType.APPLICATION_JSON));
        assertEquals("Should return status 204", 204, output.getStatus());
    }

    @Test
    public void testDelete(){
        Response output = target("/notifications/1").request().delete();
        assertEquals("Should return status 204", 204, output.getStatus());
    }
}
