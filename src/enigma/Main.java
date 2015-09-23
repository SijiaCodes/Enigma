/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigma;

import java.util.Scanner;

/**
 *
 * @author Sijia
 */
public class Main {

    public static Enigma enigma = null;
    public static Enigma enigma_copy = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        assert (Enigma.search('a', "abcdefg") == 0);
        assert (Enigma.search('g', "abcdefg") == 6);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 26; j++) {
                char a = (char) ((int) j + (int) 'A');
                int b = (int) Enigma.ROTORS[i].charAt(j) - (int) 'A';
                assert (Enigma.search(a, Enigma.INVERSE[i]) == b);
            }
        }
        int option = 1;
        do {
            option = menu();
            if (enigma == null && option > 1) {
                System.out.println("That isn't an option, Please try again");
                continue;
            }

            switch (option) {
                case 1:
                    alterEnigma();
                    break;
                case 2:
                    fromFile();
                    break;
                case 3:
                    fromConsole();
                    break;
                case 4:
                    save();
                    break;
                case 0: break;
                default:
                    System.out.println("That isn't an option."
                            + " Please try again");
            }

        } while (option != 0);
        exit();
    }

    private static void save() {
        assert (enigma != null);

        boolean cont = false;
        while (enigma_copy != null && !cont) {
            Scanner scr = new Scanner(System.in);
            scr.useDelimiter("");

            System.out.println("Are you sure you wish to overwrite this");
            System.out.println("");
            System.out.println(enigma_copy.toString());
            System.out.println("");
            System.out.println("With This?");
            System.out.println("");
            System.out.println(enigma.toString());
            System.out.println("");
            System.out.println("(Y or N)");
            
            String c = scr.next().toUpperCase();
            
            if (c.equals("N")) {
                return;
            } else if (c.equals("Y")) {
                cont = true;
            }
        }

        enigma_copy = enigma;
    }

    private static void intro() {
        System.out.println("Hello World! Welcome to my Enigma Machine.");
    }

    private static void fromFile() {
        System.out.println("This option is currently unavailable");
        //System.out.println("Please enter the path of the file you would like to open");
    }

    private static void fromConsole() {
        assert (enigma != null);
        Scanner scr = new Scanner(System.in);
        String str = scr.nextLine();
        System.out.println(enigma.translate(str));
    }

    private static int menu() {
        Scanner scr = new Scanner(System.in);
        System.out.println("What would you like to do?");

        if (enigma != null) {
            System.out.println("(1) Alter Enigma");
            System.out.println("(2) Read from txt file");
            System.out.println("(3) Read from console");
            System.out.println("(4) Save Enigma");
        } else {
            System.out.println("(1) Set a new Enigma");
        }
        System.out.println("(0) Quit Program");

        return scr.nextInt();
    }

    private static void alterEnigma() {
        Scanner scr = new Scanner(System.in);

        if (enigma_copy != null) {
            boolean cont = false;
            System.out.println("");
            
            while (!cont) {
                System.out.println("Are you sure you wish to overwrite this");
                System.out.println("");
                System.out.println(enigma.toString());
                System.out.println("");
                System.out.println("With This?");
                System.out.println("");
                System.out.println(enigma_copy.toString());
                System.out.println("");
                System.out.println("(Y or N)");
                String c = scr.next().toUpperCase();
                if (c.equals("N")) {
                    return;
                } else if (c.equals("Y")) {
                cont = true;
            }
            }
            enigma = enigma_copy;
        }

        System.out.println("Please enter the rotor numbers in the form of"
                + " N-N-N where N is a roman numeral from I to V");
        String order = scr.nextLine();

        System.out.println("Please enter the starting positions in the form of "
                + "XXX where where X is the top letter on each rotor.");
        String position = scr.nextLine();

        System.out.println("Please enter the plugboard settings as a series "
                + "of pairs of capital letters seperated by a space");
        String plugboard = scr.nextLine();
        
        System.out.println("Please enter the ring settings of the enigma "
                + "in the same format as the rotors");
        String rings = scr.nextLine();
        enigma = new Enigma(order, position, plugboard, rings);

        System.out.println("Successfully created enigma");
        System.out.println(enigma.toString());

    }

    private static void exit() {
        System.out.println("Thank you for using this Enigma Program. "
                + "\nHave a nice day!");
    }
}
