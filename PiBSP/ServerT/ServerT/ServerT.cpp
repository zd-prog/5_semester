#define _WINSOCK_DEPRECATED_NO_WARNINGS
#define _CRT_SECURE_NO_WARNINGS
#include "Errors.h"


int main()
{
	setlocale(LC_ALL, "Russian");
	/*SOCKET sS;
	try
	{
		if ((sS = socket(AF_INET, SOCK_STREAM, NULL)) == INVALID_SOCKET)
			throw SetErrorMsgText("socket: ", WSAGetLastError());
	}
	catch (string errorMsgText)
	{
		cout << endl << "WSAGetLastErrors: " << errorMsgText;
	}*/

	SOCKET sS;
	WSADATA wsaData;
	try
	{
		if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0)
			throw SetErrorMsgText("Startup: ", WSAGetLastError());
		while (true)
		{
			if ((sS = socket(AF_INET, SOCK_STREAM, NULL)) == INVALID_SOCKET)
				throw SetErrorMsgText("socket: ", WSAGetLastError());

			SOCKADDR_IN serv;
			serv.sin_family = AF_INET;
			serv.sin_port = htons(2000);
			serv.sin_addr.s_addr = INADDR_ANY;
			if (bind(sS, (LPSOCKADDR)&serv, sizeof(serv)) == SOCKET_ERROR)
				throw SetErrorMsgText("bind: ", WSAGetLastError());
			if (listen(sS, SOMAXCONN) == SOCKET_ERROR)
				throw SetErrorMsgText("listen: ", WSAGetLastError());

			SOCKET cS;
			SOCKADDR_IN clnt;
			memset(&clnt, 0, sizeof(clnt));
			int lclnt = sizeof(clnt);
			if ((cS = accept(sS, (sockaddr*)&clnt, &lclnt)) == INVALID_SOCKET)
				throw SetErrorMsgText("accept: ", WSAGetLastError());

			cout << inet_ntoa(clnt.sin_addr) << endl;
			cout << htons(clnt.sin_port);

			char ibuf[50],                     //буфер ввода 
				obuf[50] = "sever: принято ";  //буфер вывода
			int  libuf = 0,                    //количество принятых байт
				lobuf = 0;                    //количество отправленных байь 

			if ((lobuf = send(cS, obuf, strlen(obuf) + 1, NULL)) == SOCKET_ERROR)
				throw  SetErrorMsgText("send:", WSAGetLastError());

			while (true)
			{
				if ((libuf = recv(cS, ibuf, sizeof(ibuf), NULL)) == SOCKET_ERROR)
					throw  SetErrorMsgText("recv: ", WSAGetLastError());

				int n = (int)ibuf;
				if (strlen(ibuf) != 0)
				{
					for (int i = 0; i < n; i++)
					{
						if ((libuf = recv(cS, ibuf, sizeof(ibuf), NULL)) == SOCKET_ERROR)
							throw  SetErrorMsgText("recv: ", WSAGetLastError());
						Sleep(100);
						cout << endl << ibuf << " " << i + 1;

						if ((lobuf = send(cS, ibuf, strlen(ibuf) + 5, NULL)) == SOCKET_ERROR)
							throw  SetErrorMsgText("send:", WSAGetLastError());
						Sleep(200);
					}
				}
				else
					break;
				
			}
			

			if (closesocket(sS) == SOCKET_ERROR)
				throw SetErrorMsgText("closesocket: ", WSAGetLastError());
		}
		
		if (WSACleanup() == SOCKET_ERROR)
			throw SetErrorMsgText("Cleanup: ", WSAGetLastError());
	}
	catch (string errorMsgText)
	{
		cout << endl << errorMsgText;
	}
}
