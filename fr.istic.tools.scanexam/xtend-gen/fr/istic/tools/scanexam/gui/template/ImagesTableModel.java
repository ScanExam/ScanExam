package fr.istic.tools.scanexam.gui.template;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("all")
public class ImagesTableModel extends AbstractTableModel {
  Object[] donnees;
  
  String titres;
  
  public ImagesTableModel(final Object[] donnees, final String titres) {
    this.donnees = donnees;
    this.titres = titres;
  }
  
  @Override
  public int getColumnCount() {
    return 1;
  }
  
  @Override
  public Object getValueAt(final int parm1, final int parm2) {
    Object _xblockexpression = null;
    {
      final int _rdIndx_donnees = parm1;
      _xblockexpression = this.donnees[_rdIndx_donnees];
    }
    return _xblockexpression;
  }
  
  @Override
  public int getRowCount() {
    return this.donnees.length;
  }
  
  @Override
  public String getColumnName(final int col) {
    return this.titres;
  }
}
