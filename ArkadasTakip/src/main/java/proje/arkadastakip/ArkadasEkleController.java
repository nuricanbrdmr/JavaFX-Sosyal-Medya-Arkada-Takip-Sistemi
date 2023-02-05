package proje.arkadastakip;

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

import java.io.*;
import java.net.URL;
import java.util.*;

public class ArkadasEkleController extends SayfaGecis implements Mesajlar, Initializable  {
    ObservableList<String> list1 = FXCollections.observableArrayList();
    @FXML
    TextField ekleTextField;
    @FXML
    ListView<String> ekleListView;
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
    ArrayList<String> kullaniciListesi = new ArrayList<String>();
    ArrayList<String> hesapArkadaslari = new ArrayList<>();
    ArrayList<String> tumKullanicilar = new ArrayList<>();

    // Tüm kullanıcıların ArrayListe alındığı yer.
    private void KayitListesi() throws FileNotFoundException {
        File dosya = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase");
        List<File> dizi = Arrays.stream(dosya.listFiles()).toList();
        for (int i = 0; i<dosya.list().length;i++) {

            if (dizi.get(i).isFile()) {
                tumKullanicilar.add(dizi.get(i).getName());
            }
        }
    }
    // Girilen hesabın bilgileri.
    public void DegerAl(String hesapKullanici,String hesapSifre){
        this.setHesapKullanici(hesapKullanici);
        this.setHesapSifre(hesapSifre);
    }
    // Girilen hesaptaki kullanıcı için getirilecek diğer kullanıcıların ArrayListe eklendiği kısım.
    private ArrayList<String> metotKullaniciListesi() throws IOException, FileNotFoundException {
        KayitListesi();
        System.out.println(tumKullanicilar.size());
        for (int i = 0; i<tumKullanicilar.size();i++){
            if (tumKullanicilar.get(i).equals(getHesapKullanici())){
                continue;
            }else {
                kullaniciListesi.add(tumKullanicilar.get(i));
            }
        }
        File fold = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\Arkadas\\"+ getHesapKullanici());
        if (!(fold.exists())){
            fold.createNewFile();
        }
        Scanner oldData = new Scanner(fold);
        while (oldData.hasNext()){
            hesapArkadaslari.add(oldData.nextLine());
        }
        System.out.println(hesapArkadaslari.size());
        for (int a = 0; a<hesapArkadaslari.size();a++){
            System.out.println(hesapArkadaslari.get(a));
            kullaniciListesi.remove(hesapArkadaslari.get(a));
        }
        hesapArkadaslari.clear();
        tumKullanicilar.clear();
        return kullaniciListesi;
    }
    ArrayList<String>  liste;
    ArrayList<String> bulunan = new ArrayList<>();
    ArrayList<String> bulunanCopy = new ArrayList<>();

    // Ara button Çalışma. Girilen metine göre getirilecek kullanıcılar.
    public void bulunanListele() throws IOException {
        String aranan = ekleTextField.getText();
        liste = metotKullaniciListesi();
        int sayac = 0;
        for (int i = 0; i < liste.size(); i++){
            sayac = 0;
            for (int j = 0; j<aranan.length(); j++){
                if (liste.get(i).charAt(j) == aranan.charAt(j) ){
                    sayac++;
                }
                else if (liste.get(i).length() == j+1) break;
            }
            if (sayac > 2) this.bulunan.add(liste.get(i));
        }
        bulunanCopy = (ArrayList<String>) bulunan.clone();
        for (int i = 0; i< this.bulunan.size(); i++){
            list1.add(bulunan.get(i).replace(".txt",""));
        }
        if (bulunan.size() < list1.size()){
            list1.remove(list1.get(list1.size()-1));
        }
        liste.clear();
        kullaniciListesi.clear();
        bulunan.clear();
    }
    //ListView kontrol.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ekleListView.setItems(list1);
    }

    // Ekle Button Çalışma. Seçilen kullanıcıyı ekleme.
    public void ArkadasEkle() throws IOException {
        bulunanListele();
        try {
            String secilen = ekleListView.getSelectionModel().getSelectedItem().toString();
            int secim = bulunanCopy.indexOf(secilen+".txt");
            File fold=new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\Arkadas\\"+ getHesapKullanici());
            Scanner oldDAta = new Scanner(fold);
            ArrayList<String> oldVeri = new ArrayList<>();
            while (oldDAta.hasNext()){
                oldVeri.add(oldDAta.nextLine());
            }
            fold.delete();
            FileWriter file = new FileWriter("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\Arkadas\\"+ getHesapKullanici(),false);
            System.out.println(oldVeri.size());
            for (int i = 0; i < oldVeri.size();i++){
                file.write(oldVeri.get(i)+"\n");
            }
            file.write(bulunanCopy.get(secim));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Başarılı Ekleme");
            alert.setContentText("Kullanıcı Eklendi.");
            alert.showAndWait();
            System.out.println("Kullanıcı Eklendi\n");
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata Mesajı");
            alert.setContentText("Seçilmeyen Değer eklemeye Çalıştınız!");
            alert.showAndWait();
            System.out.println(npe.getMessage());
        }finally {
            list1.clear();
            ekleTextField.clear();
            bulunanCopy.clear();
        }
    }
    public void KullaniciDetayYazdir(ActionEvent event) throws FileNotFoundException {
        try {
            String secilenKullanici = ekleListView.getSelectionModel().getSelectedItem().toString();
            File secilenFile = new File("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\main\\java\\proje\\arkadastakip\\dataBase\\"+secilenKullanici+".txt");
            Scanner read = new Scanner(secilenFile);
            ArrayList<String> Bilgiler = new ArrayList<>();
            while (read.hasNext()){
                Bilgiler.add(read.nextLine());
            }
            read.close();
            ArrayList<KullaniciBilgiDeger> arkadasList = new ArrayList<KullaniciBilgiDeger>();
            arkadasList.add(new KullaniciBilgiDeger(Bilgiler.get(0),Bilgiler.get(1),Bilgiler.get(2)));

            KullaniciBilgiDeger kullaniciBilgi = arkadasList.get(0);
            secilenAdLabel.setText(kullaniciBilgi.ad); // ad bilgisi
            secilenSoyadLabel.setText(kullaniciBilgi.soyad); // soyad bilgisi
            secilenYasLabel.setText(kullaniciBilgi.yas); // yas bilgisi

        }catch (NullPointerException npe) {
            mesaj();
        }

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
