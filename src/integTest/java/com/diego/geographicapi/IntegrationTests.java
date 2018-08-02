package com.diego.geographicapi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class IntegrationTests {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void IntegrationTestExample() {

	}

}
