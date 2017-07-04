# Android HTTP Requester
This library is a simple way to do http requests in the android enviroment
### Supports
- POST 
- GET
- Only HTTP for now

### Future work
- HTTPS
- Enforce HTTPS use
- Validing TLS/SSl keys from public key folder 

Thanks to [Paulo Ribeiro][prgit] for the firts initial simplification ideia 

# Usage
----
GET request
----
```java
public static void doGet(){
        GET.ResponseHandler responseHandler = new GET.ResponseHandler() {
            @Override
            public void onResponse(byte[] result) {
                // TODO: ADD YOUR CODE HERE
            }
        };

        GET get = new GET("http://yoururl.com",responseHandler);

        // OR

        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("key","value");
        ...
        GET get = new GET("http://yoururl.com",responseHandler,parameters);
}
```
Post request
----
```java
public static void doPost(){
        POST.ResponseHandler responseHandler = new POST.ResponseHandler(){
            @Override
            public void onResponse(byte[] result) {
                // TODO: ADD YOUR CODE HERE
            }
        };

        HashMap<String,String> properties = new HashMap<>();
        properties.put("Content-Type","application/json; charset=UTF-8");

        byte[] data = "{'id':'my json obj'}".getBytes();

        POST post = new POST("http://yoururl.com",properties,data,responseHandler);
}
```
License
----
GNU General Public License v3.0

[prgit]: <https://github.com/pauloacribeiro92>

