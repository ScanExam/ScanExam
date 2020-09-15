package fr.istic.tools.scanexam.gui.template;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("all")
class ImageLabel extends JLabel {
  private Image _myimage;
  
  public ImageLabel(final String text) {
    super(text);
  }
  
  @Override
  public void setIcon(final Icon icon) {
    super.setIcon(icon);
    if ((icon instanceof ImageIcon)) {
      this._myimage = ((ImageIcon) icon).getImage();
    }
  }
  
  @Override
  public void paint(final Graphics g) {
    g.drawImage(this._myimage, 0, 0, this.getWidth(), this.getHeight(), null);
  }
}
