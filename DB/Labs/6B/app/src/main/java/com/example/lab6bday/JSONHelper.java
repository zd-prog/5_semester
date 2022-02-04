package com.example.lab6bday;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JSONHelper {

    static List<Contact> importFromJSON(Context context, String FILE_NAME) {

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = new FileInputStream(FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return  dataItems.getContacts();
        }
        catch (IOException ex){
            Log.d("Тута проблем", ex.getMessage());
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static class DataItems {
        private List<Contact> contacts;

        List<Contact> getContacts() {
            return contacts;
        }
        void setContacts(List<Contact> contacts) {
            this.contacts = contacts;
        }
    }
}

