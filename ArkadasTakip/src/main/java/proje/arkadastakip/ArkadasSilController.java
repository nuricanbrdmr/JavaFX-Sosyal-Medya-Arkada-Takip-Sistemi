package proje.arkadastakip;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ArkadasSilController extends SayfaGecis implements Mesajlar, Initializable {
    ObservableList<String> list1 = FXCollections.observableArrayList();
    @FXML
    ListView<String> arkadasListView;

    Stage stage;
    Scene scene;
    Parent root;
    private String hesapKullanici;
    private String hesapSifre;

    // Girilen hesabın bilgileri.
    public void DegerAl(String hesapKullanici,String hesapSifre){
        this.setHesapKullanici(hesapKullanici);
        this.setHesapSifre(hesapSifre);
    }
    // Girilen hesaptaki tüm arkadaşları alma
    public void ArkadasListele() throws IOException {
        File file = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\Arkadas\\"+ getHesapKullanici());
        Scanner data = new Scanner(file);
        while (data.hasNext()){
            list1.add(data.nextLine().replace(".txt",""));
        }
        data.close();
    }
    //ListView kontrol.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        arkadasListView.setItems(list1);
    }

    // Ekle Button Çalışma. Seçilen kullanıcıyı ekleme.
    public void ArkadasSil() throws Hatalar.KullaniciBulunamadi, IOException {
        try {
            String secilen = arkadasListView.getSelectionModel().getSelectedItem().toString();
            int silinecek = list1.indexOf(secilen);
            File fold=new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\Arkadas\\"+ getHesapKullanici());
            Scanner oldDAta = new Scanner(fold);
            ArrayList<String> oldVeri = new ArrayList<>();
            while (oldDAta.hasNext()){
                oldVeri.add(oldDAta.nextLine());
            }
            if (oldVeri.size() < silinecek){
                throw new Hatalar.KullaniciBulunamadi(silinecek);
            }
            oldDAta.close();
            fold.delete();
            FileWriter file = new FileWriter("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\Arkadas\\"+ getHesapKullanici(),false);
            for (int i = 0; i < oldVeri.size();i++){
                if (i == silinecek){
                    continue;
                }else {
                    file.write(oldVeri.get(i)+"\n");
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Başarılı Silme");
            alert.setContentText("Kullanıcı Silindi.");
            alert.showAndWait();
            System.out.println("Kullanıcı Silindi\n");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata Mesajı");
            alert.setContentText("Seçilmeyen Değer silmeye Çalıştınız!");
            alert.showAndWait();
        }finally {
            list1.clear();
            ArkadasListele();
        }
    }
    @Override
    public void mesaj() {
        System.out.println("Arkadaş Silden Çıkış Yapıldı.");
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
