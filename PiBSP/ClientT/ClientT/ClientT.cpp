#define _WINSOCK_DEPRECATED_NO_WARNINGS
#include "ErrorsH.h"
#define _CRT_SECURE_NO_WARNINGS

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
		if ((cC = socket(AF_INET, SOCK_STREAM, NULL)) == INVALID_SOCKET)
			throw  SetErrorMsgText("socket:", WSAGetLastError());

		SOCKADDR_IN serv;
		serv.sin_family = AF_INET;
		serv.sin_port = htons(2000);
		serv.sin_addr.s_addr = inet_addr("127.0.0.1");
		if ((connect(cC, (sockaddr*)&serv, sizeof(serv))) == SOCKET_ERROR)
			throw SetErrorMsgText("connect: ", WSAGetLastError());

		char ibuf[50],                     //буфер ввода 
			obuf[50] = "Hello from Client",//буфер вывода
			obuf1[1];
		int  libuf = 0,                    //количество прин€тых байт
			lobuf = 0;                    //количество отправленных байь 
		int n;
		clock_t start, end;

		if ((libuf = recv(cC, ibuf, sizeof(ibuf), NULL)) == SOCKET_ERROR)
			throw  SetErrorMsgText("recv: ", WSAGetLastError());
		cout << "¬ведите количество сообщений" << endl;
		cin >> n;
		start = clock();
		if (n != 0)
		{
			*obuf1 = (char)n;
			if ((lobuf = send(cC, obuf1, strlen(obuf1) + 2, NULL)) == SOCKET_ERROR)
				throw  SetErrorMsgText("send:", WSAGetLastError());
			for (int i = 0; i < n; i++)
			{

				if ((lobuf = send(cC, obuf, strlen(obuf) + 2, NULL)) == SOCKET_ERROR)
					throw  SetErrorMsgText("send:", WSAGetLastError());
				Sleep(200);

				if ((libuf = recv(cC, ibuf, sizeof(ibuf), NULL)) == SOCKET_ERROR)
					throw  SetErrorMsgText("recv: ", WSAGetLastError());

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
