module proje.arkadastakip {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens proje.arkadastakip to javafx.fxml;
    exports proje.arkadastakip;
}