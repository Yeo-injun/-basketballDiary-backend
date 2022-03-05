package com.threeNerds.basketballDiary.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@Builder
@AllArgsConstructor // 모든 필드를 파라미터로 받아서 생성자를 만들어 줌. 이를 통해 @Builder사용시 MyBatis에서 select 결과를 객체에 매핑 시켜줄 때 모든 필드를 가진 객체를 생성하여 오류 해결.
@NoArgsConstructor
public class TeamJoinRequest {
    // 팀가입요청Seq
    private Long teamJoinRequestSeq;

    // 팀Seq
    private Long teamSeq;

    // 사용자Seq
    private Long userSeq;

    // 가입요청 유형코드
    private String joinRequestTypeCode;

    // 가입요청상태 코드
    private String joinRequestStateCode;

    // 가입요청일시
    private Date requestDate; // TODO sql패키지와 util패키지의 Date차이는?? => LocalDate 로 수정하는 것이 좋음

    // 요청확정일시
    private Date confirmationDate;

    /**
     * 1. DTO의 모든 필드의 값을 Entity에 복사한다.
     * */
//    public static TeamJoinRequest toEntity(Object dto)
//    {
//        // extractGetterMapFromSource()
//        Map<String, Method> getterMap = new HashMap<String, Method>();
//        Method[] declaredMethods = dto.getClass().getDeclaredMethods();
//        Arrays.stream(declaredMethods)
//                .filter(method -> method.getName().startsWith("get"))
//                .forEach(method -> getterMap.put(method.getName(), method));
//
//        TeamJoinRequest.TeamJoinRequestBuilder targetClass = new TeamJoinRequest.TeamJoinRequestBuilder();
//        Method[] builderSetterArray = targetClass.getClass().getDeclaredMethods();
////        Field[] fields = TeamJoinRequest.builder().getClass().getDeclaredFields();
//        Arrays.stream(builderSetterArray)
//                .forEach(builderSetter -> {
//
//                    builderSetter.invoke();
//                });
//
//
//    }
//
//    private static String makeGetterName (String fieldName)
//    {
//        StringBuffer sb = new StringBuffer();
//        sb.append("get");
//        sb.append(fieldName.substring(0,1).toUpperCase());
//        sb.append(fieldName.substring(1));
//        return sb.toString();
//    }
//
//    private static Method getGetter (Field field, Map<String, Method> getterMap)
//    {
//        return getterMap.get(makeGetterName(field.getName()));
//    }
}
