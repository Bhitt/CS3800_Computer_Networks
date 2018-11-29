/*
HtmlParse: Parses out various text from given html page (string)
 */
package network_test;

import java.util.Scanner;

/**
 *
 * @author bhitt
 */
public class HtmlParse {

    public void parseFree(String htmlPage){
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
