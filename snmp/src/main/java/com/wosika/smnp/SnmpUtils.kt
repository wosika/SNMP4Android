package com.wosika.smnp

import android.util.Log
import org.snmp4j.CommunityTarget
import org.snmp4j.PDU
import org.snmp4j.Snmp
import org.snmp4j.TransportMapping
import org.snmp4j.mp.SnmpConstants
import org.snmp4j.smi.*
import org.snmp4j.transport.DefaultUdpTransportMapping
import java.net.InetAddress

/***
 * 基于snmp4j的安卓snmp工具
 * @author  wosika
 * 2020.4.23
 */
object SnmpUtils {

    private const val TAG = "SNMPUtils"

    //版本
    const val SNMP_VERSION_1 = SnmpConstants.version1
    const val SNMP_VERSION_2c = SnmpConstants.version2c
    const val SNMP_VERSION_3 = SnmpConstants.version3

    //默认团体名
    private const val SNMP_COMMUNITY = "public"

    //默认的snmp 端口
    private const val SNMP_PORT = 161

    //默认重试次数
    private const val DEFAULT_RETRY_COUNT = 2

    //默认超时时间 为1秒
    private const val DEFAULT_TIMEOUT_MILLISECOND = 1000L


    /**
     *
     * @param oidCmd String  oid命令字符串
     * @param ipAddress String  访问ip地址
     * @param port Int 可选参数 端口号  默认 161
     * @param snmpVersion Int  可选参数 版本 默认2c
     * @param community String 可选参数  团体名 默认 public
     * @param retryCount Int 可选参数 重试次数 默认2次
     * @param timeoutMillisecond Long 可选参数 超时时间 默认 1000毫秒
     * @param responseListener Function1<ResponseState, Unit>?  可选参数 回调函数 返回请求状态
     */
    fun sendSNMP(
        oidCmd: String,
        ipAddress: String,
        port: Int = SNMP_PORT,
        snmpVersion: Int = SNMP_VERSION_2c,
        community: String = SNMP_COMMUNITY,
        retryCount: Int = DEFAULT_RETRY_COUNT,
        timeoutMillisecond: Long = DEFAULT_TIMEOUT_MILLISECOND,
        responseListener: ((ResponseState) -> Unit)? = null
    ) {
        var snmp: Snmp? = null
        try {
            // 创建传输映射并监听
            val transport: TransportMapping<UdpAddress> = DefaultUdpTransportMapping()
            transport.listen()
            Log.d(TAG, "创建目标地址对象")
            // 创建目标地址对象
            val comtarget = CommunityTarget()
            comtarget.community = OctetString(community)
            comtarget.version = snmpVersion
            Log.d(TAG, "-address: $ipAddress / $port  ")
            val inetAddress = InetAddress.getByName(ipAddress)
            comtarget.address = UdpAddress(inetAddress, port)
            comtarget.retries = retryCount
            comtarget.timeout = timeoutMillisecond;
            Log.d(TAG, "创建PDU对象")
            val pdu = PDU()
            val oid = OID(oidCmd)
            pdu.add(VariableBinding(oid))
            pdu.type = PDU.GETNEXT
            snmp = Snmp(transport)
            Log.d(TAG, "发送请求给代理");
            // 发送PDU
            val response = snmp.send(pdu, comtarget)
            //流程代理响应
            if (response != null) {
                // 提取响应PDU(如果超时response为null)
                val responsePDU = response.response
                // 提取代理用于发送响应的地址:
                val peerAddress: Address = response.peerAddress
                Log.d(TAG, "peerAddress $peerAddress")
                if (responsePDU != null) {
                    val errorStatus = responsePDU.errorStatus
                    val errorIndex = responsePDU.errorIndex
                    val errorStatusText = responsePDU.errorStatusText
                    if (errorStatus == PDU.noError) {
                        Log.d(TAG, "SNMP GET 返回的完整数据 " + responsePDU.variableBindings)
                        val variable: Variable = responsePDU.getVariable(oid)
                        val value = variable.toString()
                        responseListener?.invoke(ResponseState(true, value))
                        Log.d(TAG, "返回的字符串 $value")
                    } else {
                        Log.d(TAG, "Error: Request Failed")
                        Log.d(TAG, "Error Status = $errorStatus")
                        Log.d(TAG, "Error Index = $errorIndex")
                        Log.d(TAG, "Error Status Text = $errorStatusText")
                        val errorMessage =
                            "SNMP请求错误 Error Status = $errorStatus Error Index = $errorIndex Error Status Text = $errorStatusText"
                        responseListener?.invoke(
                            ResponseState(
                                false,
                                exception = SnmpException(errorMessage)
                            )
                        )
                    }
                } else {
                    Log.d(TAG, "SNMP响应PDU为空")
                    responseListener?.invoke(
                        ResponseState(
                            false,
                            exception = SnmpException("SNMP响应PDU为空")
                        )
                    )
                }
            } else {
                Log.d(TAG, "SNMP请求超时")
                responseListener?.invoke(
                    ResponseState(
                        false,
                        exception = SnmpException("SNMP请求超时")
                    )
                )
            }
        } catch (e: Exception) {
            e.fillInStackTrace()
            responseListener?.invoke(
                ResponseState(
                    false,
                    exception = SnmpException(e.javaClass.simpleName + (e.message ?: "未知异常"), e)
                )
            )
        } finally {
            //释放SNMP
            snmp?.close()
        }
    }
}
