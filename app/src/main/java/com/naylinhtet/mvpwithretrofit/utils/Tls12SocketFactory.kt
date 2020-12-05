package com.naylinhtet.mvpwithretrofit.utils

import java.net.InetAddress
import java.net.Socket
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

/**
 * Created by
 */
class Tls12SocketFactory(base: SSLSocketFactory): SSLSocketFactory() {

    private val TLS_V12_ONLY = arrayOf("TLSv1.1","TLSv1.2")
    internal  var delegate: SSLSocketFactory = base


    override fun getDefaultCipherSuites(): Array<String> {
        return delegate.defaultCipherSuites
    }

    override fun createSocket(s: Socket?, host: String?, port: Int, autoClose: Boolean): Socket {
        return patch(delegate.createSocket(s, host, port, autoClose))
    }

    override fun createSocket(host: String?, port: Int): Socket {
        return patch(delegate.createSocket(host, port))
    }

    override fun createSocket(host: String?, port: Int, localHost: InetAddress?, localPort: Int): Socket {
        return patch(delegate.createSocket(host, port, localHost, localPort))
    }

    override fun createSocket(host: InetAddress?, port: Int): Socket {
        return patch(delegate.createSocket(host, port))
    }

    override fun createSocket(address: InetAddress?, port: Int, localAddress: InetAddress?, localPort: Int): Socket {
        return patch(delegate.createSocket(address, port, localAddress, localPort))
    }

    override fun getSupportedCipherSuites(): Array<String> {
        return delegate.supportedCipherSuites
    }

    private fun patch(s: Socket): Socket {
        if (s is SSLSocket) {
            s.enabledProtocols = TLS_V12_ONLY
        }
        return s
    }
}