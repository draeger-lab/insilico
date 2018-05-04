# InSilico


## Installation
```
git clone https://github.com/draeger-lab/insilico.git
git checkout dev

# Build dependencies
cd insilico/releng/org.insilico.maven.dependencies
mvn clean p2:site

# Build product
cd ../../
mvn clean verify
```

## Start from console
The software is build in the following folders:
```
cd releng/org.insilico.product/target/products/org.insilico.lab/
```

Go to the folder for your architecture
* `linux/gtk/x86[_64]/insilico/`
* `macosx/cocoa/x86_64/org.insilico.lab.app/Contents/MacOS/`
* `win32/win32/x86[_64]/insilico/`

and, depending on your operating system, start with
* `./InSilico` or
* `InSilico.exe`

## Start from eclipse
Install `Eclipse for RCP and RAP Developers (plugin developers)`.

The following additional eclipse plugins are required (eclipse marketplace)
* `Maven Tycho Utilities 1.0.0`

**Import project** via
The project can be imported from the git repository via
```
File -> Import -> Maven -> Existing Maven Projects
```
Select the repositoy root path.

**Activate project (dependencies)** via  
* Select `org.insilico.target.target`
* `Activate Target Platform` and `Reload Target Platform`

**Build project** via  
* Right click on `org.insilico.parent.pom.xml`
* Run as `Maven build..`
* Goals `clean verify`

**Run project** via  
* Select `org.insilico.product` -> `org.insilico.product`
* `Launch an Eclipse application`
* `Run`


Additional plugins
* update sites: http://download.eclipse.org/releases/oxygen and install (Eclipse e4 Model Tooling)
* Eclipse PDE (Plugin Development Environment) 3.10 Luna

`Preferences -> Java -> Compiler -> Errors/Warnings -> Deprecated and restriced API` (change to warnings)


