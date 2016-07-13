import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
/*
 * Client.java
 *
 * Created on 21 „«—”, 2008, 09:23 ’
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Talaat Saad
 */
public class Client {
    
    /**
     * Creates a new instance of Client
     */
    
    private Socket clientSocket;
    private String hostName;
    private int serverPort;
    private DataInputStream reader;
    private DataOutputStream writer;
    private Protocol protocol;

    private static Client client;
    private Client() throws IOException 
    {
        protocol=new Protocol();
    }

    public void register(String Ip,int port,int posX,int posY) throws IOException
    {
        this.serverPort=port;
        this.hostName=Ip;
        clientSocket=new Socket(Ip,port);
        writer=new DataOutputStream(clientSocket.getOutputStream());
      
        writer.writeUTF(protocol.RegisterPacket(posX,posY));
        

    }
  
    public void sendToServer(String message)
    {   
        if(message.equals("exit"))
            System.exit(0);
        else
        {
             try {
                 Socket s=new Socket(hostName,serverPort);
                 System.out.println(message);
                 writer=new DataOutputStream(s.getOutputStream());
                writer.writeUTF(message);
            } catch (IOException ex) {

            }
        }

    }
    
    public Socket getSocket()
    {
        return clientSocket;
    }
    public String getIP()
    {
        return hostName;
    }
    public static Client getGameClient()
    {
        if(client==null)
            
            try {
                client=new Client();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        return client;
    }
    public void closeAll()
    {
        try {
            reader.close(); 
            writer.close();
            clientSocket.close();
        } catch (IOException ex) {
            
        }
    }
}
