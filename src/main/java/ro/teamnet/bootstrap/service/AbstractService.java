package ro.teamnet.bootstrap.service;

import org.springframework.data.domain.Page;
import ro.teamnet.bootstrap.extend.AppPage;
import ro.teamnet.bootstrap.extend.AppPageable;

import java.io.Serializable;
import java.util.List;


public interface AbstractService<T extends Serializable, ID extends Serializable> {

    T save(T t);

    List<T> findAll();

    AppPage<T> findAll(AppPageable appPageable);

    T findOne(ID id);

    void delete(ID id);
}