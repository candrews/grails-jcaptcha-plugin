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
	}

	dependencies {

		compile('com.octo.captcha:jcaptcha:1.0') {
			excludes 'servlet-api'
		}
	}

	plugins {
		build ':release:3.0.1', ':rest-client-builder:2.0.1', {
			export = false
		}
	}
}
