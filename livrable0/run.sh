#!/bin/sh
java -cp lib/program.jar:. Generateur > output/index.html
diff data/index.html output/index.html