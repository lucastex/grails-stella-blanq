grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {

    inherits("global") {
    }

    log "warn"

    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
    }

    dependencies {
		runtime 'br.com.caelum.stella:caelum-stella-core:1.2'
		runtime 'br.com.caelum.stella:caelum-stella-boleto:1.2'
    }

	plugins {
		build   ':maven-publisher:0.8.1'
	}

}