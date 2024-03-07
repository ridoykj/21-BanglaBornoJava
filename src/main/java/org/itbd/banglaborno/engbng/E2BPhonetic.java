package org.itbd.banglaborno.engbng;


import java.util.regex.Pattern;

public class E2BPhonetic {
    //    https://github.com/mominul/cppAvroPhonetic/tree/master
    private final boolean enableJoNukta;

    public E2BPhonetic(boolean enableJoNukta) {
        this.enableJoNukta = enableJoNukta;
    }

    private String pEnglishText;
    private int ln; //Length of English string
    private int pos; //Position of processing at English string
    private String Rs; //Result string
    private boolean AutoCorrect;

    //Bangle Numbers
    private static final String b_0 = "০";
    private static final String b_1 = "১";
    private static final String b_2 = "২";
    private static final String b_3 = "৩";
    private static final String b_4 = "৪";
    private static final String b_5 = "৫";
    private static final String b_6 = "৬";
    private static final String b_7 = "৭";
    private static final String b_8 = "৮";
    private static final String b_9 = "৯";
    //Bangle  Vowels and Kars
    private static final String b_A = "অ";
    private static final String b_AA = "আ";
    private static final String b_AAkar = "া";
    private static final String b_I = "ই";
    private static final String b_II = "ঈ";
    private static final String b_IIkar = "ী";
    private static final String b_Ikar = "ি";
    private static final String b_U = "উ";
    private static final String b_Ukar = "ু";
    private static final String b_UU = "ঊ";
    private static final String b_UUkar = "ূ";
    private static final String b_RRI = "ঋ";
    private static final String b_RRIkar = "ৃ";
    private static final String b_E = "এ";
    private static final String b_Ekar = "ে";
    private static final String b_O = "ও";
    private static final String b_OI = "ঐ";
    private static final String b_OIkar = "ৈ";
    private static final String b_Okar = "ো";
    private static final String b_OU = "ঔ";
    private static final String b_OUkar = "ৌ";
    //Bangle  Consonants
    private static final String b_Anushar = "ং";
    private static final String b_B = "ব";
    private static final String b_Bh = "ভ";
    private static final String b_Bisharga = "ঃ";
    private static final String b_C = "চ";
    private static final String b_CH = "ছ";
    private static final String b_Chandra = "ঁ";
    private static final String b_D = "দ";
    private static final String b_Dd = "ড";
    private static final String b_Ddh = "ঢ";
    private static final String b_Dh = "ধ";
    private static final String b_G = "গ";
    private static final String b_GH = "ঘ";
    private static final String b_H = "হ";
    private static final String b_J = "জ";
    private static final String b_JH = "ঝ";
    private static final String b_K = "ক";
    private static final String b_KH = "খ";
    private static final String b_L = "ল";
    private static final String b_M = "ম";
    private static final String b_N = "ন";
    private static final String b_NGA = "ঙ";
    private static final String b_Nn = "ণ";
    private static final String b_NYA = "ঞ";
    private static final String b_P = "প";
    private static final String b_Ph = "ফ";
    private static final String b_R = "র";
    private static final String b_Rr = "ড়";
    private static final String b_Rrh = "ঢ়";
    private static final String b_S = "স";
    private static final String b_Sh = "শ";
    private static final String b_Ss = "ষ";
    private static final String b_T = "ত";
    private static final String b_Th = "থ";
    private static final String b_Tt = "ট";
    private static final String b_Tth = "ঠ";
    private static final String b_Y = "য়";
    private static final String b_Z = "য";
    private static final String AssamRa = "ৰ";
    private static final String AssamVa = "ৱ";
    private static final String b_Khandatta = "ৎ";
    //Bangle Others
    private static final String b_Dari = "।";
    private static final String b_Hasanta = "্";
    private static final String b_Taka = "৳";
    private static final String ZWJ = "\u200D";
    private static final String ZWNJ = "\u200C";
    private static final String b_Nukta = "়";

