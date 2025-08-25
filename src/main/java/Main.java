package main.java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

     //Uncomment this block to pass the first stage

     try(ServerSocket serverSocket = new ServerSocket(4221)) {
       serverSocket.setReuseAddress(true);

       Socket client = serverSocket.accept();
         System.out.println("Accepted new connection");

         BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
         String request = in.readLine();

       String notFound = "HTTP/1.1 404 Not Found\r\n\r\n";
       String response = "HTTP/1.1 200 OK\r\n\r\n";

       OutputStream out = client.getOutputStream();

       if(request.equals("/")) out.write(request.getBytes());
       else out.write(notFound.getBytes());

       out.flush();
       client.close();
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
