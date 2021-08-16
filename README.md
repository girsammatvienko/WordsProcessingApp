<h1>Application description</h1>
The service layer abstraction is used to provide two presentation layers. The first presentation layer is an api (controller.api package), the second one is a web Thymeleaf\Javascript-based GUI (controller.gui package).

Database operations performed with Hibernate. *Access login "root" 

Application uses Thymeleaf templates.

All layers tested with Junit and Mockito.

Front-end part tested with Selenium. *Using Chrome driver*
<h1>How to use GUI</h1>
There is one main page which can be accessed by following address: http://localhost:8080/gui/getAll.
On the main page there is sorted in descending order by entries list of words and a field "Unique words" that 
displays amount of unique words in the database. 
GUI can handle incorrect inputs such as: *Payload cannot be empty*, *Input cannot consist only of numbers*.
