# Graphql Restful Service  - PoC #
## Introduction ##

Purpose of this project is to check if it's possible
to implement GraphQL solutions in the environment
containing multiple __separate__ datasources.

## TechStack ##

This project was written in Java using SpringBoot.
Required dependencies:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-graphql</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## Model ##
### GraphQL ###
For PoC purpose model was limited to very
basic example. Model is based on 4 entities:
- Order - which contains keys to other entities
``` 
type Order {
    id: ID!
    info: String
    shipmentID: Int
    returnID: Int
}
```

- ReturnInfo & Shipment - both containing separate
specific for that Order informations
```
type ReturnInfo {
    id: ID!
    accepted: Boolean
    reason: String
}
type Shipment {
    id: ID!
    address: String
    date: String
}
```
- OrderSummary - which contains all information regarding
specific Order
```
type OrderSummary {
    order: Order
    shipment: Shipment
    returnInfo: ReturnInfo
}
```

### Java ###

To correctly map entities from GraphQL to Objects
in Java it is required to make matching Class's names 
and Field's names & types!
```
public record Order (Integer id, String info, Integer shipmentID, Integer returnID) {}

public record ReturnInfo(Integer id, Boolean accepted, String reason) {}

public record Shipment(Integer id, String address, String date) {}

public class OrderSummary {
    private final Order order;
    private final Shipment shipment;
    private final ReturnInfo returnInfo;
    ...
```

## Queries ##

### GraphQL ###

```
type Query {
    orderSummary(id: ID): OrderSummary
    returnInfo(id: ID): ReturnInfo
    shipment(id: ID): Shipment
}
```

### Java ###

In Java to correctly map queries from GraphQL
we can use annotation '@QueryMapping' as long as 
method name matches query name, otherwise in 
other cases @SchemaMapping is required.
If query requires argument similar to @RequestParam
in REST we use @Argument in GraphQL. In other cases
we can use DataFetchingEnvironment to get access
to fields included in query.
```
    @Async
    @QueryMapping
    public CompletableFuture<ReturnInfo> returnInfo(@Argument Integer id) {
        System.out.println("ReturnInfo resolver - breakpoint");
        ReturnInfo returnInfo = returnService.specificReturn(id);
        return CompletableFuture.completedFuture(returnInfo);
    }
```
For more complex queries it might be required to
define custom DataFetcher implementing DataFetcher<>
interface. This way we can manage process of requesting
data from inside and outside sources manually. 
```
@Component
public class CombinedDataFetcher implements DataFetcher<Object> {
...
    @Override
        public Object get(DataFetchingEnvironment environment) throws Exception {
    ...
    return Object;
    }
}
```


