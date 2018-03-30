# InSilico


## Installation
```
git clone https://github.com/draeger-lab/insilico.git
cd insilico
mvn clean verify
```

## Start
The software is build in the following folders:
```
cd releng/org.draegerlab.product/target/products/insilico/
```
Go to the folder for your architecture
* `linux/linux/gtk/x86[_64]/insilico/`
* `macosx/cocoa/x86_64/insilico.app/Contents/MacOS/`
* `win32/win32/x86[_64]/insilico/`

and, depending on your operating system, start with
* `./eclipse` or
* `eclipse.exe`