    private String MyConvert() {
        String tt; //Temporary string
        ln = pEnglishText.length();
        pos = 1;
        Rs = "";
        do {
            tt = String.valueOf(pEnglishText.charAt(pos - 1));

            //--------START Number Generation---------------
            //1st, we'll convert numbers. Hassel free :)
            switch (tt) {
                case "0" -> AddRs(b_0);
                case "1" -> AddRs(b_1);
                case "2" -> AddRs(b_2);
                case "3" -> AddRs(b_3);
                case "4" -> AddRs(b_4);
                case "5" -> AddRs(b_5);
                case "6" -> AddRs(b_6);
                case "7" -> AddRs(b_7);
                case "8" -> AddRs(b_8);
                case "9" -> AddRs(b_9);
                //------------End Number Generation-------------

                //--------START Vowel Generation---------------
                //2nd, we'll convert Vowels.Cooperatively easy
                case "o" ->
                    //Lower case O, it will be shored o
                        smallO();
                case "A" -> AddRs(b_AA); //Force AA
                case "a" -> {
                    if (NextT().equals("Z")) {
                        AddRsEx(b_A + b_Hasanta + b_Z + b_AAkar, 2);
                    } else if ((beginning()) && (!NextT().equals("`"))) {
                        AddRs(b_AA);
                    } else if ((!consonant(PrevT())) && (!PrevT().equals("a")) && (!NextT().equals("`"))) {
                        AddRs(b_Y + b_AAkar);
                    } else if (NextT().equals("`")) {
                        AddRsEx(b_AAkar, 2);
                    } else if ((PrevT().equals("a")) && (!NextT().equals("`"))) {
                        AddRs(b_AA);
                    } else {
                        AddRs(b_AAkar);
                    }
                }
                case "i" -> {
                    if (((!consonant(PrevT())) || (beginning())) && (!NextT().equals("`"))) {
                        AddRs(b_I);
                    } else if (NextT().equals("`")) {
                        AddRsEx(b_Ikar, 2);
                    } else {
                        AddRs(b_Ikar);
                    }
                }
                case "I" -> {
                    if (((!consonant(PrevT())) || (beginning())) && (!NextT().equals("`"))) {
                        AddRs(b_II);
                    } else if (NextT().equals("`")) {
                        AddRsEx(b_IIkar, 2);
                    } else {
                        AddRs(b_IIkar);
                    }
                }
                case "u" -> {
                    if (((!consonant(PrevT())) || (beginning())) && (!NextT().equals("`"))) {
                        AddRs(b_U);
                    } else if (NextT().equals("`")) {
                        AddRsEx(b_Ukar, 2);
                    } else {
                        AddRs(b_Ukar);
                    }
                }
                case "U" -> {
                    if (((!consonant(PrevT())) || (beginning())) && (!NextT().equals("`"))) {
                        AddRs(b_UU);
                    } else if (NextT().equals("`")) {
                        AddRsEx(b_UUkar, 2);
                    } else {
                        AddRs(b_UUkar);
                    }
                }
                //We'll process ra, Ra,Rha, rri, rrikar, ra fola, reph later
                case "e", "E" -> {
                    if (((!consonant(PrevT())) || (beginning())) && (!NextT().equals("`"))) {
                        if (NextT().equals("e")) {
                            AddRsEx(b_II, 2);
                        } else {
                            AddRs(b_E);
                        }
                    } else if (NextT().equals("`")) {
                        AddRsEx(b_Ekar, 2);
                    } else {
                        if (NextT().equals("e")) {
                            AddRsEx(b_IIkar, 2);
                        } else {
                            AddRs(b_Ekar);
                        }
                    }
                }
                case "O" ->
                    //Capital O
                        O();

                //-----------------End Vowel Generation---------------

                //-----------------START Consonent Generation---------------
                //K
                case "k" -> k();
                //G
                case "G", "g" -> g();
                //N
                case "N", "n" -> n();
                //C
                case "c" -> c();
                //J
                case "J", "j" -> J();
                //T
                case "T", "t" -> T();
                //D
                case "D", "d" -> d();
                //P
                case "p", "f" -> p();
                //B
                case "b", "v" -> b();
                //M
                case "m" -> m();
                //Z
                case "z" -> AddRs(b_Z);
                case "Z" -> {
                    if (PrevT().equals("r")) {
                        if ((consonant(PrevTEx(2))) && (!PrevTEx(2).equals("r")) && (!PrevTEx(2).equals("y")) && (!PrevTEx(2).equals("w")) && (!PrevTEx(2).equals("x"))) {
                            //Previous character is R-Fola, don't add ZWJ
                            AddRs(b_Hasanta + b_Z);
                        } else {
                            AddRs(ZWJ + b_Hasanta + b_Z); // RAM, RAB : In Windows, we've to use ZWNJ
                        }
                    } else {
                        AddRs(b_Hasanta + b_Z);
                    }
                }
                //R
                case "R", "r" -> R();
                //L
                case "l" -> l();
                //S
                case "S", "s" -> s();
                //H
                case "h" -> h();
                //y
                case "y" -> {
                    if ((!consonant(PrevT())) && (!beginning())) {
                        AddRs(b_Y);
                    } else if (beginning()) {
                        AddRs(b_I + b_Y);
                    } else {
                        if (PrevT().equals("r")) {
                            if ((consonant(PrevTEx(2))) && (!PrevTEx(2).equals("r")) && (!PrevTEx(2).equals("y")) && (!PrevTEx(2).equals("w")) && (!PrevTEx(2).equals("x"))) {
                                //Previous character is R-Fola, don't add ZWJ
                                AddRs(b_Hasanta + b_Z);
                            } else {
                                AddRs(ZWJ + b_Hasanta + b_Z); // RAM, RAB : In Windows, we've to use ZWNJ
                            }
                        } else {
                            AddRs(b_Hasanta + b_Z);
                        }
                    }
                }
                //Y
                case "Y" -> AddRs(b_Y); //Force Y
                //W
                case "w" -> {
                    if ((beginning()) && (vowel(NextT()))) {
                        AddRs(b_O + b_Y);
                    } else if (consonant(PrevT())) {
                        AddRs(b_Hasanta + b_B);
                    } else {
                        AddRs(b_O);
                    }
                }
                //Q
                case "q" -> AddRs(b_K);
                //x
                case "x" -> {
                    if (beginning()) {
                        AddRs(b_E + b_K + b_Hasanta + b_S);
                    } else {
                        AddRs(b_K + b_Hasanta + b_S);
                    }
                }
                //-----------------End Consonent Generation---------------

                //-----------------Start Symbol Generation---------------
                case "." -> Dot();
                case ":" -> {
                    if (!NextT().equals("`")) {
                        AddRs(b_Bisharga);
                    } else {
                        AddRsEx(":", 2);
                    }
                }
                case "^" -> {
                    if (!NextT().equals("`")) {
                        AddRs(b_Chandra);
                    } else {
                        AddRsEx("^", 2);
                    }
                }
                case "," -> {
                    if (NextT().equals(",")) {
                        AddRsEx(b_Hasanta + ZWNJ, 2);
                    } else {
                        AddRs(",");
                    }
                }
                case "$" -> AddRs(b_Taka);
                //-----------------End Symbol Generation---------------

                //` - Make sure it is just above case else!
                case "`" -> pos = pos + 1; //No change made here,just to bypass juktakkhar-making
                default -> AddRs(tt);
            }
        }
        while (pos <= ln);
        return Rs;
    }

