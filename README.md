# JCaptcha Plugin

Makes using JCaptcha within a Grails app simple.

## JCaptcha

[JCaptcha](http://jcaptcha.sourceforge.net/) is an open source (LGPL) [captcha](http://en.wikipedia.org/wiki/Captcha) solution.
JCaptcha provides visual and audio challenges and could be extended to provide different kinds of challenges if you desire.

h2. Versions
<table>
<tr><td>Version</td><td>Minimum Grails Version</td></tr>
<tr><td>0.1</td><td>0.6</td></tr>
<tr><td>0.2</td><td>1.0-RC1</td></tr>
<tr><td>0.2.1</td><td>1.2.1</td></tr>
</table>

## The Plugin

The Grails JCaptcha plugin provides an easy way to define Captchas, display them and verify the response.

## Installation

The plugin is available from the official repository so can be installed via...
```
grails install-plugin jcaptcha
```

## Usage


### Defining Captchas

Captchas are defined in the `grails-app/conf/Config.groovy` file.
```groovy
jcaptchas {
	captcha1 = ...
	captcha2 = ...
}
```
Each `jcaptcha.*` entry must be an instance of [CaptchaService](http://jcaptcha.sourceforge.net/multiproject/jcaptcha-service/apidocs/com/octo/captcha/service/CaptchaService.html) which is responsible for generating challenges and verifying responses.

Some example Captchas ...
```groovy
import java.awt.Font
import java.awt.Color

import com.octo.captcha.service.multitype.GenericManageableCaptchaService
import com.octo.captcha.engine.GenericCaptchaEngine
import com.octo.captcha.image.gimpy.GimpyFactory
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator
import com.octo.captcha.component.image.color.SingleColorGenerator
import com.octo.captcha.component.image.textpaster.NonLinearTextPaster

import com.octo.captcha.service.sound.DefaultManageableSoundCaptchaService

jcaptchas {
	imageCaptcha = new GenericManageableCaptchaService(
		new GenericCaptchaEngine(
			new GimpyFactory(
				new RandomWordGenerator(
					'abcdefghijklmnopqrstuvwxyz1234567890'
				),
				new ComposedWordToImage(
					new RandomFontGenerator(
						20, // min font size
						30, // max font size
						[new Font('Arial', 0, 10)] as Font[]
					),
					new GradientBackgroundGenerator(
						140, // width
						35, // height
						new SingleColorGenerator(new Color(0, 60, 0)),
						new SingleColorGenerator(new Color(20, 20, 20))
					),
					new NonLinearTextPaster(
						6, // minimal length of text
						6, // maximal length of text
						new Color(0, 255, 0)
					)
				)
			)
		),
		180, // minGuarantedStorageDelayInSeconds
		180000 // maxCaptchaStoreSize
	)

	soundCaptcha = new DefaultManageableSoundCaptchaService()
}
```
Construction/configuration of captcha services is part of JCaptcha itself so refer to the [JCaptcha documentation](http://forge.octo.com/jcaptcha/confluence) on that.

## JCaptcha Controller

The plugin installs a controller called `JCaptchaController` . It's function is to **render** captcha challenges. It currently supports two rendering formats: jpeg and wav.

The controller supports URIs like `jcaptcha/jpeg/<captchaname>` and `jcaptcha/wav/<captchaname>` which will write the binary output to the output stream.


**NOTE:** Make sure that you don't try and render image captchas as sound or vice versa. If you attempt this you will get an IllegalArgumentException.

### JCaptcha Tags

The plugin makes tags available for jpeg and wav challenges.
```
<jcaptcha:jpeg name="<captchaname>" height="Xpx" width="Xpx" />    <!-- results in an img tag -->
<jcaptcha:wav name="<captchaname>" autostart="0" />    <!-- results in an embed tag -->
```
You can pass any other attributes that are accepted by the underlying HTML tags to the tags.

### JCaptcha Service

The JCaptchaService class is responsible for obtaining the instances of CaptchaService defined in your config. It is also used for verifying responses to challenges.
```groovy
class ExampleController {

	def jcaptchaService

	def index() {
		if (jcaptchaService.validateResponse('captchaName', session.id, params.captchaResponse)) {
			// User entered response correctly
		} else {
			// User got it wrong, OR THEY ARE A BOT!
		}
	}
}
```
Notice that you need to pass `session.id` to `validateResponse`. This is because the captcha service needs to match the response to the challenge to check the answer.
The key between the two is `session.id`.

## Example Application

Here is a fully functional [example application](https://github.com/stokito/grails-jcaptcha-demo) that uses the JCaptcha plugin.