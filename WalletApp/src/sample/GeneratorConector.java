package sample;

public class GeneratorConector {  // Save as HelloJNI.java
    static {
        // System.loadLibrary("SHA256_Algorythm");
        System.loadLibrary("Generator"); // Load native library Generator.dll (Windows) or Generator.so (Unixes)
        //  at runtime
    }

    // Declare an instance native method sayHello() which receives no parameter and returns void

    public static native long Mine(String block, int number_of_zeros);

  // static String str = "123<NONCE>{}waihdiuwahiheuygfsufushfsuhuyfhsuyfhseuyfheuyfsuyfsyfhseuyfgseuyfgseuyfgseuygfsueygfusygfseuy";

    // Test Driver
/*    public static void main(String[] args) {
	System.out.println(new GeneratorConector().Mine(str,3));
    }
*/
}
