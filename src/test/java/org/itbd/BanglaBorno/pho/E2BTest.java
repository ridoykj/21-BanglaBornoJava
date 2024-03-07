package org.itbd.BanglaBorno.pho;

import org.itbd.banglaborno.engbng.E2BPhonetic;
import org.junit.jupiter.api.Test;

public class E2BTest {
    @Test
    public void testE2B() {
        E2BPhonetic e2BPhonetic = new E2BPhonetic(false);
        String bn = e2BPhonetic.convert("amader coto nodi chola baka baka");
//        String bn = e2BPhonetic.convert("amader choto nodi chola baka baka dui dhar ucu tar dhalu tar !");
//        String bn = e2BPhonetic.convert("1 ami banglay gan gai");
        System.out.println(bn);
    }
}
