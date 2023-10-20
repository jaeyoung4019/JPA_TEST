package toy.project.local_specialty.local_famous_goods.EffectJavaTest.gc;

public class 설명 {
    /**
     *  Mark -> 오브젝트가 참조가 되는지 체크함
     *  Sweep -> 필요없는 오브젝틀르 메모리 힙에서 삭제
     *  Compact -> 메모리 낭비를 줄이기 위해서 필요함. 메모리 공간을 쌓는 느낌 테트리스
     *
     *  Yong Generation (Eden , S0 , S1) 금방 죽는 객체 Minor GC
     *  최초 eden 에 객체 생성이 되고 꽉차면 생성 못하니까 -> gc 가 일어나고 -> S1 으로 붓고 -> 그다음 S0 으로 붓고 이런식으로 넘기는 형식
     *  Old Generation 오래 살아 남는 객체 MinorGc , Full GC
     *
     *  자바 GC - > 페러럴 GC == 씨리얼 GC
     *
     *  GC 를 보는 관점
     *  Throughput -> 애플리케이션 처리하는 역량 리소스를 GC 에 쓰면 스로우 풋이 줄어들겠죠. 서버의 리소스를 얼마나 애플리케이션 활용에쓰는가
     *  Latency (Stop - The - World) -> 굉장히 중요 . gc 가 일어나면 응답을 못 받음.
     *  Footprint ->
     */
}
