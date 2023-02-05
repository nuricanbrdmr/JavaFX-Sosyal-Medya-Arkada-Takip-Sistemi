package Hatalar;

public class KullaniciYok extends Exception{
    public KullaniciYok(String metin){
        super("Bu kullanıcı Adı Bulunmamaktadır! Girilen Kullanıcı adı: "+metin);
    }
}
