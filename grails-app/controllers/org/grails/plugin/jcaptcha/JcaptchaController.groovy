package org.grails.plugin.jcaptcha;

import java.awt.image.BufferedImage;

import javax.sound.sampled.AudioInputStream;

import org.codehaus.groovy.grails.web.util.WebUtils;

import com.octo.captcha.service.CaptchaService;

/**
 * Exposes actions that 'render' captcha challenges.
 * 
 * The 'id' used to identify the challenge is session.id. Therefore you need to use session.id when validating a response.
 * 
 * You typically won't need to use this class directly, take a look at the JcaptchaTabLib.
 * 
 * @author LD <ld@ldaley.com>
 */
class JcaptchaController 
{
	JcaptchaService jcaptchaService
	
	def jpeg(String id) {
		if (id == null) throw new IllegalStateException("JcaptchaController action called with no id (captcha name)")
		CaptchaService captcha = jcaptchaService.getCaptchaService(id)
		Object challenge = captcha.getChallengeForID(session.id, request.locale)
		if (challenge instanceof BufferedImage)
		{
			WebUtils.retrieveGrailsWebRequest().setRenderView(false)
			response.contentType = "image/jpeg"
			byte[] data = jcaptchaService.challengeAsJpeg(challenge)
			response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate,max-age=0")
			response.setHeader("Content-Length", data.length as String)
			response.setDateHeader("Expires", 0)
			response.outputStream.write(data)
			response.outputStream.flush()
			response.outputStream.close()
		}
		else
		{
			throw new IllegalArgumentException("Cannot render challenge ofcaptcha '${id}' as JPEG as it is not an image")
		}
	}
	
	def wav(String id) {
		if (id == null) throw new IllegalStateException("JcaptchaController action called with no id (captcha name)")
		CaptchaService captcha = jcaptchaService.getCaptchaService(id)
		Object challenge = captcha.getChallengeForID(session.id, request.locale)
		if (challenge instanceof AudioInputStream)
		{
			WebUtils.retrieveGrailsWebRequest().setRenderView(false)
			response.contentType = "audio/x-wav"
			byte[] data = jcaptchaService.challengeAsWav(challenge)
			response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate,max-age=0")
			response.setHeader("Content-Length", data.length as String)
			response.setDateHeader("Expires", 0)
			response.outputStream.write(data)
			response.outputStream.flush()
			response.outputStream.close()
		}
		else
		{
			throw new IllegalArgumentException("Cannot render challenge of captcha '${id}' as WAV as it is not audio")
		}
	}
}