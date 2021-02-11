~~~java
interface Calc{
    int sum(int m, int n);
}

//在匿名内部类中实现
sum(new Calc(){
    public int sum(int m, int n){
        return m+n;
    }
});


//在接口中只有一个抽象方法时（函数式接口）
Calc c = (int m, int n) -> {return m+n;};

//如果方法体只有一句话可以省略{}及return
Calc c = (int m, int n) -> m+n;

Calc c = (m,n) -> m+n;

~~~

