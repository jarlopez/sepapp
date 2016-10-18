# Swedish Event Planners
This is a web application to be used by the event-planning company Swedish Event Planners (SEP).

It leverages Spring Boot to support the functioanlities needed by SEP:
* Event planning request workflow
* Staff management
* Budget management
* Task maangement for specific teams
* Events history
* Role-based access to the various components

## Install
This project comes with a Gradle wrapper (gradlew) to assist in the installation and running of the application and its tests.

The general approach, irrespective of OS, should be:
1. Open up a terminal
1. Navigate to the directory containing this file and the project source code
1. Run the  project-provided ``gradlew`` file to install all dependencies
1. Launch the web application by running ``gradlew bootRun``
1. Wait until Spring has finished initializing and bootstrapping and for Tomcat to be up and running. You should see an ``INFO`` message from Tomcat to indicate that the application is ready to be used:

~~~~
    INFO 14987 --- [  restartedMain] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
    INFO 14987 --- [  restartedMain] com.sep.SepApplication                   : Started SepApplication in 8.365 seconds (JVM running for 8.72)
~~~~
### Windows Example
~~~~
>   cd Location\of\project
>   gradlew
        << Gradle will install all dependencies here. This might take a minute or so, depending on your Internet connection >>
>   gradlew bootRun
~~~~

### UNIX Example
~~~~
>   cd Location/of/project
>   ./gradlew
        << Gradle will install all dependencies here. This might take a minute or so, depending on your Internet connection >>
        Welcome to Gradle 3.1.
        To run a build, run gradlew <task> ...
        To see a list of available tasks, run gradlew tasks
        To see a list of command-line options, run gradlew --help
        To see more detail about a task, run gradlew help --task <task>

        BUILD SUCCESSFUL
        Total time: 42.823 secs

>   ./gradlew bootRun
~~~~

## Tests
After installing all dependencies, the project's associated tests can also be run. Do note that the tests under the ``functional`` package require that the application is already running.
~~~~
>   cd Location/of/project
>   ./gradlew test

~~~~

### Selenium Tests
 All tests under the ``functional`` package rely on a hardcoded path to a Firefox v.46 binary. Therefore, they will not run successfully on environments which do not have this binary in the hardcoded location.


## Authors
Written by Johan Mickos and Mallu Goswami as part of our course project for Modern Methods in Software Engineering at KTH Royal Institue of Technology.
