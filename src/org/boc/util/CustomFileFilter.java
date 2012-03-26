package org.boc.util;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class CustomFileFilter
    extends FileFilter {
  /** Accepted extension */
  private String filterExtension;

  /** Filter description */
  private String description;

  /** Constructor */
  public CustomFileFilter(String description, String filterExtension) {
    super();
    this.filterExtension = filterExtension;
    this.description = description;

  }

  public boolean accept(File f) {
    if (f.isDirectory())return true;
    String extension = FileUtilities.getExtension(f);
    if (extension != null) {
      if (extension.equals(filterExtension))
        return true;
    }
    return false;
  }

  public String getDescription() {
    return description;
  }

  public String getExtension() {
    return filterExtension;
  }

}
