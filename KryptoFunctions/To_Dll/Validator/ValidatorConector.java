public class ValidatorConector {  // Save as HelloJNI.java
    static {
        // System.loadLibrary("SHA256_Algorythm");
        System.loadLibrary("ValidatorConector"); // Load native library Generator.dll (Windows) or Generator.so (Unixes)
        //  at runtime
    }

    // Declare an instance native method sayHello() which receives no parameter and returns void

    private native boolean Validate(String block, int number_of_zeros);


    // Test Driver
  //  public static void main(String[] args) {
    //}
}
