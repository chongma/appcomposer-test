package com.test.appcomposer.services.cors;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;

import javax.naming.NamingException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.openejb.core.security.SecurityServiceImpl;
import org.apache.openejb.jee.WebApp;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.spi.SecurityService;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.Component;
import org.apache.openejb.testing.Configuration;
import org.apache.openejb.testing.Descriptor;
import org.apache.openejb.testing.Descriptors;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.Module;
import org.apache.openejb.testng.PropertiesBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.test.appcomposer.controllers.TestController;
import com.test.appcomposer.db.TestDb;
import com.test.appcomposer.producers.EntityManagerProducer;

@EnableServices(value = "jaxrs")
@RunWith(ApplicationComposer.class)
@Descriptors(@Descriptor(name = "persistence.xml", path = "META-INF/persistence.xml"))
public class TestServiceTest {

//	@Module
//	public EnterpriseBean bean() {
//		return new SingletonBean(IsCallerInRoleBean.class).localBean();
//	}

//	@EJB
//	private IsCallerInRoleBean bean;

	@Component
	public SecurityService<?> serviceService() {
		return new SecurityServiceImpl() {
			@Override
			public boolean isCallerInRole(final String role) {
				return super.isCallerInRole(role) || "general".equals(role);
			}
		};
	}

	@Configuration
	public Properties config() throws NamingException {
		Properties p = new PropertiesBuilder().p("db", "new://Resource?type=DataSource")
				.p("db.JdbcUrld", "jdbc:hsqldb:mem:test").p("cxf-rs.auth", "BASIC").build();
//		Context context = new InitialContext(p);
		return p;
	}

	@Module
	@Classes(cdi = true, value = { ApplicationConfig.class, TestService.class, TestController.class, TestDb.class,
			EntityManagerProducer.class })
	public WebApp app() {
		return new WebApp().contextRoot("/");
	}

//	@Test
//	public void isLogged() throws LoginException {
//		final ThreadContext testContext = ThreadContext.getThreadContext();
//		testContext.set(AbstractSecurityService.SecurityContext.class, null);
//
//		final SecurityService securityService = SystemInstance.get().getComponent(SecurityService.class);
//		final Object id = securityService.login("testgeneral", "password");
//		securityService.associate(id);
//
//		assertTrue(bean.isinRole("general"));
//		assertFalse(bean.isinRole("whatever"));
//
//		securityService.disassociate();
//		securityService.logout(id);
//
//		ThreadContext.enter(testContext);
//	}

	@Test
	public void testSelectHello() throws IOException {
		final String message = WebClient.create("http://localhost:4204", "testgeneral", "password", null)
				.path("/cors/test/hello").get(String.class);
		assertEquals("Hello world 2", message);
	}

//	@Singleton
//	public static class IsCallerInRoleBean {
//		@Resource
//		private SessionContext ctx;
//
//		public boolean isinRole(final String role) {
//			return ctx.isCallerInRole(role);
//		}
//	}

}
