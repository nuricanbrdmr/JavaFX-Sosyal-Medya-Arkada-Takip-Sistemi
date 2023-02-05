package proje.arkadastakip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GirisController extends SceneController implements Mesajlar{
    @FXML
    Label nameLabel;
    private String username;
    private String password;
    private String ad;
    private String soyad;
    private int yas;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void KullaniciGiris(String username, String password) throws FileNotFoundException, Hatalar.KullaniciYok, Hatalar.SifreYanlis {
        Girisİslem(username,password); // Login işlem kontrolü yapılıyor.
        GirisBilgi(); // Giriş yapıldığında yazı ile karşılama
    }
    private void GirisBilgi(){
        nameLabel.setText("Hoş Geldin "+ getAd());
    }
    private void Girisİslem(String username,String password) throws FileNotFoundException, Hatalar.KullaniciYok, Hatalar.SifreYanlis {
        File dosya = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\"+ username);
        //Kullanici Adi Kontrol.
        if (dosya.exists()) { // Dosya varsa Kullanıcının TXT dosyasını açıp bilgilerinin çekiyoruz.
            Scanner input = new Scanner(dosya);
            setAd(input.nextLine());
            setSoyad(input.nextLine());
            setYas(input.nextInt());
            input.nextLine();
            setUsername(input.nextLine()); // Kullanıcı adı kontrolu
            setPassword(input.nextLine());  // Kullanıcı adı kontrolu
        } else {
            throw new Hatalar.KullaniciYok(username);
        }
        //Şifre Kontrol
        if (this.getPassword().equals(password)){
            mesaj();
        }else {
            throw new Hatalar.SifreYanlis();
        }

    }
    // Sayfalar
    public void arkadasEkle(ActionEvent e) throws IOException {
        // Tekrardan Giriş anasayfasına gelmek için gönderilen bilgiler
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ArkadasEkle.fxml"));
        root = loader.load();
        ArkadasEkleController arkadas = loader.getController();
        arkadas.DegerAl(getUsername()+".txt",getPassword());
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void rastgeleArkadasEkle(ActionEvent e) throws IOException {
        // Tekrardan Giriş anasayfasına gelmek için gönderilen bilgiler
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RastgeleArkadas.fxml"));
        root = loader.load();
        RastgeleArkadasEkleContoller arkadasR = loader.getController();
        arkadasR.DegerAl(getUsername()+".txt",getPassword());
        arkadasR.KullaniciListesi();
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void arkadasListele(ActionEvent e) throws IOException{
        // Tekrardan Giriş anasayfasına gelmek için gönderilen bilgiler
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("ArkadasListele.fxml"));
        root = loader1.load();
        ArkadasListeleController arkadas = loader1.getController();
        arkadas.ArkadasListele(getUsername()+".txt");
        arkadas.DegerAl(getUsername()+".txt",getPassword());
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void arkadasSil(ActionEvent e) throws IOException{
        // Tekrardan Giriş anasayfasına gelmek için gönderilen bilgiler
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("ArkadasSil.fxml"));
        root = loader2.load();
        ArkadasSilController arkadas = loader2.getController();
        arkadas.DegerAl(getUsername()+".txt",getPassword());
        arkadas.ArkadasListele();
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void anasayfa(ActionEvent e) throws IOException, Hatalar.KullaniciYok, Hatalar.SifreYanlis {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        SceneController mesaj = new GirisController(); // Polymorphism
        mesaj.mesaj(alert);

        if (alert.showAndWait().get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("anasayfa.fxml"));
            stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void mesaj(Alert alert) {
        alert.setTitle("Çıkış");
        alert.setHeaderText("Hesaptan Çıkış Yapıyorsunuz?");
        alert.setContentText("");
    }
    @Override
    public void mesaj() {
        System.out.println("Başarılı Giriş.");
    }

    //Get - Set
    public String getUsername() {
        return username;
    }

    public void setUsername(String kadi){
        this.username = kadi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String sifre) {
        this.password = sifre;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public int getYas() {
        return yas;
    }

    public void setYas(int yas) {
        this.yas = yas;
    }


}
