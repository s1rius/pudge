# WillFix

基于gradle transform api和ASM的第三方库崩溃修复插件


### 背景

App在引入第三方库的同时，也引入了第三方法库的bug。第三方团队无法即时修复问题，发布版本。需要App的开发者自行修复bug。

### WillFix是什么

WillFix是一个可以修复其他第三方库崩溃的插件。
通过简单的设置扩展，可以在目标方法中增加TryCatch代码块，捕获造成崩溃的异常

### WillFix的缺陷

WillFix仅能在返回值是空的方法上加TryCatch代码块，或者在返回值是基本类型的方法上加上TryCatch代码块，并返回0
对返回值是数组和类的方法，不做支持。如果需要可以fork代码自己修改

### WillFix的具体实现

[实现方式和步骤](README_DEV.md)

### 快速接入

WillFix 提供两种方式来进行接入

- 单独插件接入
- 作为ByteX字节码插件的子插件接入

1. 单独插件接入

	在build.gradle中加入以下代码
	
	```
	buildscript {
	    repositories {
	        google()
	        mavenCentral()
	        jcenter()
	    }
	
	    dependencies {
	        ...
	        classpath "wtf.s1.pudge:will-fix-plugin:${Versions.willFix}"
	    }
	}
	
	dependencies {
	    ...
	    implementation "wtf.s1.pudge:will-fix-core:${Versions.willFix}"
	}
	
	apply plugin: "s1.willfix"
	willFix {
	    enable true
	    enableInDebug true
	    needVerify true
	    logLevel "INFO"
	    // 捕获异常之后的处理方法
	    exceptionHandler "wtf.s1.willfix.ExceptionHandler#log#(Ljava/lang/Exception;)V"
	    // 示例：需要捕获异常的方法
	    methodList = [
	            "wtf.s1.willfix.Test#getMsgSize#()I",
                "wtf.s1.willfix.Test#setError#(I)V",
	            "com.google.firebase.perf.network.InstrumentOkHttpEnqueueCallback#onResponse#(Lokhttp3/Call;Lokhttp3/Response;)V"
	    ]
	}
	```

2. ByteX子插件接入

	在build.gradle中加入以下代码
	
	```
	buildscript {
	    repositories {
	        google()
	        mavenCentral()
	        jcenter()
	    }
	
	    dependencies {
	        ...
	        classpath "com.bytedance.android.byteX:base-plugin:${Versions.bytex}"
	        classpath "wtf.s1.pudge:will-fix-bytex:${Versions.willFix}"
	    }
	}
	
	dependencies {
	    ...
	    implementation "wtf.s1.pudge:will-fix-core:${Versions.willFix}"
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
	    exceptionHandler "wtf.s1.willfix.ExceptionHandler#log#(Ljava/lang/Exception;)V"
	    methodList = [
	            "wtf.s1.willfix.Test#getMsgSize#()I",
                "wtf.s1.willfix.Test#setError#(I)V",
	            "com.google.firebase.perf.network.InstrumentOkHttpEnqueueCallback#onResponse#(Lokhttp3/Call;Lokhttp3/Response;)V"
	    ]
	}
	```


