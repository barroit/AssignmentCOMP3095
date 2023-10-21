plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "com.gbc"
version = "0.0.1-SNAPSHOT"

allprojects {
	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	java {
		sourceCompatibility = JavaVersion.VERSION_17
	}

	configurations {
		compileOnly {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-web")

		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")

		developmentOnly("org.springframework.boot:spring-boot-devtools")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

dependencies {
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
}
