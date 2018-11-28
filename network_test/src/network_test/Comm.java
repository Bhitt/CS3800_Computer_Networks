/*

 */
package network_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bhitt
 */
public class Comm {
    
    
    //method
//    public String getHTML(String urlToRead) throws Exception {
//        StringBuilder res = new StringBuilder();
//        URL url = new URL(urlToRead);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String line;
//        while((line= rd.readLine())!=null){
//            res.append(line);
//        }
//        rd.close();
//        return res.toString();
//    }
    
    public String getHTML(String urlPath){
        Socket s;
        try {
            s = new Socket(InetAddress.getByName("stackoverflow.com"), 80);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Comm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Comm.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        pw.print("GET / HTTP/1.1\\r\\n");
        pw.print("Host: stackoverflow.com\\r\\n\\r\\n");
        pw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String t;
        String res = "";
        while((t = br.readLine()) != null) res+=t;
        br.close();
        return t;
    }
}
