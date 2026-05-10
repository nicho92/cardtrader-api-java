# cardtrader-api-java

[![Maven Central](https://img.shields.io/maven-central/v/com.github.nicho92/cardtrader-api-java.svg)](https://central.sonatype.com/artifact/com.github.nicho92/cardtrader-api-java)
[![Java](https://img.shields.io/badge/Java-21%2B-blue.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](http://www.apache.org/licenses/LICENSE-2.0)

`cardtrader-api-java` is a Java client for the [CardTrader](https://www.cardtrader.com/) API. It provides a small service layer around CardTrader API v2 for reading catalogue data, searching marketplace products, managing inventory products, adding marketplace products to a cart, and retrieving order details.

## Requirements

- Java 21 or newer
- Maven 3.x
- A CardTrader API token

## Installation

Add the library to your Maven project:

```xml
<dependency>
    <groupId>com.github.nicho92</groupId>
    <artifactId>cardtrader-api-java</artifactId>
    <version>0.0.52</version>
</dependency>
```

## Quick start

```java
import java.util.List;

import org.api.cardtrader.modele.BluePrint;
import org.api.cardtrader.modele.Expansion;
import org.api.cardtrader.services.CardTraderService;

public class Example {
    public static void main(String[] args) {
        String token = System.getenv("CARDTRADER_TOKEN");
        CardTraderService service = new CardTraderService(token);

        Expansion expansion = service.getExpansionByCode("M21");
        List<BluePrint> blueprints = service.listBluePrints("Fiery Emancipation", expansion);

        blueprints.forEach(blueprint -> {
            System.out.println(blueprint.getName() + " - " + blueprint.getProductUrl());
        });
    }
}
```

## Authentication

Create a `CardTraderService` with your CardTrader API token:

```java
CardTraderService service = new CardTraderService("YOUR_CARDTRADER_API_TOKEN");
```

For applications and scripts, prefer loading the token from an environment variable or another secret-management mechanism rather than committing it to source control.

## Common operations

### Catalogue data

```java
var service = new CardTraderService(token);

var appInfo = service.getApp();
var games = service.listGames();
var categories = service.listCategories();
var allExpansions = service.listExpansions();
var magic2021 = service.getExpansionByCode("M21");
```

### Blueprint lookup

```java
var expansion = service.getExpansionByCode("M21");
var blueprints = service.listBluePrints("Fiery Emancipation", expansion);
var blueprint = service.getBluePrintById(12345);
var expansionBlueprints = service.listBluePrintsByExpansion(expansion);
```

### Marketplace products

```java
var expansion = service.getExpansionByCode("M21");
var marketplaceProducts = service.listMarketProductByExpansion(expansion);

var blueprintProducts = service.listMarketProductByBluePrint(blueprints.get(0));
```

### Inventory / stock

```java
var allStock = service.listStock();
var matchingStock = service.listStock("Lightning Bolt");

service.downloadProducts(gameId, categoryId, new File("products.json"));
```

### Create, update, and delete products

```java
import org.api.cardtrader.enums.ConditionEnum;
import org.api.cardtrader.enums.Identifier;

Integer productId = service.addProduct(
    "123456",
    Identifier.blueprint_id,
    9.99,
    1,
    "Listed from cardtrader-api-java",
    ConditionEnum.NEAR_MINT,
    "internal-sku-001"
);

service.updateProduct(productId, 8.99, 2, null, ConditionEnum.NEAR_MINT, null);
service.deleteProduct(productId);
```

### Orders

```java
var firstPageOrders = service.listOrders(1);
var orderDetails = service.getOrderDetails(firstPageOrders.get(0).getId());
```

### Cart

```java
service.addProductToCart(marketProduct, true, 1, billingAddress, shippingAddress);
```

## Caching

`CardTraderService` includes an in-memory cache for repeated API calls. Caching can be enabled or disabled, and individual cache keys can be cleared:

```java
service.enableCaching(true);
service.clearCache("expansions");
```

By default, if a marketplace product does not include expansion data, the service attempts to load expansion details from the product blueprint. You can disable that fallback:

```java
service.setForceExpansionLoadingIfNotFound(false);
```

## API base URL

The client targets CardTrader API v2:

```text
https://api.cardtrader.com/api/v2
```

## Development

Clone the repository and build it with Maven:

```bash
git clone https://github.com/nicho92/cardtrader-api-java.git
cd cardtrader-api-java
mvn test
```

Useful project files:

- `pom.xml` - Maven coordinates, Java version, dependencies, and publishing configuration.
- `src/main/java/org/api/cardtrader/services/CardTraderService.java` - main API client entry point.
- `src/main/java/org/api/cardtrader/modele/` - model objects returned by the service.
- `src/main/java/org/api/cardtrader/enums/` - enums for identifiers, conditions, states, rarities, and versions.

## License

This project is licensed under the Apache License, Version 2.0. See the license metadata in `pom.xml` for details.
