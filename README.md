Java Multithreaded HTTP Server

A lightweight multithreaded HTTP/1.1 server written in Java that supports concurrent connections, persistent connections, explicit connection closure, file serving, file upload, and optional gzip compression.

Features

Persistent connections (HTTP/1.1)
Keeps connections alive for multiple requests per client.

Explicit connection closure
Supports Connection: close header and responds with Connection: close when closing.

Endpoints supported:

/ – Returns a basic 200 OK response.

/echo/{message} – Echoes the message in plain text.

/user-agent – Returns the User-Agent header.

/files/{filename} –

GET: Reads and serves a file (with concurrency-safe locking).

POST: Creates or overwrites a file with provided content (with concurrency-safe locking).

Content encoding:

Supports gzip compression via Accept-Encoding: gzip.

Multithreaded design:
Each client connection is handled in a separate thread (Thread-per-connection model).

Safe concurrent access to files:
Uses ConcurrentHashMap<String, Object> for per-file locking to prevent partial reads during writes.

How It Works

Server Initialization

Listens on port 4221.

Optional: --directory <path> specifies the root directory for file serving.

Connection Handling

Each incoming TCP connection spawns a new thread via new Thread(() -> handleClient(...)).start().

Request Parsing

Reads request line, headers, and body (for POST).

Supports Content-Length for POST uploads.

File Access Synchronization

Uses a per-file lock to synchronize GET and POST access to the same file.

Ensures reads are not served while writes are in progress.

Response Generation

Returns proper Content-Type, Content-Length, and compression headers when applicable.

Supports Connection: close behavior for graceful shutdown per request.

Example Usage
Start the Server
javac -d . main/java/*.java
java -cp . main.java.Main --directory "/path/to/files"

Example Requests
# Echo message
curl --http1.1 -v http://localhost:4221/echo/hello

# Get User-Agent
curl --http1.1 -v http://localhost:4221/user-agent -H "User-Agent: custom-agent"

# Upload a file
curl --http1.1 -v -X POST http://localhost:4221/files/test.txt \
     -d "Hello World!"

# Download a file
curl --http1.1 -v http://localhost:4221/files/test.txt

Thread Safety

Uses a per-file locking mechanism via ConcurrentHashMap to prevent race conditions.

Reads and writes are synchronized for each file individually — no global locking is used.

Future Improvements

Switch from Thread-per-connection to a thread pool for better scalability.

Add support for more HTTP methods (PUT, DELETE).

Implement HTTP/1.1 Keep-Alive timeout handling.

Add MIME type detection for files.

Improve error handling and logging.

Would you like me to:

Make this more concise (good for Codecrafters submission)?

Keep it detailed like this (good for a proper GitHub README)?

Or make two versions (short + detailed)?

Also, do you want me to add a diagram (architecture flow) to your README?
