# Hugo2

Hugo2 是借鉴 [hugo](https://github.com/JakeWharton/hugo) 和 [Hunter-Debug](https://github.com/Leaking/Hunter/blob/master/README_hunter_debug.md) 使用 ASM 重新实现的 ByteX 插件

### 快速接入

```
classpath "wtf.s1.pudge:hugo2-bytex:${Versions.pudge}"

// 在 app 模块中加入
apply plugin: "s1.hugo2.bytex"
hugo2ByteX {
    enable true
    enableInDebug true
    logLevel "DEBUG"
}

dependencies {
    ...
    implementation "wtf.s1.pudge:hugo2-core:${Versions.pudge}"
    ...
}

```

gradle sync 中后，在需要打印Log的类或者方法上增加`@DebugLog`的注解

```
@DebugLog
class Test {
    ...
}

@DebugLog
fun test() {
    ...
}
```

在 Application 的初始化方法中加上自定义的Logger

```
class App: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        Hugo2.setLogger(object: Hugo2.Hugo2Logger {
            override fun logI(clazz: String, method: String, params: String) {
                super.logI(clazz, method, params)
                Log.i("hugo", "---> $clazz $method $params")
            }

            override fun logO(clazz: String, method: String) {
                super.logO(clazz, method)
                Log.i("hugo", "<--- $clazz $method")
            }
        })
    }
}
```

就可以在LogCat中看到对应方法调用的Log输出了

示例
```
 I/hugo: ---> wtf/s1/pudge/Test test7 b=false,i=4,s=null,ooo=[1, 9, 9, 7, 0, 7, 0, 1],
 I/hugo: <--- wtf/s1/pudge/Test test7
```