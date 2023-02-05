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
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class RastgeleArkadasEkleContoller extends SayfaGecis implements Initializable {
    ObservableList<String> list1 = FXCollections.observableArrayList();

    @FXML
    private ListView<String> rastgeleListView;
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
    ArrayList<String> hesapArkadaslari = new ArrayList<>();
    ArrayList<String> tumKullanicilar = new ArrayList<>();

    private void tumKullaniciAl(){
        File dosya = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase");
        List<File> dizi = Arrays.stream(dosya.listFiles()).toList();
        for (int i = 0; i<dosya.list().length;i++) {

            if (dizi.get(i).isFile()) {
                tumKullanicilar.add(dizi.get(i).getName().replace(".txt",""));
            }
        }
    }
    // Girilen hesabın bilgileri.
    public void DegerAl(String hesapKullanici,String hesapSifre){
        this.setHesapKullanici(hesapKullanici);
        this.setHesapSifre(hesapSifre);
    }

    // Girilen hesaptaki kullanıcı için getirilecek diğer kullanıcıların ListView'e listelendiği kısım.
    public void KullaniciListesi() throws IOException, FileNotFoundException {
        tumKullaniciAl();
        System.out.println(tumKullanicilar.size());
        for (int i = 0; i<tumKullanicilar.size();i++){
            if (tumKullanicilar.get(i).equals(getHesapKullanici().replace(".txt",""))){
                continue;
            }else {
                list1.add(tumKullanicilar.get(i));
            }
        }
        File fold = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\Arkadas\\"+ getHesapKullanici());
        if (!(fold.exists())){
            fold.createNewFile();
        }
        Scanner oldData = new Scanner(fold);
        while (oldData.hasNext()){
            hesapArkadaslari.add(oldData.nextLine().replace(".txt",""));
        }
        for (int a = 0; a<hesapArkadaslari.size();a++){
            list1.remove(hesapArkadaslari.get(a));
        }

    }

    //ListView kontrol.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rastgeleListView.setItems(list1);
    }

    public void ArkadasEkle(ActionEvent event) throws IOException {
        String secilenKullanici = rastgeleListView.getSelectionModel().getSelectedItem().toString();
        File fold = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\Arkadas\\"+ getHesapKullanici());
        Scanner oldDAta = new Scanner(fold);
        ArrayList<String> oldVeri = new ArrayList<>();
        while (oldDAta.hasNext()){
            oldVeri.add(oldDAta.nextLine());
        }
        fold.delete();
        FileWriter fileWriter = new FileWriter("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\Arkadas\\"+ getHesapKullanici(),false);
        System.out.println(oldVeri.size());
        for (int i = 0; i < oldVeri.size();i++){
            fileWriter.write(oldVeri.get(i)+"\n");
        }
        fileWriter.write(secilenKullanici+".txt");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bilgi");
        alert.setContentText(secilenKullanici+ " Başarı ile Eklendi.");
        alert.showAndWait();
        fileWriter.close();
        list1.clear();
        hesapArkadaslari.clear();
        tumKullanicilar.clear();
        KullaniciListesi();
    }
    public void KullaniciDetayYazdir(ActionEvent event) throws FileNotFoundException {
        try {
            String secilenKullanici = rastgeleListView.getSelectionModel().getSelectedItem().toString();
            File secilenFile = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\"+secilenKullanici+".txt");
            Scanner read = new Scanner(secilenFile);
            ArrayList<String> Bilgiler = new ArrayList<>();
            while (read.hasNext()){
                Bilgiler.add(read.nextLine());
            }
            read.close();
            secilenAdLabel.setText(Bilgiler.get(0)); // ad bilgisi
            secilenSoyadLabel.setText(Bilgiler.get(1)); // soyad bilgisi
            secilenYasLabel.setText(Bilgiler.get(2)); // yas bilgisi

        }catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata Mesajı");
            alert.setContentText("Seçilmeyen Değer eklemeye Çalıştınız!");
            alert.showAndWait();
            System.out.println(npe.getMessage());
        }

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
