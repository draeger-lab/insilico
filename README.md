# InSilico


## Installation
```
git clone https://github.com/draeger-lab/insilico.git
git checkout dev
cd insilico
mvn clean verify
```

## Start from console
The software is build in the following folders:
```
cd releng/org.draegerlab.insilico.product/target/products/insilico/

```
Go to the folder for your architecture
* `linux/gtk/x86[_64]/insilico/`
* `macosx/cocoa/x86_64/insilico.app/Contents/MacOS/`
* `win32/win32/x86[_64]/insilico/`

and, depending on your operating system, start with
* `./InSilico` or
* `InSilico.exe`

## Start from eclipse
Install `Eclipse for RCP and RAP Developers" (plugin developers)`.

The following additional eclipse plugins are required (eclipse marketplace)
* `Kotlin Plugin for Eclipse`

**Import project** via  
The project can be imported from the git repository via
```
File -> Import -> Maven -> Existing Maven Projects
```
Select the repositoy root path.

**Activate project (dependencies)** via  
* Select `org.draegerlab.insilico.target.target`
* `Activate Target Platform` and `Reload Target Platform`

**Build project** via  
* Right click on `org.draegerlab.insilico.parent.pom.xml`
* Run as `Maven build..`
* Goals `clean verify`

**Run project** via  
* Select `org.draegerlab.insilico.product` -> `org.draegerlab.insilico.product`
* `Launch an Eclipse application`
* `Run`


Additional plugins
* update sites: http://download.eclipse.org/releases/oxygen and install (Eclipse e4 Model Tooling)
* Eclipse PDE (Plugin Development Environment) 3.10 Luna


