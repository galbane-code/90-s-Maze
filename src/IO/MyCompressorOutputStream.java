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

        int current_index = 24;
        int lengthRemain = b.length - 24;
        int finalInt = lengthRemain % 8;
        ByteArrList.add((byte)finalInt);

        while(lengthRemain > 0) {
            if (lengthRemain >= 8) {
                //byte [] current_eight = Arrays.copyOfRange(b, current_index, current_index + 8);
                String current_string = convert(b, current_index , current_index + 8);
                byte toAdd = (byte) (Integer.parseInt(current_string, 2));
                ByteArrList.add(toAdd);
                current_index += 8;
                lengthRemain -= 8;
            } else {
                //byte[] current_eight = Arrays.copyOfRange(b, current_index, current_index + lengthRemain);
                String current_string = convert(b, current_index, current_index + lengthRemain );
                byte toAdd = (byte) (Integer.parseInt(current_string, 2));
                ByteArrList.add(toAdd);
                ByteArrList.add((byte)lengthRemain);
                lengthRemain -= lengthRemain;
            }
        }
        byte [] totalSize = ByteBuffer.allocate(4).putInt(ByteArrList.size() + 4).array();
        byte[] newarr = new byte[ByteArrList.size() + 4];
        int k = 0;

        for(int i = 0; i < newarr.length; i++)
        {

            if(i > 24 && i < 29)
            {
                newarr[i] = totalSize[k++];
            }
            else if(i >= 29)
            {
                newarr[i] = ByteArrList.get(i - 4);
            }
            else
            {
                newarr[i] = ByteArrList.get(i);
            }
        }

        b = newarr;
        out.write(b);





        /*
        ///////OLD
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

        for(int j = 24; j < b.length-1; j++) {


            if (b[j] == 1) {
                if (j == 24) {
                    ByteArrList.add(Zeros);
                }

                if (Ones == 255) {
                    ByteArrList.add(Ones);
                    ByteArrList.add(Zeros);
                    Ones = 0;
                }

                Ones++;

                if (b[j + 1] == 0) {
                    ByteArrList.add(Ones);
                    Ones = 0;
                }
            } else if (b[j] == 0) {
                if (Zeros == 255) {
                    ByteArrList.add(Zeros);
                    ByteArrList.add(Ones);
                    Zeros = 0;
                }

                Zeros++;

                if (b[j + 1] == 1) {
                    ByteArrList.add(Zeros);
                    Zeros = 0;
                }
            }

            if (j == b.length - 2) {
                if (b[b.length - 1] == 1) {
                    Ones++;
                    ByteArrList.add(Ones);
                } else {
                    Zeros++;
                    ByteArrList.add(Zeros);
                }

            }

        }

        byte[] newarr = new byte[ByteArrList.size()];

        for (int i = 0; i < ByteArrList.size(); i++) {
            newarr[i] = ByteArrList.get(i);
        }

        b = newarr;
        out.write(b);*/


    }

    private String convert(byte[] arr,int index,int endindex)
    {
        String toReturn = "";
        for (int i = index; i < endindex; i++) {
            char b = (char) (arr[i] + 48);
            toReturn = toReturn + b;
        }
        return toReturn;
    }
        @Override
    public void write(int b) throws IOException
    {
        out.write(b);
    }
}
