package com.olymp;

        import java.lang.StringBuilder;
        import java.lang.Math;



public class PhysicalLayer
{
    private StringBuilder codedBuff;
    private StringBuilder bitBuff;

    String encode( String message )
    {
        toBitBuff( message );
        applyEncoding( );
        return codedBuff.toString( );
    }

    void toBitBuff( String original )
    {
        int limit = original.length();
        int tempVal;
        bitBuff = new StringBuilder( limit * 8 );
        byte[] bytes = original.getBytes();

        for ( byte b : bytes )
        {
            tempVal = b;
            for ( int i = 0; i < 8; i++ )
            {
                bitBuff.append( ( tempVal & 128 ) == 0 ? '0' : '1' );
                tempVal <<= 1;
            }
        }
        System.out.printf( "Entered bits = %s\n", bitBuff.toString() );
    }

    void applyEncoding( )
    {
        int cbInd = 0;
        int limit = bitBuff.length( );
        int size = limit * 5 / 4 - 4;
        codedBuff = new StringBuilder( size );
        prepBuff( );
        for ( int ind = 0; ind < limit; ind += 4 )
        {
            if ( bitBuff.charAt( ind ) == '0' )
                if ( bitBuff.charAt( ind + 1 ) == '0' )
                    if ( bitBuff.charAt( ind + 2 ) == '0' )
                        if ( bitBuff.charAt( ind + 3 ) == '0' )
                            codedBuff.replace( cbInd, cbInd+4, "11110" ); // 0000
                        else
                            codedBuff.replace( cbInd, cbInd+4, "01001" ); // 0001
                    else
                    if ( bitBuff.charAt( ind + 3 )== '0' )
                        codedBuff.replace( cbInd, cbInd+4, "10100" ); // 0010
                    else
                        codedBuff.replace( cbInd, cbInd+4, "10101" ); // 0011
                else
                if ( bitBuff.charAt( ind + 2 ) == '0' )
                    if ( bitBuff.charAt( ind + 3 ) == '0' )
                        codedBuff.replace( cbInd, cbInd+4, "01010" ); // 0100
                    else
                        codedBuff.replace( cbInd, cbInd+4, "01011" ); // 0101
                else
                if ( bitBuff.charAt( ind + 3 ) == '0' )
                    codedBuff.replace( cbInd, cbInd+4, "01110" ); // 0110
                else
                    codedBuff.replace( cbInd, cbInd+4, "01111" ); // 0111
            else
            if ( bitBuff.charAt( ind + 1 ) == '0' )
                if ( bitBuff.charAt( ind + 2 ) == '0' )
                    if ( bitBuff.charAt( ind + 3 ) == '0' )
                        codedBuff.replace( cbInd, cbInd+4, "10010" ); // 1000
                    else
                        codedBuff.replace( cbInd, cbInd+4, "10011" ); // 1001
                else
                if ( bitBuff.charAt( ind + 3 ) == '0' )
                    codedBuff.replace( cbInd, cbInd+4, "10110" ); // 1010
                else
                    codedBuff.replace( cbInd, cbInd+4, "10111" ); // 1011
            else
            if ( bitBuff.charAt( ind + 2 ) == '0' )
                if ( bitBuff.charAt( ind + 3 ) == '0' )
                    codedBuff.replace( cbInd, cbInd+4, "11010" ); // 1100
                else
                    codedBuff.replace( cbInd, cbInd+4, "11011" ); // 1101
            else
            if ( bitBuff.charAt( ind + 3 ) == '0' )
                codedBuff.replace( cbInd, cbInd+4, "11100" ); // 1110
            else
                codedBuff.replace( cbInd, cbInd+4, "11101" ); // 1111
            cbInd += 5;
        }
    }

    void prepBuff( )
    {
        int limit = codedBuff.length();
        for ( int nn = 0; nn < limit; nn++ )
            codedBuff.append( "0" );
    }


    String decode(String fourB5B)
    {
        String[] str=splitInParts(fourB5B,8);
        StringBuilder text= new StringBuilder( );

        for(int a=0;a<str.length;a++)
            text.append( decoder(str[a]) );
        return text.toString( );
    }

    public String fiveB4B(String textE)
    {
        String[] parts=splitInParts(textE,5);
        int size=parts.length;
        String[] decode= new String[size];
        String newString="";

        for(int a=0;a<parts.length;a++)
            decode[a]=fiveB4BD(parts[a]);

        for(int a=0;a<parts.length;a++)
            newString = newString+decode[a];

        return newString;
    }

    private String fiveB4BD(String s)
    {
        if(s.equals("11110"))
            return "0000";

        else if(s.equals("01001"))
            return "0001";

        else if(s.equals("10100"))
            return "0010";

        else if(s.equals("10101"))
            return "0011";

        else if(s.equals("01010"))
            return "0100";

        else if(s.equals("01011"))
            return "0101";

        else if(s.equals("01110"))
            return "0110";

        else if(s.equals("01111"))
            return "0111";

        else if(s.equals("10010"))
            return "1000";

        else if(s.equals("10011"))
            return "1001";

        else if(s.equals("10110"))
            return "1010";

        else if(s.equals("10111"))
            return "1011";

        else if(s.equals("11010"))
            return "1100";

        else if(s.equals("11011"))
            return "1101";

        else if(s.equals("11100"))
            return "1110";

        else if(s.equals("11101"))
            return "1111";
        else
            return "fail";
    }

    public String[] splitInParts(String s, int partLength)
    {
        int len = s.length();

        // Number of parts
        int nparts = (len + partLength - 1) / partLength;
        String parts[] = new String[nparts];

        // Break into parts
        int offset= 0;
        int i = 0;
        while (i < nparts){
            parts[i] = s.substring(offset, Math.min(offset + partLength, len));
            offset += partLength;
            i++;
        }

        return parts;
    }

    public String decoder(String str)
    {
        int charCode = Integer.parseInt(str, 2);
        String str2 = new Character((char)charCode).toString();
        return str2;
    }
	
	/*	unused method
    private boolean[] toBinary(int number, int base) {
        final boolean[] ret = new boolean[base];
        for (int i = 0; i < base; i++) {
           ret[base - 1 - i] = (1 << i & number) != 0;
        }
        return ret;
     } */
}