/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import com.bug.display.DisplayThread;
import com.bug.keyPad.ButtonTread;
import com.bug.keyPad.ButtonEvents;

/**
 *
 * @author pusig
 */
public class Application {
    
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, Exception {

        ButtonTread test;
        test = new ButtonTread();
        DisplayThread dt=new DisplayThread();
        
        dt.start();
        test.addButtonEventsListener((ButtonEvents e) -> {
            //System.out.println(e.getMessage());
            //System.out.println();
            dt.setDisplayData(0, 0, test.getButtonsState(), 0);
        });
        test.start();
        Thread.sleep(60000);
        test.finish();
        dt.finish();
        //System.out.println(test.flag);

    }

}
