#pragma once

#include <iostream>
using namespace std;
#include <string>

#include "Winsock2.h" 
#pragma comment(lib, "WS2_32.lib") 

string GetErrorMsgText(int code);
string SetErrorMsgText(string msgText, int code);