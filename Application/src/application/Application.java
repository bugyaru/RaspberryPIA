/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import com.bug.display.DisplayThread;
import com.bug.keyPad.ButtonTread;
import com.bug.keyPad.ButtonEvents;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;


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
                // create gpio controller
        GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #01 as an output pin and turn on
        GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.HIGH);

        // set shutdown state for this pin
        pin.setShutdownOptions(true, PinState.HIGH);

        
        dt.start();
        test.addButtonEventsListener((ButtonEvents e) -> {
            //System.out.println(e.getMessage());
            //System.out.println();
            if("00100".equals(test.getButtonsState())){
            pin.setState(PinState.LOW);
            System.out.println(e.getMessage());
            }
             if("01000".equals(test.getButtonsState())){
            pin.setState(PinState.HIGH);
            System.out.println(e.getMessage());
            }
            dt.setDisplayData(0, 0, test.getButtonsState(), 0);
        });
        test.start();
        pin.setState(PinState.HIGH);
        Thread.sleep(60000);
        
        test.finish();
        dt.finish();
        //System.out.println(test.flag);

    }

}
