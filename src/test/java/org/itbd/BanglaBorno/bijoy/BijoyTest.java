package org.itbd.BanglaBorno.bijoy;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.itbd.banglaborno.bijoy.b2u.Bijoy2Unicode;
import org.itbd.banglaborno.bijoy.u2b.Unicode2Bijoy;
import org.junit.jupiter.api.Test;

public class BijoyTest {
    private static String BIJOY = "bijoy";
    private static String SOMEWHEREIN = "somewherein";
    private static String BOISAKHI = "boisakhi";

    @Test
    public void Unicode2BijoyTest() {
        Unicode2Bijoy unicode2Bijoy = new Unicode2Bijoy();
        try {
//            String dom = unicode2Bijoy.ConvertToASCII(BIJOY, "সোনার ");
            String dom = unicode2Bijoy.ConvertToASCII(BIJOY, "আমার সোনার বাংলা আমি তোমায় ভালোবাসি  ");
            System.out.println(dom);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void Bijoy2UnicodeTest() {
        Bijoy2Unicode bijoy2Unicode = new Bijoy2Unicode();
        try {
            String dom = bijoy2Unicode.ConvertToUnicode(BIJOY, "Avgvi ‡mvbvi evsjv Avwg ‡Zvgvq fv‡jvevwm  ");
            System.out.println(dom);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
