package proje.arkadastakip;

import javafx.event.ActionEvent;

import java.io.IOException;

public abstract class SayfaGecis{

    public abstract void anasayfa(ActionEvent e) throws IOException, Hatalar.KullaniciYok, Hatalar.SifreYanlis;

}
