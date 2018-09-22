This is a Java Web Service defined by the WSDL file that generates Java code
(contract-first approach, also called top-down approach).

The service runs in a stand-alone HTTP server.
It throws a Fault in roughly 1/3 of the times.


Instructions using Maven:
------------------------

To compile:
  mvn compile

To run using exec plugin:
  mvn exec:java

To generate launch scripts for Windows and Linux:
  (appassembler:assemble is attached to install phase)
  mvn install

To run using appassembler plugin:
  On Windows:
    target\appassembler\bin\ping-ws http://localhost:8080/ping-ws/endpoint
  On Linux:
    ./target/appassembler/bin/ping-ws http://localhost:8080/ping-ws/endpoint


When running, the web service awaits connections from clients.
You can check if the service is running using your web browser 
to see the generated WSDL file:

    http://localhost:8080/ping-ws/endpoint?WSDL

This address is defined when the publish() method is called.

To call the service you will need a web service client,
including code generated from the WSDL.


To configure the Maven project in Eclipse:
-----------------------------------------

'File', 'Import...', 'Maven'-'Existing Maven Projects'
'Select root directory' and 'Browse' to the project base folder.
Check that the desired POM is selected and 'Finish'.


--
Revision date: 2017-03-10
leic-sod@disciplinas.tecnico.ulisboa.pt
