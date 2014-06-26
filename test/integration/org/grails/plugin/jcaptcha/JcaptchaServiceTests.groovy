package org.grails.plugin.jcaptcha

import grails.test.mixin.integration.IntegrationTestMixin
import grails.test.mixin.TestMixin
import org.junit.Test
import static org.hamcrest.CoreMatchers.instanceOf
import static org.junit.Assert.assertThat
import com.octo.captcha.service.CaptchaService
import java.awt.image.BufferedImage
import javax.sound.sampled.AudioInputStream

@TestMixin(IntegrationTestMixin)
class JcaptchaServiceTests {
	JcaptchaService jcaptchaService

	@Test
	void testJpeg(){
		String id='image'
		String sessionId='123'
		Locale locale = Locale.US
		CaptchaService captcha = jcaptchaService.getCaptchaService(id)
		Object challenge = captcha.getChallengeForID(sessionId, locale)
		assertThat(challenge, instanceOf(BufferedImage.class));
		byte[] data = jcaptchaService.challengeAsJpeg(challenge)
	}

	@Test
	void testWav(){
		String id='sound'
		String sessionId='123'
		Locale locale = Locale.US
		CaptchaService captcha = jcaptchaService.getCaptchaService(id)
		Object challenge = captcha.getChallengeForID(sessionId, locale)
		assertThat(challenge, instanceOf(AudioInputStream.class));
		byte[] data = jcaptchaService.challengeAsWav(challenge)
	}
	
}
