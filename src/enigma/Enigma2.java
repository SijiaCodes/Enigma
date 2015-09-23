
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigma;
import java.lang.Math;
import java.lang.Exception;
/**
 *
 * @author Sijia
 * @version 1.00 on 15-04-2015
 */
public class Enigma2 {
    private int[] rotor;
    private int[] notch;
    private char[][] plugboard;
    /**
     * The maximum amount of letters each rotor can have;
     */
    public final int MAX_rotor = 26;

    private final boolean DEBUGGING = true;

    /**
     * Creates a new Enigma with the given information.
     * @param rotor an array of integers to be stored as the rotors of the
     * enigma they are the starting positions of the rotors.
     * @param notch The position of the notches on each of the rotors. It is
     * implemented as an integer array
     * @param plugboard The plugboard which switches two characters. It is
     * implemented as an n x 2 array of characters. Null if the plugboard is
     * not initialized
     */
    public Enigma2(int[] rotor, int[] notch, char[][] plugboard) {
        this.rotor = rotor;
        this.notch = notch;
        this.plugboard = plugboard;
    }

    /**
     * Creates a new Enigma with the given information.
     *
     * @param rotors the rotors of the Enigma as denoted by <code>XXX</code>,
     * where <code>X</code> is any capital letter
     * @param notches the notches on each of the rotors of the Enigma
     * as denoted by <code>XXX</code>, where <code>X</code> is any capital
     * letter.
     * @param plugboard the plugboard of the Enigma as denoted by two capital
     * letters followed by a space as denoted by "<code>XX </code>".
     * <br><b>Requires</b>: plugs cannot be repeated.
     */
    public Enigma2(String rotors, String notches, String plugboard) {
        
        
        this.rotor = new int[3];
        this.notch = new int[3];

        for(int i = 0; i < 3; i++) {
            this.rotor[i] = (int)rotors.charAt(i) - (int)'A';
            this.notch[i] = (int)notches.charAt(i) - (int)'A';

        }
        if (plugboard == null)
            return;
        this.plugboard = new char[10][2];
        String[] temp = plugboard.split(" ");
        int index = 0;
        for(int i = 0; i < 10; i++) {
            this.plugboard[i][0] = (temp[i]).charAt(0);
            this.plugboard[i][1] = (temp[i]).charAt(1);
            if(this.plugboard[i][1] == this.plugboard[i][0]) {
                System.out.println("Yo this is impossible "
                        + "on the actual machine");
            }
        }
    }

    /**
     * Returns the notches on each of the rotors in order from left to right.
     * It is denoted by <code>XXX</code>.
     *
     * @return the notches on each of the rotors in order from left to right
     * as denoted by <code>XXX</code>
     */
    public String getNotches() {
        String returnString = "";
        for(int i = 0; i < 3; i++) {
            returnString += (char)(notch[i] + (int)'A');
        }
        return returnString;
    }

    /**
     * Returns the position on each of the rotors in order from left to right.
     * It is denoted by <code>XXX</code> where <code>X</code> is the top letter
     * on each rotor.
     *
     * @return the notches on each of the rotors in order from left to right
     * as denoted by <code>XXX</code>
     */

    public String getRotors() {
        String returnString = "";
        for(int i = 0; i < 3; i++) {
            returnString += (char)(rotor[i] + (int)'A');
        }
        return returnString;
    }

    /**
     * Returns the state of the switchboard of this Enigma. It is in the form of
     * "<code>XX </code>" where <code>XX</code> is a pair of boards getting
     * switched.
     * @return the state of the switchboard of this Enigma
     */
    public String getPlugboard() {
        String returnString = "";

        if(this.plugboard == null) {
            return "NULL";
        }
        
        for(int i = 0; i < 10; i++) {
            returnString += plugboard[i][0] + plugboard[i][1] + " ";
        }

        return returnString;
    }

    /**
     * Puts a single character through the enigma, then turns the routers.
     * <b>This is how an enigma works.</b>
     * In the physical machine, a letter would be put through each of the
     * routers which would map the letter to another. then there would be a
     * reflected which puts the letter through the system again in reverse
     * order.
     *
     * Information from: http://practicalcryptography.com/ciphers/enigma-cipher/
     *
     * @param c the character which is put through the enigma
     * @return the result after the character is put through the enigma, the
     */
    private char goThrough(char c) {
        int capital;
        int temp = 0;

        if(c > 'a' && c < 'z')
            capital = (int)'a';
        else if (c > 'A' && c < 'Z')
            capital = (int)'A';
        else
            return c;

        this.increment();

        if(plugboard != null)
            temp = (int)usePlugboard(c) - capital;
        else
            temp = (int)c - capital;
        System.out.println((char)(temp + capital));

        for(int i = 2; i >= 0; i--) {
            temp += rotor[i];
            if(temp > 25)
                temp -= 26;
        }

        for(int i = 0; i < 3; i++) {
            temp += rotor[i];
            if(temp > 25)
                temp -= 26;
        }
        if(plugboard != null)
            return usePlugboard((char)(temp + capital));
        else
            return (char)(temp + capital);


    }

    /**
     * Increments the enigma machine.
     * <b>How the enigma increments</b>
     * Each rotor has a notch on it which if triggered will cause the rotor next
     * to it to increment. The leftmost or first rotor increments each time.
     * One more thing is that the 3rd or rightmost rotor turns, it causes the
     * second or middle rotor to also turn.
     *
     * Information from: http://practicalcryptography.com/ciphers/enigma-cipher/
     */
    private void increment() {
        rotor[2]++;

        for(int i = 2; i >= 0; i--) {
            if (rotor[i] == notch[i]) {
                rotor[Math.max(0, i - 1)]++;
                rotor[i] = 0;
                if(i == 1) {
                    rotor[0]++;
                }
            }
        }
    }

    public String toString() {
        String returnString =
                "Roters: " + getRotors()+
                "\nnotches: " + getNotches() +
                "\nSwitchboard Pairs: " + getPlugboard();

        return returnString;
    }

    /**
     * Translates the given string by putting it through the enigma machine.
     * @param str the string to be translated
     * @return the result of putting the given string through
     */

    public String translate(String str) {
        String returnStr = "";
        int len = str.length();

        for(int i = 0; i < len; i++) {
            returnStr += this.goThrough(str.charAt(i));
        }

        return returnStr;
    }


    private char usePlugboard(char c) {
        for(int i = 0; i < 10; i++) {
            if(plugboard[i][0] == c)
                return plugboard[i][1];
            else if (plugboard[i][1] == c)
                return plugboard[i][0];
        }

        return c;
    }
}

