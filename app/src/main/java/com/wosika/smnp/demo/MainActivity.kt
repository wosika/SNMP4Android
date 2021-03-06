package com.wosika.smnp.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wosika.smnp.SnmpUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val text: StringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSend.setOnClickListener {
            text.clear()
            setMessage("")
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
        }
    }

    //发送指令
    private fun sendSnmp() {
        //子线程中调用

    }

    private fun setMessage(msg: String) {
        text.append("$msg \n")
        runOnUiThread {
            tvMessage?.text = text.toString()
        }
    }
}
