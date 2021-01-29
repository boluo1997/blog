在Java的集合排序中有两种排序方式,一是使用比较器Comparator,重写compare()方法.二是实现Comparable接口,重写compareTo()方法.我们来看一下这两种方式的区别.



第一种使用比较器Comparator,有两个要注意的方面,

1).通过集合调用sort()方法返回比较器.

2)需要对compare()方法进行重写.



~~~java
/**
 * 排序方法1: 通过比较器Comparator
 * 通过集合对象调用sort()方法返回比较器
 * 需要对compare方法进行重写
 *
 */

public class Test30_compare {
    public static void main(String[] args) {
        Person p1 = new Person("张三",20,86);
        Person p2 = new Person("李四",20,96);
        Person p3 = new Person("王五",20,76);

        List list = new ArrayList();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        list.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.score - o2.score;
            }
        });

        System.out.println(list);
    }
}

class Person{

    String name;
    int age;
    int score;

    public Person(String name,int age,int score){
        this.name = name;
        this.age = age;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }
}
~~~



第二种是实现Comparable接口,重写compareTo()方法,来指定排序规则

~~~java
/**
 * 排序方法2: 实现comparable接口
 */

public class Test31_compareTo {

    public static void main(String[] args) {

        Animal3 an1 = new Animal3("张三",20,86);
        Animal3 an2 = new Animal3("李四",20,76);
        Animal3 an3 = new Animal3("王五",20,96);

        List list = new ArrayList();

        list.add(an1);
        list.add(an2);
        list.add(an3);

        Collections.sort(list);
        System.out.println(list);
    }

}

class Animal3 implements Comparable<Animal3>{

    String name;
    int age;
    int score;

    public Animal3(String name,int age,int score){
        this.name = name;
        this.age = age;
        this.score = score;
    }

    //在这里重写compareTo()方法,来指定排序规则
    @Override
    public int compareTo(Animal3 an) {
        return this.score - an.score;
    }

    @Override
    public String toString() {
        return "Animal3{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }
}
~~~



上面是两种方法的不同用法



compareTo()方法是Comparable接口中的方法,当需要对某个类的对象进行排序的时候,这个类需要实现Comparable接口,重写接口中的compareTo()方法.这是一种内部排序的方式,也就是说实现了这个接口的对象就有了排序的能力,所以叫内部排序.

compare()方法是Comparator接口中的方法,它的内部其实也调用了compareTo()方法.它是针对一些本身没有比较能力的对象,为他们实现比较的功能,是一种外部排序的方式.