#include <string>
#include "Winsock2.h"
#pragma comment(lib, "WS2_32.lib") 
#pragma once
using namespace std;

string GetErrorMsgText(int code);

string SetErrorMsgText(string msgText, int code);