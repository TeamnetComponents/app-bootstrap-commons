package ro.teamnet.bootstrap.plugin.jpa;

import org.springframework.plugin.core.Plugin;

import java.util.List;

public interface JpaPackagesToScanPlugin extends Plugin<JpaType> {

    public List<String> packagesToScan();

}
