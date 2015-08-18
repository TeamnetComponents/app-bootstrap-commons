package ro.teamnet.bootstrap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.extend.AppPage;
import ro.teamnet.bootstrap.extend.AppPageable;
import ro.teamnet.bootstrap.extend.AppRepository;

import java.io.Serializable;
import java.util.List;

@Transactional(readOnly = true)
public abstract class AbstractServiceImpl<T extends Serializable, ID extends Serializable> implements AbstractService<T, ID> {


    private final Logger log = LoggerFactory.getLogger(AbstractServiceImpl.class);

    private final AppRepository<T, ID> repository;

    public AbstractServiceImpl(AppRepository<T, ID> repository) {
        this.repository = repository;
    }

    /**
     * Retrieves the specific entity {@link ro.teamnet.bootstrap.extend.AppRepository}, accessible to implementations
     * extending this class.
     *
     * @return The underlying main entity repository bound to this instance.
     */
    protected AppRepository<T, ID> getRepository() {
        return this.repository;
    }

    @Override
    @Transactional
    public T save(T t) {
        log.debug("REST request to save : {}", t);
        return repository.save(t);
    }

    @Override
    public List<T> findAll() {
        log.debug("REST request to get all records");
        return repository.findAll();
    }

    @Override
    public AppPage<T> findAll(AppPageable appPageable) {
        log.debug("REST request to get all records with pagination");
        return repository.findAll(appPageable);
    }


    @Override
    public T findOne(ID id) {
        log.debug("REST request to get : {}", id);
        return (T) repository.findOne(id);
    }


    @Override
    @Transactional
    public void delete(ID id) {
        log.debug("REST request to delete : {}", id);
        repository.delete(id);
    }
}
