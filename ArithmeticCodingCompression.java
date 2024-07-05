import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;

public class ArithmeticCodingCompression {

    public static char[] UniqueCharString(String str) {       // Returns an array of unique characters from the given string.
        char[] s = str.toCharArray();   int k, q = 1;
        for(int i = 1; i < str.length(); i++) {
            k = 0;
            for(int j = 0; j < i; j++) {
                if(s[i] == s[j]) {
                    k = 1; break;
                }
            }
            if ( k == 0 ) {
                s[q] = s[i];
                q++;
            }
        }

        s = Arrays.copyOf(s, q);    // resizing s[str.length] to s[q], where q is the no.of unique char in the string.
        return s;
    }

    public static String ByteToBiString(byte by) {          // byte number to 8 bit binary String
        String str = Integer.toBinaryString(by);
        String str1;
        if(by < 0) {
            str1 = str.substring(24,32);
        }
        else {
            int n = 8 - str.length();
            char[] ch = new char[8];
            for(int l = 0; l < n; l++) {
                ch[l] = '0';
            }
            for(int l = n; l < 8; l++) {
                ch[l] = str.charAt((l+str.length())-8);
            }
            str1 = new String(ch);
        }
        return str1;
    }

    public static String ByteArrToOneBiString(byte[] by) {
        String str= ByteToBiString(by[0]);
        for(int i = 1; i < by.length; i++)
            str = str.concat( ByteToBiString( by[i] ) );
        return str;
    }

    public static int[] UniqueCharRepArray(String str, char[] arr) {       //To return rep array of UniqueCharacterString.

        int[] rep_arr = new int[arr.length];      int rep_count = 0;
        for (int j = 0; j < arr.length; j++) {
            for(int k = 0; k < str.length(); k++) {
                if(arr[j] == str.charAt(k))
                {
                    rep_count++;
                }
            }
            rep_arr[j] = rep_count;
            rep_count = 0;
        }
        return rep_arr;
    }

    public static BigDecimal[] FreqArray(int[] arr) {   // returns an array of (frequency of occurrence of each letter b/w [0,1]) in the text.
        int sum = 0;    BigDecimal[] freqArray = new BigDecimal[arr.length];
        for (int j : arr)
            sum += j;
        for (int j = 0; j < arr.length; j++)
            freqArray[j] = BigDecimal.valueOf((double) arr[j]/(double) sum);
        return freqArray;
    }

    public static BigDecimal[] IniFractionArr(BigDecimal[] arr) {
        for(int i = 1; i < arr.length; i++) {
            arr[i] = arr[i].add(arr[i-1]);
        }
        return arr;
    }

    public static BigDecimal[] LineFractionArr(BigDecimal[] arr, BigDecimal stValue, BigDecimal endValue) {
        BigDecimal lineLength = endValue.subtract(stValue);      BigDecimal[] newArr = new BigDecimal[arr.length];
        for(int i = 0; i < arr.length; i++) {
            newArr[i] = stValue.add(arr[i].multiply(lineLength));
        }
        return newArr;
    }

    public static BigDecimal FinalOptimisedFloatingPoint(BigDecimal a, BigDecimal b) {   //  returns the mid-value of two bigDecimal with min no.of floating point digits.
        int i, min_pre = Math.min(a.precision(), b.precision());    //min of (precision of start value, precision of end value)
        for (i = 1; i <= min_pre; i++) {
            if (!a.setScale(i, RoundingMode.FLOOR).equals(b.setScale(i, RoundingMode.FLOOR))) {
                a = a.setScale(i+1, RoundingMode.FLOOR);
                b = b.setScale(i+1, RoundingMode.FLOOR);
                BigDecimal c = a.add(b);
                c = c.divide(BigDecimal.valueOf(2), i+1, RoundingMode.FLOOR);
//                System.out.println("hi " + c);
                return c;
            }
        }
        return a;
    }

    public static String Compress(String str, char[] u, BigDecimal[] cLFArr) {
        BigDecimal stValue = new BigDecimal(0);             BigDecimal endValue = new BigDecimal(1);
        char[] s = str.toCharArray();                           BigDecimal[] iLFArr = cLFArr;

        for (char value : s) {
            for (int j = 0; j < u.length; j++) {
                if (u[j] == value) {
                    if (j == 0) {
                        endValue = cLFArr[0];
                    } else if (j == u.length - 1) {
                        stValue = cLFArr[j - 1];
                    } else {
                        stValue = cLFArr[j - 1];
                        endValue = cLFArr[j];
                    }
                    cLFArr = LineFractionArr(iLFArr, stValue, endValue);
                }
            }
        }
        stValue = stValue.stripTrailingZeros();     endValue = endValue.stripTrailingZeros();
//        System.out.println("The Start value and end value are: " + stValue + " " + endValue);
        BigDecimal c = FinalOptimisedFloatingPoint(stValue, endValue);
        c = c.stripTrailingZeros();
//        c.to
        return c.toString();
    }

