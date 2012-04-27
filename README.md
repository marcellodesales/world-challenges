Reverse String Server Counter
===========================================

This is a TCP server, listening to port 4567, that can revert your strings and maintain a shared
counter.

## Building 

You must have Gradle installed in your machine.

    gradle clean jar

That should be enough to generate the server Jar and the executable script "run.sh", as shown below.

    marcello@hawaii:/u1/development/workspaces/open-source/challenges/interviews/ticketly$ gradle clean jar
    :clean
    :compileJava
    :processResources UP-TO-DATE
    :classes
    :jar
    
    ##############################
    WOOOHOOO! FINISHED!
    You are ready to run the command "./run.sh --help" for instructions!
    ##############################

## Starting the Server

Just run the script "run.sh". It has the Java command to start the server.

    marcello@hawaii:/u1/development/workspaces/open-source/challenges/interviews/ticketly$ ./run.sh 
    TicketFly Server running, waiting connections on 192.168.190.190:4567
    Developed by Marcello de Sales (marcello.desales@gmail.com)

Upon receiving a new connection, the server prints out just some info about the request and server status.

    Thread Pool [ 50 , 50 ]
    The Largest Pool size: 1
    # of active threads: 0
    # of maximum pool size:50
    Handling client request Client [/127.0.0.1:39158]
    
    Thread Pool [ 50 , 50 ]
    The Largest Pool size: 2
    # of active threads: 1
    # of maximum pool size:50
    Handling client request Client [/127.0.0.1:39159]

## Interacting with the server

The status above was initialized for 2 different clients. Simply telnet to the listening port.

    marcello@hawaii:/u1/development/workspaces/open-source/google-go-tutorial/src$ telnet localhost 4567
    Trying 127.0.0.1...
    Connected to localhost.
    Escape character is '^]'.
    |

At this point you can issue the commands to the server. Here's the interaction between two clients.

## Allowed Commands 

* "string"
 - The server will return the reverted input with the current counter value "gnirts 1"

* "string 3"
 - The server will return the reverted input with the incremented value of the request "gnirts 4".
 - Note that the reset value will only occur when the current server's value is smaller than the received one.
   That is, "string 2" will result in "gnirts 5".

* "/q"
* "/quit"
 - The server terminates the connection with the client. The ignored cases are also recognized as valid.

## Session Example

Client 1: started the interaction, entering wrong command..

    marcello@hawaii:/u1/development/workspaces/open-source/google-go-tutorial/src$ telnet localhost 4567
    Trying 127.0.0.1...
    Connected to localhost.
    Escape character is '^]'.
    Marcello
    ollecraM 1
    Go 4
    oG 52
    WrongInput noNumber
    INCORRECT_REQUEST_PARAMETER(6): The parameter provided is incorrect.
    /quit
    Have a nice day!
    Connection closed by foreign host.

Client 2: started the interaction after client 2, and set a new number.

    marcello@hawaii:/u1/development/workspaces/open-source/google-go-tutorial/src$ telnet localhost 4567
    Trying 127.0.0.1...
    Connected to localhost.
    Escape character is '^]'.
    Google
    elgooG 2
    Gradle 50
    eldarG 51
    /q
    Have a nice day!
    Connection closed by foreign host.
