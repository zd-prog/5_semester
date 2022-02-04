package by.ziziko;

import android.util.Log;

import java.io.File;
import java.io.IOException;

public class FileWork {

    static boolean ExistBase(String fname, File path)
    {
        boolean rc = false;
        File f = new File(path, fname);
        if (rc = f.exists())
            Log.d("Log_02", "Файл " + fname + " существует");
        else
            Log.d("Log_02", "Файл " + fname + " не найден");
        return rc;
    }
    static File CreateFile(String fname, File path)
    {
        File f = new File(path, fname);
        try
        {
            f.createNewFile();
            Log.d("Log_02", "Файл " + fname + "создан");
        }
        catch (IOException e)
        {
            Log.d("Log_02", "Файл " + fname + " не создан");
        }
        return f;
    }
}
