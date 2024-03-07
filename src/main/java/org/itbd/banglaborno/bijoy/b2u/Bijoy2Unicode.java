package org.itbd.banglaborno.bijoy.b2u;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.itbd.banglaborno.bijoy.common.*;

public class Bijoy2Unicode {
    private String ReArrangeUnicodeConvertedText(String str) {
        for (var i = 0; i < str.length(); i++) {
            if (i > 0 && str.charAt(i) == '\u09CD' && (IsBanglaKar(str.charAt(i - 1)) || IsBanglaNukta(str.charAt(i - 1))) && i < str.length() - 1) {
                var temp = str.substring(0, i - 1);
                temp += str.charAt(i);
                temp += str.charAt(i + 1);
                temp += str.charAt(i - 1);
                temp += str.substring(i + 2, str.length());
                str = temp;
            }
            if (i > 0 && i < str.length() - 1 && str.charAt(i) == '\u09CD' && str.charAt(i - 1) == '\u09B0' && str.charAt(i - 2) != '\u09CD' && IsBanglaKar(str.charAt(i + 1))) {
                var temp = str.substring(0, i - 1);
                temp += str.charAt(i + 1);
                temp += str.charAt(i - 1);
                temp += str.charAt(i);
                temp += str.substring(i + 2, str.length());
                str = temp;
            }
            if (i < str.length() - 1 && str.charAt(i) == 'র' && IsBanglaHalant(str.charAt(i + 1)) && !IsBanglaHalant(str.charAt(i - 1))) {
                var j = 1;
                while (true) {
                    if (i - j < 0)
                        break;
                    if (IsBanglaBanjonborno(str.charAt(i - j)) && IsBanglaHalant(str.charAt(i - j - 1)))
                        j += 2;
                    else if (j == 1 && IsBanglaKar(str.charAt(i - j)))
                        j++;
                    else
                        break;
                }
                var temp = str.substring(0, i - j);
                temp += str.charAt(i);
                temp += str.charAt(i + 1);
                temp += str.substring(i - j, i);
                temp += str.substring(i + 2, str.length());
                str = temp;
                i += 1;
                continue;
            }
            if (i < str.length() - 1 && IsBanglaPreKar(str.charAt(i)) && IsSpace(str.charAt(i + 1)) == false) {
                var temp = str.substring(0, i);
                var j = 1;
                while (IsBanglaBanjonborno(str.charAt(i + j))) {
                    if (IsBanglaHalant(str.charAt(i + j + 1)))
                        j += 2;
                    else
                        break;
                }
                temp += str.substring(i + 1, i + j + 1);
                var l = 0;
                if (str.charAt(i) == 'ে' && str.charAt(i + j + 1) == 'া') {
                    temp += "ো";
                    l = 1;
                } else if (str.charAt(i) == 'ে' && str.charAt(i + j + 1) == 'ৗ') {
                    temp += "ৌ";
                    l = 1;
                } else
                    temp += str.charAt(i);
                temp += str.substring(i + j + l + 1);
                str = temp;
                i += j;
            }
            if (i < str.length() - 1 && str.charAt(i) == 'ঁ' && IsBanglaPostKar(str.charAt(i + 1))) {
                var temp = str.substring(0, i);
                temp += str.charAt(i + 1);
                temp += str.charAt(i);
                temp += str.substring(i + 2);
                str = temp;
            }
        }
        return str;
    }

    public String ConvertToUnicode(String ConvertFrom, String line) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Bijoy2UnicodeMap bijoy2Unicode = new Bijoy2UnicodeMap();
        var conversion_map = mapper.readValue(bijoy2Unicode.getBijoy_string_conversion_map(), new TypeReference<Map<String, String>>() {
        });
        if (ConvertFrom == "bijoy")
            conversion_map = mapper.readValue(bijoy2Unicode.getBijoy_string_conversion_map(), new TypeReference<Map<String, String>>() {
            });
        else if (ConvertFrom == "somewherein")
            conversion_map = mapper.readValue(bijoy2Unicode.getSomewherein_string_conversion_map(), new TypeReference<Map<String, String>>() {
            });
        else if (ConvertFrom == "boisakhi")
            conversion_map = mapper.readValue(bijoy2Unicode.getBoisakhi_string_conversion_map(), new TypeReference<Map<String, String>>() {
            });

        final String[] finalLine = {line};
        conversion_map.forEach((k, v) -> finalLine[0] = finalLine[0].replaceAll(k, v));

        line = ReArrangeUnicodeConvertedText(finalLine[0]);
        line = line.replaceAll("অা", "আ");
        return line;
    }
}
