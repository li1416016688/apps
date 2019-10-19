/**
 * 身份证有效性检验的工具类，具体规则如下：
 *     1．号码的结构
 * 　　公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。
 *     排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
 * 　　2．地址码
 * 　　表示编码对象常住户口所在县（县级市、旗、区）的行政区划代码，按GB/T2260的规定执行。
 * 　　3．出生日期码
 * 　　表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 * 　　4．顺序码
 * 　　表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
 * 　　5．校验码
 * 　　根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。
 */

package com.easyexam.apps.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class IDCardVerify {
    /**
     * 根据传入的身份证号码和性别进行身份证号有效性效验，需要同时传入性别
     * @param idCardNum String型
     * @param sex 性别，传入汉字"男"或者"女"
     * @return
     */
    public boolean verify(String idCardNum,String sex){
        //长度效验
        if(idCardNum.length() != 18){
            return false;
        }

        //地址码省份效验
        ArrayList<String> provinces = new ArrayList<>();
        setProvinces(provinces);
        String province = idCardNum.substring(0, 2);
        if(!provinces.contains(province)){
            return false;
        }

        //生日效验
        Integer year = Integer.valueOf(idCardNum.substring(6, 10));
        Integer month = Integer.valueOf(idCardNum.substring(10, 12));
        Integer day = Integer.valueOf(idCardNum.substring(12, 14));
        if(year < 1900 || year > (new Date().getYear()+1900)){
            return false;
        }
        switch (month){
            case 1: case 3: case 5: case 7:
            case 8: case 10: case 12:
                if(day < 1 || day > 31){
                    return false;
                }
                break;
            case 4: case 6: case 9: case 11:
                if(day < 1 || day > 30){
                    return false;
                }
                break;
            case 2:
                if(day < 1 || day > 29){
                    return false;
                }
                break;
            default:
                return false;
        }

        //性别代码效验
        char genderCode = idCardNum.charAt(16);
        if(sex.equals("男")){
            if (Double.valueOf(genderCode) % 2 == 0){
                return false;
            }
        }else if(sex.equals("女")){
            if (Double.valueOf(genderCode) % 2 != 0){
                return false;
            }
        }

        //校验码效验
        List<Integer> weightingFactor = Arrays.asList(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //加权因子
        String[] splitId = idCardNum.substring(0, 18).split("");//前17位
        Integer S = 0;
        Integer T;
        for(int i = 0; i < 17; i++){
            S = S + weightingFactor.get(i) * Integer.valueOf(splitId[i]);
        }
        T = S % 11;
        String[] verifyCodes = new String[]{"1","0","X","9","8","7","6","5","4","3","2"};
        String trueVerifyCode = verifyCodes[T];
        String nativeVerifyCode = String.valueOf(idCardNum.charAt(17));

        if(!trueVerifyCode.equals(nativeVerifyCode)){
            return false;
        }
        return true;
    }

    private void setProvinces(ArrayList provinces){
        provinces.add("11");
        provinces.add("12");
        provinces.add("13");
        provinces.add("14");
        provinces.add("15");
        provinces.add("21");
        provinces.add("22");
        provinces.add("23");
        provinces.add("31");
        provinces.add("32");
        provinces.add("33");
        provinces.add("34");
        provinces.add("35");
        provinces.add("36");
        provinces.add("37");
        provinces.add("41");
        provinces.add("42");
        provinces.add("43");
        provinces.add("44");
        provinces.add("45");
        provinces.add("46");
        provinces.add("50");
        provinces.add("51");
        provinces.add("52");
        provinces.add("53");
        provinces.add("54");
        provinces.add("61");
        provinces.add("62");
        provinces.add("63");
        provinces.add("64");
        provinces.add("65");
        provinces.add("81");
        provinces.add("82");
        provinces.add("83");
    }
}
