clean:
	rm -rf target/
	rm -f *~

javadoc:
	rm -rf docs/*
	javadoc -d ./docs ./runtime/src/main/java/com/spnlangagent/langagent/*
