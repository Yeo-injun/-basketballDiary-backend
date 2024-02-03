package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.pagination.Pagination;
import com.threeNerds.basketballDiary.utils.LocalDateUtil;
import lombok.Getter;
import org.apache.tomcat.jni.Local;
import org.springframework.util.StringUtils;

@Getter
public class GameSearchCriteriaDTO {
    private Pagination pagination;
    private boolean inAllSpan;
    private String gameBgngYmd;
    private String gameEndYmd;

    private Long teamSeq;
    private String gamePlaceName;
    private String gameTypeCode;
    private String homeAwayCode;

    public GameSearchCriteriaDTO teamSeq(Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public GameSearchCriteriaDTO pagination( Pagination pagination ) {
        this.pagination = pagination;
        return this;
    }

    public GameSearchCriteriaDTO setSearchSpan( String gameBgngYmd, String gameEndYmd ) {
        /** 기간조회 파라미터가 하나라도 없을시 전체범위 조회 */
        if ( !StringUtils.hasText( gameBgngYmd ) || !StringUtils.hasText( gameEndYmd ) ) {
            this.inAllSpan = true;
            return this;
        }

        /** 검색기간 유효성 체크 */
        if ( !LocalDateUtil.isValidYmdFormat( gameBgngYmd ) || !LocalDateUtil.isValidYmdFormat( gameEndYmd ) ) {
            throw new CustomException( SystemErrorType.PARAMETER_FORMAT_ERROR );
        }
        long diffDays = LocalDateUtil.getDateDifference( gameBgngYmd, gameEndYmd );
        int DAYS_OF_YEAR = 365;
        if (  0 > diffDays || DAYS_OF_YEAR < diffDays ) {
            throw new CustomException( DomainErrorType.INVALID_SEARCH_DATE_SPAN );
        }

        this.inAllSpan      = false;
        this.gameBgngYmd    = gameBgngYmd;
        this.gameEndYmd     = gameEndYmd;
        return this;
    }

    public GameSearchCriteriaDTO gamePlaceName(String gamePlaceName) {
        this.gamePlaceName = gamePlaceName;
        return this;
    }
    public GameSearchCriteriaDTO gameTypeCode(String gameTypeCode) {
        this.gameTypeCode = gameTypeCode;
        return this;
    }
    public GameSearchCriteriaDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }
}

