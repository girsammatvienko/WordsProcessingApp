Application description
The service layer abstraction is used to provide two presentation layers. The first presentation layer is an api (controller.api package), the second one is a web Thymeleaf-based GUI (controller.gui package).

Database operations performed with Hibernate. *Address of console http://localhost:8080/h2-console/ *Access login "sa" without password

Application uses Thymeleaf templates.

All layers tested with Junit and Mockito.

Front-end part tested with Selenium. *Using Firefox driver

There is starter data in data.sql *two planets *one master owning planet *one master without planet