    private void Dot() {
        if (Cnv("...", "...")) return;//...
        if (Cnv(".`", ".")) return;//.
        if (Cnv("..", b_Dari + b_Dari)) return;//||
        if (number(NextT())) {
            if (Cnv(".", ".")) return;// Decimal Mark
        }
        if (Cnv(".", b_Dari)) return; //|
    }

    private void smallO() {
        if (((!consonant(PrevT())) || (beginning())) && (!NextT().equals("`"))) {
            if (Cnv("oo", b_U)) return;//U
            if (Cnv("oZ", b_A + b_Hasanta + b_Z)) return; //U
            if ((vowel(PrevT())) && (!PrevT().equals("o"))) {
                if (Cnv("o", b_O)) return;//O
            } else {
                if (Cnv("o", b_A)) return;//A
            }
        }

        if (Cnv("oo", b_Ukar)) return;//U Kar
        if (Cnv("o`", "")) return;//Nothing
        if (Cnv("o", "")) return;//Nothing
    }

    private void O() {
        if (Cnv("OI`", b_OIkar)) return;//OIKar
        if (Cnv("OU`", b_OUkar)) return;//OUKar
        if (Cnv("O`", b_Okar)) return;//OKar
        if ((!consonant(PrevT())) || (beginning())) {
            if (Cnv("OI", b_OI)) return;//OI
            if (Cnv("OU", b_OU)) return;//OU
            if (Cnv("O", b_O)) return; //O
        } else {
            if (Cnv("OI", b_OIkar)) return; //OIKar
            if (Cnv("OU", b_OUkar)) return;//OUKar
            if (Cnv("O", b_Okar)) return;//OKar
        }
    }

    private String CorrectCase(String inputT) {
        StringBuilder s = new StringBuilder();
        for (char ch : inputT.toCharArray()) {
            String T = String.valueOf(ch);
            if (patternMatcher("[aoiu]", T.toLowerCase())) s.append(T);
            else if (patternMatcher("[dgjnrstyz]", T.toLowerCase())) s.append(T);
            else s.append(T.toLowerCase());
        }
        return s.toString();
    }

    private void h() {
        if (Cnv("hN", b_H + b_Hasanta + b_Nn)) return;//H+Nn
        if (Cnv("hn", b_H + b_Hasanta + b_N)) return; //H+N
        if (Cnv("hm", b_H + b_Hasanta + b_M)) return; //H+m
        if (Cnv("hl", b_H + b_Hasanta + b_L)) return;//H+L
        if (Cnv("h", b_H)) return; //H
    }

