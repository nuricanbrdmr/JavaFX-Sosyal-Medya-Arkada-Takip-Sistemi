package proje.arkadastakip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ArkadasListeleController extends SayfaGecis implements Mesajlar {
    @FXML
    ListView listeleListView;
    @FXML
    private Label secilenAdLabel;

    @FXML
    private Label secilenSoyadLabel;

    @FXML
    private Label secilenYasLabel;
    Stage stage;
    Scene scene;
    Parent root;

    private String hesapKullanici;
    private String hesapSifre;


    // Girilen kullanıcıdaki eklenen arkadaşları listelenen kısım.
    public void ArkadasListele(String username) throws FileNotFoundException {
        Image resim = new Image("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\images\\user.png");
        File file = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\Arkadas\\"+ username);
        Scanner data = new Scanner(file);
        while (data.hasNext()){
            listeleListView.getItems().add(data.nextLine().replace(".txt",""));
            listeleListView.setCellFactory(param -> {
                return new ListCell<String>() {
                    ImageView icon = new ImageView();
                    @Override
                    public void updateItem(String name, boolean empty) {
                        super.updateItem(name, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            for (int i = 0; i<listeleListView.getItems().size();i++){
                                if(name.equals(listeleListView.getItems().get(i))){
                                    icon.setFitWidth(20);
                                    icon.setFitHeight(20);
                                    icon.setImage(resim);
                                }
                                setText(name);
                                setGraphic(icon);
                            }
                        }
                    }
                };
            });
        }
    }
    public void KullaniciDetayYazdir(ActionEvent event) throws FileNotFoundException {
        try {
            String secilenKullanici = listeleListView.getSelectionModel().getSelectedItem().toString();
            File secilenFile = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\"+secilenKullanici+".txt");
            Scanner read = new Scanner(secilenFile);
            ArrayList<String> Bilgiler = new ArrayList<>();
            while (read.hasNext()){
                Bilgiler.add(read.nextLine());
            }
            read.close();

            ArrayList<KullaniciBilgiDeger> arkadasList = new ArrayList<KullaniciBilgiDeger>(); // ArrayList'e nesne ekleme
            arkadasList.add(new KullaniciBilgiDeger(Bilgiler.get(0),Bilgiler.get(1),Bilgiler.get(2)));

            KullaniciBilgiDeger arkadas = arkadasList.get(0);
            secilenAdLabel.setText(arkadas.ad); // ad bilgisi
            secilenSoyadLabel.setText(arkadas.soyad); // soyad bilgisi
            secilenYasLabel.setText(arkadas.yas); // yas bilgisi

        }catch (NullPointerException npe) {
            mesaj();
        }

    }
    // Girilen hesabın bilgileri.
    public void DegerAl(String hesapKullanici,String hesapSifre){
        this.setHesapKullanici(hesapKullanici);
        this.setHesapSifre(hesapSifre);
    }

    @Override
    public void mesaj() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hata Mesajı");
        alert.setContentText("Seçilmeyen Değer eklemeye Çalıştınız!");
        alert.showAndWait();
    }

    // Sayfalar
    @Override
    public void anasayfa(ActionEvent e) throws IOException, Hatalar.KullaniciYok, Hatalar.SifreYanlis {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KullaniciGiris.fxml"));
        root = loader.load();
        GirisController giris = loader.getController();
        giris.KullaniciGiris(getHesapKullanici(),getHesapSifre());
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public String getHesapKullanici() {
        return hesapKullanici;
    }

    public void setHesapKullanici(String hesapKullanici) {
        this.hesapKullanici = hesapKullanici;
    }

    public String getHesapSifre() {
        return hesapSifre;
    }

    public void setHesapSifre(String hesapSifre) {
        this.hesapSifre = hesapSifre;
    }

}
