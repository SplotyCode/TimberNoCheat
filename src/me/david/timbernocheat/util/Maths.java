package me.david.timbernocheat.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Maths {

    public static double round(double number, int places){
        BigDecimal bd = new BigDecimal(number).setScale(places, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }
}
