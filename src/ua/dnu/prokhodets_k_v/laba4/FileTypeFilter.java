package ua.dnu.prokhodets_k_v.laba4;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileTypeFilter extends FileFilter { private final String extension;
    private final String description;
    public FileTypeFilter(String extension, String description) {
        this.extension = extension; this.description = description;
    }
    @Override
    public boolean accept(File f) { if (f.isDirectory()) {
        return true;
    }
        return f.getName().endsWith(extension);
    }
    @Override
    public String getDescription() { return extension;
    }
}