    private void s() {
        if (Cnv("shch", b_Sh + b_Hasanta + b_CH)) return; //Sh+Chh
        if (Cnv("ShTh", b_Ss + b_Hasanta + b_Tth)) return;//Sh+Tth
        if (Cnv("Shph", b_Ss + b_Hasanta + b_Ph)) return; //Sh+Ph

        if (Cnv("Sch", b_Sh + b_Hasanta + b_CH)) return;//Sh+Chh
        if (Cnv("skl", b_S + b_Hasanta + b_K + b_Hasanta + b_L)) return; //S+K+L
        if (Cnv("skh", b_S + b_Hasanta + b_KH)) return;//S+Kh
        if (Cnv("sth", b_S + b_Hasanta + b_Th)) return; //S+Th
        if (Cnv("sph", b_S + b_Hasanta + b_Ph)) return;//S+Ph
        if (Cnv("shc", b_Sh + b_Hasanta + b_C)) return; //Sh+C
        if (Cnv("sht", b_Sh + b_Hasanta + b_T)) return; //Sh+T
        if (Cnv("shn", b_Sh + b_Hasanta + b_N)) return; //Sh+N
        if (Cnv("shm", b_Sh + b_Hasanta + b_M)) return;//Sh+M
        if (Cnv("spl", b_S + b_Hasanta + b_P + b_Hasanta + b_L)) return;//s+p+l
        if (Cnv("shl", b_Sh + b_Hasanta + b_L)) return;//Sh+L
        if (Cnv("Shk", b_Ss + b_Hasanta + b_K)) return;//Sh+K
        if (Cnv("ShT", b_Ss + b_Hasanta + b_Tt)) return; //Sh+Tt
        if (Cnv("ShN", b_Ss + b_Hasanta + b_Nn)) return;//Sh+Nn
        if (Cnv("Shp", b_Ss + b_Hasanta + b_P)) return; //Sh+P
        if (Cnv("Shf", b_Ss + b_Hasanta + b_Ph)) return;//Sh+Ph
        if (Cnv("Shm", b_Ss + b_Hasanta + b_M)) return; //Sh+M

        if (Cnv("sk", b_S + b_Hasanta + b_K)) return;//S+K
        if (Cnv("Sc", b_Sh + b_Hasanta + b_C)) return;//Sh+Ch
        if (Cnv("sT", b_S + b_Hasanta + b_Tt)) return;//S+Tt
        if (Cnv("st", b_S + b_Hasanta + b_T)) return;//S+T
        if (Cnv("sn", b_S + b_Hasanta + b_N)) return;//S+N
        if (Cnv("sp", b_S + b_Hasanta + b_P)) return;//S+P
        if (Cnv("sf", b_S + b_Hasanta + b_Ph)) return;//S+Ph
        if (Cnv("sm", b_S + b_Hasanta + b_M)) return;//S+M
        if (Cnv("sl", b_S + b_Hasanta + b_L)) return;//S+L
        if (Cnv("sh", b_Sh)) //Sh
            if (Cnv("Sc", b_Sh + b_Hasanta + b_C)) return;//Sh+Ch
        if (Cnv("St", b_Sh + b_Hasanta + b_T)) return;//Sh+T
        if (Cnv("Sn", b_Sh + b_Hasanta + b_N)) return;//Sh+N
        if (Cnv("Sm", b_Sh + b_Hasanta + b_M)) return;//Sh+M
        if (Cnv("Sl", b_Sh + b_Hasanta + b_L)) return;//Sh+L
        if (Cnv("Sh", b_Ss)) return;//Sh

        if (Cnv("s", b_S)) return; //S
        if (Cnv("S", b_Sh)) return;//Sh

    }

    private void l() {
        if (Cnv("lbh", b_L + b_Hasanta + b_Bh)) return; //L+Bh
        if (Cnv("ldh", b_L + b_Hasanta + b_Dh)) return;//L+Dh

        if (Cnv("lkh", b_L + b_KH)) return;//L & Kk
        if (Cnv("lgh", b_L + b_GH)) return;//L & Gh
        if (Cnv("lph", b_L + b_Ph)) return; //L & Ph

        if (Cnv("lk", b_L + b_Hasanta + b_K)) return;//L+K
        if (Cnv("lg", b_L + b_Hasanta + b_G)) return;//L+G
        if (Cnv("lT", b_L + b_Hasanta + b_Tt)) return;//L+T
        if (Cnv("lD", b_L + b_Hasanta + b_Dd)) return;//L+Dd
        if (Cnv("lp", b_L + b_Hasanta + b_P)) return;//L+P
        if (Cnv("lv", b_L + b_Hasanta + b_Bh)) return;//L+Bh
        if (Cnv("lm", b_L + b_Hasanta + b_M)) return;//L+M
        if (Cnv("ll", b_L + b_Hasanta + b_L)) return; //L+L
        if (Cnv("lb", b_L + b_Hasanta + b_B)) return; //L+B

        if (Cnv("l", b_L)) return; //L
    }

