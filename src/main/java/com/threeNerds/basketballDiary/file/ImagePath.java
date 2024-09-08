package com.threeNerds.basketballDiary.file;


import lombok.Getter;

@Getter
public enum ImagePath implements PathType {

    TEAM_PROFILE( "소속팀 프로필" ),
    TEAM_LOGO( "팀 로고" );
    private final String name;

    ImagePath( String name ) {
        this.name = name;
    }

}
