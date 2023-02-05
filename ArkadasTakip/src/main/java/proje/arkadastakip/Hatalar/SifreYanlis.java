package Hatalar;

public class SifreYanlis extends Exception{

    public SifreYanlis(){
        super("Girilen Şifre Bu Kullanıcı ile Uyuşmamaktadır!");
    }
}
