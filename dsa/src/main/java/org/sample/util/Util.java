package org.sample.util;

public class Util {
    public void setBit(int number, int bit) { // make feature bit 1
        number |= (int) Math.pow(2, bit);
    }

    public void resetPriviledge(int number, int bit) { // make feature bit 1
        number &= ~(1 << bit);
    }

    public boolean isBitSet(int number, int bit) { // check if feature bit is 1
        return ((number >> bit) & 1) == 1;
    }
}
