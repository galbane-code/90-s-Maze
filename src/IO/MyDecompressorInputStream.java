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
    public int read(byte[] b) throws IOException {

        int x = in.read(b);
        ArrayList<Byte> byteArrayList = new ArrayList<Byte>();
        int i = 0;

        while(i < 29 )
        {
            byteArrayList.add(b[i]);
            i++;
        }

        boolean flag = false;
        byte count;
        int eightCounter;
        byte [] sizeOfEightArr = new byte [8];
        byte [] finalSizeArr = Arrays.copyOfRange(b, 25, 29);

        int totalArrbSize = ByteBuffer.wrap(finalSizeArr).getInt();
        for(i = 29; i < totalArrbSize; i++)
        {
            int j = 0;
            count = b[i];
            int byteToInt = count & 0xFF;
            String byteString = Integer.toBinaryString(byteToInt);
            byte[] byteArr = byteString.getBytes();

            if (i == totalArrbSize - 1 && b[24] > 0)//b[24] is the size of the last array we compressed ( it is less then 8)
            {
                int finalInt = b[24];
                int [] finalArr = new int [finalInt];
                if(byteArr.length < finalArr.length)
                {
                    for (int k = 0; k < 8; k++)
                    {
                        if (k < byteArr.length)
                            finalArr[finalArr.length - k - 1] = byteArr[finalArr.length - k - 1];
                        else
                        {
                            finalArr[finalArr.length - k - 1] = 0;
                        }
                    }
                }
                else
                {
                    for(int h = 0; h < finalArr.length; h++)
                    {
                        finalArr[h] = byteArr[h];
                    }
                }
                while (j < finalArr.length)
                {
                    byteArrayList.add((byte) (finalArr[j] - ((byte) 48)));
                    j++;
                }
                break;
            }

            else
            {
                if(byteArr.length < 8)
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
                else
                {
                    for(int h = 0; h < 8; h++)
                    {
                        sizeOfEightArr[h] = byteArr[h];
                    }
                }

                while (j < sizeOfEightArr.length)
                {
                    byteArrayList.add((byte) (sizeOfEightArr[j] - ((byte) 48)));
                    j++;
                }
            }
        }

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

        ////OLD
        /*ArrayList<Byte> byteArrayList = new ArrayList<Byte>();
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

         */
    }






    public int xboxDecomp(byte[] b)
    {
        ArrayList<Byte> byteArrayList = new ArrayList<Byte>();
        int i = 0;

        while(i < 24 )
        {
            byteArrayList.add(b[i]);
            i++;
        }

        boolean flag = false;
        byte count;
        int eightCounter;
        for(i = 24; i < b.length; i++)
        {
            int j = 0;
            count = b[i];
            int byteToInt = count + 0xFF;
            String byteString = Integer.toBinaryString(byteToInt);
            byte[] byteArr = byteString.getBytes();

            while (j < byteArr.length)
            {
                byteArrayList.add((byte) (byteArr[j] - ((byte) 48)));
                j++;
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
