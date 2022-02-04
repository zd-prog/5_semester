#define _WINSOCK_DEPRECATED_NO_WARNINGS

#include <string>
#include <iostream>
#include "Winsock2.h" 
#pragma comment(lib, "WS2_32.lib")
#include "Error.h"
#include <ctime>

using namespace std;

SOCKET sS;


bool GetRequestFromClient(char* name, sockaddr* from, int* flen)
{
	char ibuf[50];
	while (true)
	{
		if (recvfrom(sS, ibuf, sizeof(ibuf), NULL, from, flen) == SOCKET_ERROR)
		{
			if (WSAGetLastError() == WSAETIMEDOUT)
				return false;
			else
				throw SetErrorMsgText("recvfrom: ", WSAGetLastError());
		}
		if (strcmp(ibuf, name) == 0)
		{
			cout << "Сокет клиента: " << inet_ntoa(((SOCKADDR_IN*)from)->sin_addr) << ":" << htons(((SOCKADDR_IN*)from)->sin_port) << endl;
			return true;
		}
		else return false;
	}
}

void PutAnswerToClient(char* name, sockaddr* to, int* lto)
{
	if ((sendto(sS, name, strlen(name) + 1, NULL, to, *lto)) == SOCKET_ERROR)
	{
		throw  SetErrorMsgText("send:", WSAGetLastError());
	}
}

int main()
{
	setlocale(LC_CTYPE, "rus");

	WSADATA wsaData;
	hostent* server;
	hostent* client;

	char host[] = "LAPTOP-5FEF06FG";
	char name[] = "Hello";
	char hostname[32];
	try
	{
		if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)
			throw SetErrorMsgText("Startup: ", WSAGetLastError());
		if ((sS = socket(AF_INET, SOCK_DGRAM, NULL)) == INVALID_SOCKET)
			throw SetErrorMsgText("Socket: ", WSAGetLastError());

		SOCKADDR_IN serv;
		serv.sin_family = AF_INET;
		serv.sin_port = htons(2000);
		serv.sin_addr.s_addr = INADDR_ANY;
		if (bind(sS, (LPSOCKADDR)&serv, sizeof(serv)) == SOCKET_ERROR)
			throw SetErrorMsgText("bind: ", WSAGetLastError());

		SOCKADDR_IN from;
		memset(&from, 0, sizeof(from));
		int lfrom = sizeof(from);

		gethostname(hostname, sizeof(hostname));
		cout << "Сервер: " << hostname << endl;
		do
		{
			if (GetRequestFromClient(name, (sockaddr*)&from, &lfrom))
			{
				cout << "От клиента: " << gethostbyaddr((char*)&from.sin_addr, sizeof(from.sin_addr), AF_INET)->h_name << endl;
				PutAnswerToClient(host, (sockaddr*)&from, &lfrom);
			}
			else
				cout << "Error" << endl;
		} while (true);

		if (closesocket(sS) == SOCKET_ERROR)
			throw SetErrorMsgText("closesocket: ", WSAGetLastError());
		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("Cleanup: ", WSAGetLastError());
	}
	catch (string errorMsgText)
	{
		cout << "WSAGetLastError: " << errorMsgText << endl;
	}
	system("pause");
}