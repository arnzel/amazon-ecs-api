This is a Java API to Amazon's Product Advertising SOAP API. It handles for you the signing
of the SOAP requests. All you have to do is provide a Configuration object with your
AWS developer credentials. There exists a Properties implementation: 

```java
Properties properties = new Properties();
properties.load(getClass().getResourceAsStream("/amazon.properties"));
Configuration configuration = new PropertiesConfiguration(properties);
ProductAdvertisingAPI api = new ProductAdvertisingAPI(configuration);
```

The amazon.properties file has the following format:

```
amazon.accessKey=[Your access key]
amazon.secretKey=[Your secret key]
amazon.associateTag=[Your associateTag]
```

Or use the class `StringConfiguration` for a Configuration with pure Strings.

If you want to use the JAX-WS client which was generated by wsimport, you can get the port
by calling api.getPort(). It still has attached the handler for signing the SOAP requests.

As using the generated SOAP API is a bit inconvenient, the API facades the boiler plate
code by giving you ApiCall objects.

This API is build upon a JAX-WS client. This API is
[not thread safe as the JAX-WS client](https://cwiki.apache.org/CXF/faq.html#FAQ-AreJAXWSclientproxiesthreadsafe%253F). 


# Example
Have a look at this example how to use the API with a bit more convenient API.

```java
ProductAdvertisingAPI api;
Cart cart;


// Instantiate the API
Properties properties = new Properties();
properties.load(getClass().getResourceAsStream("/amazon.properties"));
Configuration configuration = new PropertiesConfiguration(properties);
ProductAdvertisingAPI api = new ProductAdvertisingAPI(configuration,
        new AWSECommerceService().getAWSECommerceServicePortDE());


// Search items
ItemSearchRequest itemSearchRequest = new ItemSearchRequest();
itemSearchRequest.setSearchIndex("Books");
itemSearchRequest.setKeywords("Star Wars");
Items foundItems = api.getItemSearch().call(itemSearchRequest);


// Create a shopping cart with the first found item
{
    CartCreateRequest request = new CartCreateRequest();
    CartCreateRequest.Items items = new CartCreateRequest.Items();
    request.setItems(items);
    Item item = new Item();
    items.getItem().add(item);
    item.setASIN(foundItems.getItem().get(0).getASIN());
    item.setQuantity(BigInteger.valueOf(1));
    cart = api.getCartCreate().call(request);
}


// Add the second found item to the created cart
{
    CartAddRequest request = api.getCartAdd().buildRequest(cart);
    request.setItems(new CartAddRequest.Items());
    CartAddRequest.Items.Item item = new CartAddRequest.Items.Item();
    item.setASIN(foundItems.getItem().get(1).getASIN());
    item.setQuantity(BigInteger.valueOf(1));
    request.getItems().getItem().add(item);
    cart = api.getCartAdd().call(request);
}


// send the user to purchasing at amazon
System.out.println(cart.getPurchaseURL());


// Clear the cart
cart = api.getCartClear().call(cart);
```
    

# Maven
You find this package in Maven Central:
```xml
<dependency>
    <groupId>de.malkusch.amazon.product-advertising-api</groupId>
    <artifactId>amazon-ecs-api</artifactId>
    <version>1.1.2</version>
</dependency>
```


# License and author

Markus Malkusch <markus@malkusch.de> is the author of this project. This project is free and under the WTFPL.

## Donations

If you like this project and feel generous donate a few Bitcoins here:
[1335STSwu9hST4vcMRppEPgENMHD2r1REK](bitcoin:1335STSwu9hST4vcMRppEPgENMHD2r1REK)


