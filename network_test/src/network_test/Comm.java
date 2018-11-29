/*

 */
package network_test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
//import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bhitt
 */
public class Comm {
    
    public String getIndex() throws UnknownHostException, IOException{
        Socket s = new Socket(InetAddress.getByName("leagueoflegends.wikia.com"), 80);
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        pw.print("GET /wiki/League_of_Legends_Wiki HTTP/1.1\r\n");
        pw.print("Host: leagueoflegends.wikia.com\r\n");
        pw.print("Connection: close\r\n\r\n");
        pw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        StringBuilder htmlPage = new StringBuilder();
        String t = null;
        //String temp = "";
        while((t = br.readLine()) != null){
            //System.out.println(t);
//            temp+=t;
//            temp+="\n";
            htmlPage.append(t).append("\n");
        }
        br.close();
        //System.out.print(temp);
        return htmlPage.toString();
    }
    
    public void indexFile() throws UnknownHostException, IOException{
        Socket s = new Socket(InetAddress.getByName("leagueoflegends.wikia.com"), 80);
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        pw.print("GET /wiki/League_of_Legends_Wiki HTTP/1.1\r\n");
        pw.print("Host: leagueoflegends.wikia.com\r\n");
        pw.print("Connection: close\r\n\r\n");
        pw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        StringBuilder htmlPage = new StringBuilder();
        String t = null;
        //String temp = "";
        while((t = br.readLine()) != null){
            //System.out.println(t);
//            temp+=t;
//            temp+="\n";
            htmlPage.append(t).append("\n");
        }
        br.close();
        
        File html = new File("index.html");
        FileWriter fW = new FileWriter(html);
        fW.write(htmlPage.toString());
        fW.flush();
        fW.close();
        
    }
    
    public void patchFile(String patch)throws UnknownHostException, IOException{
        Socket s = new Socket(InetAddress.getByName("leagueoflegends.wikia.com"), 80);
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        pw.print("GET /wiki/V"+patch+" HTTP/1.1\r\n");
        pw.print("Host: leagueoflegends.wikia.com\r\n");
        pw.print("Connection: close\r\n\r\n");
        pw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        StringBuilder htmlPage = new StringBuilder();
        String t = null;
        //String temp = "";
        while((t = br.readLine()) != null){
            //System.out.println(t);
//            temp+=t;
//            temp+="\n";
            htmlPage.append(t).append("\n");
        }
        br.close();
        
        File html = new File("patchNotes.html");
        FileWriter fW = new FileWriter(html);
        fW.write(htmlPage.toString());
        fW.flush();
        fW.close();
        
    }
    
}
