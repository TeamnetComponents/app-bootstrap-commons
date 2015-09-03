package ro.teamnet.bootstrap.plugin.jpa;

import org.springframework.plugin.core.Plugin;

import java.util.List;

/**
 * Interfata de tip extension point cu ajutorul careia se vor putea scana dupa
 * entitati jpa in toate modulele din runtime
 */
public interface JpaPackagesToScanPlugin extends Plugin<JpaType> {

    public List<String> packagesToScan();

}
