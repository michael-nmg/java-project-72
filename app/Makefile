.PHONY: build

tests:
	@./gradlew test

rebuild: clean build
	@clear

report:
	@./gradlew jacocoTestReport

checkstyle:
	@./gradlew checkstyleMain
	@./gradlew checkstyleTest

build:
	@./gradlew build

clean:
	@./gradlew clean

run:
	@./gradlew run
