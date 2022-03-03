package com.threeNerds.basketballDiary.exception;

/**
 * Exception은 예외를 언제 발생시키는지에 따라 크게 2가지 종류로 나뉨
 * 1. Exception (Checked Exception) : 컴파일 단계에서 예외를 발생시킴.
 * 2. RuntimeException (Unchecked Exception) : 컴파일 단계가 아닌 프로그램이 작동하고 있을때(Runtime때) 예외를 체크해서 발생시킴.
 *
 * 예외가 발생하면 서비스 수행이 중단(예외 발생지점 이후의 코드는 동작X)되고, DB처리 내용이 Rollback됨.
 * 예외가 발생해도 프로그램을 정상적으로 동작하게 하려면 예외처리를 해주어야 함.
 * try ... catch 문을 활용하여 발생한 예외를 처리해줘야 함.
 *
 * 예외가 발생한 곳이 아닌, 예외가 발생한 곳의 상위 호출부에서 예외를 처리하게 하는 방법이 있음.
 * throw/throws 문법을 통해 예외를 던져서 상위 호출부에서 try ... catch 문으로 예외를 처리해줄 수 있음.
 *
 * [Exception 적용방안]
 * 방안 1. (22.03.03 인준) 서비스 통합 Exception클래스를 생성하여 ENUM으로 Error메세지를 관리하여 Exception클래스의 파라미터로 넘겨주기.
 *      - 차세대 구조 참고
 *
 * [고민해봐야 할 이슈]
 * 1. (22.03.03 인준) 예외를 발생시켜야 하는 상황은 어떤 상황인가.
 *      - 서버에서 발생한 예외를 클라이언트에 안내하여 클라이언트가 적절한 조치를 취할 수 있도록 안내해주는 것을 목적으로 해야 하는 것인지?
 *
 * [참고자료]
 * 점프 투 자바 : https://wikidocs.net/229
 * 예외클래스의 구조 : https://itmining.tistory.com/9
 */
public class TeamMemberException extends RuntimeException {
}
