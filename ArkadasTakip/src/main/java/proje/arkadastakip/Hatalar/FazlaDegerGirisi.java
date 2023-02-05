package Hatalar;

public class FazlaDegerGirisi extends Exception{
    public FazlaDegerGirisi(String metin){
        super("14 karakterden yüksek değer girilemez! Girilen Kullanıcı adı uzunluğu: "+metin.length());
    }
}
