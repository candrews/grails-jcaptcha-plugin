package grails.jcaptcha.plugin

import grails.plugins.Plugin

/**
 * Simplest. Plugin. Class. Ever.
 *
 * @author LD <ld@ldaley.com>
 */
class JcaptchaGrailsPlugin extends Plugin {
	def title = "Grails JCaptcha Plugin"
	def description = 'Makes using JCaptcha within a Grails app simple'
	def author = "Luke Daley"
	def authorEmail = "ld@ldaley.com"
	def documentation = "http://grails.org/plugin/jcaptcha"
	def developers = [[name: 'Luke Daley', email: 'ld@ldaley.com'], [name: 'Rohit Bishnoi', email: 'rbdharnia@gmail.com']]
	def grailsVersion = "3.0.2 > *"
	def profiles = ['web']

	def license = "APACHE"
	def issueManagement = [system: 'GitHub', url: 'https://github.com/candrews/grails-jcaptcha-plugin/issues']
	def scm = [url: 'https://github.com/candrews/grails-jcaptcha-plugin']
}
