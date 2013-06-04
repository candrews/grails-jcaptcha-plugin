import com.octo.captcha.service.CaptchaService;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import javax.imageio.*;
import javax.imageio.plugins.jpeg.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;

/**
 * Provides access to the captchas as well as provides some util
 * type methods to convert captchas to usable data.
 * 
 * @author LD <ld@ldaley.com>
 */
class JcaptchaService 
{
	/**
	 * Used to access the captchas defined as part of the app config.
	 */
	def grailsApplication
	
	/**
	 * Retrieves a captcha by name.
	 * 
	 * @param captchaName The 'key' of the captcha defined in config.
	 * @throws IllegalArgumentException If captchaName is null.
	 * @throws IllegalStateException If there is no captcha by that name.
	 * @returns The captcha service keyed by 'captchaName'
	 */
	CaptchaService getCaptchaService(String captchaName)
	{
		if (captchaName == null) throw IllegalArgumentException("'captchaName' cannot be null")
		def c = grailsApplication.config.jcaptchas[captchaName]
		if (c == null) throw new IllegalStateException("There is no jcaptcha defined with name '${captchaName}'")
		c
	}

	/**
	 * Used to verify the response to a challenge.
	 * 
	 * @param captchaName The key of the captcha
	 * @param id The identifier used when retrieving the challenge (often session.id)
	 * @param response What the user 'entered' to meet the challenge
	 * @return True if the response meets the challenge
	 * @see getCaptchaService()
	 */
	boolean validateResponse(captchaName, id, response)
	{
		def c = getCaptchaService(captchaName)
		c.validateResponseForID(id, response)
	}
	
	/**
	 * Utility routine to turn an image challenge into a JPEG stream.
	 * 
	 * @param challenge The image data
	 * @return A raw bunch of bytes which come together to be a JPEG.
	 */
	byte[] challengeAsJpeg(BufferedImage challenge)
	{
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream()
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(jpegOutputStream)
        ImageWriter jpegEncoder = (ImageWriter) ImageIO.getImageWritersByFormatName("JPEG").next()

        JPEGImageWriteParam param = new JPEGImageWriteParam(null)
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT)
        param.setCompressionQuality(new Float(1.0).floatValue())

        jpegEncoder.setOutput(imageOutputStream)
        jpegEncoder.write((IIOMetadata) null, new IIOImage(challenge, null, null), param)
        jpegOutputStream.toByteArray()
	}	

	/**
	 * Utility routine to turn a sound challenge into a WAV stream.
	 * 
	 * @param challenge The sound data
	 * @return A raw bunch of bytes which come together to be a WAV.
	 */	
	byte[] challengeAsWav(AudioInputStream challenge)
	{
		ByteArrayOutputStream soundOutputStream = new ByteArrayOutputStream()
		AudioSystem.write(challenge, AudioFileFormat.Type.WAVE, soundOutputStream)
		soundOutputStream.flush()
		soundOutputStream.close()
		soundOutputStream.toByteArray()
	}
}