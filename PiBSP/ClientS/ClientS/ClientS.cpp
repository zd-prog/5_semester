#define _WINSOCK_DEPRECATED_NO_WARNINGS

#include <string>
#include <iostream>
#include "Winsock2.h" 
#pragma comment(lib, "WS2_32.lib") 
#include "Error.h"
#include <ctime>

using namespace std;

SOCKET cC;
bool GetServerByName(char* name, char* call, struct sockaddr* from, int* flen)
{
	hostent* server;
	int optval = 1;
	if ((cC = socket(AF_INET, SOCK_DGRAM, NULL)) == INVALID_SOCKET)
		throw SetErrorMsgText("socket: ", WSAGetLastError());

	if (setsockopt(cC, SOL_SOCKET, SO_BROADCAST, (char*)&optval, sizeof(int)) == SOCKET_ERROR)
		throw SetErrorMsgText("opt: ", WSAGetLastError());
	server = gethostbyname(name);
	if (!(server))
		throw SetErrorMsgText("Сервер не найден.", 0);
	else
		cout << "Host: " << server;

	SOCKADDR_IN addr;
	addr.sin_family = AF_INET;
	addr.sin_port = htons(2000);
	addr.sin_addr.s_addr = inet_addr(inet_ntoa(*(in_addr*)(server->h_addr)));

	if (sendto(cC, call, strlen(call) + 1, NULL, (sockaddr*)&addr, sizeof(addr)) == SOCKET_ERROR)
		throw SetErrorMsgText("sendto: ", WSAGetLastError());

	char ibuf[50];
	if (recvfrom(cC, ibuf, sizeof(ibuf), NULL, from, flen) == SOCKET_ERROR)
	{
		if (WSAGetLastError() == WSAETIMEDOUT)
			return false;
		else
			throw SetErrorMsgText("recvfrom: ", WSAGetLastError());
	}

	cout << ibuf << endl;

	if (closesocket(cC) == SOCKET_ERROR)
		throw SetErrorMsgText("closesocket: ", WSAGetLastError());
	return true;
}
int main()
{
	setlocale(LC_CTYPE, "rus");

	WSADATA wsaData;

	char hostname[] = "LAPTOP-5FEF06FG";
	char call[] = "Hello";
	try
	{
		if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)
			throw SetErrorMsgText("Startup: ", WSAGetLastError());

		SOCKADDR_IN from;
		memset(&from, 0, sizeof(from));
		int lfrom = sizeof(from);
		GetServerByName(hostname, call, (sockaddr*)&from, &lfrom);
		cout << "Сокет сервера: " << inet_ntoa(from.sin_addr) << ":" << htons(from.sin_port) << endl;

		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("Cleanup: ", WSAGetLastError());
	}
	catch (string errorMsgText)
	{
		cout << "WSAGetLastError: " << errorMsgText << endl;
	}
	system("pause");
}

