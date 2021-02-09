静态方法：

静态方法随着类的加载而加载到方法区的静态区，当静态方法被调用时加载到栈中执行，静态方法可以通过类名.方法名调用，也可以通过对象调用



静态方法中可以定义静态变量吗？

不可以，方法中的内容被调用时才执行，但是静态变量随着类的加载而加载



静态方法中可以使用this吗

不能，静态方法和类同级，this是对象级别的，类级别优于对象级别先加载



静态方法可以重载和重写吗

可以重载、可以继承、没有重写。重写针对的是对象级别，静态方法随着类的加载而加载，与类同级，类级别有限对象级别加载。

在Java代码中如果父子类出现了方法签名一致的方法，要么都是静态方法，要么都是非静态方法。

1.如果两个方法都没有 static 就是重写

2.如果两个方法都有 static 就是两个方法，没有重写

3.如果两个方法一个是 static 另一个非 static 会报错





static修饰的信息都只加载一次，因为类只加载一次
