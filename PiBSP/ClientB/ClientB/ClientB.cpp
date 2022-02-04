#define _WINSOCK_DEPRECATED_NO_WARNINGS


#include <string>
#include <iostream>
#include "Winsock2.h"
#pragma comment(lib, "WS2_32.lib") 
#include "Error.h"
#include <ctime>

using namespace std;

SOCKET cC;

bool GetServer(char* call, short port, sockaddr* from, int* flen)
{
	memset(from, 0, sizeof(flen));

	if ((cC = socket(AF_INET, SOCK_DGRAM, NULL)) == INVALID_SOCKET)
	{
		throw  SetErrorMsgText("socket:", WSAGetLastError());
	}

	int optval = 1;
	if (setsockopt(cC, SOL_SOCKET, SO_BROADCAST, (char*)&optval, sizeof(int)) == SOCKET_ERROR)
	{
		throw  SetErrorMsgText("opt:", WSAGetLastError());
	}

	SOCKADDR_IN all;
	all.sin_family = AF_INET;
	all.sin_port = htons(2000);
	all.sin_addr.s_addr = INADDR_BROADCAST;

	if ((sendto(cC, call, strlen(call) + 1, NULL, (sockaddr*)&all, sizeof(all))) == SOCKET_ERROR)
	{
		throw  SetErrorMsgText("sendto:", WSAGetLastError());
	}

	char nameServer[50];
	if ((recvfrom(cC, nameServer, sizeof(nameServer), NULL, from, flen)) == SOCKET_ERROR)
	{
		if (WSAGetLastError() == WSAETIMEDOUT)
		{
			return false;
		}
		else
		{
			throw  SetErrorMsgText("recv:", WSAGetLastError());
		}
	}
	else cout << endl << "Сообщение: " << nameServer << endl;


	SOCKADDR_IN* addr = (SOCKADDR_IN*)&from;
	cout << endl << "Номер порта сервера: " << addr->sin_port;
	cout << endl << "IP сервера: " << inet_ntoa(addr->sin_addr);

	if (strcmp(nameServer, call) != 0)
	{
		cout << endl << "Неправильный позывной сервера." << endl;
		return true;
	}
	else
	{
		cout << endl << "Правильный позывной сервера.";
		return false;
	}

	return true;
}

int main()
{
	setlocale(LC_CTYPE, "rus");

	WSADATA wsaData;

	try
	{
		if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)
		{
			throw  SetErrorMsgText("Startup:", WSAGetLastError());
		}

		char call[] = "HI!";

		SOCKADDR_IN clnt;
		int lc = sizeof(clnt);

		GetServer(call, 2000, (sockaddr*)&clnt, &lc);

		if (closesocket(cC) == SOCKET_ERROR)
		{
			throw  SetErrorMsgText("closesocket:", WSAGetLastError());
		}
		if (WSACleanup() == SOCKET_ERROR)
		{
			throw  SetErrorMsgText("Cleanup:", WSAGetLastError());
		}
	}
	catch (string errorMsgText)
	{
		cout << endl << errorMsgText;
	}

	cout << endl;
	system("pause");
	return 0;
}