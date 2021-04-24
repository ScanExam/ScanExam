package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.config.LanguageManager;
import java.io.File;
import java.util.Objects;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("all")
public class FileLoaderDisplayer {
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * Affiche un sélectionneur de fichier
   * @param format l'extension du fichier à ouvrir (non null)
   * @param formatDes la description du format (non null)
   * @param startPath le répertoire initial du loader (non null)
   * @param root la fenêtre racine du loader (non null)
   */
  public Optional<String> loadFile(final String format, final String formatDes, final File startPath, final Window root) {
    Optional<String> _xblockexpression = null;
    {
      Objects.<String>requireNonNull(format);
      Objects.<String>requireNonNull(formatDes);
      Objects.<File>requireNonNull(startPath);
      Objects.<Window>requireNonNull(root);
      FileChooser fileChooser = new FileChooser();
      ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
      String _translate = LanguageManager.translate(formatDes);
      FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter(_translate, format);
      _extensionFilters.add(_extensionFilter);
      fileChooser.setInitialDirectory(startPath);
      File file = fileChooser.showOpenDialog(root);
      Optional<String> _xifexpression = null;
      if ((file != null)) {
        _xifexpression = Optional.<String>of(file.getPath());
      } else {
        Optional<String> _xblockexpression_1 = null;
        {
          FileLoaderDisplayer.logger.warn("File not chosen");
          _xblockexpression_1 = Optional.<String>empty();
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
