# SNMP4Android

[![](https://jitpack.io/v/wosika/SNMP4Android.svg)](https://jitpack.io/#wosika/SNMP4Android)

简易使用于安卓的SNMP工具类，基于snmp4j

转载这篇文章，SNMP介绍可以看这，便于理解：https://blog.csdn.net/jonbb/article/details/51353201

Use:<br/>
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
	  
 需要指定JDK 1.8
 
	android {
  	  ......
  	  //指定jdk版本
  	  compileOptions {
  	      sourceCompatibility JavaVersion.VERSION_1_8
   	     targetCompatibility JavaVersion.VERSION_1_8
  	  }
	}	
    
