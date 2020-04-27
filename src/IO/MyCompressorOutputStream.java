package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import java.nio.ByteBuffer;
import java.util.Arrays;


public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream outputStream)
    {
        this.out = outputStream;
    }

    @Override
    public void write(byte[] b) throws IOException {

        /** THIS FUNCTION COMPRESS THE BYTE ARR BY CHANGING THE MAZE 8 BINARY TO A BYTE */

        ArrayList<Byte> ByteArrList = new ArrayList<Byte>(); // in this arrays list we insert the bytes that we want to send after the compressor.

        // first we insert the first 24 bytes for the size of the maze and the S and E position indexes.

        for ( int i=0 ; i < 24; i++)
        {
            ByteArrList.add(b[i]);
        }


        // we change the values of the the S and E to zero so they dont interrupt with the compress procedure.

        for(int g = 24; g < b.length; g++)
        {
            if( b[g] == 69 || b[g] == 83 )
            {
                b[g] = 0;
            }
        }

        int current_index = 24; // the index from which we begin to insert the compressed bytes.
        int lengthRemain = b.length - 24; // the length that remain to compress
        int finalInt = lengthRemain % 8; // an int to help us with the size of a "cut" of the bytes if it below 8
        ByteArrList.add((byte)finalInt); // send it to the output file to help us with the decompressed part.

        while(lengthRemain > 0) {

            // in this function we use the convert function that gets a byte arr and two indexes of the arr and return the "slice" as a string
            // check if the there is 8 bits to change into a byte

            if (lengthRemain >= 8)
            {
                String current_string = convert(b, current_index , current_index + 8);
                byte toAdd = (byte) (Integer.parseInt(current_string, 2)); // change the binary string into int and cast to byte
                ByteArrList.add(toAdd);
                current_index += 8;
                lengthRemain -= 8;
            }

            // else we change only the amount of the string to convert

            else
            {
                String current_string = convert(b, current_index, current_index + lengthRemain );
                byte toAdd = (byte) (Integer.parseInt(current_string, 2)); // change the binary string into int and cast to byte
                ByteArrList.add(toAdd);
                lengthRemain -= lengthRemain;
            }
        }

        // we initial the compressed arr we wish to send to the output.

        byte[] newarr = new byte[ByteArrList.size()];

        // insert to the compressed arr from the Arrayslist.

        for(int i = 0; i < newarr.length; i++)
        {
            newarr[i] = ByteArrList.get(i);

        }

        // change the pointer and write out

        b = newarr;
        out.write(b);
    }

        @Override
    public void write(int b) throws IOException
    {
        out.write(b);
    }

    private String convert(byte[] arr,int index,int endindex)
    {
        //function that gets a byte arr and two indexes of the arr and return the "slice" as a string

        String toReturn = "";
        for(int i = index; i < endindex ; i++ )
        {
           char b = (char)(arr[i]+ 48);
           toReturn = toReturn + b;
        }

      return toReturn;
    }
}
