# JavaWebServer
**A simple HTTP server using JAVA and MAVEN.**
This server is custom made implementing the GET method with Content-Type and Content-Length Headers only for both client requests and server responses.

## 1.Client Requests
The client requests are parsed using a Lexerless Parser which parses the bytes as they are received from the client. 
This ensures that errors are detected soon and the connection can be dropped as soon as a malicious or is not well made.
All client requests must include the Content-Type and the Content-Length header fields in order for a request to be accepted and processed.
The list od client errors ans status codes supporte by this server inlcude:
1.
## 2.Server Responses
The server responses include the index page which is the default page defined in the webroot folder. The server also supports a default 404 (file/resource not found)
response page, a form for URL rewrite to support session management when the cookies are disabled on the browser.

The server supports SSL(Secure Sockets Layer)/TLS(Transport Layer Security) via a certificate.

Use the following command to generate a keystore file for authentication using Keytool:
keytool -genkey -keyalg RSA -alias <alias> -keystore <filename.jks> -validity 90 -keysize 2048
The above command generates a keystore with validity of 90 days of size 2048 bytes.

Use the following command to check keystore details such as encryption etc.
keytool -list -keystore <filename.jks> alias <aliasprovided> -v
and enter password to decrypt the file.

## Tests
### This projects comprises of certain tests: 
#### 1. Client requests tests to assert client requests are valid
#### 2. Client request errors and status codes to assert proper errors and corresponding status messages
#### 3. Server response tests to assert server responses are valid
#### 4. Server response errors and status tests to assert that proper errors and corresponding status messages
#### 5. Tests to assert Content-Type and Content-Length headers fields in both requests and responses
#### 6. Tests to assert SSL/TSL encryption.


This server supports both **GET** and **POST** request and response methods, **Content-Type** and **Content-Length** response headers, HTTPS alongside **URL-Rewrite** for session management.