    private void R() {
        if (NextTEx(1, 2).equals("`")) {
            if (Cnv("rri", b_RRIkar)) return; //RRI-Kar
        }

        if (!consonant(PrevT())) {
            if (Cnv("rri", b_RRI)) return; //RRI
        } else if (beginning()) {
            if (Cnv("rri", b_RRI)) return; //RRI
        } else {
            if (Cnv("rri", b_RRIkar)) return; //RRI-Kar
        }

        if (!consonant(PrevT()) && !vowel(NextTEx(1, 1)) && !NextTEx(1, 1).equals("r") & !NextTEx(1, 1).equals("")) {
            if (Cnv("rr", b_R + b_Hasanta)) return;//Reph
        }

        if (Cnv("Rg", b_Rr + b_Hasanta + b_G)) return;//Rh+G
        if (Cnv("Rh", b_Rrh)) return;//Rrh
        if ((consonant(PrevT())) && (!PrevT().equals("r")) && (!PrevT().equals("y")) && (!PrevT().equals("w")) && (!PrevT().equals("x")) && (!PrevT().equals("Z"))) {
            if (Cnv("r", b_Hasanta + b_R)) return;//R-Fola
        } else {
            if (Cnv("r", b_R)) return; //R
        }
        if (Cnv("R", b_Rr)) return; //Rh
    }

    private void m() {
        if (Cnv("mth", b_M + b_Hasanta + b_Th)) return;//M+Th
        if (Cnv("mph", b_M + b_Hasanta + b_Ph)) return;//M+Ph
        if (Cnv("mbh", b_M + b_Hasanta + b_Bh)) return;//M+V
        if (Cnv("mpl", b_M + b_P + b_Hasanta + b_L)) return;//

        if (Cnv("mn", b_M + b_Hasanta + b_N)) return;//M+N
        if (Cnv("mp", b_M + b_Hasanta + b_P)) return;//M+P
        if (Cnv("mv", b_M + b_Hasanta + b_Bh)) return;//M+V
        if (Cnv("mm", b_M + b_Hasanta + b_M)) return;//M+M
        if (Cnv("ml", b_M + b_Hasanta + b_L)) return;//M+L
        if (Cnv("mb", b_M + b_Hasanta + b_B)) return;//M+B
        if (Cnv("mf", b_M + b_Hasanta + b_Ph)) return; //M+Ph

        if (Cnv("m", b_M)) return; //M
    }

    private void b() {
        if (Cnv("bdh", b_B + b_Hasanta + b_Dh)) return;//B+Dh
        if (Cnv("bhl", b_Bh + b_Hasanta + b_L)) return;//Bh+L
        if (Cnv("bj", b_B + b_Hasanta + b_J)) return;//B+J
        if (Cnv("bd", b_B + b_Hasanta + b_D)) return;//B+D
        if (Cnv("bb", b_B + b_Hasanta + b_B)) return;//B+B
        if (Cnv("bl", b_B + b_Hasanta + b_L)) return;//B+L
        if (Cnv("bh", b_Bh)) return;//Bh
        if (Cnv("vl", b_Bh + b_Hasanta + b_L)) return;//Bh+L
        if (Cnv("b", b_B)) return;//B
        if (Cnv("v", b_Bh)) return;//Bh
    }

    private void p() {
        if (Cnv("phl", b_Ph + b_Hasanta + b_L)) return;//Ph+L
        if (Cnv("pT", b_P + b_Hasanta + b_Tt)) return;//P+Tt
        if (Cnv("pt", b_P + b_Hasanta + b_T)) return; //P+T
        if (Cnv("pn", b_P + b_Hasanta + b_N)) return; //P+N
        if (Cnv("pp", b_P + b_Hasanta + b_P)) return;//P+P
        if (Cnv("pl", b_P + b_Hasanta + b_L)) return; //P+L
        if (Cnv("ps", b_P + b_Hasanta + b_S)) return;//P+S
        if (Cnv("ph", b_Ph)) return;//Ph
        if (Cnv("fl", b_Ph + b_Hasanta + b_L)) return;//Ph+L
        if (Cnv("f", b_Ph)) return;//Ph
        if (Cnv("p", b_P)) return;//P
    }

