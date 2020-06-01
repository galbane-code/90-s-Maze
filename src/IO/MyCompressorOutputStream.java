package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;


public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream outputStream)
    {
        this.out = outputStream;
    }

    @Override
    public void write(byte[] b) throws IOException
    {
        /**
         * a byte array that will be used to store the compressed data.
         * the main compression idea - turning 8 bytes of data (values 1 or 0 only except the first 24 bytes that represent the maze
         * size, entry and goal) into 1 byte data
         * compression rate is about 16 times smaller than the original (depends on how big is the maze - because of the fixed data)
         * @param b
         * @throws IOException
         */

        ArrayList<Byte> ByteArrList = new ArrayList<Byte>();

        for ( int i=0 ; i < 24; i++)
        {
            ByteArrList.add(b[i]);
        }

        //changes the values of the "Start" and "End" to zero for later use.
        for(int g = 24; g < b.length; g++)
        {
            if( b[g] == 69 || b[g] == 83 )
            {
                b[g] = 0;
            }
        }

        /**
         * data for createSmallData function
         */
        byte[] compressBy16 = Arrays.copyOfRange(b,24 , b.length);
        byte[] fixedInfo = Arrays.copyOfRange(b,0 , 24);

        byte[] colSizeBytes = Arrays.copyOfRange(b, 4, 8);
        int colSizeInt = ByteBuffer.wrap(colSizeBytes).getInt();

        compressBy16 = createSmallData(compressBy16, colSizeInt);

        b = new byte[fixedInfo.length + compressBy16.length];

        System.arraycopy(fixedInfo, 0, b, 0, fixedInfo.length);
        System.arraycopy(compressBy16, 0, b, fixedInfo.length,compressBy16.length);
        ///////////////////////////////////////////////////////////////////////////////////

        int current_index = 24; // the index from which we begin the insertion to the compressed byte array.
        int lengthRemain = b.length - 24; // the length remained to compress
        int finalInt = lengthRemain % 8; // an indication int. evaluates the size of "the last cut" (between 0 to 8)
        ByteArrList.add((byte)finalInt); // writes the finalInt to the outputStream. later use in the decompression part.

        while(lengthRemain > 0) {
            /**
             * conversion of 8 bytes (or less) with values 0 or 1 to 1 byte (values -128 to 127)
             */
            if (lengthRemain >= 8)
            {
                String current_string = convert(b, current_index , current_index + 8);
                byte toAdd = (byte) (Integer.parseInt(current_string, 2)); // change the binary string into int and cast to byte
                ByteArrList.add(toAdd);
                current_index += 8;
                lengthRemain -= 8;
            }

            else
            {
                String current_string = convert(b, current_index, current_index + lengthRemain );
                byte toAdd = (byte) (Integer.parseInt(current_string, 2)); // change the binary string into int and cast to byte
                ByteArrList.add(toAdd);
                lengthRemain -= lengthRemain;
            }
        }

        /**
         * copy the ArrayList into an array. the new array will be pointed by array b.
         * write the b array to the outputStream.
         */
        byte[] newarr = new byte[ByteArrList.size()];
        for(int i = 0; i < newarr.length; i++)
        {
            newarr[i] = ByteArrList.get(i);

        }

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
        /**
         * the function receives a byte arr and two indices of the arr and returns the "slice" as a string
         * @param arr
         * @param index
         * @param endindex
         * @return String toReturn
         */

        String toReturn = "";
        for(int i = index; i < endindex ; i++ )
        {
           char b = (char)(arr[i]+ 48);
           toReturn = toReturn + b;
        }

      return toReturn;
    }

    /**
     * The function takes out the relevant data only.
     * the idea was to extract the paths from each position, without considering
     * the extra int rows we have add between each positions row.
     * that is because of the reason that the int data is not relevant to the searchable maze.
     * the extra int rows were useful only for the visualization of the maze
     * @param mazeToCompress
     * @param rowlen
     * @return
     */
    private byte[] createSmallData(byte[] mazeToCompress, int rowlen)
    {

        ArrayList<Byte> mazeArrList = new ArrayList<Byte>();
        boolean isRow = true;

        for(int i = 0; i < mazeToCompress.length; i++)
        {
            if(isRow)
            {
                for(int j = 0; j < rowlen; j++)
                {
                    if( j % 2 == 0)
                    {
                        i++;
                        continue;
                    }

                    else
                    {
                        mazeArrList.add(mazeToCompress[i]);
                        i++;
                    }
                }

                i--;
                isRow = false;
            }
            else
            {
                for(int j = 0; j < rowlen; j++)
                {
                    if( j % 2 == 0)
                    {
                        mazeArrList.add(mazeToCompress[i]);
                        i++;
                    }
                    else
                    {
                        i++;
                        continue;
                    }
                }
                i--;
                isRow = true;
            }
        }

        byte [] arrToReturn = new byte[mazeArrList.size()];

        for(int h = 0; h < mazeArrList.size(); h++)
        {
            arrToReturn[h] = mazeArrList.get(h);
        }

        return arrToReturn;
    }
}
