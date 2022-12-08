package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.dto.PlayerInfoDTO;
import lombok.Getter;

import java.util.List;

/**
 * >> 인준 제안 22.12.08(목)
 * API하나당 하나의 Message클래스를 생성
 * 즉, 컨트롤러 메소드 하나당 Message클래스를 생성하여 관리
 * >> 컨트롤러에서 @RequestBody로 받는 객체는 APIMessage000 타입으로 받기
 *
 * 만약 위와같은 패턴으로 사용한다고 하면 Req와 Res별로 나눠서 사용할 것인지
 * 혹은, 하나의 API의 Req와 Res는 같은 클래스를 사용할 것인지를 결정해야 함.
 *
 * GET메소드를 고려하면 API하나당 하나의 Messsage클래스를 가지는 것이 좋을 것으로 판단
 * Message클래스 내부에서 Req와 Res를 구분하여 작성하는 방식으로..?
 *
 * 이점 : API전문 단위로 관리가능. 클래스 명명시 API의 ID를 활용하면 이름짓기가 명확해짐.
 *          >> ex. APIMessage000 / MessageAPI000 / RequestMessageAPI000 ...
 *       전문단위로 필수값을 지정관리할 수 있음(Message별로 Valid 어노테이션으로 관리 가능)
 * 단점 : API하나 만들때마다 클래스가 생성되면서 클래스가 많아질 수 있음.
 *          >> 허나, 패키지를 잘 구분하여 관리하면 괜찮을지도? (재사용성이 떨어짐... )
 */
@Getter
public class APIMessage060 {

    private Long gameJoinTeamSeq;
    private String homeAwayCode;
    private String quarterCode;
    private List<PlayerInfoDTO> playerList;
}
