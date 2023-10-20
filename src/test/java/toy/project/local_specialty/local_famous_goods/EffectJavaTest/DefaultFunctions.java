package toy.project.local_specialty.local_famous_goods.EffectJavaTest;

import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DefaultFunctions {

    public static void main(String[] args) {
        /**
         * JAVA.FUNCTION 에서 찾아보기
         */

        /**
         *  1번은 인풋
         *  2번은 아웃풋
         *  타입 선언
         *  return -> 정의 가능
         */
        Function<Integer , String> function1 = (i) -> "String";
        Function<Integer , String> function2 = Object::toString; // 대상::해야할일

        /**
         * 인자 없으나 뭔가를 리턴함. 그와중에 리턴은 1번
         */
        Supplier<Integer> supplier;
        /**
         *      public Person() {
         *         }
         */
        Supplier<Person> personSupplier = Person::new;
        /**
         *
         *  public Person(LocalDate now) {
         *             this.now = now;
         *         }
         *
         */
        Function<LocalDate , Person> personFunction = Person::new;
        /**
         *  void 인데 매개변수는 있는것
         *  예를 들어 soutv
         */
        Consumer<Integer> consumer = System.out::println;

        /**
         *  1번을 받아서
         *  불린을 리턴함
         */
        Predicate<Integer> predicate;

    }

    private static class Person{

        public Person(LocalDate now) {
            this.now = now;
        }

        public Person() {
        }

        LocalDate now;

        public LocalDate getNow() {
            return now;
        }
    }
}
