#! /bin/bash

# Find directory of this script
cd $(dirname $0)
cd ../releng/org.draegerlab.insilico.maven.dependencies

#echo "Update GraphStream Dependencies"
#cd gs-deps
#git pull
#mvn clean install
#cd ..

#echo "Update GraphStream JavaFX UI"
#cd gs-ui-javafx
#git pull
#mvn clean install
#cd ..

echo "Create P2 Repository"
mvn clean p2:site

# Done
echo "updated dependencies!"
echo "Please reload target platform"
