package sample;

import java.io.File;

public class GeneratorConector {  // Save as HelloJNI.java
    static {
        // System.loadLibrary("SHA256_Algorythm");
        //System.loadLibrary("Generator"); // Load native library Generator.dll (Windows) or libGenerator.so (Unixes)
        System.out.println("adding lib");
        //System.out.println(System.getProperty("java.library.path"));
        try{
            System.load("/home/matai/dolar_ankh/GIT/Dolar-AnkhMorpork/WalletApp/out/production/AnkhMorporkDollar/libs/libGeneratorConector.so");
        }catch (UnsatisfiedLinkError e){
            e.printStackTrace();
            System.out.println("łączenie biblioteki");
        }
        System.out.println("added lib");
        //  at runtime
    }

    //

    //public static native long Mine(String block, int number_of_zeros);
    public native long Mine(String block, int number_of_zeros);

}
