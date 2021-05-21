package com.test.appcomposer;

import java.util.Properties;

import org.apache.openejb.api.configuration.PersistenceUnitDefinition;
import org.apache.openejb.testing.Application;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.Configuration;
import org.apache.openejb.testing.Default;
import org.apache.openejb.testing.Descriptor;
import org.apache.openejb.testing.Descriptors;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.SimpleLog;
import org.apache.openejb.testng.PropertiesBuilder;

import com.test.appcomposer.controllers.TestController;
import com.test.appcomposer.db.TestDb;
import com.test.appcomposer.producers.EntityManagerProducer;
import com.test.appcomposer.services.cors.ApplicationConfig;
import com.test.appcomposer.services.cors.TestService;

@Default
@SimpleLog
@PersistenceUnitDefinition
@EnableServices(jaxrs = true)
@Classes(cdi = true, context = "app", value = { ApplicationConfig.class, TestService.class, TestController.class,
		TestDb.class, EntityManagerProducer.class })
@Application
@Descriptors(@Descriptor(name = "persistence.xml", path = "META-INF/persistence.xml"))
public class AppDescriptor {
//	@RandomPort("http")
//	private URL base;

	@Configuration
	public Properties config() {
		return new PropertiesBuilder().p("db", "new://Resource?type=DataSource")
				.p("db.JdbcUrld", "jdbc:hsqldb:mem:test").p("cxf-rs.auth", "BASIC").build();
	}
}