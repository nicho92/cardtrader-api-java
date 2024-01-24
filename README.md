# cardtrader-api-java
java api for cardtrader.com



**Import via maven**

```xml
<dependency>
    <groupId>com.github.nicho92</groupId>
    <artifactId>cardtrader-api-java</artifactId>
    <version>0.0.39</version>
</dependency>
```

**USAGE**

```java
var service = new CardTraderService(<YOUR TOKEN API>);
		var set = service.getExpansionByCode("M21");
		var bps = service.listBluePrints(service.getCategoryById(1), "Fiery Emancipation",set);

```
