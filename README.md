

# (ANC) accountable and controllable system

you need 

backend:
- java 8
- mysql
- maven

frontend:
- npm
- gulp
- bower


there are 5 main directory. 4 of them is maven module project which is
- accounting as core accounting system
- inventory as inventory module
- manufacture as manufacture module -> main application is in this module
- security as user management module (not used already)
- web as front end apps. this is angularjs application not an maven module project

# how to compile frontend?

go to web folder and run 

```
npm install
bower install
```
in development mode, you can use
```
gulp serve
```
to edit the javascript/html/css code while watching it in browser without refreshing the browser after code change

in production mode, you must run
```
gulp
```
to compile the frontend code (minifying processed and uglifying processed) and automatically put the result in
manufacture/src/main/resources/public folder

in this production mdoe, make sure you change the code in /Users/mirzaakhena/Documents/workspace/testing/anc/anc/web/src/appindex.constant.js

from 
```
.constant('SERVER_PATH', 'http://localhost:8080')
```
in to
```
.constant('SERVER_PATH', '')
```




# how to compile backend ?

if you already have a maven, goto root folder (where you can see that 5 folder) then you need to run 
```
mvn clean install
```
after that, goto manufacture directory and run
```
mvn spring-boot:run
```
make sure you know where to set the username password for database
setting username and password for mysql database in accounting/src/main/resources/application.properties

open browser http://localhost:8080



