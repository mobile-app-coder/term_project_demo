#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <string.h>
#include <arpa/inet.h>
#include <jni.h>

#define PORT 8080

// JNI function to connect to the server and return the server IP as a jstring if successful
extern "C" JNIEXPORT jstring JNICALL
Java_BankClient_connectToServer(JNIEnv* env, jobject obj, jstring ip, jint port) {
    const char* ipAddress = env->GetStringUTFChars(ip, 0);

    int sock = 0;
    struct sockaddr_in serv_addr;

    printf("CREATING CLIENT SOCKET .....\n");
    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        printf("\n Socket creation error \n");
        env->ReleaseStringUTFChars(ip, ipAddress);
        return NULL;
    }

    printf("DEFINING CLIENT SOCKET FAMILY, ADDRESS & PORT .....\n");
    memset(&serv_addr, '0', sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(port);

    if (inet_pton(AF_INET, ipAddress, &serv_addr.sin_addr) <= 0) {
        printf("\nInvalid address/ Address not supported \n");
        env->ReleaseStringUTFChars(ip, ipAddress);
        return NULL;
    }

    printf("CLIENT CONNECTING ON PORT %d TO COMMUNICATE WITH SERVER.....\n", port);
    if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
        printf("\nConnection Failed \n");
        env->ReleaseStringUTFChars(ip, ipAddress);
        return NULL;
    }

    printf("Connected to the server at IP: %s\n", ipAddress);
    env->ReleaseStringUTFChars(ip, ipAddress);

    // Return the IP address of the server as a jstring
    return env->NewStringUTF(inet_ntoa(serv_addr.sin_addr));
}

// Additional JNI functions for sending and receiving messages
extern "C" JNIEXPORT void JNICALL
Java_BankClient_sendMessage(JNIEnv* env, jobject obj, jint socket, jstring message) {
    const char* msg = env->GetStringUTFChars(message, 0);
    send(socket, msg, strlen(msg), 0);
    env->ReleaseStringUTFChars(message, msg);
}

extern "C" JNIEXPORT jstring JNICALL
Java_BankClient_receiveMessage(JNIEnv* env, jobject obj, jint socket) {
    char buffer[1024] = {0};
    int valread = read(socket, buffer, 1024);
    if (valread < 0) {
        return env->NewStringUTF("Error reading from socket");
    }
    return env->NewStringUTF(buffer);
}

extern "C" JNIEXPORT void JNICALL
Java_BankClient_disconnect(JNIEnv* env, jobject obj, jint socket) {
    close(socket);
}
