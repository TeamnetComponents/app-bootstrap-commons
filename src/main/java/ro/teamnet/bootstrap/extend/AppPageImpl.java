package ro.teamnet.bootstrap.extend;

import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * O concreta pentru pagina de date transmisa catre client
 *  @param <T> Tipul de baza pentru care s-a facut paginarea
 */
public class AppPageImpl<T> extends PageImpl<T> implements AppPage<T>, Serializable {

    private Filters filters;
    private AppPageable appPageable;
    private List<Metadata> metadata;

    public AppPageImpl(List<T> content, AppPageable pageable, long total, Filters filters) {
        super(content, pageable, total);
        this.appPageable = pageable;
        this.filters = filters;
    }

    public AppPageImpl(List<T> content) {
        super(content);
    }

    public AppPageImpl(List<T> content, AppPageable pageable,
                       long total, Filters filters, List<Metadata> metadata) {
        super(content, pageable, total);
        this.filters = filters;
        this.appPageable = pageable;
        this.metadata = metadata;
    }

    @Override
    public Filters getFilters() {
        return filters;
    }

    @Override
    public AppPageable getPageable() {
        return appPageable;
    }

    @Override
    public List<Metadata> getMetadata() {
        return metadata;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<T> getContent() {
        try {
            Field field = PageImpl.class.getDeclaredField("content");
            field.setAccessible(true);
            return (List<T>) field.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return super.getContent();
        }

    }
}
