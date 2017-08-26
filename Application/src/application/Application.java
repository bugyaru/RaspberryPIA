/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

/**
 *
 * @author pusig
 */
public class Application {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ButtonTread test;
        test = new ButtonTread();

        test.addButtonEventsListener((ButtonEvents e) -> {
            System.out.println(e.getMessage());
            System.out.println(test.getButtonsState());
        });
        test.start();
        Thread.sleep(60000);
        test.finish();
        System.out.println(test.flag);
    }

}
