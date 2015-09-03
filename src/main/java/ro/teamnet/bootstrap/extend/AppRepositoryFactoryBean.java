package ro.teamnet.bootstrap.extend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Un factory bean care rezolva ca toate clasele ce extind AppRepository sa fie rezolvate ca si bean-uri de Spring
 * pe nivelul de repository
 * @param <R> Clasa de Repository
 * @param <T> Clasa de tip @Entity folosista de Repository
 * @param <I>
 */
public class AppRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I
        extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {

        return new AppRepositoryFactory(entityManager);
    }
}
