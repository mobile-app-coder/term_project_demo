#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <string.h>
#include <arpa/inet.h>
#include <jni.h>

JNIEXPORT jint JNICALL Java_network_ClientKt_connectToServerC(JNIEnv* env, jobject obj, jstring ip, jint port) {
    const char* ipAddress = (*env)->GetStringUTFChars(env, ip, 0);

    int sock = 0;
    struct sockaddr_in serv_addr;

    printf("CREATING CLIENT SOCKET .....\n");
    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        printf("\nSocket creation error\n");
        (*env)->ReleaseStringUTFChars(env, ip, ipAddress);
        return -1;  // Return error code
    }

    printf("DEFINING CLIENT SOCKET FAMILY, ADDRESS & PORT .....\n");
    memset(&serv_addr, '0', sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(port);

    if (inet_pton(AF_INET, ipAddress, &serv_addr.sin_addr) <= 0) {
        printf("\nInvalid address / Address not supported\n");
        (*env)->ReleaseStringUTFChars(env, ip, ipAddress);
        return -1;  // Return error code
    }

    printf("CLIENT CONNECTING ON PORT %d TO COMMUNICATE WITH SERVER.....\n", port);
    if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
        printf("\nConnection Failed\n");
        (*env)->ReleaseStringUTFChars(env, ip, ipAddress);
        return -1;  // Return error code
    }

    printf("Connected to the server at IP: %s\n", ipAddress);
    (*env)->ReleaseStringUTFChars(env, ip, ipAddress);

    return sock;  // Return socket descriptor
}

JNIEXPORT void JNICALL Java_network_ClientKt_sendMessageC(JNIEnv* env, jobject obj, jint socket, jstring message) {
    const char* msg = (*env)->GetStringUTFChars(env, message, 0);

    // Send message to server
    int sendResult = send(socket, msg, strlen(msg), 0);
    if (sendResult < 0) {
        printf("Send failed!\n");
    } else {
        printf("Message sent: %s\n", msg);
    }

    (*env)->ReleaseStringUTFChars(env, message, msg);
}

// Receive a message from the server
JNIEXPORT jstring JNICALL Java_network_ClientKt_receiveMessageC(JNIEnv* env, jobject obj, jint socket) {
    char buffer[4096] = {0};  // Buffer to store the received message
    int valread = recv(socket, buffer, sizeof(buffer) - 1, 0);  // Receive message from the socket

    if (valread < 0) {
        printf("Failed to receive message.\n");
        return (*env)->NewStringUTF(env, "Error: Failed to receive message.");
    } else if (valread == 0) {
        printf("Server disconnected.\n");
        return (*env)->NewStringUTF(env, "Server disconnected.");
    }

    buffer[valread] = '\0';  // Null-terminate the message
    printf("Message received: %s\n", buffer);
    return (*env)->NewStringUTF(env, buffer);  // Return the message to the Java side
}

// Disconnect from the server
JNIEXPORT void JNICALL Java_network_ClientKt_disconnectC(JNIEnv* env, jobject obj, jint socket) {
    if (close(socket) == 0) {
        printf("Socket closed successfully.\n");
    } else {
        printf("Failed to close the socket.\n");
    }
}
