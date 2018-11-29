/*
LeagueFetch: simple command line program to fetch the free champions of the week
             can also output a specific version's patch notes to an html file
             --help --synopsis --bat --free --patch#
 */
package network_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


/**
 *
 * @author bhitt
 */
public class Network_test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //Version number
        String version = "v1.0.0";
        //Scan for input from the user
        Scanner input = new Scanner(System.in);
        String line;
        StringBuilder commands = new StringBuilder();
        while(!(line = input.nextLine()).equals(".eot")){
            commands.append(line).append("\n");
        }
        //check for switches/parameters
        if(commands.toString().contains("--help")) help();
        if(commands.toString().contains("--synopsis")) synopsis();
        if(commands.toString().contains("--free")) {
            String temp = getIndex();
            parseFree(temp);
        }
        if(commands.toString().contains("--patch (current)")) currentP();
        else if(commands.toString().contains("--patch")) patch(commands.toString());
        //System.out.print(commands);
    }
    
    
    public static void help(){
        System.out.println("================== \n"
                + "'LeagueFetch' serves two simple uses: \n"
                + "1)Return the names of the current free rotation of champions \n"
                + "2)Output specific patch notes to an html file for quick viewing \n"
                + "\n"
                + "Enter: --synopsis for a quick synopsis of the program \n"
                + "       --help to bring up this help prompt \n"
                + "       --free to list the current free champions \n"
                + "       --patch (current) to return an html file with the current version of patch notes \n" 
                + "       --patch (#) to return an html file with a specific game version's patch notes \n"
                + "       (Note: most patch note images and links are disabled) \n"
                + "\n"
                + "Ex Input: --patch (8.20) \n"
                + "  Output: The patch notes are saved to /8.20PatchNotes.html (Many images and links are disabled) \n"
                + "Ex Input: --free \n"
                + "  Output: Diana | Jinx | Kindred | Lee Sin | Leona | Lissandra | Malzahar | Poppy | Soraka | Swain | Tahm Kench | Veigar | Wukong | Zac | \n"
                + "\n"
                + "Version: 1.0.0 \n"
                + "Author : Branden Hitt \n"
                + "Contact: BrandenHitt@gmail.com");
    }
    
    public static void synopsis(){
        System.out.println("Returns current free rotation of champions or outputs patch notes to an html file");
    }
    
    public static void bat(){
        //Do nothing
    }
    
    public static void currentP() throws UnknownHostException, IOException{
        //Create a socket for the connection
        Socket s = new Socket(InetAddress.getByName("leagueoflegends.wikia.com"), 80);
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        //Send out a request for the patch page
        pw.print("GET /wiki/Patch HTTP/1.1\r\n");
        pw.print("Host: leagueoflegends.wikia.com\r\n");
        pw.print("Connection: close\r\n\r\n");
        pw.flush();
        //Create a string from the response
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        StringBuilder htmlPage = new StringBuilder();
        String t;
        while((t = br.readLine()) != null){
            htmlPage.append(t).append("\n");
        }
        br.close();
        //Parse the patch number from the page string
        String temp[] = htmlPage.toString().split("Current Patch: V",2);
        String temp2 = temp[1];
        temp = temp2.split("</a>",2);
        temp2 = temp[0];
        //System.out.println(temp2);
        patchFile(temp2);
    }
    
    public static void patch(String commands) throws IOException{
        //Parse out the patch number if available
        String[] temp = commands.split("--patch ",2);
        commands = temp[1];
        temp = commands.split("\\)",2);
        commands = temp[0];
        commands = commands.replace("(", "");
        //call the method to create a file from the patch notes
        patchFile(commands);
    }
    
    public static String getIndex() throws UnknownHostException, IOException{
        //Create a socket for the connection
        Socket s = new Socket(InetAddress.getByName("leagueoflegends.wikia.com"), 80);
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        //Send out a request for the index page
        pw.print("GET /wiki/League_of_Legends_Wiki HTTP/1.1\r\n");
        pw.print("Host: leagueoflegends.wikia.com\r\n");
        pw.print("Connection: close\r\n\r\n");
        pw.flush();
        //Create a string from the response
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        StringBuilder htmlPage = new StringBuilder();
        String t;
        while((t = br.readLine()) != null){
            htmlPage.append(t).append("\n");
        }
        br.close();
        //Return the html string
        return htmlPage.toString();
    }
    
    public static void patchFile(String patch)throws UnknownHostException, IOException{
        //Open socket
        Socket s = new Socket(InetAddress.getByName("leagueoflegends.wikia.com"), 80);
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        //Throw request for the page
        pw.print("GET /wiki/V"+patch+" HTTP/1.1\r\n");
        pw.print("Host: leagueoflegends.wikia.com\r\n");
        pw.print("Connection: close\r\n\r\n");
        pw.flush();
        //Take the response and build a string
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        StringBuilder htmlPage = new StringBuilder();
        String t;
        while((t = br.readLine()) != null){
            htmlPage.append(t).append("\n");
        }
        br.close();
        //Output the response string to an html file for viewing
        File html = new File(patch+"PatchNotes.html");
        FileWriter fW = new FileWriter(html);
        fW.write(htmlPage.toString());
        fW.flush();
        fW.close();
        //Alert the user
        System.out.println("The patch notes are saved to /"+patch+"PatchNotes.html (Many images and links are disabled)");
    }
    
    public static void parseFree(String htmlPage){
        //Parse out html ordered list <ol></ol>
        Scanner scanner = new Scanner(htmlPage);
        boolean remFlag = true;
        boolean end=false;
        StringBuilder temp = new StringBuilder();
        String line = null;
        while (scanner.hasNextLine()) {
          line = scanner.nextLine();
          if(line.equals("<ol class=\"free_champion_rotation\">") && end==false) remFlag=false;
          if(!remFlag){
              temp.append(line).append("\n");
          }
          if(!remFlag && line.equals("</ol>")){
              remFlag=true;
              end=true;
          }
        }
        scanner.close();
        
        //Parse names from inside <ol></ol>
        String[] temp2;
        String[] temp3;
        StringBuilder response = new StringBuilder();
        for(int i=0;i<14;i++){
            temp2 = temp.toString().split("data-champion=\"",2);
            temp3 = temp2[1].split("\"",2);
            response.append(temp3[0]).append(" | ");
            temp.setLength(0);
            temp.append(temp3[1]);
        }
        
        //Print out names of free champions
        System.out.println(response);
    }
}
