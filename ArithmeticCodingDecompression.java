import java.math.BigDecimal;
import java.math.BigInteger;
import static java.lang.Integer.valueOf;

public class ArithmeticCodingDecompression {

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

    public static String Decompress(char[] u_String, BigDecimal[] lFArr, BigDecimal theNumber, int tot_char) {
        BigDecimal stValue = new BigDecimal(0);             BigDecimal endValue = new BigDecimal(1);
        BigDecimal[] lFArr1 = lFArr;        char[] str = new char[tot_char];
//        System.out.println(Arrays.toString(lFArr));
//        System.out.println(lFArr[lFArr.length-1]);
        for(int i = 0; i < tot_char; i++) {
//            System.out.println(Arrays.toString(lFArr));
            for(int j = 0; j < lFArr.length; j++) {
                if(theNumber.compareTo(lFArr[j]) < 0) {
                    if(j == 0) {
                        endValue = lFArr[j];
                    } else if (j == lFArr.length - 1) {
                        stValue = lFArr[j-1];
                    }
                    else {
                        stValue = lFArr[j-1];
                        endValue = lFArr[j];
                    }
                    str[i] = u_String[j];
                    lFArr = LineFractionArr(lFArr1, stValue, endValue);
                    break;
                }
            }
        }
        return new String(str);
    }

    public static String UTF8toBigD(String str3) {

        int numof0 = 0; char ch = str3.charAt(0);

        for(int i = 2; i < str3.length(); i++) {
            if(str3.charAt(i) == '.') {
                numof0 = Integer.parseInt(str3.substring(2, i));
                str3 = str3.substring(i+1);
                break;
            }
        }
//        int[] intArr = new int[str3.length()];
        char[] charArr = str3.toCharArray();    byte[] byArr = new byte[str3.length()];  int i = 0;
        for(char cha : charArr ) {
//            System.out.println("ek " + cha);
//            intArr[i] = (int) cha;
//            byArr[i] = (byte) intArr[i];
            byArr[i] = (byte) cha;
            i++;

        }
//        System.out.println(charArr);
//        System.out.println(Arrays.toString(intArr));
//        System.out.println(Arrays.toString(byArr));
        BigInteger bid = new BigInteger(byArr);
//        System.out.println("result: " + bid);
        StringBuilder sb = new StringBuilder(); sb.append(ch);  sb.append('.');
        for(i = 0; i < numof0; i++) {
            sb.append('0');
        }
        sb.append(bid);
//        System.out.println(bid);

        str3 = sb.toString();
//        System.out.println("lov " + str3);
        return str3;
    }

    public static String ArithCodingDecompress(String str) {
        String str1 = "", str2 = "", str3;     BigDecimal compDecimalPoint;    int tot_char = 0, i = 0, j = 0;
        BigDecimal[] freqArr, lineFractionArr;  char[] ch;

        for(i = 0; i < str.length(); i++) {                 // extracting str1 and converting it to char[].
            if(str.charAt(i) == ',') {
                if (str.charAt(i + 1) == ',') {
                    if (str.charAt(i + 2) == ',') {
                        j = i + 1;
                        str1 = str.substring(0, j);
                        break;
                    }
                    j = i;
                    str1 = str.substring(0, j);
                    break;
                }
            }
        }
        ch = str1.toCharArray();

        for(i = j+2; i < str.length(); i++) {               // extracting str2.
            if(str.charAt(i) == ',') {
                if (str.charAt(i + 1) == ',') {
                    str2 = str.substring(j+2, i);
                    break;
                }
            }
        }   int y = i;

        int count = 0;                              // creating repArr of size (no.of int in str2)
        for(i = 0; i < str2.length(); i++) {
            if(str2.charAt(i) == ' ')
                count++;
        }
        int[] repArr = new int[count];

        j = 0; int k = 0;
        for(i = 0; i < str2.length(); i++) {        // converting str2 to int[]
            if(str2.charAt(i) == ' ') {
                repArr[k] = valueOf(str2.substring(j, i));
                k++; j = i+1;
            }
        }

        str3 = str.substring(y+2);      // extracting str3 and converting to BigDecimal.
        str3 = UTF8toBigD(str3);
        compDecimalPoint = new BigDecimal(str3);
//        System.out.println(compDecimalPoint);

        for (int p : repArr) {      // Calculating total no of char in the secret msg
            tot_char += p;
        }
//        System.out.println("totchar " + tot_char);

        freqArr = FreqArray(repArr);
        lineFractionArr = IniFractionArr(freqArr);
//        System.out.println(Arrays.toString(lineFractionArr));
        return Decompress(ch, lineFractionArr, compDecimalPoint, tot_char);
    }
}