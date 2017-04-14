# TigerIsland

Private repository for the Spring 2017 CEN3031 Semester Project, titled "Tiger Island"

# Complete Setup Tools and Instructions 

See the Maven file (pom.xml) for a full list of dependencies.

* Required
  * IntelliJ
    * Download: https://www.jetbrains.com/idea/#chooseYourEdition
    * Getting started: https://www.jetbrains.com/idea/documentation/
      * Not entirely necessary, but helpful.
  * Git
    * Download: https://git-scm.com/downloads
  * Clone the repository, if you do not already have it.
  * You may need to convert the project to a Maven project.
    * To do so, right click on the project folder (“TigerIsland”) and select “Add Framework Support”
    * Check “Maven” and hit “OK”
    * NOTE: It is likely best to close and reopen the project after completing this step.
  * Install required packages (specified by “pom.xml”)
    * To be able to compile code you will need the following installed:
      * Argparse4j
    * To be able to run unit tests you will need the following installed:
      * JUnit (4.11)
    * To be able to run acceptance tests you will need the following installed:
      * Cucumber-java
      * Cucumber-junit
    * **NOTE:** If the specified libraries are not automatically downloaded, try right-clicking the “pom.xml” file, scrolling down to “Maven”, and selecting an option there, likely “import/reimport”
* Not required (but highly recommended)
  * Plugins:
    * Gherkin
      * Prereq for "Cucumber for Java"
    * Cucumber for Java
      * Allows prompts and syntax checking for cucumber
    * Atlassian Clover for IDEA
      * Code coverage tool sitting atop JUnit

# Building and Running TigerIsland Client

## Building TigerIsland

1. File -> Save All
2. Run driver/class with main method (TigerIsland.java)
3. File -> Project Structure
4. Select Tab “Artifacts”
5. Click green plus button near top of window
6. Select JAR from Add drop down menu.
  a. Select “From modules with dependencies”
7. Form “Main Class” select “TigerIsland”
8. Under “JAR files from libraries” select “copy to the output directory and link via manifest
9. For “Directory for META-INF/MANIFEST.IMF” select “src\main\java\resources”
10. Press OK
11. From the main menu, select the build drop down
12. Select the option “build artifacts”

## Running TigerIsland

1. Go to the directory containing the .jar file
  a. Should be “out\artifacts\TigerIsland_jar”
2. Run using the command “java -jar TigerIsland.jar”
3. For help on proper usage, check “java -jar TigerIsland.jar --help”
  a. A screencap of the help text is provided below for reference.

## TigerIsland Command-Line

The following is an example configuration with which to run TigerIsland.

```{bash}
java -jar TigerIsland.jar --offline false --aiType JACKSAI_V2 --ipaddress localhost --port 1234 --tournamentPassword heygang --username TEAM_N --password PASS_N
```

Use the following help text to configure the TigerIsland client to your needs.

```{bash}
java -jar TigerIsland.jar --help
usage: TigerParser [-h] [-o {true,false}] [-t TURNTIME] [-i IPADDRESS]
                   [-p PORT] [--tournamentPassword TOURNAMENTPASSWORD]
                   [--username USERNAME] [--password PASSWORD]
                   [-m {true,false}] [-d {true,false}] [-a AITYPE]

Specify TigerIsland match globalSettings.

optional arguments:
  -h, --help             show this help message and exit
  -o {true,false}, --offline {true,false}
                         Toggle running system in  offline  mode,  AI v. AI
                         (default: true)
  -t TURNTIME, --turnTime TURNTIME
                         Specify the time  allowed  per turnState (default:
                         20.0)
  -i IPADDRESS, --ipaddress IPADDRESS
                         Specify the ip  address  of  the  TigerHost server
                         (default: localhost)
  -p PORT, --port PORT   Specify the  port  used  by  the  TigerHost server
                         (default: 6539)
  --tournamentPassword TOURNAMENTPASSWORD
                         Specify     tournament      password     (default:
                         tournamentPassword)
  --username USERNAME    Specify username  used  by  the  TigerHost  server
                         (default: username)
  --password PASSWORD    Specify password  used  by  the  TigerHost  server
                         (default: password)
  -m {true,false}, --manual {true,false}
                         Run the system  with  Player  1  as  a  human user
                         (default: false)
  -d {true,false}, --dummyFeed {true,false}
                         Run  with  dummy  values   in   the  inbound  feed
                         (default: false)
  -a AITYPE, --aiType AITYPE
                         Select AI type  from  choices:  'HUMAN', 'SAFEAI',
                         'JACKSAI(_V2)',         'TOTOROLINESAI(_V2)(_V3)',
                         'RANDOMAI',    and     'TIGERFORMAI'     (default:
                         TOTOROLINESAI_V2)
net.sourceforge.argparse4j.internal.HelpScreenException: Help Screen
```

# References

* [Building jars in IntelliJ properly](http://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly)
