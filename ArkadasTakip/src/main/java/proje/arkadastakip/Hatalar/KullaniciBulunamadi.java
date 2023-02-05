package Hatalar;

public class KullaniciBulunamadi extends Exception{
    public KullaniciBulunamadi(int metin){
        super("Geçersiz Değer girildi.");
    }
}
