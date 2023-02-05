package Hatalar;

public class AyniKullaniciAdi extends Exception{
    public AyniKullaniciAdi(String metin){
        super("Bu kullanıcı Adı mevcut bulunmaktadır! Girilen Kullanıcı adı: "+metin);
    }
}
