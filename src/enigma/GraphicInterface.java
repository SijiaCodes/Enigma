/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigma;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sijia
 */
public class GraphicInterface extends JFrame {

    /**
     * @param args the command line arguments
     */
    private final int WIDTH = 1200;
    private final int HEIGHT = 800;
    
    private final int BUTTON_WIDTH = 300;
    private final int BUTTON_HEIGHT = 100;

    /**
     *
     * @throws IOException
     */
     


    public GraphicInterface() throws IOException {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setContentPane(frame);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        frame.setTitle("Enigma");  // this JFrame sets the title
        frame.setVisible(true);    // this JFrame show
        frame.setBackground(Color.WHITE);  // set background color for this JPanel
        
        frame.add(new JLabel(new ImageIcon("AlanTuring.jpg")));        
        
        /*
        
        g.setColor(Color.white);
            
        int w = 1200;
        int h = 800;
        g.fillRect(0, 0, w, h);
            
        g.setColor(Color.lightGray);
        g.fillRect(100, 600, BUTTON_WIDTH, BUTTON_HEIGHT);
        g.fillRect(450, 600, BUTTON_WIDTH, BUTTON_HEIGHT);
        g.fillRect(800, 600, BUTTON_WIDTH, BUTTON_HEIGHT);
            
        Font buttonFont = new Font("Monospaced", Font.PLAIN, 26);
        g.setFont(buttonFont);
        g.setColor(Color.BLACK);
            
            
            
        g.drawImage(turing, 100, 100, null);
            
        g.drawString("About the Enigma", 120, 660);
        g.drawString("Breaking the ", 490, 640);
        g.drawString("Enigma", 530,670);
        g.drawString("Use the Enigma", 820, 660);
        frame.pack();              // Either pack() the components; or setSize()
         */
        }
    
    public static void main(String[] args) throws IOException {
        GraphicInterface a = new GraphicInterface();
    }
}
