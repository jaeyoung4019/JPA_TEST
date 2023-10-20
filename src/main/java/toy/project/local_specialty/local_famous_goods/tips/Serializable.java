package toy.project.local_specialty.local_famous_goods.tips;

public class Serializable {
    /**
     *
     * https://www.inflearn.com/questions/16570
     * https://www.inflearn.com/questions/17117
     *  해당 사항이 아니면
     *  엔티티에는 실무적 관점으로 봤을 때 필요가 없다.
     *  다만, 위 사항 처럼 join 컬럼을 유니크 컬럼으로 잡을 경우
     *  PK 시점으로 관리하는 영속성 컨텍스트에서 참조할 때 문제가 발생한다.
     *  그렇기 때문에 직렬화를 걸어주면 해결 된다.
     *
     *  차라리 DTO 에 직렬화를 걸어주는 것이 좋다. Response 같은 경우
     */
}
