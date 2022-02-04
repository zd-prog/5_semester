package by.ziziko;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Hash {
    File file;

    public Hash(File file) {
        this.file = file;
    }

    public int GetHash(String key)
    {
        int hash;
        hash = Integer.parseInt(key.substring(4,5));
        return hash;
    }

    public void WriteValue(String key, String value)
    {
        while (value.length() < 10)
        {
            value = value + "*";
        }
        int link;
        String str = key + value + "***";
        int position = GetHash(key)*18;

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(position);
            String keyRead = "";
            if (randomAccessFile.length()==0)
                keyRead = "";
            else {
                if (randomAccessFile.readLine() == null) {
                    keyRead = "";
                } else {
                    randomAccessFile.seek(position);
                    int b = randomAccessFile.read();

                    for (int i = 0; i < 5; i++)
                    {
                        keyRead = keyRead + (char)b;

                        b = randomAccessFile.read();
                    }
                    keyRead = keyRead.substring(0, 5);
                    Log.d("Значение keyRead", keyRead);
                }
            }
                if (keyRead == "")
                    {
                        randomAccessFile.seek(position);
                        randomAccessFile.writeBytes(str);
                    }
                    else
                    {
                        if (keyRead.equals(key))
                        {
                            randomAccessFile.seek(position);
                            randomAccessFile.writeBytes(str);
                        }
                        else
                        {
                            if (GetHash(keyRead) == GetHash(key))
                            {
                                position = position + 15;
                                randomAccessFile.seek(position);
                                String LINK = "";
                                while (!LINK.equals("***"))
                                {
                                    for (int i = 0; i < 3; i++)
                                    {
                                        int b = randomAccessFile.read();
                                        LINK += (char)b;
                                    }
                                    if (!LINK.equals("***")) {
                                        position = Integer.parseInt(LINK) + 15;
                                        randomAccessFile.seek(position);
                                        LINK = "";
                                    }
                                }

                                if (file.length() > 180)
                                {
                                    link = (int) file.length();
                                }
                                else
                                {
                                    link = 180;
                                }
                                String s = String.valueOf(link);
                                randomAccessFile.seek(position);
                                for (int i = 0; i < 3; i++)
                                {
                                    randomAccessFile.write(s.charAt(i));
                                }
                                randomAccessFile.seek(link);
                            }
                            else
                            {
                                randomAccessFile.seek(0);
                            }
                            randomAccessFile.writeBytes(str);
                        }
                    }

            randomAccessFile.close();

        } catch (IOException e) {
            Log.d("WriteValue", e.getMessage());
        }
    }

    public String GetValue(String key)
    {
        String value = "";
        int position = GetHash(key)*18;
        String keyRead = "";
        String LINK = "";
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(position);

            while (position != randomAccessFile.length())
            {
                randomAccessFile.seek(position);
                int b = randomAccessFile.read();

                for (int i = 0; i < 5; i ++){
                    keyRead = keyRead + (char)b;

                    b = randomAccessFile.read();
                }
                String valueRead = "";
                if (keyRead.equals(key))
                {
                    randomAccessFile.seek(position+5);
                    b = randomAccessFile.read();

                    for (int i = 0; i < 10; i++)
                    {
                        valueRead = valueRead + (char)b;

                        b = randomAccessFile.read();
                    }
                    if (valueRead.contains("*"))
                        value = valueRead.substring(0, valueRead.indexOf("*"));
                    else
                        value = valueRead;
                    return value;
                }
                else
                {
                    randomAccessFile.seek(position+15);
                    String link = "";
                    for (int i = 0; i < 3; i++)
                    {
                        b = randomAccessFile.read();
                        LINK += (char)b;
                    }

                        if (!LINK.equals("***")) {
                            try {
                                position = Integer.parseInt(LINK);
                            } catch (NumberFormatException e) {
                                return "Ключа нет";
                            }
                            LINK = "";
                        }


                    keyRead = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

}