    private void d() {
        if (Cnv("dhn", b_Dh + b_Hasanta + b_N)) return;//Dh+N
        if (Cnv("dhm", b_Dh + b_Hasanta + b_M)) return;//Dh+M
        if (Cnv("dgh", b_D + b_Hasanta + b_GH)) return;//D+Gh
        if (Cnv("ddh", b_D + b_Hasanta + b_Dh)) return;//D+Dh
        if (Cnv("dbh", b_D + b_Hasanta + b_Bh)) return;//D+Bh
        if (Cnv("dv", b_D + b_Hasanta + b_Bh)) return;//D+Bh
        if (Cnv("dm", b_D + b_Hasanta + b_M)) return; //D+M
        if (Cnv("DD", b_Dd + b_Hasanta + b_Dd)) return;//Dd+Dd
        if (Cnv("Dh", b_Ddh)) return;//Ddh
        if (Cnv("dh", b_Dh)) return;//Dh
        if (Cnv("dg", b_D + b_Hasanta + b_G)) return;//D+G
        if (Cnv("dd", b_D + b_Hasanta + b_D)) return;//D+D

        if (Cnv("D", b_Dd)) return; //Dd
        if (Cnv("d", b_D)) return; //D
    }

    private void T() {
        if (Cnv("tth", b_T + b_Hasanta + b_Th)) return;//T+Th
        if (Cnv("t``", b_Khandatta)) return; //Khandatta

        if (Cnv("TT", b_Tt + b_Hasanta + b_Tt)) return;//Tt+Tt
        if (Cnv("Tm", b_Tt + b_Hasanta + b_M)) return;//Tt+M
        if (Cnv("Th", b_Tth)) return; //Tth
        if (Cnv("tn", b_T + b_Hasanta + b_N)) return; //T+N
        if (Cnv("tm", b_T + b_Hasanta + b_M)) return;//T+M
        if (Cnv("th", b_Th)) return;//Th
        if (Cnv("tt", b_T + b_Hasanta + b_T)) return;//T+T

        if (Cnv("T", b_Tt)) return;//Tt
        if (Cnv("t", b_T)) return;//T
    }

    private void J() {
        if (Cnv("jjh", b_J + b_Hasanta + b_JH)) return; //J+Jh
        if (Cnv("jNG", b_J + b_Hasanta + b_NYA)) return;//J+NYA
        if (Cnv("jh", b_JH)) return;//Jh
        if (Cnv("jj", b_J + b_Hasanta + b_J)) return; //J+J
        if (Cnv("j", b_J)) return; //J
        if (enableJoNukta) {
            if (Cnv("J", b_J + b_Nukta)) return; //J+Nukta
        }
        if (Cnv("J", b_J)) return;//J
    }

    private void c() {
        if (Cnv("cNG", b_C + b_Hasanta + b_NYA)) return; //C+NYA
        if (Cnv("cch", b_C + b_Hasanta + b_CH)) return;//C+C
        if (Cnv("cc", b_C + b_Hasanta + b_C)) return;//C+C

        if (Cnv("ch", b_CH)) return; //C
        if (Cnv("c", b_C)) return; //C
    }

