#!/bin/bash

javac -cp src/ -d bin/ -Xlint:unchecked src/org/addressbook/tests/*.java src/org/addressbook/storage/*.java src/org/addressbook/main/*.java src/org/addressbook/ui/cli/menu/*.java src/org/addressbook/textutils/*.java
