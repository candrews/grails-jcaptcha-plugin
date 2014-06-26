package org.grails.plugin.jcaptcha

import grails.test.mixin.integration.IntegrationTestMixin
import grails.test.mixin.TestMixin
import org.junit.Test
import org.junit.Before

@TestMixin(IntegrationTestMixin)
class JcaptchaControllerTests {
	JcaptchaController controller
	
	JcaptchaService jcaptchaService
	
	@Before
	void setUp() {
		controller = new JcaptchaController()
		controller.jcaptchaService = jcaptchaService
	}

	@Test
	void testJpeg(){
		controller.jpeg('image')
	}

	@Test
	void testWav(){
		controller.wav('sound')
	}
	
}
