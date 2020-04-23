package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MyDecompressorInputStream extends InputStream {

    private InputStream in;
    public MyDecompressorInputStream(InputStream inputStream)
    {
        this.in = inputStream;
    }


    @Override
    public int read(byte[] b) throws IOException {

        int x = in.read(b);

        ArrayList<Byte> byteArrayList = new ArrayList<Byte>();
        int i = 0;

        while(i < 24 )
        {
            byteArrayList.add(b[i]);
            i++;
        }

        boolean flag = false;
        byte count;
        for(i = 24; i < b.length; i++)
        {
            count = b[i];


            if(!flag)
            {
                while (count != 0)
                {
                    byteArrayList.add((byte) 0);
                    count--;
                }
                flag = true;
            }

            else
            {
                while (count != 0)
                {
                    byteArrayList.add((byte) 1);
                    count--;
                }
                flag = false;
            }
        }

        byte [] toAssign = new byte[byteArrayList.size()];
        for (int k = 0; k < byteArrayList.size(); k++)
        {
            toAssign[k] = byteArrayList.get(k);
        }


        for(int h = 0; h < b.length; h++)
        {
            b[h] = toAssign[h];
        }

        return -1;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
