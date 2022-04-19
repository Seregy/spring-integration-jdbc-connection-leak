## Spring Integration JDBC connection leak example

This is an example application for reproducing the issue with Spring Integration JDBC that leads to
the connection pool depletion.

Check out the test scenario in `DatabaseConnectionLeakTest` or adjust the database-related
configuration in the `application.yaml` and launch the application directly. 
