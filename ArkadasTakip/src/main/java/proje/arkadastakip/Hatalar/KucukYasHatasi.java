package Hatalar;

public class KucukYasHatasi extends Exception{
    public KucukYasHatasi(Integer kucukDeger){
        super("18 yaşından küçükler kayıt yapamaz! Girilen yas: "+kucukDeger);
    }

}
