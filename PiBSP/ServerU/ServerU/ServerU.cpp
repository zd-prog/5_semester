#include <iostream>
#include "Errors.h"

int main()
{
	setlocale(LC_ALL, "Russian");
	try
	{
		SOCKET sS;
		WSADATA wsaData;
		if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)
			throw SetErrorMsgText("Startup: ", WSAGetLastError());
		if ((sS = socket(AF_INET, SOCK_DGRAM, NULL)) == INVALID_SOCKET)
			throw SetErrorMsgText("socket: ", WSAGetLastError());

		SOCKADDR_IN serv;
		serv.sin_family = AF_INET;
		serv.sin_port = htons(2000);
		serv.sin_addr.s_addr = INADDR_ANY;
		if (bind(sS, (LPSOCKADDR)&serv, sizeof(serv)) == SOCKET_ERROR)
			throw SetErrorMsgText("bind: ", WSAGetLastError());

		SOCKADDR_IN clnt;
		memset(&clnt, 0, sizeof(clnt)); 
		int lc = sizeof(clnt);
		char ibuf[55];
		int lb = 0; //количество принятых байт

		while (true)
		{
			int libuf = 0, lobuf = 0;
			if (libuf = recvfrom(sS, ibuf, sizeof(ibuf), NULL, (sockaddr*)&clnt, &lc) == SOCKET_ERROR)
				throw SetErrorMsgText("recv:", WSAGetLastError());
			int n = (int)ibuf;
			if (strlen(ibuf) != 0)
			{
				for (int i = 0; i < n; i++)
				{
					if (libuf = recvfrom(sS, ibuf, sizeof(ibuf) + 5, NULL, (sockaddr*)&clnt, &lc) == SOCKET_ERROR)
						throw SetErrorMsgText("recv:", WSAGetLastError());
					Sleep(100);
					cout << endl << ibuf << " " << i + 1;

					if (lobuf = sendto(sS, ibuf, strlen(ibuf) + 5, NULL, (sockaddr*)&clnt, sizeof(clnt)) == SOCKET_ERROR)
						throw SetErrorMsgText("recv:", WSAGetLastError());
					Sleep(200);
				}
			}
			else
				break;
		}


		if (closesocket(sS) == SOCKET_ERROR)
			throw SetErrorMsgText("closesocket: ", WSAGetLastError());
		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("Cleanup: ", WSAGetLastError());
	}
	catch (string errorMsgText)
	{
		cout << endl << errorMsgText;
	}
}