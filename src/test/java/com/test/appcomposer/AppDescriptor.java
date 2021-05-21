package com.test.appcomposer;

import java.net.URL;
import java.util.Objects;

import org.apache.openejb.api.configuration.PersistenceUnitDefinition;
import org.apache.openejb.core.security.SecurityServiceImpl;
import org.apache.openejb.spi.SecurityService;
import org.apache.openejb.testing.Application;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.Component;
import org.apache.openejb.testing.ContainerProperties;
import org.apache.openejb.testing.Default;
import org.apache.openejb.testing.Descriptor;
import org.apache.openejb.testing.Descriptors;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.RandomPort;
import org.apache.openejb.testing.SimpleLog;

@Default
@SimpleLog
@PersistenceUnitDefinition
@EnableServices(jaxrs = true)
@Classes(cdi = true, context = "/")
@Application
@ContainerProperties({ @ContainerProperties.Property(name = "test", value = "new://Resource?type=DataSource"),
		@ContainerProperties.Property(name = "test.JdbcUrl", value = "jdbc:hsqldb:mem:test"),
		@ContainerProperties.Property(name = "cxf-rs.auth", value = "BASIC") })
@Descriptors(@Descriptor(name = "persistence.xml", path = "META-INF/persistence.xml"))
public class AppDescriptor {
	@RandomPort("http")
	private URL base;

	private String someRole;

	public void setSomeRole(final String someRole) {
		this.someRole = someRole;
	}

	public URL getBase() {
		return base;
	}

	@Component
	public SecurityService<?> serviceService() {
		return new SecurityServiceImpl() {
			@Override
			public boolean isCallerInRole(final String role) {
				return super.isCallerInRole(role) || Objects.equals(someRole, role);
			}
		};
	}
}