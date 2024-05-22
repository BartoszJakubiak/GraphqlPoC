# Graphql Restful Service  - PoC #
## Introduction ##

Purpose of this project is to check if it's possible
to implement GraphQL solutions in the environment
containing multiple __separate__ datasources.

## Dependencies ##

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
    delayed: Boolean
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

## Queries ##

There is only single query availabe for users. This query returns list of 
OrderSummary. 
There are 3 optional arguments which affects returned result:

- order's ID
- returnInfo field (accepted)
- shipment field (delayed)

```
type Query {
    allOrderSummary(id: ID, accepted: Boolean, delayed: Boolean): [OrderSummary]
}
```

This query is mapped with @QueryMapping annotation.

```
    @QueryMapping
    public Object allOrderSummary(DataFetchingEnvironment environment) {
        try {
            return allOrdersDataFetcher.get(environment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
```

Logic behind this query is implemented in AllOrderDataFetcher class specifically
_get()_ method with DataFetchingEnvironment environment passed.

Using _environment.getSelectionSet()_ we can determine which resolvers
to call (asynchronously) in order to get requested data in a query. Based on 
_environment.getArgument("")_ we can pass optional arguments from query.

At the end data fetched from all APIs are connected in OrderSummary entities
and returned.


## Notes ##

- Data filtering could be passed on outside API using environment's content.
Requesting all data from API each time query is used is just for PoC purposes.
- Couldn't find a way to pass arguments to subqueries in GrapQL. That is way
all the arguments are in main query.
- Couldn't find a way for GraphQL to chain queries on it's own (OrderSummary
query calls subqueries).
- Outside APIs are mocked with Mockoon for testing purposes
Shipment's API response 
```
[{
  "id": 0,
  "address": "123 Main St",
  "date": "10-02-2024",
  "delayed": true
},
{
  "id": 1,
  "address": "Groove Street",
  "date": "30-04-2024",
  "delayed": false
}]
}
```
ReturnInfo's API response
```
[{
  "id": 0,
  "accepted": true,
  "reason": "Item didn't meet expectations"
},
{
  "id": 1,
  "accepted": false,
  "reason": "Item arrived broken"
}]
```