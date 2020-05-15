# SNMP4Android

[![](https://jitpack.io/v/wosika/SNMP4Android.svg)](https://jitpack.io/#wosika/SNMP4Android)

I have been looking for a long time, but I have not found a suitable SNMP client for android, so I hope to help you <br/>


Use:<br/>

	 //Called in a new thread
 	 thread {
 	            SnmpUtils.sendSNMP(
                                    oidCmd = ".1.3.6.1.2.1.25.3.5.1.1"
                                    , ipAddress = "169.254.198.16"
                                    // Optional parameters: snmp version default "2c"
                                    , snmpVersion = SnmpUtils.SNMP_VERSION_2c
                                    //Optional parameters: timeout default "1000ms"
                                    , timeoutMillisecond = 1000
                                    //Optional parameters: retry count  default "2 time"
                                    , retryCount = 2
                                     //Optional parameters: snmp community  default "public"
                                    , community = "public"
                                    , //Optional parameters: listener ,it can be null
                                    responseListener = { responseState ->
                                        //Determine the success first, and then use value after success
                                        if (responseState.isSuccess) {
                                            setMessage("成功~回调数据：${responseState.value}")
                                        } else {
                                            setMessage("失败~回调数据：${responseState.exception?.message}")
                                        }
                                    }
                                )
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
	          implementation 'com.github.wosika:SNMP4Android:1.0.1'
    }
    
