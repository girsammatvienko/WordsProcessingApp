<h1>Application description</h1>
The service layer abstraction is used to provide two presentation layers. The first presentation layer is an api (controller.api package), the second one is a web Thymeleaf\Javascript-based GUI (controller.gui package).

Database operations performed with Hibernate. In the application was used such database as MySql.
 *Access login: "root", password: "root"* 

Application uses Thymeleaf templates.

All layers tested with Junit and Mockito.

Front-end part tested with Selenium. *Using Chrome driver*
<h1>How to use GUI</h1>
There is one main page which can be accessed by following address: <a href = "http://localhost:8080/gui/getAll"> http://localhost:8080/gui/getAll</a>.
On the main page there is sorted in descending order by entries list of words and a field "Unique words" that 
displays amount of unique words in the database. 
GUI can handle incorrect inputs such as: *Payload cannot be empty*, *Input cannot consist only of numbers*.
<h1>Deploying on docker</h1>
To define a multi-container application in Docker you can use a docker-compose.yml compose file which provided in src directory by executing 
following command: *docker-compose docker-compose.yml up*.
Also there is a Docker image build script that contains all commands needed to build an image of the app. To build an image you should use
this command: *docker build -f DockerFile -t <desired name in lower case>*.
   
