package com.baigiuaky.util;

import com.baigiuaky.util.AbrirSite;
import com.kingaspx.util.BrowserUtil;
import com.kingaspx.version.Version;
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.BrowserKeyEvent.KeyCode;
import com.teamdev.jxbrowser.chromium.BrowserKeyEvent.KeyModifiers;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import java.awt.*;

import static com.teamdev.jxbrowser.chromium.BrowserKeyEvent.KeyEventType.*;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.TitleEvent;

import static javax.swing.DefaultButtonModel.PRESSED;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class AbrirSite {

    public Browser browser;
    public BrowserView view;

    public void abrirFrame(String URL) {
        String perfil = "C:\\Historico Chromium\\KingAspx\\";

        //Licença do JX
        BrowserUtil.setVersion(Version.V6_22);

        BrowserContextParams params = new BrowserContextParams(perfil);
        BrowserPreferences.setChromiumSwitches("user-data-dir=" + perfil);
        BrowserContext context = new BrowserContext(params);

        browser = new Browser(context);
        view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 600); 
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(6);
        frame.setUndecorated(true);
        frame.setVisible(true);

        
        browser.addTitleListener((TitleEvent evt) -> {
            frame.setTitle(evt.getTitle());
        });

      
        browser.addConsoleListener((ConsoleEvent evt) -> {
            System.out.println("LOG: " + evt.getMessage());
        });

        browser.loadHTML(URL);
    }

    public void abrirPanel(String URL, JPanel panel, JLabel label) {
        
        String perfil = "C:\\Historico Chromium\\KingAspx\\";

        BrowserUtil.setVersion(Version.V6_22);

        browser = new Browser();
        view = new BrowserView(browser);

        panel.add(view, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 530));

        browser.addTitleListener((TitleEvent evt) -> {
            label.setText(evt.getTitle());
        });

        browser.addConsoleListener((ConsoleEvent evt) -> {
            System.out.println("LOG: " + evt.getMessage());
        });

        browser.loadURL(URL);
    }

    public static void main(String[] args) {
        new AbrirSite().abrirFrame("google.com");
    }

}
