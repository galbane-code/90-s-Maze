package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.ByteBuffer;


public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream outputStream)
    {
        this.out = outputStream;
    }

    @Override
    public void write(byte[] b) throws IOException {
        //super.write(b);
        ArrayList<Byte> ByteArrList = new ArrayList<Byte>();
        for ( int i=0 ; i < 24; i++)
        {
            ByteArrList.add(b[i]);
        }

        for(int g = 24; g < b.length; g++)
        {
            if( b[g] == 69 || b[g] == 83 )
            {
                b[g] = 0;
            }
        }

        Byte Ones = 0;
        Byte Zeros = 0;

        for(int j = 24; j < b.length-1; j++)
        {
          if(b[j] == 1)
          {
              if(Ones == 255)
              {
                  ByteArrList.add(Ones);
                  ByteArrList.add(Zeros);
                  Ones = 0;
              }

              Ones++;

              if(b[j+1] == 0)
              {
                  ByteArrList.add(Ones);
                  Ones = 0;
              }
          }
          else if(b[j] == 0)
          {
              if(Zeros == 255)
              {
                  ByteArrList.add(Zeros);
                  ByteArrList.add(Ones);
                  Zeros = 0;
              }

              Zeros++;

              if(b[j+1] == 1)
              {
                  ByteArrList.add(Zeros);
                  Zeros = 0;
              }
          }

          if( j == b.length - 2)
          {
              if(b[b.length-1] == 1)
              {
                  Ones++;
                  ByteArrList.add(Ones);
              }
              else
              {
                  Zeros++;
                  ByteArrList.add(Zeros);
              }

          }
        }

        byte[] newarr = new byte[ByteArrList.size()];

        for(int i = 0; i < ByteArrList.size(); i++)
        {
            newarr[i] = ByteArrList.get(i);
        }

        b = newarr;
    }

    @Override
    public void write(int b) throws IOException {

    }
}
