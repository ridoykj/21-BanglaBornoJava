package org.itbd.banglaborno.bijoy.u2b;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.itbd.banglaborno.bijoy.common.*;

public class Unicode2Bijoy {
    private String ReArrangeUnicodeText(String str) {
        int barrier = 0;
        for (int i = 0; i < str.length(); i++) {
            if (i < str.length() && IsBanglaPreKar(str.charAt(i))) {
                int j = 1;
                while (IsBanglaBanjonborno(str.charAt(i - j))) {
                    if (i - j < 0)
                        break;
                    if (i - j <= barrier) break;
                    if (IsBanglaHalant(str.charAt(i - j - 1)))
                        j += 2;
                    else
                        break;
                }
                String temp = str.substring(0, i - j);
                temp += str.charAt(i);
                temp += str.substring(i - j, i);
                temp += str.substring(i + 1);
                str = temp;
                barrier = i + 1;
                continue;
            }
            if (i < str.length() - 1 && IsBanglaHalant(str.charAt(i)) && str.charAt(i - 1) == 'র' && !IsBanglaHalant(str.charAt(i - 2))) {
                int j = 1;
                int found_pre_kar = 0;
                while (true) {
                    if (IsBanglaBanjonborno(str.charAt(i + j)) && IsBanglaHalant(str.charAt(i + j + 1)))
                        j += 2;
                    else if (IsBanglaBanjonborno(str.charAt(i + j)) && IsBanglaPreKar(str.charAt(i + j + 1))) {
                        found_pre_kar = 1;
                        break;
                    } else
                        break;
                }
                String temp = str.substring(0, i - 1);
                temp += str.substring(i + j + 1, i + j + found_pre_kar + 1);
                temp += str.substring(i + 1, i + j + 1);
                temp += str.charAt(i - 1);
                temp += str.charAt(i);
                temp += str.substring(i + j + found_pre_kar + 1);
                str = temp;
                i += (j + found_pre_kar);
                barrier = i + 1;
            }
        }
        return str;
    }

    public String ConvertToASCII(String ConvertTo, String line) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Unicode2BijoyMap unicode2BijoyMap = new Unicode2BijoyMap();
        Map<String, String> conversion_map = mapper.readValue(unicode2BijoyMap.getUni2bijoy_string_conversion_map(), new TypeReference<Map<String, String>>() {
        });
        if (ConvertTo == "bijoy")
            conversion_map = mapper.readValue(unicode2BijoyMap.getUni2bijoy_string_conversion_map(), new TypeReference<Map<String, String>>() {
            });
        else if (ConvertTo == "somewherein")
            conversion_map = mapper.readValue(unicode2BijoyMap.getUni2somewherein_string_conversion_map(), new TypeReference<Map<String, String>>() {
            });
        else if (ConvertTo == "boisakhi")
            conversion_map = mapper.readValue(unicode2BijoyMap.getUni2boisakhi_string_conversion_map(), new TypeReference<Map<String, String>>() {
            });
        line = line.replaceAll("ো", "ো").replaceAll("ৌ", "ৌ");
        line = ReArrangeUnicodeText(line);
        final String[] finalLine = {line};

        conversion_map.forEach((k, v) -> finalLine[0] = finalLine[0].replaceAll(k, v));
        return finalLine[0];
    }
}
