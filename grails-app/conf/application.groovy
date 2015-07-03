jcaptchas {
	image = new com.octo.captcha.service.multitype.GenericManageableCaptchaService(
			new com.octo.captcha.engine.GenericCaptchaEngine(
					new com.octo.captcha.image.gimpy.GimpyFactory(
							new com.octo.captcha.component.word.wordgenerator.RandomWordGenerator("ABCDEFGHJKLMNPQRSTUVWXYZ123456789"),
							new com.octo.captcha.component.image.wordtoimage.ComposedWordToImage(
									new com.octo.captcha.component.image.fontgenerator.RandomFontGenerator(Integer.valueOf(20), Integer.valueOf(30)),
									new com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator(
											190, // width
											65, // height
											new com.octo.captcha.component.image.color.SingleColorGenerator(java.awt.Color.WHITE),
											new com.octo.captcha.component.image.color.SingleColorGenerator(java.awt.Color.WHITE)
									),
									new com.octo.captcha.component.image.textpaster.NonLinearTextPaster(7, 7, java.awt.Color.BLACK)) )
			),
			180, // minGuarantedStorageDelayInSeconds
			180000, // maxCaptchaStoreSize
			75000 // captchaStoreLoadBeforeGarbageCollection
	)

	// Uncomment this to enable the sound captcha, you must install FreeTTS for it to work though.
	sound = new com.octo.captcha.service.sound.DefaultManageableSoundCaptchaService(new com.octo.captcha.service.captchastore.FastHashMapCaptchaStore(),
			new com.octo.captcha.engine.GenericCaptchaEngine(
					new com.octo.captcha.sound.gimpy.GimpySoundFactory(
							// Word generator
							new com.octo.captcha.component.word.wordgenerator.RandomWordGenerator(
									"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
							),
							new com.octo.captcha.component.sound.wordtosound.FreeTTSWordToSound(new com.octo.captcha.component.sound.soundconfigurator.FreeTTSSoundConfigurator("kevin16",
								"com.sun.speech.freetts.en.us.cmu_time_awb.AlanVoiceDirectory,com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory",
								1.0f, 100, 100), 7, 7)
					)

			),
			80, 100000, 75000
	)

}
