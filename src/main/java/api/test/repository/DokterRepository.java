package api.test.repository;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import api.test.model.Dokter;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

@Singleton
public class DokterRepository implements DokterInterface {

    @PersistenceContext
    private EntityManager manager;

    public DokterRepository(@CurrentSession EntityManager entityManager) {
        this.manager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dokter> findAll(int page, int limit) {
        TypedQuery<Dokter> query = manager.createQuery("from Dokter", Dokter.class)
                .setFirstResult(page > 1 ? page * limit - limit : 0).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    @Transactional
    public String save(@NotNull Dokter dokter) {
        try {
            manager.persist(dokter);
            return "{\"status\":\"ok\"}";
        } catch (Exception e) {
            return "{\"status\":\"fail\", \"message\": \"" + e.getMessage() + "\"}";
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long size() {
        return manager.createQuery("select count(*) from Dokter", Long.class).getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public Dokter findById(@NotNull Long id) {
        return manager.find(Dokter.class, id);
    }

    @Override
    @Transactional
    public boolean update(@NotNull Long id, String nama_dokter, String keahlian_dokter) {
        try {
            Dokter dokter = manager.find(Dokter.class, id);
            if (nama_dokter != null)
                dokter.setNama_dokter(nama_dokter);
            if (keahlian_dokter != null)
                dokter.setKeahlian_dokter(keahlian_dokter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean destroy(@NotNull Long id) {
        try {
            Dokter dokter = manager.find(Dokter.class, id);
            manager.remove(dokter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}