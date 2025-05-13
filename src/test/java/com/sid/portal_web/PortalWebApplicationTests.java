package com.sid.portal_web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"DB_URL=jdbc:postgresql://localhost:5432/sid_portalweb_test",
		"DB_USERNAME=kgnot",
		"DB_PASSWORD=1234"
})
class PortalWebApplicationTests {

	@Test
	void contextLoads() {
	}

}
