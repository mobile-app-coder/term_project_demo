object BankClient {
    init {
        //System.setProperty("java.library.path", "${projectDir}/c_code")
        System.setProperty("java.library.path", "/home/shahriyor/IdeaProjects/term_project_demo/c_code")

        System.load("/home/shahriyor/IdeaProjects/term_project_demo/c_code/libbank_client.so")
    }

    external fun connectToServer(ip: String, port: Int): Int
    external fun sendMessage(socket: Int, message: String)
    external fun receiveMessage(socket: Int): String?
    external fun disconnect(socket: Int)
}