    public static String ByteArrToIntArrToString(byte[] byArr) {
        char[] chArr = new char[byArr.length];
//        int[] intArr = new int[byArr.length];
        int i = 0, j = 0;
        for(byte num : byArr){
            if(num < 0) {
//                intArr[i] = num + 256;
                j = num + 256;
            }
            else {
//                intArr[i] = num;
                j = num;
            }
            chArr[i] = (char) j;
            i += 1;
        }
//        System.out.println(Arrays.toString(byArr));
//        System.out.println(Arrays.toString(intArr));
        return new String(chArr);
    }

    public static String BigDStrToUTF8CompStr(String str) {
        int i = 2, count = 0;   String utf8_final_str = "";

        char ch = str.charAt(0);

        for( ; i < str.length(); i++) {
            if(str.charAt(i) == '0') {
                count++;
            }
            else {
                String num_str = str.substring(count + 2);
                BigInteger num = new BigInteger(num_str);
//                System.out.println("heh " + num);
//                System.out.println("heh " + Arrays.toString(num.toByteArray()));
                byte[] byArr = num.toByteArray();
//                System.out.println(num.toString(2) + " length: " + num.toString(2).length());
//                System.out.println("pika " + Arrays.toString(byArr));
                String utf8_str = ByteArrToIntArrToString(byArr);
//                System.out.println("heh " + utf8_str);
                utf8_final_str = ch + "." + Integer.toString(count) + "." + utf8_str;
//                System.out.println(utf8_final_str);
//                utf8_final_str = utf8_final_str + utf8_str;
//                System.out.println("utf8_str: " + utf8_str);
//                System.out.println("utf8_final_str: " + utf8_final_str);
                break;
            }

        }
        return utf8_final_str;
    }

    public static void PrintCompressionRate(double o, double a, double b, double c, double d){
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.println("BigDecimal Compressed data is " + String.format("%.2f", (c/o)*100) + "% of Original data");
        System.out.println("utf8 Compressed Data is " + String.format("%.2f", (d/o)*100) + "% of Original Data");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.println("Big Decimal to utf8 compression rate: " + String.format("%.2f", ((a-b)/a)*100) + "%");
//        System.out.println("Further Compressed Data is "+ String.format("%.2f", (d/c)*100) + "% of Compressed Data");
//        System.out.println("--------------------------------------------------------------------------------------------");

//        System.out.println("utf8 Compressed Data is "+ String.format("%.2f", (d/o)*100) + "% of Original Data");
    }

    public static String ArithCodingCompress(String str) {
        String str1, str2, str3;     int[] uniqueCharRepArray;      char[] uniqueCharString;

        uniqueCharString = UniqueCharString(str);
        str1 = new String(uniqueCharString);

        uniqueCharRepArray = UniqueCharRepArray(str, uniqueCharString);
        str2 = "";
        for(int element : uniqueCharRepArray) {
            str2 = str2.concat(element + " ");
        }

        str3 = Compress(  str, uniqueCharString, IniFractionArr( FreqArray(uniqueCharRepArray) )  );
//        System.out.println(str3 + "  || " + str3.length());
        int big_d_length1 = str3.length();
        String res1 = str1 + ",," + str2 + ",," + str3;
//        System.out.println(res1 + "  || " + res1.length());
        int com_str_len1 = res1.length();

        System.out.println("Compressed floating point:-");
        System.out.println(str3);
        System.out.println("It's length: " + str3.length());

        String hh = str1 + ",," + str2 + ",," + str3;
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("compressed result without utf8 conversion: ");
        System.out.println(hh);
        System.out.println("It's length: " + hh.length());
        System.out.println("It's binary length: " + ByteArrToOneBiString(hh.getBytes()).length());
//        System.out.println("--------------------------------------------------------------------------------------");

//        System.out.println("------------------------------------------------------------------------------------------------------");
//        System.out.println("BigDecimal Compressed data:- ");
//        System.out.println(str3);
        str3 = BigDStrToUTF8CompStr(str3);
//        System.out.println(str3 + "  || " + str3.length());
        int big_d_length2 = str3.length();
        String res2 = str1 + ",," + str2 + ",," + str3;
//        System.out.println(res2 + "  || " + res2.length());
        int com_str_len2 = res2.length();

        PrintCompressionRate(str.length(), big_d_length1, big_d_length2, com_str_len1, com_str_len2);

        return str1 + ",," + str2 + ",," + str3;
    }
}