    private void n() {
        if (Cnv("NgkSh", b_NGA + b_Hasanta + b_K + b_Hasanta + b_Ss)) return;//NGA+Khio
        if (Cnv("Ngkkh", b_NGA + b_Hasanta + b_K + b_Hasanta + b_Ss)) return; //NGA+Khio
        if (Cnv("NGch", b_NYA + b_Hasanta + b_CH)) return;//NYA+Ch
        if (Cnv("Nggh", b_NGA + b_Hasanta + b_GH)) return;//NGA+Gh
        if (Cnv("Ngkh", b_NGA + b_Hasanta + b_KH)) return;//NGA+Kh
        if (Cnv("NGjh", b_NYA + b_Hasanta + b_JH)) return;//NYA+Jh
        if (Cnv("ngOU", b_NGA + b_Hasanta + b_G + b_OUkar)) return;//NGA+G
        if (Cnv("ngOI", b_NGA + b_Hasanta + b_G + b_OIkar)) return;//NGA+G
        if (Cnv("Ngkx", b_NGA + b_Hasanta + b_K + b_Hasanta + b_Ss)) return;//NGA+Khio
        if (Cnv("NGc", b_NYA + b_Hasanta + b_C)) return; //NYA+Ch
        if (Cnv("nch", b_NYA + b_Hasanta + b_CH)) return;//NYA+Ch
        if (Cnv("njh", b_NYA + b_Hasanta + b_JH)) return;//NYA+Jh
        if (Cnv("nch", b_NYA + b_Hasanta + b_C)) return;//NYA+Ch
        if (Cnv("ngh", b_NGA + b_Hasanta + b_GH)) return;//NGA+Gh
        if (Cnv("Ngk", b_NGA + b_Hasanta + b_K)) return;//NGA+K
        if (Cnv("Ngx", b_NGA + b_Hasanta + b_Ss)) return;//NGA+Khio
        if (Cnv("Ngg", b_NGA + b_Hasanta + b_G)) return; //NGA+G
        if (Cnv("Ngm", b_NGA + b_Hasanta + b_M)) return;//NGA+M
        if (Cnv("NGj", b_NYA + b_Hasanta + b_J)) return;//NYA+J
        if (Cnv("ndh", b_N + b_Hasanta + b_Dh)) return;//N+Dh
        if (Cnv("nTh", b_N + b_Hasanta + b_Tth)) return; //N+Tth
        if (Cnv("NTh", b_Nn + b_Hasanta + b_Tth)) return; //Nn+Tth
        if (Cnv("nth", b_N + b_Hasanta + b_Th)) return;//N+Th
        if (Cnv("nkh", b_NGA + b_Hasanta + b_KH)) return; //NGA+Kh
        if (Cnv("ngo", b_NGA + b_Hasanta + b_G)) return; //NGA+G
        if (Cnv("nga", b_NGA + b_Hasanta + b_G + b_AAkar)) return; //NGA+G
        if (Cnv("ngi", b_NGA + b_Hasanta + b_G + b_Ikar)) return;//NGA+G
        if (Cnv("ngI", b_NGA + b_Hasanta + b_G + b_IIkar)) return; //NGA+G
        if (Cnv("ngu", b_NGA + b_Hasanta + b_G + b_Ukar)) return; //NGA+G
        if (Cnv("ngU", b_NGA + b_Hasanta + b_G + b_UUkar)) return; //NGA+G
        if (Cnv("nge", b_NGA + b_Hasanta + b_G + b_Ekar)) return;//NGA+G
        if (Cnv("ngO", b_NGA + b_Hasanta + b_G + b_Okar)) return; //NGA+G
        if (Cnv("NDh", b_Nn + b_Hasanta + b_Ddh)) return; //Nn+Ddh
        if (Cnv("nsh", b_N + b_Sh)) return;//N & Sh
        if (Cnv("Ngr", b_NGA + b_R)) return;//NGA & R
        if (Cnv("NGr", b_NYA + b_R)) return;//NYA & R
        if (Cnv("ngr", b_Anushar + b_R)) return;//Anushar & R
        if (Cnv("nj", b_NYA + b_Hasanta + b_J)) return; //NYA+J
        if (Cnv("Ng", b_NGA)) return;//NGA
        if (Cnv("NG", b_NYA)) return;//NYA
        if (Cnv("nk", b_NGA + b_Hasanta + b_K)) return; //NGA+K
        if (Cnv("ng", b_Anushar)) return; //Anushar
        if (Cnv("nn", b_N + b_Hasanta + b_N)) return;//N+N
        if (Cnv("NN", b_Nn + b_Hasanta + b_Nn)) return;//Nn+Nn
        if (Cnv("Nn", b_Nn + b_Hasanta + b_N)) return; //Nn+N
        if (Cnv("nm", b_N + b_Hasanta + b_M)) return;//N+M
        if (Cnv("Nm", b_Nn + b_Hasanta + b_M)) return;//Nn+M
        if (Cnv("nd", b_N + b_Hasanta + b_D)) return;//N+D
        if (Cnv("nT", b_N + b_Hasanta + b_Tt)) return;//N+Tt
        if (Cnv("NT", b_Nn + b_Hasanta + b_Tt)) return; //Nn+Tt
        if (Cnv("nD", b_N + b_Hasanta + b_Dd)) return;//N+Dd
        if (Cnv("ND", b_Nn + b_Hasanta + b_Dd)) return; //Nn+Dd
        if (Cnv("nt", b_N + b_Hasanta + b_T)) return;//N+T
        if (Cnv("ns", b_N + b_Hasanta + b_S)) return; //N+S
        if (Cnv("nc", b_NYA + b_Hasanta + b_C)) return;//NYA+C
        if (Cnv("n", b_N)) return;//N
        if (Cnv("N", b_Nn)) return; //N
    }

    private void k() {
        if (Cnv("kkhN", b_K + b_Hasanta + b_Ss + b_Hasanta + b_Nn)) return; //khioN
        if (Cnv("kShN", b_K + b_Hasanta + b_Ss + b_Hasanta + b_Nn)) return;  //khioN
        if (Cnv("kkhm", b_K + b_Hasanta + b_Ss + b_Hasanta + b_M)) return;  //khioM
        if (Cnv("kShm", b_K + b_Hasanta + b_Ss + b_Hasanta + b_M)) return;  //khioM
        if (Cnv("kxN", b_K + b_Hasanta + b_Ss + b_Hasanta + b_Nn)) return;  //khioN
        if (Cnv("kxm", b_K + b_Hasanta + b_Ss + b_Hasanta + b_M)) return; //khioM
        if (Cnv("kkh", b_K + b_Hasanta + b_Ss)) return;  //khio
        if (Cnv("kSh", b_K + b_Hasanta + b_Ss)) return;  //khio
        if (Cnv("ksh", b_K + b_Sh)) return;  //K`Sh
        if (Cnv("kx", b_K + b_Hasanta + b_Ss)) return; //khio
        if (Cnv("kk", b_K + b_Hasanta + b_K)) return; //k+k
        if (Cnv("kT", b_K + b_Hasanta + b_Tt)) return;  //k+T
        if (Cnv("kt", b_K + b_Hasanta + b_T)) return; //k+t
        if (Cnv("km", b_K + b_Hasanta + b_M)) return; //k+M
        if (Cnv("kl", b_K + b_Hasanta + b_L)) return;  //k+L
        if (Cnv("ks", b_K + b_Hasanta + b_S)) return;  //k+S
        if (Cnv("kh", b_KH)) return; //kh
        if (Cnv("k", b_K)) return;  //k
    }

