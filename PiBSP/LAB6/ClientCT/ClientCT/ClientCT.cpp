#include "stdafx.h"
#include "windows.h"
#include <string>
#include "iostream"
using namespace std;

string GetErrorPipe(int code)
{
	string msgText;

	switch (code)
	{
	case WSAEINTR:				 msgText = "Func breaked\n";						  break;
	case WSAEACCES:				 msgText = "Accept broken\n";						  break;
	case WSAEFAULT:				 msgText = "Wrong addr\n";								  break;
	case WSAEINVAL:				 msgText = "Argument error\n";							  break;
	case WSAEMFILE:				 msgText = "Too much files\n";				  break;
	case WSAEWOULDBLOCK:		 msgText = "Now resurce is not avaible\n";					  break;
	case WSAEINPROGRESS:		 msgText = "Operation is processing\n";				  break;
	case WSAEALREADY: 			 msgText = "Opreration is working\n";					  break;
	case WSAENOTSOCK:   		 msgText = "Wrong socket\n";						  break;
	case WSAEDESTADDRREQ:		 msgText = "Need addr location\n";				  break;
	case WSAEMSGSIZE:  			 msgText = "Too long message\n";				      break;
	case WSAEPROTOTYPE:			 msgText = "Wrong protocol type for socket\n";		  break;
	case WSAENOPROTOOPT:		 msgText = "Error in the protocol option\n";					  break;
	case WSAEPROTONOSUPPORT:	 msgText = "Unsupported protocol\n";					  break;
	case WSAESOCKTNOSUPPORT:	 msgText = "Wrong socket type\n";				  break;
	case WSAEOPNOTSUPP:			 msgText = "Unsupported operation\n";					  break;
	case WSAEPFNOSUPPORT:		 msgText = "Unsupported protocol type\n";			  break;
	case WSAEAFNOSUPPORT:		 msgText = "Unsupported addr type by protocol\n";	  break;
	case WSAEADDRINUSE:			 msgText = "Addr in using yet\n";						  break;
	case WSAEADDRNOTAVAIL:		 msgText = "You cannot use this addr\n";	  break;
	case WSAENETDOWN:			 msgText = "Net is unplugged\n";								  break;
	case WSAENETUNREACH:		 msgText = "Net is not avaible\n";							  break;
	case WSAENETRESET:			 msgText = "Net connection is broken\n";					  break;
	case WSAECONNABORTED:		 msgText = "Connection broken by program\n";						  break;
	case WSAECONNRESET:			 msgText = "Connection is restored\n";							  break;
	case WSAENOBUFS:			 msgText = "Not enough memory for buffer\n";				  break;
	case WSAEISCONN:			 msgText = "Socket is plugged\n";							  break;
	case WSAENOTCONN:			 msgText = "Socket is unplugged\n";							  break;
	case WSAESHUTDOWN:			 msgText = "Connot start send: socket is done work\n";  break;
	case WSAETIMEDOUT:			 msgText = "Time is ended\n";		  break;
	case WSAECONNREFUSED:		 msgText = "Connection is broken\n";						  break;
	case WSAEHOSTDOWN:			 msgText = "Host is not avaible\n";			  break;
	case WSAEHOSTUNREACH:		 msgText = "Isn't addr for host\n";						  break;
	case WSAEPROCLIM:			 msgText = "Too much process\n";						  break;
	case WSASYSNOTREADY:		 msgText = "Net is not avaible\n";							  break;
	case WSAVERNOTSUPPORTED:	 msgText = "This version is not avaible\n";					  break;
	case WSANOTINITIALISED:		 msgText = "Inicialisation of WS2_32.DLL is broken\n";		  break;
	case WSAEDISCON:			 msgText = "Disconnecting\n";						  break;
	case WSATYPE_NOT_FOUND:		 msgText = "Class not found\n";								  break;
	case WSAHOST_NOT_FOUND:		 msgText = "Host not found\n";								  break;
	case WSATRY_AGAIN:			 msgText = "Non authorizen host is not found\n";			  break;
	case WSANO_RECOVERY:		 msgText = "Unkown error\n";						  break;
	case WSANO_DATA:			 msgText = "Type not found\n";				  break;
	case WSAEINVALIDPROCTABLE:	 msgText = "Wrong service\n";							  break;
	case WSAEINVALIDPROVIDER:	 msgText = "Service version error\n";						  break;
	case WSAEPROVIDERFAILEDINIT: msgText = "Cannot initiolize service\n";			  break;
	case WSASYSCALLFAILURE:		 msgText = "Stop system call\n";		  break;
	default:					 msgText = "Error\n";										  break;
	};

	return msgText;
};

string SetErrorPipe(string msgText, int code)
{
	return msgText + GetErrorPipe(code);
};

void main()
{
	try
	{
		char rbuf[50];
		char wbuf[50] = "Client CallNamedPipe";
		DWORD rb = sizeof(rbuf);
		DWORD wb = sizeof(wbuf);

		for (int i = 0; i < 10; i++)
		{
			char inum[6];
			sprintf_s(inum, "%d", i + 1);
			char buf[sizeof(wbuf) + sizeof(inum)];
			strcpy_s(buf, wbuf);
			strcat_s(buf, inum);
			wb = sizeof(wbuf);

			if (!CallNamedPipe(L"\\\\.\\pipe\\Tube", buf, wb, rbuf, rb, &rb, 20))
				throw SetErrorPipe("CallNamedPipe: ", GetLastError());

			cout << rbuf << endl;
		}

		char chr[] = " ";
		DWORD lchr = sizeof(chr);

		if (!CallNamedPipe(L"\\\\.\\pipe\\Tube", chr, lchr, rbuf, rb, &rb, 20))
			throw SetErrorPipe("CallNamedPipe: ", GetLastError());
	}
	catch (string e)
	{
		cout << e << endl;
	}
	catch (...)
	{
		cout << "Error" << endl;
	}
}

