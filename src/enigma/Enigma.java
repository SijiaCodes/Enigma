/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigma;

/**
 *
 * @author Sijia
 * @version 2.0 17-04-2015
 */
public class Enigma {

    public static final String[] ROTORS = {
       //ABCDEFGHIJKLMNOPQRSTUVWXYZ
        "EKMFLGDQVZNTOWYHXUSPAIBRCJ",
        "AJDKSIRUXBLHWTMCQGZNPYFVOE",
        "BDFHJLCPRTXVZNYEIWGAKMUSQO",
        "ESOVPZJAYQUIRHXLNFTGKDCMWB",
        "VZBRGITYUPSDNHLXAWMJQOFECK"
    };
    
    public static final String[] INVERSE = {
       //ABCDEFGHIJKLMNOPQRSTUVWXYZ
        "UWYGADFPVZBECKMTHXSLRINQOJ",
        "AJPCZWRLFBDKOTYUQGENHXMIVS",
        "TAGBPCSDQEUFVNZHYIXJWLRKOM",
        "HZWVARTNLGUPXQCEJMBSKDYOIF",
        "QCYLXWENFTZOSMVJUDKGIARPHB"
    };
//YRUHQSLPXNOZWVTMKJIGFEDCBA
    public static final String REFLECTOR = "YRUHQSLPXNOZWVTMKJIGFEDCBA";
    //"YRUHQSLDPXNGOKMIEBFZCWVJAT";
    public static final char[] NOTCHES = {'R', 'F', 'W', 'K', 'A'};

    public static final int ROTERS_IN_BOX = 5;
    public static final int ROTERS_IN_MACHINE = 3;
    public static final int MAX_PLUG_PAIRS = 12;

    private int[] rotor;
    private int[] position;
    private int[] ring;
    private int[][] plugboard;

    private static final boolean DEBUGGING = true;

    /**
     * Creates a new Enigma with the given information.
     *
     * @param rotors the rotors of the Enigma as denoted by <code>X-X-X</code>,
     * where <code>X</code> is the number of each rotor in Roman numerals.
     * @param positions the start position of the enigma denoted by
     * <code>XXX</code> where <code>X</code> is the top letter on each rotor.
     * @param plugboard the state of the plugboard as denoted by a string of 
     * "<code>XX </code>"s where <code>XX</code> is a pair of letters getting
     * switched.
     * @param rings the ring positions of the Enigma as denoted by
     * <code>XXX</code> where <code>X</code> is the start position of each
     * ring.
     */
    public Enigma(String rotors, String positions, String plugboard,
            String rings) {

        this.rotor = new int[3];
        this.position = new int[3];
        this.ring = new int[3];

        String[] temp = rotors.split("-");
        if (DEBUGGING); //System.out.println(position);

        for (int i = 0; i < ROTERS_IN_MACHINE; i++) {
            if (temp[i].equals("I")) {
                rotor[i] = 0;
            } else if (temp[i].equals("II")) {
                rotor[i] = 1;
            } else if (temp[i].equals("III")) {
                rotor[i] = 2;
            } else if (temp[i].equals("IV")) {
                rotor[i] = 3;
            } else if (temp[i].equals("V")) {
                rotor[i] = 4;
            }
        }

        for (int i = 0; i < this.ROTERS_IN_MACHINE; i++) {
            position[i] = (int) positions.charAt(i) - (int) 'A';
            ring[i] = (int) rings.charAt(i) - (int) 'A';
            //(int) search(positions.charAt(i), ROTORS[rotor[i]])
        }

        if (plugboard == null || plugboard.isEmpty()) {
            return;
        }

        temp = plugboard.split(" ");
        this.plugboard = new int[temp.length][2];
        int index = 0;

        for (int i = 0; i < temp.length; i++) {
            this.plugboard[i][0] = charToInt((temp[i]).charAt(0), 'A');
            this.plugboard[i][1] = charToInt((temp[i]).charAt(1), 'A');
            //System.out.println("" + this.plugboard[i][0] + " " + this.plugboard[i][1]);
            if (this.plugboard[i][1] == this.plugboard[i][0]) {
                System.out.println("Yo this is impossible "
                        + "on the actual machine");
            }
        }
    }

    /**
     *
     * @param c
     * @param offset
     * @return
     */
    static public int charToInt(char c, char offset) {
        return (int) c - (int) offset;
    }

    /**
     * Returns the position on each of the rotors in order from left to right.
     * It is denoted by <code>X-X-X</code> where <code>X</code> is the number of
     * each rotor in Roman numerals.
     *
     * @return the notches on each of the rotors in order from left to right as
     * denoted by <code>XXX</code>
     */
    public String getRotors() {
        String returnString = "";
        for (int i = 0; i < 3; i++) {
            switch (rotor[i]) {
                case 0:
                    returnString += "I";
                    break;
                case 1:
                    returnString += "II";
                    break;
                case 2:
                    returnString += "III";
                    break;
                case 3:
                    returnString += "IV";
                    break;
                case 4:
                    returnString += "V";
                    break;
            }
            if (i != 2) {
                returnString += "-";
            }
        }

        return returnString;
    }

