package toy.project.local_specialty.local_famous_goods.EffectJavaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CompareTo {

    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        integers.add(100);
        integers.add(44);
        integers.add(20);
        integers.add(99);
        integers.add(43);
        System.out.println("integers = " + integers);

        Comparator<Integer> comparator = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        };

        integers.sort(comparator);
        System.out.println("integers1 = " + integers);
        Collections.sort(integers);
        System.out.println("integers2 = " + integers);

        integers.sort((p1 , p2) -> p2.compareTo(p1) );
        integers.sort(Comparator.reverseOrder());
        System.out.println("integersReverse = " + integers);

        integers.sort((p1 , p2) -> p1.compareTo(p2) );
        integers.sort(Comparator.naturalOrder());

        System.out.println("integersNatural = " + integers);


        List<Person> list = new ArrayList<>();
        list.add(new Person(LocalDate.of(2014 ,11 ,15)));
        list.add(new Person(LocalDate.of(2011 ,1 ,15)));
        list.add(new Person(LocalDate.of(2015 ,5 ,15)));
        list.add(new Person(LocalDate.of(2014 ,8 ,15)));
        for (Person person : list) {
            System.out.println("list = " + person.getNow());
        }

        list.sort(Person::compareByAge);
        for (Person person : list) {
            System.out.println("list compare = " + person.getNow());
        }
    }

    private static class Person{

        public Person(LocalDate now) {
            this.now = now;
        }
        LocalDate now;

        public LocalDate getNow() {
            return now;
        }

        private int compareByAge(Person p){
            return this.now.compareTo(p.now);
        }
    }
}
