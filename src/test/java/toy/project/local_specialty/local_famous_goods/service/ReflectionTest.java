package toy.project.local_specialty.local_famous_goods.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import toy.project.local_specialty.local_famous_goods.dto.member.save.SellerSaveRequest;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


@RunWith(SpringRunner.class)
public class ReflectionTest {

    @Test
    public void ReflectionTestOne () throws Exception {
        //give
        SellerSaveRequest sellerSaveRequest =
                SellerSaveRequest.builder()
                        .userId("TEST")
                        .city("TEST_CITY")
                        .companyCity("TEST_COMPANY_CITY")
                        .acountNumber("TEST_ACCOUNT")
                        .bankName("TEST_BANK_NAME")
                        .street("TEST_STREET")
                        .zipCode("TEST_ZIP_CODE")
                        .name("TEST_NAME")
                        .category("TEST_CATEGORY")
                        .companyStreet("TEST_STREET")
                        .companyZipCode("TEST_ZIP")
                        .password("TEST_123123")
                        .brandName("TEST_BRAND")
                        .build();

        // 셀러세이브 dto 클래스를 구현 했을 때,
        // 클래스 정보를 가져와서
        Class<? extends SellerSaveRequest> class_SellerSaveRequest = sellerSaveRequest.getClass();
        // 필드를 가져온다. 단, 퍼블릭만 가져옴.
        Field[] field = class_SellerSaveRequest.getFields();
        // SellerSqveRequest 에 멤버 변수로
        //    @Transient
        //    public String testReflection; 있다면,
        System.out.println("field = " + Arrays.toString(field));
        // field = [public java.lang.String toy.project.local_specialty.local_famous_goods.dto.member.save.SellerSaveRequest.testReflection]
        // 이런 형식으로 어떤 필드가 있는지를 참조할 수 있습니다.
        // 퍼블릭이 아닌 애들까지 모두 들고 오고 싶다면
        Field[] field2 = class_SellerSaveRequest.getDeclaredFields();
        System.out.println("field2 = " + Arrays.toString(field2));

        // 메소드 목록을 모두 가져온다
        Method[] field3 = class_SellerSaveRequest.getDeclaredMethods();
        System.out.println(Arrays.toString(field3)); // [public java.lang.String house.Person.getName(), public int house.Person.getAge()]

        // 인터페이스 목록을 모두 가져온다.
        Class<?>[] field4 = class_SellerSaveRequest.getInterfaces();
        System.out.println(Arrays.toString(field4)); // [interface java.lang.Cloneable]

        // super 클래스를 가져온다
        Class field5 = class_SellerSaveRequest.getSuperclass();
        System.out.println(field5); // class java.lang.Object

        // 물론 메소드 자체를 실행시킬수도 있다. 하지만 퇴근을 해야한다.
    }
    
    @Test
    public void arrayTest(){
        
        List<String> testList = new ArrayList<>();
        testList.add("1");
        testList.add("2");
        testList.add("3");

        String [] testArray = new String[4];
        testArray[0] = "s";
        testArray[3] = "s";

        StringBuilder sb = new StringBuilder();

        for (String s : testList) {
            System.out.println("s = " + s);
            sb.append(s);
        }
        sb.toString();

        List<Map<String , Integer>> testmap = new HashMap<>();

        Map<String , Integer> test1 = new HashMap<>();
        Map<String , Integer> test2 = new HashMap<>();
        test1.put("string1" , 1);

        testmap.add(test1);
        testmap.add(test2);

        for (Map<String, Integer> stringIntegerMap : testmap) {
            Set<String> key = stringIntegerMap.keySet();
            for (String s : key) {
                stringIntegerMap.get(s);
            }
        }

    }

    static class seller {
        
        static int testint = 0;
        public static void testStatic(){
            testint += 10;
        }
    }
}
