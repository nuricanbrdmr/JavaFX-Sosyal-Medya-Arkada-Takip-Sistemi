package proje.arkadastakip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class SifreUnutController {
    @FXML
    private TextField kullanıcıConTextFieald;
    @FXML
    private TextField sifreCon1TextFieald;
    @FXML
    private TextField sifreCon2TextFieald;
    private String username;
    Stage stage;
    Scene scene;
    Parent root;
    // Kullanıcı Bilgilerini alma.
    public void DegerAl(String username){
        this.setUsername(username);
    }
    // Kullanıcı Adı var mı yok mu kontrolü.
    public void KullaniciKontrol(ActionEvent event) throws Hatalar.KullaniciYok, IOException {
        if (kullanıcıConTextFieald.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata Mesajı");
            alert.setContentText("Bilgi Alanları Boş Bırakılamaz!");
            alert.showAndWait();
            System.out.println("Bilgi Alanları Boş Bırakılamaz!");
        } else {
            setUsername(kullanıcıConTextFieald.getText() + ".txt");
            try {
                File dosya = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\" + getUsername());
                //Kullanici Adi Kontrol.
                if (dosya.exists()) { // Dosya varsa Kullanıcının TXT dosyasını açıp bilgilerinin çekiyoruz.
                    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("SifreUnuttum2.fxml"));
                    root = loader2.load();
                    SifreUnutController deger = loader2.getController();
                    deger.DegerAl(username);
                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    throw new Hatalar.KullaniciYok(getUsername());
                }
            }catch (Hatalar.KullaniciYok ky){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata Mesajı");
                alert.setContentText(ky.getMessage());
                alert.showAndWait();
                System.out.println(ky.getMessage());
            }

        }
    }
    public void SifreDegistir(ActionEvent event) throws IOException {
        if (sifreCon1TextFieald.getText().trim().isEmpty() || sifreCon2TextFieald.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata Mesajı");
            alert.setContentText("Bilgi Alanları Boş Bırakılamaz!");
            alert.showAndWait();
            System.out.println("Bilgi Alanları Boş Bırakılamaz!");
        } else {
            if (sifreCon1TextFieald.getText().equals(sifreCon2TextFieald.getText())){
                File fold=new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\"+ getUsername());
                Scanner oldDAta = new Scanner(fold);
                ArrayList<String> oldVeri = new ArrayList<>();
                while (oldDAta.hasNext()){
                    oldVeri.add(oldDAta.nextLine());
                }
                fold.delete();
                File dosya = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\" + getUsername());
                FileWriter file = new FileWriter("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\" + getUsername(),false);
                for (int i = 0; i < oldVeri.size();i++){
                    if (i == oldVeri.size()-1){
                        file.write(sifreCon1TextFieald.getText());// Yeni Şifre yazdırma
                    }else {
                        file.write(oldVeri.get(i)+"\n");
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bilgi");
                alert.setContentText("Şifreniz Değiştirildi.");
                alert.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("giris.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                file.close();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata Mesajı");
                alert.setContentText("Girilen Şifre Aynı Değil!");
                alert.showAndWait();
                System.out.println("Bilgi Alanları Boş Bırakılamaz!");
            }

        }
    }
    // Sayfalar
    public void anasayfa(ActionEvent e) throws IOException, Hatalar.KullaniciYok, Hatalar.SifreYanlis {
        Parent root = FXMLLoader.load(getClass().getResource("anasayfa.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
