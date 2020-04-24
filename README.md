# SNMP4Android

[![](https://jitpack.io/v/wosika/SNMP4Android.svg)](https://jitpack.io/#wosika/SNMP4Android)

找了很久都没有找到适合用在android的snmp客户端所以自己开源一份希望能帮助到你。<br/>

SNMP介绍可以看这，便于理解：<br/>
https://www.jianshu.com/p/18e2a26183a0

使用方法:<br/>

	 //在子线程中调用
 	  thread {
                //调用发送
                SnmpUtils.sendSNMP(
                    oidCmd = ".1.3.6.1.2.1.25.3.5.1.1"
                    , ipAddress = "169.254.198.16"
                    //设置版本 可选参数默认为2c
                    , snmpVersion = SnmpUtils.SNMP_VERSION_2c
                    //设置超时时间 可选参数 默认为1000毫秒
                    , timeoutMillisecond = 1000
                    //设置重试次数 可选参数 默认为2次
                    , retryCount = 2
                    //设置团体名  可选参数 默认public
                    , community = "public"
                    ,   //设置监听器 可选参数
                    responseListener = { responseState ->
                        //先判定是否成功，成功再去使用value
                        if (responseState.isSuccess) {
                            setMessage("成功~回调数据：${responseState.value}")
                        } else {
                            setMessage("失败~回调数据：${responseState.exception?.message}")
                        }
                    }
                )
            }
            //切记不要再使用start kotlin中语法糖thread{}会自动start
            // .start()
	  
 需要指定JDK 1.8，在app build:gradle 中的android 下添加 指定jdk版本的代码,如下:
 
	android {
  	  ......
  	  //指定jdk版本
  	  compileOptions {
  	      sourceCompatibility JavaVersion.VERSION_1_8
   	      targetCompatibility JavaVersion.VERSION_1_8
  	  }
	}	
	
增加权限：

	<uses-permission android:name="android.permission.INTERNET"/>
	
依赖添加：<br/>
  1.Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  2.Add the dependency
  
    dependencies {
	          implementation 'com.github.wosika:SNMP4Android:1.0.1'
    }
    
