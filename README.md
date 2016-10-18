Clickatell Java Library
=====================

You can see our other libraries and more documentation at the [Clickatell APIs and Libraries Project](http://clickatell.github.io/).

------------------------------------

This is a basic library to demonstrate the use of the Clickatell Rest and HTTP APIs.

Usage
-----

The class files can be copied directly into your project, then reference the json-simple-1.1.1.jar library. and then use as follows:

To initialize:

```
ClickatellHttp click = new ClickatellHttp(USERNAME, APIID, PASSWORD);
ClickatellRest clickRest = new ClickatellRest(API_KEY);
```

To send one message:

```
Message response = click.sendMessage("27821234567",
                    "Hello, this is a test message!");
Message response = clickRest.sendMessage("27821234567",
                    "Hello, this is a test message!");
```

To get the status of a message:

```
Message response = click.getMessageStatus("b305c3445e37626ffabb21edc9320e1e");
int status = response.statusToInt();
```

To get the cost of a message:

```
Message reply = click.getMessageCharge("b305c3445e37626ffabb21edc9320e1e");
System.out.println("Charge: " + reply.charge);
System.out.println("Status: " + reply.status);
```

To get the cost and status of message in REST:

```
Message msg = clickRest.getMessageStatus(response.message_id);
System.out.println("ID:" + msg.message_id);
System.out.println("Status:" + msg.status);
System.out.println("Status Description:" + msg.statusString);
System.out.println("Charge:" + msg.charge);
```

To do a coverage check:

```
double reply = click.getCoverage("27820909090");
```

To do a message stop request:

```
click.stopMessage("b305c3445e37626ffabb21edc9320e1e");
```

To check your balance:

```
double balance = click.getBalance();
```

Testing Sample Code
-------------------

Compile:
```
mvn clean compile 
```

Update Clickatell configuration
```
echo "" > src/test/resources/test.properties
echo "APIID       = YOUR_APIID"     >> src/test/resources/test.properties
echo "USERNAME    = YOUR_USERNAME"  >> src/test/resources/test.properties
echo "PASSWORD    = YOUR_PASSWORD"  >> src/test/resources/test.properties
echo "APIKEY      = YOUR_APIKEY"    >> src/test/resources/test.properties
```

Compile Tests:
```
mvn test-compile
```


Run Tests:
```
mvn surefire:test  -Dtest=com.clickatell.example.run.TestHttp#testAuth
mvn surefire:test  -Dtest=com.clickatell.example.run.TestHttp
mvn surefire:test  -Dtest=com.clickatell.example.run.TestRest
```

Install an artifact into your local repository:
```
mvn clean compile install -Dmaven.test.skip=true
```

Usage in maven projects:
------------------------

pom.xml
```
<dependencies>
    ...
    <dependency>
        <groupId>com.clickatell.sdk</groupId>
        <artifactId>clickatell-java</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
<dependencies>
```
