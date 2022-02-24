package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;

// 키값을 받아내기 위한 DTO클래스
public class KeyDTO {

    @Getter
    public static class TeamMember { // TODO 왜 static 클래스여야 하는지?!
        private Long teamMemberSeq;

        public TeamMember teamMemberSeq(Long teamMemberSeq) {
            this.teamMemberSeq = teamMemberSeq;
            return this;
        }

    }
}
