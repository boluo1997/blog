单例模式是Java中最简单的设计模式之一，它的定义是：确保一个类只有一个实例，而且自行实例化并向整个系统提供这个实例。



根据官方给出的定义，我们可以理解一下，确保一个类只有一个实例，这句话就说明该类的构造方法不能是public的，即不能被外界实例化，那么只能是private的。只有一个实例，这个实例属于当前类，即这个实例是当前类的类成员变量（静态变量）。向整个系统提供这个实例，即提供一个方法，向外界提供当前类的实例（实例化只能在内部）。



单例模式的主要作用是确保一个类只有一个实例。

~~~java
class Singleton{
    private static Singleton singleton;
    
    private Singleton(){}
    
    public static Singleton getInstance(){
        return singleton;
    }
}
~~~



根据定义，我们可以大概推测出单例模式的结构就是这样。其实根据实例化的实际不同，单例模式还可以分为饿汉式和懒汉式两种模式。



饿汉式：在类加载的时候就进行实例化

~~~java
class Singleton{
    private static Singleton singleton = new Singleton();
    
    private Singleton(){}
    
    public static Singleton getInstance(){
        return singleton;
    }
}
~~~



懒汉式：在类加载的时候不进行实例化，在第一次使用的时候进行实例化。

~~~java
//懒汉式：(线程不安全)
class Singleton{
    private static Singleton singleton;
    
    private Singleton(){}
    
    public static Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }
}
~~~



在这里，我们需要进行同步处理，也就是加锁，来防止它被多次实例化。

~~~java
//懒汉式：(线程安全)
class Singleton{
    private static Singleton singleton;
    
    private Singleton(){}
    
    public synchronized static Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }
}
~~~



在懒汉式中还有一种模式：双重检查锁

以上代码中，我们同步处理的处理范围是整个方法，但是我们的实例化仅仅发生在第一次，一旦被实例化，下次判断便不可能为空，就不会再实例化，但是我们还会执行同步块，为了提高效率，我们可以减小同步的范围，即在第一次判断为空时，我们再加锁。

~~~java
class Singleton{
    private volatile static Singleton singleton;
    
    private Singleton(){}
    
    public static Singleton getInstance(){
        if(singleton == null){
            synchronized(Singleton.class){
                if(singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
~~~

