# SNMP4Android

[![](https://jitpack.io/v/wosika/SNMP4Android.svg)](https://jitpack.io/#wosika/SNMP4Android) [![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu/#/en_US) 

I have been looking for a long time, but I have not found a suitable SNMP client for android, so I hope to help you <br/>


Use:<br/>

	 //Called in a new thread
 	 thread {
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
                    //call to send
                    .sendSNMP(".1.3.6.1.2.1.25.3.5.1.1", "169.254.198.16")
            }
                //Don't call the function thread{}.start() , kotlin syntax automatically start
               // .start()
	  
To set JDK 1.8, add the code to set JDK version under android in app build:gradle, as follows:
 
	android {
  	  ......
  	  //Set JDK version
  	  compileOptions {
  	      sourceCompatibility JavaVersion.VERSION_1_8
   	      targetCompatibility JavaVersion.VERSION_1_8
  	  }
	}	
	
uses-permission：

	<uses-permission android:name="android.permission.INTERNET"/>
	
dependency：<br/>
  1.Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  2.Add the dependency
  
    dependencies {
	          implementation 'com.github.wosika:SNMP4Android:1.0.2'
    }
    
