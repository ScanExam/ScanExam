/**
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 * 
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 * 
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fr.istic.tools.scanexam.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class TabbedPaneDemo extends JPanel {
  public TabbedPaneDemo() {
    super(new GridLayout(1, 1));
    JTabbedPane tabbedPane = new JTabbedPane();
    ImageIcon icon = TabbedPaneDemo.createImageIcon("images/middle.gif");
    JComponent panel1 = this.makeTextPanel("Panel #1");
    tabbedPane.addTab("Tab 1", null, panel1, "Does nothing");
    tabbedPane.setMnemonicAt(0, KeyEvent.VK_F1);
    JComponent panel2 = this.makeTextPanel("Panel #2");
    tabbedPane.addTab("Tab 2", icon, panel2, "Does twice as much nothing");
    tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
    JComponent panel3 = this.makeTextPanel("Panel #3");
    tabbedPane.addTab("Tab 3", icon, panel3, "Still does nothing");
    tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
    JComponent panel4 = this.makeTextPanel("Panel #4 (has a preferred size of 410 x 50).");
    Dimension _dimension = new Dimension(410, 50);
    panel4.setPreferredSize(_dimension);
    tabbedPane.addTab("Tab 4", icon, panel4, "Does nothing at all");
    tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
    this.add(tabbedPane);
    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
  }
  
  protected JComponent makeTextPanel(final String text) {
    JPanel panel = new JPanel(false);
    JLabel filler = new JLabel(text);
    filler.setHorizontalAlignment(JLabel.CENTER);
    GridLayout _gridLayout = new GridLayout(1, 1);
    panel.setLayout(_gridLayout);
    panel.add(filler);
    return panel;
  }
  
  /**
   * Returns an ImageIcon, or null if the path was invalid.
   */
  protected static ImageIcon createImageIcon(final String path) {
    URL imgURL = TabbedPaneDemo.class.getResource(path);
    if ((imgURL != null)) {
      return new ImageIcon(imgURL);
    } else {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Couldn\'t find file: ");
      _builder.append(path);
      System.err.println(_builder);
      return null;
    }
  }
  
  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from
   * the event dispatch thread.
   */
  private static void createAndShowGUI() {
    JFrame frame = new JFrame("TabbedPaneDemo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    TabbedPaneDemo _tabbedPaneDemo = new TabbedPaneDemo();
    frame.add(_tabbedPaneDemo, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
  }
  
  public static void main(final String[] args) {
    final Runnable _function = () -> {
      UIManager.put("swing.boldMetal", Boolean.FALSE);
      TabbedPaneDemo.createAndShowGUI();
    };
    SwingUtilities.invokeLater(_function);
  }
}
