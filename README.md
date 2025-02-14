# Network-Application-Programming-in-Java
This project is a Java-based multi-user network chat application. It employs a client-server architecture using Java Sockets for real-time communication. Multiple clients connect concurrently to exchange messages in a shared chat room.


Author: Sharon P Garapati

Email: G00438803@atu.ie


Description of Design & Implementation:

This network-based chat application is built using the Java Socket API and employs a thread pool
to handle multiple client connections efficiently. It consists of 3 main components.

1. Chat Server:
   
 Purpose: Manages incoming client connections and facilitates message
broadcasting between clients.

 Key Features: Uses a fixed thread pool (Executor service) to efficiently
handle up to 50 concurrent clients. Maintains a thread-safe set of active
ClientHandler instances to track connected clients. Listens for
connections on a predefined port (30) and delegates each client to a
ClientHandler.


2. Client Handler:

 Purpose: Manages the communication for an individual client.

 Key Features: Runs as a callable task, handling message exchange
between the server and the client. Broadcasts messages to all connected
clients, excluding the sender, via a synchronized mechanism. Handles
client disconnections gracefully, ensuring cleanup and removal from the
active clients list.


3. Chat Client:
   
 Purpose: Provides the interface for users to connect to the server and
exchange messages.

 Key Features: Connects to the server using the server’s IP and Port.
Spawns 2 threads: one for reading user input and sending messages, and
another for receiving and displaying messages from the server. Allows
users to gracefully disconnect by typing “\q”.


Key Advantages:

 Scalability: The thread pool and thread-safe collections ensure the server
can manage multiple clients efficiently.

 Robustness: Handles abrupt client disconnections and communication
errors without crashing.

 Ease of Use: Simple text-based interface for clients to participate in a chat
session.

This design supports realtime communication between multiple clients, ensuring
efficient resource utilisation and a seamless user experience.


How to Run:

1. First compile and run ChatServer.java. The server will listen for connections on
port 30.
2. Then compile and run ChatClient.Java. Each client connects the server and
send/receive messages.
3. Chat Session: Clients can exchange messages in real time. Typing “\q” ends the
session for that client.