    /**
     * Returns the state of the switchboard of this Enigma. It is in the form of
     * "<code>XX </code>" where <code>XX</code> is a pair of letters getting
     * switched.
     *
     * @return the state of the switchboard of this Enigma
     */
    public String getPlugboard() {
        String returnString = "";

        if (this.plugboard == null) {
            return "NULL";
        }

        for (int i = 0; i < plugboard.length; i++) {
            if(plugboard[i][0] == 0)
                break;
            returnString += " " + intToChar(plugboard[i][0], 'A') + intToChar(plugboard[i][1], 'A');
        }

        return returnString;
    }

    /**
     * Returns the position on each of the rotors in order from left to right.
     * It is denoted by <code>XXX</code> where <code>X</code> is the top letter
     * on each rotor.
     *
     * @return the notches on each of the rotors in order from left to right as
     * denoted by <code>XXX</code>
     */
    public String getPositon() {
        String returnString = "";
        for (int i = 0; i < 3; i++) {
            returnString += (char) (position[i] + (int) 'A');
            //System.out.println(position[i]);
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
    public char goThrough(char c) {
        char capital = 'A';
        int temp = 0;

        if (c >= 'a' && c <= 'z') {
            capital = 'a';
            temp = charToInt(c, 'a');
        } else if (c >= 'A' && c <= 'Z') {
            capital = 'A';
            temp = charToInt(c, 'A');
        }
        
        assert (temp < 26 && temp >= 0);

        if (plugboard != null) {
            temp = usePlugboard(temp);
        }

        for (int i = 2; i >= 0; i--) {
            int pos = (temp + (position[i] - ring[i])) % 26;
            if (pos < 0) {
                pos += 26;
            }
            temp = (charToInt(ROTORS[rotor[i]].charAt(pos), 'A') - (position[i] - ring[i])) % 26;
            if (temp < 0) {
                temp += 26;
            }
            //System.out.print(intToChar(temp, 'A'));

        }
        

        // reflector 
        int pos = 25 - search(intToChar(temp, 'A'), REFLECTOR);

        temp = (int) REFLECTOR.charAt(pos) - (int) 'A';
        //System.out.print(" " + REFLECTOR.charAt(pos) + " ");

        for (int i = 0; i < 3; i++) {
            pos = (temp + (position[i] - ring[i])) % 26;
            if (pos < 0) {
                pos += 26;
            }
            temp = (charToInt(INVERSE[rotor[i]].charAt(pos), 'A') - (position[i] - ring[i])) % 26;
            if (temp < 0) {
                temp += 26;
            }
            //System.out.print(intToChar(temp, 'A'));
            /*
            pos = search(intToChar(temp, 'A'), ROTORS[i]);
            assert(pos != -1);
            
            temp = pos - position[i];
            */
        }
        //System.out.println();

        if (plugboard != null) {
            return Enigma.intToChar(usePlugboard(temp), capital);
        } else {
            return intToChar(temp, capital);
        }
    }

    /**
     * Increments the enigma machine.
     * <b>How the enigma increments</b>
     * Each rotor has a notch on it which if triggered will cause the rotor next
     * to it to increment. The leftmost or first rotor increments each time. One
     * more thing is that the 3rd or rightmost rotor turns, it causes the second
     * or middle rotor to also turn.
     *
     * Information from: http://practicalcryptography.com/ciphers/enigma-cipher/
     */
    private void increment() {
        position[2]++;

        for (int i = 2; i >= 0; i--) {
            if (position[i] == NOTCHES[rotor[i]]) {
                position[Math.max(0, i - 1)]++;

                if (position[i] >= 26) {
                    position[i] = 0;
                }

                if (i == 1) {
                    position[0]++;
                    if (position[0] >= 26) {
                        position[0] = 0;
                    }
                }
            }
            if (position[i] >= 26) {
                position[i] = 0;
            }
        }
    }

    static public char intToChar(int i, char offset) {
        assert (charToInt((char) (i + (int) offset), offset) == i);
        return (char) (i + (int) offset);
    }

    /**
     * Searches for a char c in a String s.
     *
     * @param c the char to be found
     * @param s the String to be searched
     * @return the position of c if found or -1 if not found
     */
    static public int search(char c, String s) {
        for (int i = 0; i < 26; i++) {
            if (c == s.charAt(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the String representation of this Enigma.
     *
     * @return the String representation of this Enigma
     */
    public String toString() {
        String returnString
                = "Roters: " + getRotors()
                + "\nPosition: " + getPositon()
                + "\nSwitchboard Pairs:" + getPlugboard();

        return returnString;
    }

    /**
     * Translates the given string by putting it through the enigma machine.
     *
     * @param str the string to be translated
     * @return the result of putting the given string through
     */
    public String translate(String str) {
        String returnStr = "";
        int len = str.length();

        for (int i = 0; i < len; i++) {
            if ((str.charAt(i) >= 'a' && str.charAt(i) <= 'z')
                    || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')){
                // the rotors only increment if a valid letter is entered
                char c = str.charAt(i);
                this.increment();
                returnStr += this.goThrough(str.charAt(i));
            } else {
                returnStr += str.charAt(i);
            }
        }
        
        return returnStr;
    }

    /**
     * Switches a given char c with the corresponding letter on the plugboard
     *
     * @param c
     * @return the letter after it has been switched in the plugboard
     */
    private int usePlugboard(int c) {

        for (int i = 0; i < plugboard.length; i++) {
            if (plugboard[i][0] == c) {
                return plugboard[i][1];
            } else if (plugboard[i][1] == c) {
                return plugboard[i][0];
            }
        }
        //System.out.print(intToChar(c,'A'));
        return c;
    }
}
