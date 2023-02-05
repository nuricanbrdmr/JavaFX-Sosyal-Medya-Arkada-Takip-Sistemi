package Hatalar;

public class RakamBulundu extends Exception{
    public RakamBulundu(String metin){
        super("Ad veya Soyad kısmında rakam olmamalıdır! Girilen metin: "+metin);
    }
}
