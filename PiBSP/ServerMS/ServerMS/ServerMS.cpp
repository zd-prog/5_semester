#include <stdio.h>
#include <tchar.h>
#include <windows.h>
#include <iostream>
#include "time.h"
using namespace std;

int _tmain(int argc, _TCHAR* argv[])
{
	system("cls");
	HANDLE hslot;
	hslot = CreateMailslot(            
									   
		L"\\\\.\\mailslot\\Box",  
		300,                             
		MAILSLOT_WAIT_FOREVER,         
		NULL                           
	);
	if (hslot == INVALID_HANDLE_VALUE)
	{
		std::cout << "SLOT FAIL" << std::endl;
		std::cout << "Press any key to exit" << std::endl;
		std::cin.get();
		return 0;
	}
	std::cout << "Server is waiting" << std::endl;
	int i = 0;
	char rbuf[50];
	DWORD rms;
	HANDLE hMS;
	double t1, t2;
	do
	{
		i++;

		if (!ReadFile(     // ������ ������ �� ����� ��� ���������� ����� / ������ (I / O), ������� � �������, ������������ ����������
			hslot,         // ���������� ����� ��� MailSlot
			rbuf,          // ��������� �� �����, ������� ��������� ������, ��������� �� ����� ��� ����������.
			sizeof(rbuf),  // ����� ���� ��� ������.
			&rms,          // ��������� �� ����������, ������� �������� ���������� ������, ��������� ��� ������������� ����������� ��������� hFile .
			NULL           // ����������� �����
		))
		{
			std::cout << "FAIL" << std::endl;
			CloseHandle(hslot);
			std::cout << "Press any key to exit" << std::endl;
			std::cin.get();
			return 0;
		}
		if (i == 1)
			t1 = clock();

		cout << rbuf << endl;
	} while (true);

	t2 = clock();

	cout << "Time: " << (t2 - t1) / 1000 << " ���." << endl;
	cout << "Message count: " << i - 1 << endl << endl;

	if (!CloseHandle(hslot))
		throw "Error: CloseHandle";
	return 0;
}
