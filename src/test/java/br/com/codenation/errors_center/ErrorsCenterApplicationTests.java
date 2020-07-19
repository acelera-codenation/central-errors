package br.com.codenation.errors_center;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
class ErrorsCenterApplicationTests {

	@Test
	void contextLoads() {
		assert true;
	}

}
