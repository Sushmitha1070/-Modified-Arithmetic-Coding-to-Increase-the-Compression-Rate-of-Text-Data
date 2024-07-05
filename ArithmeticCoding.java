import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class ArithmeticCoding {

    public String compress(String str) throws DataFormatException, UnsupportedEncodingException {
        return ArithmeticCodingCompression.ArithCodingCompress(str);
    }
    public String decompress(String str) throws DataFormatException, UnsupportedEncodingException {
        return ArithmeticCodingDecompression.ArithCodingDecompress(str);

    }
    static public void main(String[] args) throws Exception
    {
        Scanner scan = new Scanner(System.in);
        ArithmeticCoding ac = new ArithmeticCoding();
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.println("Enter the text to be compressed: ");
        String orig_str = scan.nextLine();
        System.out.println("It's Length: " + orig_str.length());
        System.out.println("It's binary length: " + ArithmeticCodingCompression.ByteArrToOneBiString(orig_str.getBytes()).length());
        System.out.println("--------------------------------------------------------------------------------------------------------------------");

        String comp_res = ac.compress(orig_str);
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.println("Compressed result:\n" + comp_res);
        System.out.println("It's Length: " + comp_res.length());
        System.out.println("It's binary length: " + ArithmeticCodingCompression.ByteArrToOneBiString(comp_res.getBytes()).length());
        System.out.println("--------------------------------------------------------------------------------------------------------------------");

        String decomp_res = ac.decompress(comp_res);
        System.out.println("Decompressed result: ");
        System.out.println(decomp_res);
        System.out.println("Length: " + decomp_res.length());
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
    }
}