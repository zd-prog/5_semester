#define _WINSOCK_DEPRECATED_NO_WARNINGS

#pragma warning(disable : 4996);
#include "stdafx.h"
#include <string>
#include <iostream>
#include "Winsock2.h" //��������� WS2_32.dll
#pragma comment(lib, "WS2_32.lib") //������� WS2_32.dll
#include "Error.h"
#include <ctime>

using namespace std;


void main()
{
	setlocale(LC_CTYPE, "rus");

	try
	{
		HANDLE h_NP;

		char rbuf[50];
		char wbuf[50] = "Client";
		DWORD rb = sizeof(rbuf);
		DWORD wb = sizeof(wbuf);

		for (int i = 0; i < 10; i++)
		{
			if ((h_NP = CreateFile(L"\\\\.\\pipe\\Tube", GENERIC_WRITE | GENERIC_READ, FILE_SHARE_WRITE | FILE_SHARE_READ, NULL, OPEN_EXISTING, NULL, NULL)) == INVALID_HANDLE_VALUE)
				throw "Error: CreateFile";

			char inum[6];
			sprintf(inum, "%d", i + 1);
			char buf[sizeof(wbuf) + sizeof(inum)];
			strcpy(buf, wbuf);
			strcat(buf, inum);
			wb = sizeof(wbuf);

			if (!WriteFile(h_NP, buf, sizeof(wbuf), &wb, NULL))
				throw "Error: WriteFile";
			if (!ReadFile(h_NP, rbuf, sizeof(rbuf), &rb, NULL))
				throw "Error: ReadFile";

			cout << rbuf << endl;

			CloseHandle(h_NP);
		}

		char chr[] = " ";

		if ((h_NP = CreateFile(L"\\\\.\\pipe\\Tube", GENERIC_WRITE | GENERIC_READ, FILE_SHARE_WRITE | FILE_SHARE_READ, NULL, OPEN_EXISTING, NULL, NULL)) == INVALID_HANDLE_VALUE)
			throw "Error: CreateFile";
		if (!WriteFile(h_NP, chr, sizeof(chr), &wb, NULL))
			throw "Error: WriteFile";
		if (!ReadFile(h_NP, rbuf, sizeof(rbuf), &rb, NULL))
			throw "Error: ReadFile";

		CloseHandle(h_NP);
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