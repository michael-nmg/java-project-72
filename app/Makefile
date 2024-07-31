.PHONY: build

install: clean checkstyle tests dist report

start:
	@clear
	@./build/install/app/bin/app

dist:
	@./gradlew installDist

tests:
	@./gradlew test

rebuild: clean build

report:
	@./gradlew jacocoTestReport

checkstyle:
	@./gradlew checkstyleMain
	@./gradlew checkstyleTest

build:
	@./gradlew build

clean:
	@./gradlew clean
	@clear

run:
	@./gradlew run
