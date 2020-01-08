# editor-service
backend for the typeflow-editor (https://github.com/yvettemuki/typeflow-editor) in mxGraph

#### Environment Requirement

Java 8

* if you are using Intellij Idea, you can just clone this project 
and use "import project", remember choose "Maven" after choosing the "Import project from external model"

#### Run the project
You can use terminal

```
mvn spring-boot:run
```

Or just go to the 

```
EditorServiceApplicaton
``` 

and run the main function 

#### File Storage Use(=database:future will migrate to OSS service)
When you start up the project, the project will automatically or you can also add manually
####### including 3 folders below:

```models```
```picmodels```
```pictures```

#### The default port is 9090, if you want to change, go to the application.properties file
####### attention: when you change the backend port, remember change the font-end proxy file.