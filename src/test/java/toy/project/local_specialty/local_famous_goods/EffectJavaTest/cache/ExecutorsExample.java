package toy.project.local_specialty.local_famous_goods.EffectJavaTest.cache;

import java.util.concurrent.*;

/**
 *  판매자순위 , 아이템 순위 매길때 판매량 순위 100명 캐싱해두고 캐싱처리후 쓰레드로 검색해와서 캐싱 지울거지우고
 *  다시 만들어서 반환 해보기
 */
public class ExecutorsExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10); // 블록킹 큐라 컨커런트함
        ExecutorService ser = Executors.newCachedThreadPool(); // 가장많이 만듬 유동적으로 만듬
        int i = Runtime.getRuntime().availableProcessors(); // 씨피유 갯수
        ExecutorService cpuService = Executors.newFixedThreadPool(i);

        Future<String> submit = service.submit(new Task());

        System.out.println(Thread.currentThread() + " hello");

        System.out.println(submit.get());

        service.shutdown();
    }

    static class Task implements Callable<String> { // 작업의 결과를 받고싶을 때

        @Override
        public String call() throws Exception {
            Thread.sleep(2000L);
            return Thread.currentThread() + " world";
        }
    }


}
