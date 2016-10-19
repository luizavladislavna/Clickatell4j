Clickatell Java Library for **REST API**
========================================

You can see other libraries and more official documentation at the [Clickatell APIs and Libraries Project](http://clickatell.github.io/).
Refactored from: https://github.com/clickatell/clickatell-java

------------------------------------

This is a basic library to demonstrate the use of the Clickatell **Rest API**.

HOWTO: install Clickatell4J
---------------------------

Clone:
```
git clone https://github.com/sealTLV/Clickatell4j
cd Clickatell4j
```

Compile:
```
mvn clean compile 
```

Update Clickatell configuration
```
echo "APIKEY      = YOUR_APIKEY"    > src/test/resources/test.properties
```

Compile Tests:
```
mvn test-compile
```


Run Tests:
```

mvn surefire:test  -Dtest=com.clickatell.example.run.TestAccountOptions#testGetBalance
mvn surefire:test  -Dtest=com.clickatell.example.run.TestMessageSend
```

Install an artifact into your local repository:
```
mvn clean compile install -Dmaven.test.skip=true
```

HOWTO: Usage in maven projects:
-------------------------------

pom.xml
```
<dependencies>
    ...
    <dependency>
        <groupId>com.clickatell.sdk</groupId>
        <artifactId>clickatell4j</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
<dependencies>
```



HOWTO: Usage - source code examples
-----------------------------------

To initialize:

```
ClickatellRest clickRest = ClickatellRest.of(YOUR_APIKEY);
```

To send one message:

```
Message response = clickRest.sendMessage(
                        "27821234567",
                        "Hello, this is a test message!");
```

To get the status of a message:

```
Message response = clickRest.getMessageStatus("b305c3445e37626ffabb21edc9320e1e");
int status = response.status();
```

To get the cost of a message:

```
Message charge = clickRest.getMessageCoverage("b305c3445e37626ffabb21edc9320e1e");
System.out.println("ID:" + msg.message_id);
System.out.println("Status:" + msg.status);
System.out.println("Status Description:" + msg.statusString);
System.out.println("Charge:" + msg.charge);
```

To do a message stop request:

```
clickRest.stopMessage("b305c3445e37626ffabb21edc9320e1e");
```

To check your balance:

```
double balance = clickRest.getBalance();
```
