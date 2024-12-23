package network

import kotlinx.coroutines.*


external fun sendMessageC(socket: Int, message: String)
external fun disconnectC(socket: Int)
external fun receiveMessageC(socket: Int): String?
external fun connectToServerC(ip: String, port: Int): Int

object Network {
    var socket = 0;

    init {
        System.setProperty("java.library.path", "/home/shahriyor/IdeaProjects/term_project_demo/src/main/kotlin/c_code")
        System.load("/home/shahriyor/IdeaProjects/term_project_demo/src/main/kotlin/c_code/client.so")
        val ip = "127.0.0.1"
        val port = 5000
        //socket = connectToServerC(ip, port)
        CoroutineScope(Dispatchers.IO).launch {
            connect(ip, port)
            delay(100)
            sendMessage("usertype:client:connect")
        }
    }


    suspend fun connect(ip: String, port: Int) = withContext(Dispatchers.IO) {
        socket = connectToServerC(ip, port)
        if (socket < 0) {
            //throw Exception("Failed to connect to server")
        }
    }

    suspend fun sendMessage(message: String) = withContext(Dispatchers.IO) {
        val formattedMessage = "usertype:client:$message"
        println(formattedMessage)
        sendMessageC(socket, formattedMessage)
    }

    suspend fun getMessage(): String? = withContext(Dispatchers.IO) {
        receiveMessageC(socket)
    }

    suspend fun disconnect() = withContext(Dispatchers.IO) {
        disconnectC(socket)
    }
}
