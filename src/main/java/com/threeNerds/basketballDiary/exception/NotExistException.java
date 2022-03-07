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
 * 방안 1. (22.03.03 인준) 서비스 통합 Exception클래스를 생성하여 Error메세지를 파라미터로 넘겨주는 방식.(Error메세지는 ENUM or CONSTANT로 관리)
 *      - Exception클래스는 1개(모든 서비스 통합 Exception), Exception클래스의 에러 메세지를 Message클래스의 CONSTANT로 관리
 *      - 차세대 구조 참고
 * 방안 2.(채택) (22.03.03 인준) 예외별로 Exception클래스를 만들어서 사용. Error메세지는 에러를 throw할때 임의로 넣어줌.
 *      - 메세지 작성이 유연하나, 통합관리가 어려움.
 *      - Exception마다 클래스를 따로 생성해줘야 하는 번거로움이 있음.
 *      - 하지만, try ... catch 문에서 예외를 클래스명으로 잡기 때문에 가독성이 좋을 수 있다고 생각됨.
 *      - 참고자료 : f-lab프로젝트 https://github.com/f-lab-edu/sns-itda/tree/ff7a5152b1b49ad63cf7d9ddc13a3b64a5ea2172/src/main/java/me/liiot/snsserver
 *
 * [고민해봐야 할 이슈]
 * 1. (22.03.03 인준) 예외를 발생시켜야 하는 상황은 어떤 상황인가.
 *      - 서버에서 발생한 예외를 클라이언트에 안내하여 클라이언트가 적절한 조치를 취할 수 있도록 안내해주는 것을 목적으로 해야 하는 것인지?
 *      >> RuntimeExcpetion이 발생하면 동일한 트랜잭션 단위에 있는 작업들은 Rollback이 됨
 *
 * [참고자료]
 * 점프 투 자바 : https://wikidocs.net/229
 * 예외클래스의 구조 : https://itmining.tistory.com/9
 * 통합 예외클래스 적용 및 ExcpetionHandler : https://bcp0109.tistory.com/303
 */
public class NotExistException extends RuntimeException {

    public NotExistException() {
        super();
    }

    public NotExistException(String message) {
        super(message);
    }
}
