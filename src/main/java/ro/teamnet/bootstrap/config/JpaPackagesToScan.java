package ro.teamnet.bootstrap.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JpaPackagesToScan {

    private List<String> packagesToScan=new ArrayList<>();

    public List<String> getPackagesToScan() {
        return packagesToScan;
    }

    public static JpaPackagesToScan instance(){
        return new JpaPackagesToScan();
    }

    public  JpaPackagesToScan addPackage(String pack){
        packagesToScan.add(pack);
        return this;
    }

    public JpaPackagesToScan addAllPackages(String... packs){
        Collections.addAll(packagesToScan, packs);
        return this;
    }
}
