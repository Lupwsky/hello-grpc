# 变长参数基本规则

* 变长参数必须是方法中的最后一个参数
* 同一个方法不能定义多个变长参数

# 自增的陷阱

下面的这段代码最终 count 的值为 0

```java
public static void main(String[] args) {
    int count = 0;
    for (int i = 0; i < 10; i++) {
        count = count++;
    }
}
```