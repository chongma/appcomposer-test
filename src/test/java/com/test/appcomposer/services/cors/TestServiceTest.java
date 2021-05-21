package com.test.appcomposer.services.cors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import javax.ws.rs.NotAuthorizedException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.openejb.core.security.SecurityServiceImpl;
import org.apache.openejb.jee.WebApp;
import org.apache.openejb.spi.SecurityService;
import org.apache.openejb.testing.Component;
import org.apache.openejb.testing.Module;
import org.apache.openejb.testing.SingleApplicationComposerRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

//@EnableServices(value = "jaxrs")
//@RunWith(ApplicationComposer.class)
//@Descriptors(@Descriptor(name = "persistence.xml", path = "META-INF/persistence.xml"))
@RunWith(SingleApplicationComposerRunner.class)
public class TestServiceTest {

	public String someRole;

	@Component
	public SecurityService<?> serviceService() {
		return new SecurityServiceImpl() {
			@Override
			public boolean isCallerInRole(final String role) {
				return super.isCallerInRole(role) || someRole.equals(role);
			}
		};
	}

//	@Configuration
//	public Properties config() {
//		return new PropertiesBuilder().p("db", "new://Resource?type=DataSource")
//				.p("db.JdbcUrld", "jdbc:hsqldb:mem:test").p("cxf-rs.auth", "BASIC").build();
//	}

	@Module
//	@Classes(cdi = true, value = { ApplicationConfig.class, TestService.class, TestController.class, TestDb.class,
//			EntityManagerProducer.class })
	public WebApp app() {
		return new WebApp().contextRoot("/");
	}

	@Test
	public void testSelectHello() throws IOException {
		someRole = "general";
		final String message = WebClient.create("http://localhost:4204").path("/cors/test/hello").get(String.class);
		assertEquals("Hello world 2", message);
	}

	@Test
	public void testSelectHello2() throws IOException {
		someRole = "client";
		assertThrows(NotAuthorizedException.class,
				() -> WebClient.create("http://localhost:4204").path("/cors/test/hello").get(String.class));
	}

}
