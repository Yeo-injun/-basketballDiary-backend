package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.constant.code.JoinRequestTypeCode;
import org.junit.jupiter.api.Test;
import org.mockito.internal.creation.bytebuddy.SubclassLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class EnumCodeTest {
    @Test
    void 코드값으로_ENUM에서_코드이름추출하기() {
        String codeName = JoinRequestTypeCode.nameOf("01");
        System.out.println(codeName);
    }

    @Test
    void 코드값으로_ENUM에서_코드이름추출하기_code값이_없는_경우() {
        String codeName = JoinRequestTypeCode.nameOf("03");
        System.out.println(codeName);
    }

    @Test
    void 테스트_코드() {
        int N = 7;
        int[] A = { 70,45,69,69,77,81,33 };

        Solution sol = new Solution();
        sol.solution( N, A );

    }

    class Solution {

        private Integer[] scoreOrder;
        private int[] scoreOrderArr;

        public String solution(int N, int[] A) {

            // 등급의 비율을 계산 - 각 등급별 몇명이 최대로 받을 수 있는지
            int AMaxCnt = (int) Math.floor( N * 0.3 );
            int BMaxCnt = (int) Math.floor( N * 0.7 );

            // integer로 변환
            this.scoreOrder = new Integer[ N ];

            for ( int i=0; i < N; i++ ) {
                this.scoreOrder[i] = A[i];
            }
            // 성적순으로 정렬
            Arrays.sort( this.scoreOrder, Collections.reverseOrder() );

            this.scoreOrderArr = new int[ N ];
            for ( int i=0; i < N; i++ ) {
                this.scoreOrderArr[i] = this.scoreOrder[i];
            }

            // 등급별 마지노선 값 추출
            int lineScoreA = getLineScore( AMaxCnt );
            int lineScoreB = getLineScore( BMaxCnt );

            // 등급 비율에 따라 계산
            String[] grades = new String[ N ];
            for ( int idx=0; idx < N; idx++ ) {
                int score = A[idx];
                if ( score >= lineScoreA ) {
                    grades[idx] = "A";
                } else if ( score >= lineScoreB ) {
                    grades[idx] = "B";
                } else {
                    grades[idx] ="C";
                }
            }

            return String.join( "", grades );
        }

        private int getLineScore( int maxCnt ) {
            int lineScore = this.scoreOrderArr[ maxCnt - 1 ];

            // 마지노선의 점수가 몇개가 존재하는지 체크하고, 마지노선 점수 이전의 점수의 위치 기억
            int idx = 0;
            int lineScoreCnt = 0;
            for ( int score : this.scoreOrderArr ) {
                if ( score > lineScore ) {
                    idx++;
                }

                if ( score == lineScore ) {
                    lineScoreCnt++;
                }
            }

            if ( lineScoreCnt > 1 ) {
                return this.scoreOrderArr[ idx ];
            }
            return lineScore;
        }
    }
}
