package IO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;


public class MyDecompressorInputStream extends InputStream {

    private InputStream in;
    public MyDecompressorInputStream(InputStream inputStream)
    {
        this.in = inputStream;
    }


    @Override
    public int read(byte[] b) throws IOException
    {
    /**
     * decompresses the array by the same rules we compressed it.
     * first 24 bytes will be copied "as is". they represent the size of the maze, entry and goal.
     * the 25th byte represent the size of the last "cut" of the maze. (size 0 to 8)
     * 1 byte now represents an 8 byte array (values 0 or 1)
     * using 0xFF we convert the signed value of the byte into an unsigned value (0 to 255)
     *
     */

        int totalArrbSize = in.read(b); // reads the compressed array size into a variable.

        ArrayList<Byte> byteArrayList = new ArrayList<Byte>();

        int i = 0;
        while(i < 24 )
        {
            byteArrayList.add(b[i]);
            i++;
        }


        byte count; // temp byte to insert into the byte arrays lists after the decompression.
        byte [] sizeOfEightArr = new byte [8];
        for(i = 25; i < totalArrbSize; i++)
        {
            /** in this for loop we decompress the byte into the binary 10101 of the maze */

            int j = 0; // the index to insert to the array list.

            count = b[i]; // initial the temp byte

            int byteToInt = count & 0xFF; // signed to unsigned int

            String byteString = Integer.toBinaryString(byteToInt); // this function change the byte into a binary string.

            byte[] byteArr = byteString.getBytes(); // change the binary string into a byte arr of binary.


            if (i == totalArrbSize - 1 && b[24] > 0)    //b[24] is the size of the last array we compressed ( it is less then 8)
            {
                int finalInt = b[24]; // the size of the last part to add to complete the binary maze

                int [] finalArr = new int [finalInt]; // the byte arr we add to the arrays list

                if(byteArr.length < finalArr.length) // checks if we need to add zeros on the left and add if needed to the final arr.
                {
                    for (int k = 0; k < finalArr.length; k++)
                    {
                        if (k < byteArr.length)
                            finalArr[finalArr.length - k - 1] = byteArr[byteArr.length - k - 1];
                        else
                        {
                            finalArr[finalArr.length - k - 1] = 48;
                        }
                    }
                }
                else // if it does not need to add zeros it just add the binary as is to the final arr
                {
                    for(int h = 0; h < finalArr.length; h++)
                    {
                        finalArr[h] = byteArr[h];
                    }
                }
                // after that we add the binary into the arrays list.
                while (j < finalArr.length)
                {
                    byteArrayList.add((byte) (finalArr[j] - ((byte) 48)));
                    j++;
                }
                break;
            }

            else  // else is if the process is still in the middle of the maze and didnt reached the end.
            {
                if(byteArr.length < 8) //checks if we need to add zeros on the left and add if needed to the final arr.
                {
                    for (int k = 0; k < 8; k++)
                    {
                        if (k < byteArr.length)
                            sizeOfEightArr[sizeOfEightArr.length - k - 1] = byteArr[byteArr.length - k - 1];
                        else
                        {
                            sizeOfEightArr[sizeOfEightArr.length - k - 1] = 48;
                        }
                    }
                }

                // if it does not need to add zeros it just add the binary as is to the final arr
                else
                {
                    for(int h = 0; h < 8; h++)
                    {
                        sizeOfEightArr[h] = byteArr[h];
                    }
                }

                // after that we add the binary into the array list.
                while (j < sizeOfEightArr.length)
                {
                    byteArrayList.add((byte) (sizeOfEightArr[j] - ((byte) 48)));
                    j++;
                }
            }
        }

        /**
         * copy the ArrayList into an array. the new array will be copied into array b.
         */
        byte [] toAssign = new byte[byteArrayList.size()];
        for (int k = 0; k < byteArrayList.size(); k++)
        {
            toAssign[k] = byteArrayList.get(k);
        }


        for(int h = 0; h < toAssign.length; h++)
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
