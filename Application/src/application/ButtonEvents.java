/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;
import java.util.EventObject;
/**
 *
 * @author pusig
 */
public class ButtonEvents extends EventObject{
  private String message;

    public ButtonEvents(Object source, String message) {
 	   super(source);
 	   this.message = message;
   }

   public ButtonEvents(Object source){
 	   this(source, "");
   }

   public ButtonEvents(String s){
 	   this(null, s);
   }

   public String getMessage(){
 	  return message;
   }

   @Override
   public String toString(){
 	  return getClass().getName() + "[source = " + getSource() + ", message = " + message + "]";
   }
}  

