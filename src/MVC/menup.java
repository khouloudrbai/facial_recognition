package MVC;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class menup extends JPanel{
	JLabel t=new JLabel("Bonjour "+app.user+",");
	JButton menu=new JButton(Fonctionalite.ResizeImage("Images/menu.png", 30, 30));   //boutton pour afficher le fchoix
	JButton deconnecte=new JButton(Fonctionalite.ResizeImage("Images/deco.png", 20, 20)); //button pour changer l'utilisateu
	public menup() {
		// TODO Auto-generated constructor stub
		setOpaque(true);
		setLayout(new FlowLayout());
		setLocation(300,10);
		setSize(200,30);
		menu.setSize(10,10);
		menu.setBorderPainted(false);
		deconnecte.setSize(10,10);
		deconnecte.setBorderPainted(false);
		repaint();validate();
		t.setVisible(true);
		add(t);
		add(menu);
		add(deconnecte);
		revalidate();repaint();
		setVisible(false);
		menu.setToolTipText("menu");
		deconnecte.setToolTipText("se déconnecter");
		menu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				app.calmdown();
				new choix().setVisible(true);
			}
		});
		deconnecte.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				app.calmdown();
				setVisible(false);
				app.plogin.setVisible(true);
			}
		});
	}

}
