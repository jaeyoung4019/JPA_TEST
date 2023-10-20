package toy.project.local_specialty.local_famous_goods.EffectJavaTest;

@FunctionalInterface
public interface MyFunction {
    /**
     * 오직 하나만 존재 가능
     * @FunctionalInterface 할거면 1개만
     * 없어도 되긴하지만, 그래도 마킹을위해 어노테이션 달
     *
     * 스태틱 추가는 가능
     * @param integer
     * @return
     */
    String value(Integer integer);

    static String hello(){
        return "hello";
    }
}
