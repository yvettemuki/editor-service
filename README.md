# editor-service
backend for the typeflow-editor in mxGrapgh

#### Environment Requirement

Java 8

* if you are using Intellij Idea, you can just clone this project 
and use "import project", remember choose "Maven" after choosing the "Import project from external model"

#### Run the project

```
mvn spring-boot:run
```

Or just go to the 

```
EditorServiceApplicaton
``` 

and run the main function 

#### File Storage Use(=database:future will migrate to OSS service)
you must manually add the three folder in the project root directory

```models```
```picmodels```
```pictures```

#### The default port is 9090, if you want to change, go to the application.properties file