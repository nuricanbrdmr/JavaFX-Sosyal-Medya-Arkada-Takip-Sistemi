package proje.arkadastakip;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Anasayfa extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Anasayfa Bağlantısı.
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("anasayfa.fxml"));
            Scene scene = new Scene(fxmlLoader);
            Image icon = new Image("C:\\Users\\NuricanB\\Desktop\\JavaDers\\Proje\\NuriCanBirdemir_171421013\\ArkadasTakip\\src\\images\\icon.png");
            stage.getIcons().add(icon);
            stage.setTitle("FriendZone");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            // Çıkış işlemi yapılmak istendiğinde Uyarı verip emin misiniz diye soran kısım.
            stage.setOnCloseRequest(event -> {
                try {
                    event.consume();
                    cikis(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // Start Metodunda Çıkış için kullanılan cikis() metodu.
    public void cikis(Stage stage) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Çıkış");
        alert.setHeaderText("Uygulamadan Çıkış Yapıyorsunuz?");
        alert.setContentText("");

        if (alert.showAndWait().get() == ButtonType.OK){
            System.out.println("Uygulamadan Cikis Yapildi");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}