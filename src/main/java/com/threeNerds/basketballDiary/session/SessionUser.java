package com.threeNerds.basketballDiary.session;

import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamAuthDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class SessionUser {
    private Long userSeq;

    private String userId;

    private Map<Long,Long> userAuth = new HashMap<>();

    public SessionUser(Map<Long, Long> userAuth) {
        this.userAuth = userAuth;
    }

    public SessionUser() {
    }

    public SessionUser(Long userSeq, String userId) {
        this.userSeq = userSeq;
        this.userId = userId;
    }

    public SessionUser(Long userSeq, String userId, Map<Long, Long> userAuth) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.userAuth = userAuth;
    }

    public static SessionUser create(User loginUser) {
        return new SessionUser(loginUser.getUserSeq(), loginUser.getUserId());
    }

    public SessionUser updateAuthority(List<TeamAuthDTO> authList)
    {
        Map<Long, Long> userAuth = authList.stream()
                .collect(Collectors.toMap(authDTO -> Long.parseLong(authDTO.getTeamSeq()),
                                          authDTO -> Long.parseLong(authDTO.getTeamAuthCode())
                                          )
                         );
        this.userAuth = userAuth;
        return this;
    }
}
