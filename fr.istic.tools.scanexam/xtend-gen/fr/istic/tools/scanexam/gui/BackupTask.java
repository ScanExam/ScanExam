package fr.istic.tools.scanexam.gui;

import fr.istic.tools.scanexam.gui.ScanExamController;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class BackupTask extends TimerTask {
  private ScanExamController controller;
  
  @Override
  public void run() {
    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    Date _date = new Date();
    final String data_ = df.format(_date);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("auto-backup_");
    _builder.append(data_);
    _builder.append(".xls");
    File _file = new File(_builder.toString());
    this.controller.saveExcel(_file);
  }
  
  public BackupTask(final ScanExamController controller) {
    this.controller = controller;
  }
}
