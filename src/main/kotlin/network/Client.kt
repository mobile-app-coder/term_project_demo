package network


external fun sendMessageC(socket: Int, message: String)
external fun receiveMessageC(socket: Int): String?
external fun disconnectC(socket: Int)
external fun connectToServerC(ip: String, port: Int): Int

object Network {
    var socket = 0;

    init {
        System.setProperty("java.library.path", "/home/shahriyor/IdeaProjects/term_project_demo/src/main/kotlin/c_code")
        System.load("/home/shahriyor/IdeaProjects/term_project_demo/src/main/kotlin/c_code/client.so")
        val ip = "127.0.0.1"
        val port = 9999
        socket = connectToServerC(ip, port)
    }


    fun getMessage(): String? {
        return receiveMessageC(socket);
    }

    fun sendMessage(message: String) {
        sendMessageC(socket, message)
    }

    fun disconnect() {
        disconnectC(socket)
    }
}
