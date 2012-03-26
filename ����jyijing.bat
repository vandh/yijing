cd bin
jar cf ..\jyijing.jar .

cd ..

copy /Y jyijing.jar D:\exe\shihuang\develop\jyijing.jar
copy /Y jyijing.jar D:\exe\shihuang\release\jyijing.jar
Xcopy /c/q/e/i/y database    D:\exe\shihuang\develop\database
copy /Y D:\exe\shihuang\develop\dat\qm.xml  dat\qm.xml
copy /Y D:\exe\shihuang\develop\dat\qm.dat  dat\qm.dat

del jyijing.jar

