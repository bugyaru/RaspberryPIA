/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import com.pi4j.gpio.extension.pcf.PCF8574GpioProvider;
import com.pi4j.gpio.extension.pcf.PCF8574Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 *
 * @author pusig
 */
public class ButtonTread extends Thread {

    private ArrayList<ButtonEventsListener> listeners = new ArrayList<ButtonEventsListener>();
    public boolean flag = true;
    private Boolean buttonsState[]={false,false,false,false,false};
    @Override
    public void run() {
        System.out.println("<--Pi4J--> PCF8574 GPIO Example ... started.");
        final GpioController gpio = GpioFactory.getInstance();
        final PCF8574GpioProvider provider;
        try {

            provider = new PCF8574GpioProvider(I2CBus.BUS_1, PCF8574GpioProvider.PCF8574_0x24);
            GpioPinDigitalInput myInputs[] = {
                gpio.provisionDigitalInputPin(provider, PCF8574Pin.GPIO_01),
                gpio.provisionDigitalInputPin(provider, PCF8574Pin.GPIO_02),
                gpio.provisionDigitalInputPin(provider, PCF8574Pin.GPIO_03),
                gpio.provisionDigitalInputPin(provider, PCF8574Pin.GPIO_04),
                gpio.provisionDigitalInputPin(provider, PCF8574Pin.GPIO_05)
            };
            gpio.addListener((GpioPinListenerDigital) (GpioPinDigitalStateChangeEvent event) -> {
                if (event.getState().isLow()) {
                    buttonsState[event.getPin().getPin().getAddress()-1]=true;
                    fireButtonEvents(String.valueOf(event.getPin().getPin().getAddress())+"-DOWN");
                    
                }
                if (event.getState().isHigh()) {
                    buttonsState[event.getPin().getPin().getAddress()-1]=false;
                    fireButtonEvents(String.valueOf(event.getPin().getPin().getAddress())+"-UP");
                    
                }
            }, myInputs);
            GpioPinDigitalOutput myOutputs[] = {
                gpio.provisionDigitalOutputPin(provider, PCF8574Pin.GPIO_00, PinState.LOW)
            };

            gpio.setShutdownOptions(true, PinState.HIGH, myOutputs);
            while (flag) {

            }
            gpio.removeAllListeners();
            gpio.shutdown();
            System.out.println("Exiting PCF8574GpioExample");
        } catch (UnsupportedBusNumberException | IOException ex) {
            Logger.getLogger(ButtonTread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void finish() //Инициирует завершение потока
    {
        flag = false;
    }
    public String getButtonsState(){
        String out="";
        for (Boolean buttonsState1 : this.buttonsState) {
            if (buttonsState1) {
                out=out+"1";
            } else {
                out=out+"0";
            }
        }
        return out;
    }
    public void addButtonEventsListener(ButtonEventsListener listener) {
        listeners.add(listener);
    }

    public ButtonEventsListener[] getButtonEventsListeners() {
        return listeners.toArray(new ButtonEventsListener[listeners.size()]);
    }

    public void removeButtonEventsListener(ButtonEventsListener listener) {
        listeners.remove(listener);
    }

    protected void fireButtonEvents(String message) {
        ButtonEvents ev = new ButtonEvents(this, message);
        listeners.forEach((listener) -> {
            listener.ButtonEvent(ev);
        });
    }
}
