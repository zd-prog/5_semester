#define _WINSOCK_DEPRECATED_NO_WARNINGS

#include "stdafx.h"
#include <string>
#include <iostream>
#include "Winsock2.h" //заголовок WS2_32.dll
#pragma comment(lib, "WS2_32.lib") //экспорт WS2_32.dll
#include "Error.h"
#include <ctime>

using namespace std;


void main()
{

	cout << "Listening..." << endl;

	try
	{
		HANDLE h_NP;

		char rbuf[50];
		char wbuf[50] = "Serv";
		DWORD rb = sizeof(rbuf);
		DWORD wb = sizeof(wbuf);

		if ((h_NP = CreateNamedPipe(L"\\\\.\\pipe\\Tube", PIPE_ACCESS_DUPLEX, PIPE_TYPE_MESSAGE | PIPE_WAIT, 1, NULL, NULL, INFINITE, NULL)) == INVALID_HANDLE_VALUE)
			throw "Eroor: CreateNamedPipe";

		while (true)
		{
			if (!ConnectNamedPipe(h_NP, NULL))
				throw "Eroor: ConnectNamedPipe";
			if (!ReadFile(h_NP, rbuf, sizeof(rbuf), &rb, NULL))
				throw "Eroor: ReadFile";

			cout << rbuf << endl;

			if (!WriteFile(h_NP, wbuf, sizeof(wbuf), &wb, NULL))
				throw "Eroor: WriteFile";
			if (!DisconnectNamedPipe(h_NP))
				throw "Eroor: DisconnectNamedPipe";
		}
		if (!CloseHandle(h_NP))
			throw "Eroor: CloseHandle";
	}
	catch (string e)
	{
		cout << e << endl;
	}
	catch (...)
	{
		cout << "Error" << endl;
	}
	system("pause");
}