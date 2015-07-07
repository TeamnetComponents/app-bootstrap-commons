package ro.teamnet.bootstrap.plugin.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class DefaultPackagesToScanPlugin implements JpaPackagesToScanPlugin {

    private List<String> jpaPacks=new ArrayList<>();

    public static DefaultPackagesToScanPlugin instance(){
        return new DefaultPackagesToScanPlugin();
    }

    public DefaultPackagesToScanPlugin addAllPackages(Collection<? extends String> c) {
        jpaPacks.addAll(c);
        return this;
    }

    public DefaultPackagesToScanPlugin addPackage(String s) {
        jpaPacks.add(s);
        return this;
    }

    @Override
    public List<String> packagesToScan() {
        return jpaPacks;
    }



    @Override
    public boolean supports(JpaType delimiter) {
        return delimiter==JpaType.DEFAULT_JPA_PACKAGE_TO_SCAN;
    }
}
