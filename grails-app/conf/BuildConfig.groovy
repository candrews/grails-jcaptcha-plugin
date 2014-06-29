grails.project.dependency.resolver = "maven"


grails.project.work.dir = 'target'

grails.project.repos.grailsCentral.username = System.getenv("GRAILS_CENTRAL_USERNAME")
grails.project.repos.grailsCentral.password = System.getenv("GRAILS_CENTRAL_PASSWORD")

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()

		//com.jhlabs:imaging (a dependency of 'com.octo.captcha:jcaptcha:1.0') is not in Maven Central. Reported issue at https://issues.sonatype.org/browse/MVNCENTRAL-463
		mavenRepo 'http://maven.jahia.org/maven2/'

		//org.mobicents.external.freetts:freetts:1.2.2 is not in Maven Central.
		mavenRepo 'http://maven.it.su.se/nexus/content/groups/public/'
	}

	dependencies {

		compile('com.octo.captcha:jcaptcha:1.0') {
			excludes 'javax.servlet:servlet-api', 'com.jhlabs:imaging'
		}
		compile('com.jhlabs:filters:2.0.235-1')
		compile('com.octo.captcha:jcaptcha-extension-sound-freetts:1.0'){
			excludes 'com.octo.captcha:jcaptcha-common-test','com.sun.speech.freetts:freetts', 'com.octo.captcha:jcaptcha','com.jhlabs:imaging'
		}
        compile('org.mobicents.external.freetts:freetts:1.2.2',
            'org.mobicents.external.freetts:cmu_time_awb:1.2.2',
            'org.mobicents.external.freetts:cmu_us_kal:1.2.2',
            'org.mobicents.external.freetts:en_us:1.2.2',
            'org.mobicents.external.freetts:cmulex:1.2.2',
            'org.mobicents.external.freetts:cmutimelex:1.2.2'){
        }
	}

	plugins {
		build ':release:3.0.1', ':rest-client-builder:2.0.1', {
			export = false
		}
	}
}