    private void g() {
        if (Cnv("ghn", b_GH + b_Hasanta + b_N)) return; //gh+N
        if (Cnv("Ghn", b_GH + b_Hasanta + b_N)) return; //gh+N
        if (Cnv("gdh", b_G + b_Hasanta + b_Dh)) return; //g+dh
        if (Cnv("Gdh", b_G + b_Hasanta + b_Dh)) return; //g+dh
        if (Cnv("gN", b_G + b_Hasanta + b_Nn)) return; //g+N
        if (Cnv("GN", b_G + b_Hasanta + b_Nn)) return; //g+N
        if (Cnv("gn", b_G + b_Hasanta + b_N)) return; //g+n
        if (Cnv("Gn", b_G + b_Hasanta + b_N)) return; //g+n
        if (Cnv("gm", b_G + b_Hasanta + b_M)) return; //g+M
        if (Cnv("Gm", b_G + b_Hasanta + b_M)) return; //g+M
        if (Cnv("gl", b_G + b_Hasanta + b_L)) return; //g+L
        if (Cnv("Gl", b_G + b_Hasanta + b_L)) return; //g+L
        if (Cnv("gg", b_J + b_Hasanta + b_NYA)) return; //gg
        if (Cnv("GG", b_J + b_Hasanta + b_NYA)) return; //gg
        if (Cnv("Gg", b_J + b_Hasanta + b_NYA)) return; //gg
        if (Cnv("gG", b_J + b_Hasanta + b_NYA)) return; //gg
        if (Cnv("gh", b_GH)) return; //gh
        if (Cnv("Gh", b_GH)) return; //gh
        if (Cnv("g", b_G)) return; //g
        if (Cnv("G", b_G)) return; //g
    }

    private boolean Cnv(String Compare, String IfTrue) {
        int i = Compare.length();
        int endString = Math.min((pos + i - 1), pEnglishText.length());
        String tmp = pEnglishText.substring(pos - 1, endString);
        if (Compare.equals(tmp)) {
            Rs = Rs + IfTrue;
            pos = pos + i;
            return true;
        }
        return false;
    }

    private void AddRs(String T) {
        Rs = Rs + T;
        pos = pos + 1;
    }

    private void AddRsEx(String T, int p) {
        Rs = Rs + T;
        pos = pos + p;
    }

    private String PrevT() {
        int i = pos - 1;
        if (i < 1) return "";
        return String.valueOf(pEnglishText.charAt(i - 1));
    }

    private String PrevTEx(int Position) {
        int i = pos - Position;
        if (i < 1) return "";
        return String.valueOf(pEnglishText.charAt(i - 1));
    }

    private String NextT() {
        return String.valueOf(pEnglishText.charAt(pos - 1));
    }

    private String NextTEx(int length, int skipstart) {
        if (length < 1) length = 1;
        if (pEnglishText.length() >= (pos + skipstart + length))
            return pEnglishText.substring(pos + skipstart, pos + skipstart + length);
        return "";
    }

    private boolean number(String T) {
        return patternMatcher("\\d", T);
    }

    private boolean alphabetCapital(String T) {
        return patternMatcher("[A-Z]", T);
    }

    private boolean alphabetSmall(String T) {
        return patternMatcher("[a-z]", T);
    }

    private boolean vowel(String T) {
        return patternMatcher("[aeiou]", T.toLowerCase());
    }

    private boolean consonant(String T) {
        return !patternMatcher("[aeiou]", T.toLowerCase());
    }

    private boolean beginning() {
        return !patternMatcher("[a-z]", PrevT());
    }

    private static boolean patternMatcher(String regex, String matcher) {
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(matcher).matches();
    }

    /**
     * @param EnglishT The English text to convert.
     * @return The converted Bangla(Bengali) text.
     * @brief Converts phonetic English text to Bangla(Bengali) text.
     */
    public String convert(String EnglishT) {
        pEnglishText = CorrectCase(EnglishT);
        return MyConvert();
    }
}
