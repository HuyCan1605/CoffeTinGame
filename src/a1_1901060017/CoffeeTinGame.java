/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a1_1901060017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import utils.TextIO;

/**
 *
 * @author Khánh Huyền
 */
public class CoffeeTinGame {

    /**
     * constant value for the green bean
     */
    private static final char GREEN = 'G';
    /**
     * constant value for the blue bean
     */
    private static final char BLUE = 'B';
    /**
     * constant for removed beans
     */
    private static final char REMOVED = '-';
    /**
     * the null character
     */
    private static final char NULL = '\u0000';

    /**
     * the main procedure
     *
     * @effects initialise a coffee tin {@link TextIO#putf(String, Object...)}:
     * print the tin content {@link @tinGame(char[])}: perform the coffee tin
     * game on tin {@link TextIO#putf(String, Object...)}: print the tin content
     * again if last bean is correct {@link TextIO#putf(String, Object...)}:
     * print its colour else {@link TextIO#putf(String, Object...)}: print an
     * error message
     */
    public static void main(String[] args) {
        // initialise some beans
        char[][] tins = {
            {BLUE, BLUE, BLUE, GREEN, GREEN},
            {BLUE, BLUE, BLUE, GREEN, GREEN, GREEN},
            {GREEN},
            {BLUE},
            {BLUE, GREEN}
        };

        for (int i = 0; i < tins.length; i++) {
            // count number of greens
            char[] tin = tins[i];
            int greens = 0;
            for (char bean : tin) {
                if (bean == GREEN) {
                    greens++;
                }
            }

            final char last = (greens % 2 == 1) ? GREEN : BLUE;
            // p0 = green parity /\
            // (p0=1 -> last=GREEN) /\ (p0=0 -> last=BLUE)

            // print the content of tin before the game
            TextIO.putf("tin before: %s %n", Arrays.toString(tin));

            // perform the game
            char lastBean = tinGame(tin);
            // lastBean = last \/ lastBean != last

            // print the content of tin and last bean
            TextIO.putf("tin after: %s %n", Arrays.toString(tin));

            // check if last bean as expected and print
            if (lastBean == last) {
                TextIO.putf("last bean: %c %n", lastBean);
            } else {
                TextIO.putf("Oops, wrong last bean: %c (expected: %c)%n",
                        lastBean, last);
            }
            TextIO.putf("%n");
        }
    }

    /**
     * must use procedure randInt to randomly take out a bean.
     *
     * @requires: BeansBag not null, BeansBag.size > 0
     * @modifies:
     * @effects: use randInt to take order of bean return color of that bean
     * which is taken order.
     */
    public static char takeOne(ArrayList<Character> BeansBag) {
        int rdBean;
        char bean = 0;
        int count = 0; //count beans in bag 
        for (int i = 0; i < BeansBag.size(); i++) {
            if (BeansBag.get(i) != REMOVED) {
                count++;
            }
        }

        for (int i = 0; i < BeansBag.size(); i++) {
            rdBean = randInt(count); // index of random bean
            bean = BeansBag.get(rdBean);
            if (bean != REMOVED) {
                BeansBag.remove(rdBean); //remove bean
                break;
            }
        }
        return bean;
    }

    /**
     * returns as output an integer number randomly selected from the range [0,
     * size of bean bag).
     *
     * @requires: BeansBag not null /\ BeansBag.size > 0
     * @modifies:
     * @effects: repeatedly return order of bean
     */
    public static int randInt(int n) {
        int rdBean = 0;
        Random rd = new Random();
        rdBean = rd.nextInt(n);
        return rdBean;
    }

    /**
     * find and return a randomly-selected bean that matches the bean type. The
     * found bean is also removed from the array
     *
     * @requires: BeansBag not null /\ BeanBag.size > 0
     * @modifies: BeansBag
     * @effects: after taking out two beans if { they are the same color throw
     * them both away put a Blue bean back in } else { throw away the blue one
     * put the green one back }
     */
    public static void getBean(char bean1, char bean2, ArrayList<Character> BeansBag) {
        if (bean1 == bean2) {
            BeansBag.add(REMOVED);
            BeansBag.add(0, BLUE);
        } else {
            BeansBag.add(REMOVED);
            BeansBag.add(0, GREEN);
        }
    }

    /**
     * takes as input the tin and two beans and updates tin accordingly. In
     * addition, this procedure must use getBean to obtain a desired bean from
     * BeansBag.
     *
     * @requires: BeansBag not null /\ BeanBag.size > 0
     * @modifies: BeansBag
     * @effects: take random 2 beans. Use getBean to update tin
     */
    public static void updateTin(ArrayList<Character> BeansBag) {
        char bean1 = 0, bean2 = 0;
        bean1 = takeOne(BeansBag);
        bean2 = takeOne(BeansBag);
        getBean(bean1, bean2, BeansBag);
    }

    /**
     * must be changed to use procedure updateTin.
     *
     * @requires: BeansBag not null /\ BeanBag.size > 0
     * @modifies:
     * @effects: implement the game. Input the bean G for Green and B for Blue
     * Print all the beans before play Repeat implement the game until BeansBag
     * = 1 Print the last bean.
     */
    public static char tinGame(char[] tin) {
        ArrayList<Character> BeansBag = new ArrayList<>(100);
        putIn(tin, BeansBag); //put tin in Beans Bag
        //start play
        
        int n = BeansBag.size(); //after 1 round, we will lose total 1 bean.
        while (n != 1) {          //Therefore, when we have only 1 bean left that mean 
            updateTin(BeansBag);// we have play size - 1 round
            n--;
        }
        
        putBack(tin, BeansBag); // put out beans bag in tin
        return BeansBag.get(0);
    }

    /**
     * removes all bean from tin to Beans Bag
     *
     * @requires:BeansBag not null /\ BeanBag.size > 0 /\ Tin not null /\ Tin >
     * 0
     * @modifies: tin
     * @effects: bean in tin has been removed become '-'
     */
    public static void putIn(char[] tin, ArrayList<Character> BeansBag) {
        for (int i = 0; i < tin.length; i++) {
            BeansBag.add(tin[i]);
            tin[i] = REMOVED;
        }
    }

    /**
     * removes all bean from tin to Beans Bag
     *
     * @requires:BeansBag not null /\ BeanBag.size > 0 /\ Tin not null /\ Tin >
     * 0
     * @modifies: tin
     * @effects: remove last bean from Beans Bag to tin
     */
    public static void putBack(char[] tin, ArrayList<Character> BeansBag) {
        tin[0] = BeansBag.get(0);
    }

}
