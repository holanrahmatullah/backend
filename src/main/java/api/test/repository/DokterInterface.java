package api.test.repository;

import java.util.List;

import javax.validation.constraints.NotNull;

import api.test.model.Dokter;

public interface DokterInterface {

    List<Dokter> findAll(int page, int limit);

    String save(@NotNull Dokter dokter);

    Long size();

    Dokter findById(@NotNull Long id);

    boolean update(@NotNull Long id, String nama_dokter, String keahlian_dokter);

    boolean destroy(@NotNull Long id);
}