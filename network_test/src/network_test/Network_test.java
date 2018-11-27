/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network_test;


/**
 *
 * @author bhitt
 */
public class Network_test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Comm comm = new Comm();
        String test;
        test=comm.getHTML("http://leagueoflegends.wikia.com/wiki/League_of_Legends_Wiki");
        System.out.println(test);
    }
    

    
}
