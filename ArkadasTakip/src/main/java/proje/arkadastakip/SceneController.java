package proje.arkadastakip;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    TextField kayitAd;
    @FXML
    TextField kayitSoyad;
    @FXML
    TextField kayitYas;
    @FXML
    TextField kayitKadi;
    @FXML
    TextField kayitSifre;
    @FXML
    private Pane mainPane;
    @FXML
    private Button cikisButton;

    private Stage stage;
    private Scene scene;
    private Parent root;
    //İşlemler
    private void loginCode(Event event) throws IOException, Hatalar.KullaniciYok, Hatalar.SifreYanlis{
        String username = usernameTextField.getText()+".txt";
        String password = passwordTextField.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("KullaniciGiris.fxml"));
        root = loader.load();
        GirisController girisController = loader.getController();
        try {
            girisController.KullaniciGiris(username,password);
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata Mesajı");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            System.out.println(ex.getMessage());
        }

    }
    @FXML
    private void txtFieldKeyRelesed(KeyEvent event) throws IOException, Hatalar.KullaniciYok, Hatalar.SifreYanlis{
        if (event.getCode() == event.getCode().ENTER){
            loginCode(event);
        }
    }
    public void loginIslem(ActionEvent event) throws IOException, Hatalar.KullaniciYok, Hatalar.SifreYanlis {
            loginCode(event);
        }
    public void mesaj(Alert alert){
        alert.setTitle("Başarılı Kayıt");
        alert.setContentText("Başarılı Kayıt oluşturdunuz.");
        alert.showAndWait();
    }
    public void kayitİslem(ActionEvent event) throws IOException, Hatalar.RakamBulundu, Hatalar.KucukYasHatasi, Hatalar.AyniKullaniciAdi, Hatalar.FazlaDegerGirisi {
        if (kayitAd.getText().trim().isEmpty() || kayitSoyad.getText().trim().isEmpty() || kayitYas.getText().trim().isEmpty() || kayitKadi.getText().trim().isEmpty() || kayitSifre.getText().trim().isEmpty()) {
            //overload
            KayitController kayit = new KayitController();
            kayit.KayitController();
        }else {
            try {
                String ad = kayitAd.getText();
                String soyad = kayitSoyad.getText();
                int yas = Integer.parseInt(kayitYas.getText());
                String kullaniciAdi = kayitKadi.getText();
                String sifre = kayitSifre.getText();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("KullaniciKayit.fxml"));
                root = loader.load();
                KayitController kayitController = loader.getController();
                kayitController.KayitController(ad, soyad, yas, kullaniciAdi, sifre);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                mesaj(alert);
                Parent root = FXMLLoader.load(getClass().getResource("anasayfa.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (Hatalar.RakamBulundu rb) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata Mesajı");
                alert.setContentText(rb.getMessage());
                alert.showAndWait();
                System.out.println(rb.getMessage());
            } catch (Hatalar.KucukYasHatasi ky) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata Mesajı");
                alert.setContentText(ky.getMessage());
                alert.showAndWait();
                System.out.println(ky.getMessage());
            } catch (Hatalar.FazlaDegerGirisi fdg) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata Mesajı");
                alert.setContentText(fdg.getMessage());
                alert.showAndWait();
                System.out.println(fdg.getMessage());
            } catch (Hatalar.AyniKullaniciAdi aka) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata Mesajı");
                alert.setContentText(aka.getMessage());
                alert.showAndWait();
                System.out.println(aka.getMessage());
            }catch (NumberFormatException nfe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata Mesajı");
                alert.setContentText("Yaş Bilginizi Doğru Giriniz!");
                alert.showAndWait();
                System.out.println("Yaş Bilginizi Doğru Giriniz!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    public void cikis(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Çıkış");
        alert.setHeaderText("Uygulamadan Çıkış Yapıyorsunuz?");
        alert.setContentText("");

        if (alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) mainPane.getScene().getWindow();
            System.out.println("Uygulamadan Cikis Yapildi");
            stage.close();
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
    public void girisSayfasi(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("giris.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void sifreYenileSayfasi(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("SifreUnuttum1.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void kayitSayfasi(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("kayit.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
