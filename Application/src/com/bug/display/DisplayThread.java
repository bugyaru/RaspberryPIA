package com.bug.display;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.pi4j.component.lcd.impl.I2CLcdDisplay;
import com.pi4j.component.lcd.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
class displayData {
    public int row=0;
    public int col=0;
    public boolean flChange = false;
    public int align = 0;
    public String message="";
}

public class DisplayThread extends Thread {

    public boolean flagx = true;
    private I2CLcdDisplay lcd;
    private displayData displayDt =new displayData();
    @Override
    public void run() {
        try {
            
            lcd = new I2CLcdDisplay(
                    2, //     * @param rows
                    16, //     * @param columns
                    1, //     * @param i2cBus
                    39, //     * @param i2cAddress
                    3, //     * @param backlightBit
                    0, //     * @param rsBit
                    1, //     * @param rwBit
                    2, //     * @param eBit
                    7, //     * @param d7
                    6, //     * @param d6
                    5, //     * @param d5
                    4 //     * @param d4
            );
            while(flagx){
                if(displayDt.flChange){
                    lcd.write(displayDt.row,displayDt.col,displayDt.message);
                    displayDt.flChange=false;
                }
            }
            
            
        } catch (Exception ex) {
            Logger.getLogger(DisplayThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void finish() //Инициирует завершение потока
    {
        flagx = false;
    }
    
    public void setDisplayData(int row,int col, String message,int align){
        displayDt.row=row;
        displayDt.col=col;
        displayDt.message=message;
        displayDt.align=align;
        displayDt.flChange=true;
    }
}
