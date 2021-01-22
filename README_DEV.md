# WillFix的实现

用demo中的类来做例子

```java
public class Test {

    public String msg;
    public int error;

    public int getMsgSize() {
        return msg.length();
    }

    public void setError(int err) {
        error = 100 / err;
    }
}

```

在这个例子中，当`msg`为空时，调用getMsgSize()方法将会抛出NullPointerException。
调用setError()方法时，如果参数传0则会抛出ArithmeticException。

为了防止这样的异常抛出导致程序崩溃，我们要在两个方法上都加上TryCatch的代码块，如下

```java
public int getMsgSize() {
    try {
        return msg.length();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}

public void setError(int err) {
    try {
        error = 100 / err;
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

用过javap命令比对前后的字节码区别

<table>
<tr>
<th>
修改前
</th>
<th>
修改后
</th>
</tr>

<tr>
<td>
<pre>
public int getMsgSize();
    Code:
        0: aload_0
        1: getfield      #2
        4: invokevirtual #3
        7: ireturn
        
        
        
        
        
        
        
        
        
</pre>
</td>
<td>
<pre>
public int getMsgSize();
    Code:
        0: aload_0
        1: getfield      #2
        4: invokevirtual #3
        7: ireturn
        8: astore_1
        9: aload_1
        10: invokevirtual #5
        13: iconst_0
        14: ireturn
    Exception table:
        from    to  target type
           0     7     8   Class java/lang/Exception

</pre>
</td>
</tr>

<tr>
<td>
<pre>
public void setError(int);
    Code:
        0: aload_0
        1: bipush        100
        3: iload_1
        4: idiv
        5: putfield      #4
        8: return
        
        
        
        
        


</pre>
</td>
<td>
<pre>
public void setError(int);
    Code:
        0: aload_0
        1: bipush        100
        3: iload_1
        4: idiv
        5: putfield      #6
        8: goto          16
        11: astore_2
        12: aload_2
        13: invokevirtual #5
        16: return
    Exception table:
        from    to  target type
           0     8    11   Class java/lang/Exception
</pre>
</td>
</tr>
</table>



通过javap命令可以对比经过ASM处理的字节码和目标字节码的差异，来排查错误。

根据方法返回值的不同，有不同的处理方式。

1. 空返回
2. 基础类型的返回
3. 数组返回
4. 对象返回

对于返回对象的方法，无法作通用的处理，不考虑支持
基础类型返回的方法，都统一返回了0

WillFix用了ASM的[TreeApi](https://asm.ow2.io/asm4-guide.pdf)来进行字节码修改
具体的实现在core的HasReturnMethodTransformer和VoidReturnMethodTransformer两个类中