all: build

build: Main.class
	cp main.sh main && chmod +x main

Main.class:
	javac Main.java Graph.java

clean:
	rm -rf main *.class