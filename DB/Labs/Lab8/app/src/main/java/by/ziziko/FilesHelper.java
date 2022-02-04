package by.ziziko;

import android.util.Log;

import java.io.File;

public class FilesHelper {
    static boolean ExistBase(File f)
    {
        boolean rc = false;
        if (rc = f.exists())
            Log.d("Log_02", "Файл " + f.getName() + "существует");
        else
            Log.d("Log_02", "Файл " + f.getName() + "не найден");
        return rc;
    }
}
