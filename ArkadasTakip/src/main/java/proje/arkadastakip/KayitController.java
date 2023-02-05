package proje.arkadastakip;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Hatalar.AyniKullaniciAdi;
import Hatalar.FazlaDegerGirisi;
import Hatalar.KucukYasHatasi;
import Hatalar.RakamBulundu;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;


public class KayitController extends SceneController {
    private String ad;
    private String soyad;
    private int yas;
    private String kullaniciAdi;
    private String sifre;

    public void KayitController(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hata Mesajı");
        alert.setContentText("Bilgi Alanları Boş Bırakılamaz!");
        DialogPane dialogPane = alert.getDialogPane();
        alert.showAndWait();
        System.out.println("Bilgi Alanları Boş Bırakılamaz!");
        //System.out.println("Bilgiler girilmemiştir.");
    }


    public void KayitController(String ad, String soyad, int yas, String kullaniciAdi, String sifre) throws KucukYasHatasi, RakamBulundu, FazlaDegerGirisi, AyniKullaniciAdi, IOException{
        setAd(ad);
        setSoyad(soyad);
        setYas(yas);
        setKullaniciAdi(kullaniciAdi);
        setSifre(sifre);
        KullaniciText();
        System.out.println("Başarılı Kayıt.");
        Platform.setImplicitExit(false);
    }

    public void KullaniciText() throws IOException {
        try {
            File dosya = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\"+this.getKullaniciAdi()+".txt");
            File fileAr = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\Arkadas\\"+ this.getKullaniciAdi()+".txt");
            if (!(fileAr.exists())){
                fileAr.createNewFile();
            }
            if (!(dosya.exists())){
                dosya.createNewFile();
            }
            FileWriter wrt = new FileWriter(dosya);
            wrt.write(getAd()+"\n");
            wrt.write(getSoyad()+"\n");
            wrt.write(getYas()+"\n");
            wrt.write(getKullaniciAdi()+"\n");
            wrt.write(getSifre()+"\n");
            wrt.close();
        }catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata Mesajı");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }


    //Getter / Setter Metotlar
    public String getAd() {
        return ad;
    }

    public void setAd(String ad) throws RakamBulundu {
        // Rakam Kontrolu
        char[] nums = {'1','2','3','4','5','6','7','8','9'};
        for (int i = 0; i<ad.length();i++){
            for (int j = 0; j<nums.length;j++){
                if (ad.charAt(i) == nums[j]){
                    throw new RakamBulundu(ad);
                }
                else {
                    this.ad = ad;
                }
            }
        }
    }


    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) throws RakamBulundu{
        // Rakam Kontrolu
        char[] nums = {'1','2','3','4','5','6','7','8','9'};
        for (int i = 0; i<soyad.length();i++){
            for (int j = 0; j<nums.length;j++){
                if (soyad.charAt(i) == nums[j]){
                    throw new RakamBulundu(soyad);
                }
                else {
                    this.soyad = soyad;
                }
            }
        }
    }

    public int getYas() {
        return yas;
    }

    public void setYas(int yas) throws KucukYasHatasi {
        // Yaş kontrolü
        if (yas >= 18){
            this.yas = yas;
        }else {
            throw new  KucukYasHatasi(yas);
        }
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) throws AyniKullaniciAdi, FazlaDegerGirisi {
        File dosya = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\" + kullaniciAdi + ".txt");
        // Kullanıcı var mı yok mu kontrol
        if (!(dosya.exists())) {
            if (kullaniciAdi.length() > 14) {
                throw new FazlaDegerGirisi(kullaniciAdi);
            }
            this.kullaniciAdi = kullaniciAdi;

        } else {
            throw new AyniKullaniciAdi(kullaniciAdi);
        }

    }
    public String getSifre() {

        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    // Anasayfa Buton geçişi.
    public void anasayfa(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("anasayfa.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
