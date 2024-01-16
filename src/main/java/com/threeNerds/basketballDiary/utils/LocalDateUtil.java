package com.threeNerds.basketballDiary.utils;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Slf4j
public class LocalDateUtil {
    /**
     *  날짜포맷 유효성 체크
     **/
    public static boolean isValidStringFormat( String date ) {
        // yyyyMMdd 8자리인지 체크
        if ( 8 != date.length() ) {
            return false;
        }

        try {
            toLocalDate( date );
            return true;
        } catch ( DateTimeException ex ) {
            return false;
        }
    }

    /**
     *  두 날짜의 차이
     *  - startYmd에서 endYmd까지의 일자
     **/
    public static long getDateDifference( String startYmd, String endYmd ) {
        if ( !isValidStringFormat( startYmd ) || !isValidStringFormat( endYmd ) ) {
            // TODO SystemErrorType 하위의 별도 구체타입 구현하기
            throw new CustomException( SystemErrorType.INTERNAL_ERROR );
        }

        LocalDate startDate = toLocalDate( startYmd );
        LocalDate endDate   = toLocalDate( endYmd );
        return ChronoUnit.DAYS.between( startDate, endDate );
    }

    private static LocalDate toLocalDate( String ymd ) {
        int year         = Integer.parseInt( ymd.substring( 0, 4 ) );
        int month        = Integer.parseInt( ymd.substring( 4, 6 ) );
        int dayOfMonth   = Integer.parseInt( ymd.substring( 6, 8 ) );
        return LocalDate.of( year, month, dayOfMonth );
    }
}
