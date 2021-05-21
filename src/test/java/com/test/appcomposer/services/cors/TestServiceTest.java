package com.test.appcomposer.services.cors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import javax.ws.rs.NotAuthorizedException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.openejb.testing.Application;
import org.apache.openejb.testing.SingleApplicationComposerRunner;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.test.appcomposer.AppDescriptor;

@RunWith(SingleApplicationComposerRunner.class)
public class TestServiceTest {

	@Application
	private AppDescriptor descriptor;

	@After
	public void resetRole() {
		descriptor.setSomeRole(null);
	}

	@Test
	public void testSelectHello() throws IOException {
		descriptor.setSomeRole("general");
		final String message = WebClient.create(descriptor.getBase().toExternalForm()).path("/cors/test/hello")
				.get(String.class);
		assertEquals("Hello world 2", message);
	}

	@Test
	public void testSelectHello2() throws IOException {
		descriptor.setSomeRole("client");
		assertThrows(NotAuthorizedException.class, () -> WebClient.create(descriptor.getBase().toExternalForm())
				.path("/cors/test/hello").get(String.class));
	}

}
