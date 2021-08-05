# Pudge

Pudge 是基于 ByteX 开发的字节码插件库的合集

- [WillFix(第三方代码错误捕获)](#willfix)
- [Hugo2(自动打印方法日志)](#hugo2)

### WillFix

基于 Gradle API 和 ASM 的第三方库崩溃修复插件
    	
	在指定方法的方法体中增加 try catch 块的插件


#### 插件接入

在 project build.gradle 中添加 classpath
	
```groovy
dependencies {
    ...
    classpath "com.bytedance.android.byteX:base-plugin:${Versions.bytex}"
    classpath "wtf.s1.pudge:will-fix-bytex:${Versions.pudge}"
}
```

在 app build.gradle 中添加依赖，并启用插件
```groovy
dependencies {
    ...
    implementation "wtf.s1.pudge:will-fix-core:${Versions.pudge}"
}

apply plugin: 'bytex'
ByteX {
    enable true
    enableInDebug true
    logLevel "DEBUG"
}

apply plugin: "s1.willfix.bytex"
willFixByteX {
    enable true
    enableInDebug true
    logLevel "DEBUG"
    // 捕获错误后执行的静态方法
    exceptionHandler "wtf.s1.willfix.ExceptionHandler#log#(Ljava/lang/Exception;)V"
    // 需要增加 try catch 的方法列表
    methodList = [
            "wtf.s1.willfix.Test#getMsgSize#()I",
            "wtf.s1.willfix.Test#setError#(I)V",
            "com.google.firebase.perf.network.InstrumentOkHttpEnqueueCallback#onResponse#(Lokhttp3/Call;Lokhttp3/Response;)V"
    ]
}
```
编译阶段插件将 try catch 代码块自动织入目标方法中。

### Hugo2

 使用 ASM 重新实现 [hugo](https://github.com/JakeWharton/hugo)


#### 快速接入

在 project build.gradle 中添加 classpath

```groovy
dependencies {
    ...
    classpath "com.bytedance.android.byteX:base-plugin:${Versions.bytex}"
    classpath "wtf.s1.pudge:hugo2-bytex:${Versions.pudge}"
}
```

在 app build.gradle 中添加依赖，并启用插件


```groovy
dependencies {
    ...
    implementation "wtf.s1.pudge:hugo2-core:${Versions.pudge}"
    ...
}


apply plugin: "s1.hugo2.bytex"
hugo2ByteX {
    enable true
    enableInDebug true
    logLevel "DEBUG"
}

```
在目标的类或者方法上添加 @DebugLog 注解，在 Debug Application 的启动方法中自定义输出的样式

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

```kotlin
class DebugApp: Application() {

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

输出 log 示例
```
 I/hugo: ---> wtf/s1/pudge/Test test7 b=false,i=4,s=null,ooo=[1, 9, 9, 7, 0, 7, 0, 1],
 I/hugo: <--- wtf/s1/pudge/Test test7
```
