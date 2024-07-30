# Simple HTTP server

A simple HTTP server that is capable of handling GET/POST requests,
serving files and handling multiple concurrent connections or requests from clients.

[HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol) is the
protocol that powers the web. In this repo, I've built a HTTP/1.1 server
that is capable of serving multiple clients.

## Tasks completed
1. Open a TCP connection on a port and accept client connection
2. Respond with a simple 200 http response
3. Read request headers and extract url
4. Respond to the request with body in the response
5. Read different request headers
6. Handle multiple concurrent connections
7. Read and return file content as part of the request response
8. Read request body and process the request accordingly
9. Handle Accept-Encoding and Content-Encoding in the request and response respectively
10. Compress the response in GZIP format and send it as response to the client

## To do
1. Currently, forming the request response as a simple string in each respective controller. Idea is to divide the response into line, header, body and then respective request controller will form the response and send it to the client
2. Currently, RequestHandler class is sending the response through OutputStream. Instead, pass the OutputStream as an object to each controller and let each controller write the response to client through OutputStream