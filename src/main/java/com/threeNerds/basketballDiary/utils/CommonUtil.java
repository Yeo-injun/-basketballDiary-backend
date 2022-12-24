package com.threeNerds.basketballDiary.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class CommonUtil {

    /**
     * BeanUtils.copyProperties(source, target, ignoreProperties) 에서
     * ignoreProperties 파라미터로 전달하는 String[]을 생성하는 Util
     * source의 Null field 배열을 return한다.
     * @author 강창기
     * @param source
     * @return String[]
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
