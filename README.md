# Simple HTTP Server

This project features a basic HTTP server designed to handle GET and POST requests, serve files, and manage multiple concurrent connections from clients. The server implements the HTTP/1.1 protocol, enabling it to efficiently serve multiple clients.

## Completed Features

- **TCP Connection**: Open a TCP connection on a specified port and accept incoming client connections.
- **Basic HTTP Response**: Respond with a simple HTTP 200 OK status.
- **Request Handling**: Read request headers, extract URLs, and process requests accordingly.
- **File Serving**: Read and return file content as part of the HTTP response.
- **Concurrent Connections**: Handle multiple connections simultaneously.
- **Encoding Support**: Handle `Accept-Encoding` in requests and `Content-Encoding` in responses, including GZIP compression.

## Future Enhancements

- **Response Construction**: Currently, responses are formed as simple strings in each respective controller. The plan is to break down responses into lines, headers, and bodies, allowing each controller to construct and send responses more efficiently.
- **OutputStream Handling**: The `RequestHandler` class currently sends responses through `OutputStream`. Future updates will involve passing the `OutputStream` object to each controller, enabling controllers to write responses directly to clients.