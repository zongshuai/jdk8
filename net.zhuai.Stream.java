package net.zshuai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Stream {
    public static void main(String[] args) {
        // 集合的筛选
        Student s1 = new Student(1L, "张三", 10,"北京");
        Student s2 = new Student(2L, "李四", 10,"天津");
        Student s3 = new Student(3L, "王五", 12,"北京");
        Student s4 = new Student(4L, "赵六", 13,"湖北");
        Student s5 = new Student(5L, "马七", 10,"浙江");
        Student s6 = new Student(6L, "钱八", 15,"北京");
        Student s7 = new Student(1L, "张三", 10,"北京");
        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);
        studentList.add(s4);
        studentList.add(s5);
        studentList.add(s6);
        studentList.add(s7);
        List<Student> students = testFilterAge(studentList);
        System.out.println("筛选的年龄大于10的学生：");
        students.forEach(System.out::println);
        System.out.println("筛选住址为北京的：");
        List<Student> studentAdress = testFilterAdress(studentList);
        studentAdress.forEach(System.out::println);
        testMap(studentList);
        // List去重测试
        testDistinct();

        // 对象去重需要重写pojo里面的equals、hashCode方法，不然达不到去重效果
        studentList.stream().distinct().forEach(System.out ::println);

        // 排序
        testSort();

        List<Student> studentsSort = testSortVO(studentList);
        studentsSort.forEach(System.out:: println);
        testSortVOAesc(studentList).forEach(System.out::println);
        // 返回集合前两个
        testLimit(studentList).forEach(System.out:: println);
        testSkip();

        testReduce();

        Student student = testMin(studentList);
        System.out.println("年龄最小的："+student.toString());

        testMatch( studentList);


    }
    private static List<Student> testFilterAge(List<Student> studentList){
        // 筛选年龄大于10的数据
        return studentList.stream().filter(s -> s.getAge() > 10).collect(Collectors.toList());
    }

    private static List<Student> testFilterAdress(List<Student> studentList){
        // 筛选地址为北京的数据
        return studentList.stream().filter(student -> student.getAddress().equals("北京")).collect(Collectors.toList());
    }

    /**
     * 集合转换
     * @param studentList
     * @return
     */
    private static void testMap(List<Student> studentList){
        // 在地址前面加上部分信息，只获取地址输出
        // map就是将对应的元素按照给定的方法进行转换。
        List<String> adress = studentList.stream().map(student -> "住址：" + student.getAddress()).collect(Collectors.toList());
        adress.forEach(s -> System.out.println(s));

    }
    
    // 去重Demo
    private static void testDistinct(){
        List<String> strList = Arrays.asList("111","222","333","222","444");
        List<String> collect = strList.stream().distinct().collect(Collectors.toList());
        strList.stream().distinct().forEach(System.out:: println);
    }

    // 排序Demo
    private static void testSort(){
        List<String> strList = Arrays.asList("555","222","333","444");
        strList.stream().sorted().forEach(System.out:: println);


    }

    // 对象排序
    private static List<Student> testSortVO(List<Student> studentList){
        List<Student> sortList = studentList.stream().sorted((s1, s2) -> Long.compare(s2.getId(), s1.getId()))
                .sorted((s1, s2) -> Integer.compare(s2.getAge(), s1.getAge())).collect(Collectors.toList());
        System.out.println("降序排列：");
        return sortList;
    }

    // 对象排序
    private static List<Student> testSortVOAesc(List<Student> studentList){
        List<Student> sortList = studentList.stream().sorted((s1, s2) -> Long.compare(s1.getId(), s2.getId()))
                .sorted((s1, s2) -> Integer.compare(s1.getAge(), s2.getAge())).collect(Collectors.toList());
        System.out.println("升序排列：");
        return sortList;
    }

    /**
     * 集合limit，返回前几个元素
     */
    private static List<Student> testLimit(List<Student> studentList) {
        System.out.println("返回集合前两个：");
        return studentList.stream().limit(2).collect(Collectors.toList());
    }
    /**
     * 集合skip，删除前n个元素
     */
    private static void testSkip() {
        List<String> list = Arrays.asList("333","222","111");
        System.out.println("删除集合前n个元素：");
        list.stream().skip(2).forEach(System.out::println);
    }

    /**
     * 集合reduce,将集合中每个元素聚合成一条数据
     */
    private static void testReduce() {
        List<String> list = Arrays.asList("欢","迎","你");
        String appendStr = list.stream().reduce("北京",(a,b) -> a+b);
        System.out.println(appendStr);
    }

    /**
     * 按条件获取集合里面最小值
     */
    private static Student testMin(List<Student> studentList){
        Student student = studentList.stream().min((s1, s2) -> Integer.compare(s1.getAge(), s2.getAge())).get();
        return student;
    }

    /**
     * anyMatch：Stream 中任意一个元素符合传入的 predicate，返回 true
     *
     * allMatch：Stream 中全部元素符合传入的 predicate，返回 true
     *
     * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
     * @param studentList
     */

    private static void testMatch(List<Student> studentList){

        boolean b1 = studentList.stream().anyMatch(s -> "北京".equals(s.getAddress()));
        if (b1){
            System.out.println("有北京的同学");
        }
        boolean b2 = studentList.stream().allMatch(s -> s.getAge() > 9);
        if (b2){
            System.out.println("所有学生都大于9岁");
        }
        boolean b3 = studentList.stream().noneMatch(s -> "夏祯".equals(s.getName()));
        if (b3){
            System.out.println("没有叫夏祯得同学");
        }
    }

}
