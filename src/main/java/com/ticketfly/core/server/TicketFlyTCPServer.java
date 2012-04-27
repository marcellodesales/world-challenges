package com.ticketfly.core.server;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The TicketFly Telnet Server class responsible for handing clients requests.
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 */
public class TicketFlyTCPServer {

  /**
   * The current host IP address is the IP address from the device.
   */
  public static String currentHostIpAddress;
  /**
   * The port number where the server is serving
   */
  public static final int SERVER_PORT = 4567;
  /**
   * The total number of threads serving.
   */
  public static final int THREAD_POOL_SIZE = 50;
  /**
   * The current number of computations performed.
   */
  public static AtomicCounter computationCounter = new AtomicCounter();

  /**
   * The pool of threads will be used to control the requests to the server. If has the following
   * attributes: <BR>
   * <li>corePoolSize is the number of threads that are allowed to be in the pool even if they are
   * in idle; <BR> <li>maximumPoolSize is the maximum number of threads allowed in the pool; <BR>
   * <LI>keepAliveTime and timeUnit are used to indicate how long new threads will wait when the the
   * number of threads in the pool are greater than the corePoolSize; <BR> <LI>workQueue is the
   * queue that will hold the runnables before they execute; <br> <li>retentionPolicy is the policy
   * used to remove the tasks.
   */
  private static Executor threadsPool;

  static {
    currentHostIpAddress = getCurrentEnvironmentNetworkIp();
    threadsPool =
        new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE, Long.MAX_VALUE,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2 * THREAD_POOL_SIZE),
            new ThreadPoolExecutor.DiscardOldestPolicy());
  }

  /**
   * @return The current IP address of the SO, no matter if running on a DMZ.
   */
  public static String getCurrentEnvironmentNetworkIp() {
    if (currentHostIpAddress == null) {
      Enumeration<NetworkInterface> netInterfaces = null;
      try {
        netInterfaces = NetworkInterface.getNetworkInterfaces();

        while (netInterfaces.hasMoreElements()) {
          NetworkInterface ni = netInterfaces.nextElement();
          Enumeration<InetAddress> address = ni.getInetAddresses();
          while (address.hasMoreElements()) {
            InetAddress addr = address.nextElement();
            if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()
                && !(addr.getHostAddress().indexOf(":") > -1)) {
              currentHostIpAddress = addr.getHostAddress();
            }
          }
        }
        if (currentHostIpAddress == null) {
          currentHostIpAddress = "127.0.0.1";
        }

      } catch (SocketException e) {
        currentHostIpAddress = "127.0.0.1";
      }
    }
    return currentHostIpAddress;
  }

  /**
   * Prints the clients thread pool.
   */
  private static void printServerPoolStatus() {
    ThreadPoolExecutor pool = (ThreadPoolExecutor) threadsPool;
    System.out.println();
    System.out.println("Thread Pool [ " + pool.getCorePoolSize() + " , "
        + pool.getMaximumPoolSize() + " ]");
    System.out.println("The Largest Pool size: " + pool.getLargestPoolSize());
    System.out.println("# of active threads: " + pool.getActiveCount());
    System.out.println("# of maximum pool size:" + pool.getMaximumPoolSize());
  }

  public static void main(String[] args) {

    String listeningPort = String.valueOf(SERVER_PORT);
    String name = currentHostIpAddress;

    System.out.println("TicketFly Server running, waiting connections on " + name + ":"
        + listeningPort);
    System.out.println("Developed by Marcello de Sales (marcello.desales@gmail.com)");
    System.out.println();

    try {
      ServerSocket socketServer = new ServerSocket(SERVER_PORT);
      while (true) {
        final Socket clientSocket = socketServer.accept();

        // after a client connect, handle each client by a different thread.
        Runnable connectionHandler = new TicketFlyServerRequestMediator(clientSocket, 
          computationCounter);

        // run the handler for the client.
        threadsPool.execute(connectionHandler);

        // print the current thread pool information
        printServerPoolStatus();
      }

    } catch (BindException otherProcessRunningOnSamePortError) {
      System.out.println("############### ERROR INITIALIZING THE SERVER ####################");
      System.out.println("# CAUSE: The port specified in server: " + listeningPort);
      System.out.println("# SOLUTION:");
      System.out.println("#  * Change the specified port;");
      System.out.println("#  * Stop the application running on that port and try again.");
      System.out.println("#######################################################################");

      System.exit(ServerTerminationError.CANT_BIND_PORT.getCode());

    } catch (IOException otherErrorWhileInitializing) {
      System.out.println("############### ERROR INITIALIZING THE SERVER ####################");
      System.out.println("Another error while initializing the server");
      System.out.println(otherErrorWhileInitializing.getMessage());
      System.out.println("#######################################################################");

      System.exit(ServerTerminationError.CLIENT_CONNECTION_ERROR.getCode());

    } catch (TflyServiceException error) {
      error.printStackTrace();
    } 
  }
}
