#define _WINSOCK_DEPRECATED_NO_WARNINGS
#include <iostream>
#include "ErrorsH.h"


int main()
{
	setlocale(LC_ALL, "Russian");
	SOCKET sS;
	WSADATA wsaData;

	try
	{
		if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)
			throw SetErrorMsgText("Startup: ", WSAGetLastError());
		SOCKET cC;
		if ((cC = socket(AF_INET, SOCK_DGRAM, NULL)) == INVALID_SOCKET)
			throw  SetErrorMsgText("socket:", WSAGetLastError());

		SOCKADDR_IN serv;
		serv.sin_family = AF_INET;
		serv.sin_port = htons(2000);
		serv.sin_addr.s_addr = inet_addr("127.0.0.1");

		char obuf[50] = "HI"; 
		int lobuf = 0, libuf = 0;
		if ((lobuf = sendto(cC, obuf, strlen(obuf) + 1, NULL,
			(sockaddr*)&serv, sizeof(serv))) == SOCKET_ERROR)
			throw SetErrorMsgText("recv:", WSAGetLastError());

		char ibuf[55];
		int lb = 0; //количество принятых байт
		int ls = sizeof(serv);
		int n;
		clock_t start, end;
		char obuf1[1];

		cout << "Введите количество сообщений" << endl;
		cin >> n;
		start = clock();
		if (n != 0)
		{
			*obuf1 = (char)n;
			if (lobuf = sendto(cC, obuf1, sizeof(obuf1) + 2, NULL, (sockaddr*)&serv, sizeof(serv)) == SOCKET_ERROR)
				throw SetErrorMsgText("recv:", WSAGetLastError());
			for (int i = 0; i < n; i++)
			{
				if (lobuf = sendto(cC, obuf, sizeof(obuf) + 5, NULL, (sockaddr*)&serv, sizeof(serv)) == SOCKET_ERROR)
					throw SetErrorMsgText("recv:", WSAGetLastError());
				Sleep(200);

				if (libuf = recvfrom(cC, ibuf, strlen(ibuf) + 5, NULL, (sockaddr*)&serv, &ls) == SOCKET_ERROR)
					throw SetErrorMsgText("recv:", WSAGetLastError());
				Sleep(100);
				cout << ibuf << " " << i + 2 << endl;
			}
		}
		end = clock();
		cout << end - start;
		if (closesocket(cC) == SOCKET_ERROR)
			throw SetErrorMsgText("closesocket: ", WSAGetLastError());
		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("Cleanup: ", WSAGetLastError());
	}
	catch (string errorMsgText)
	{
		cout << endl << errorMsgText;
	}
}