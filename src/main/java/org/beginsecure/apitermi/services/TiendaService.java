package org.beginsecure.apitermi.services;

import org.beginsecure.apitermi.entities.Tienda;
import org.beginsecure.apitermi.repositories.TiendaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TiendaService {
    private final TiendaRepository repositorio;
    public TiendaService(TiendaRepository repositorio) {
        this.repositorio = repositorio;
    }

    //Metodos Find:
    public Optional<Tienda> findByTienda(String tienda) {
        return repositorio.findByTienda(tienda);
    }
    public List<Tienda> findByAgencia(String agencia) {
        return repositorio.findByAgencia(agencia);
    }
    public List<Tienda> findAll() {
        return repositorio.findAll();
    }
    public Optional<Tienda> findById(String id) {
        return repositorio.findById(id);
    }

    //Metodos Delete
    public void deleteByTienda(String tienda) {
        repositorio.deleteByTienda(tienda);
    }
    public void deleteByAgencia(String agencia) {
        repositorio.deleteByAgencia(agencia);
    }
    public void deleteAll() {
        repositorio.deleteAll();
    }
    public void deleteById(String id) {
        repositorio.deleteById(id);
    }

    // --- Métodos Count & Exists ---
    public long count() {
        return repositorio.count();
    }
    public boolean existsByTienda(String tienda) {
        return repositorio.existsByTienda(tienda);
    }
    public boolean existsByAgencia(String agencia) {
        return repositorio.existsByAgencia(agencia);
    }
    public boolean existsById(String id) {
        return repositorio.existsById(id);
    }

    // --- Métodos de Guardado / Actualización
    public Tienda save(Tienda tienda) {

        // Buscamos si ya hay una tienda con ese nombre en la BD
        Optional<Tienda> tiendaExistente = findByTienda(tienda.getTienda());

        // Si existe una tienda con ese nombre Y (no tiene ID o su ID es distinto al que estoy guardando)
        // significa que estoy intentando crear un duplicado, o cambiar el nombre a uno que ya usa otra tienda.
        if (tiendaExistente.isPresent() &&
                (tienda.getId() == null || !tiendaExistente.get().getId().equals(tienda.getId()))) {
            throw new IllegalArgumentException("El nombre de la tienda ya existe");
        }

        return repositorio.save(tienda);
    }
    public List<Tienda> guardarTodas(List<Tienda> tiendas) {
        return repositorio.saveAll(tiendas);
    }

    // Metodo para obtener páginas de tiendas
    public Page<Tienda> findAllPaginated(Pageable pageable) {
        return repositorio.findAll(pageable);
    }
    // Metodo para obtener páginas de tiendas filtradas por agencia
    public Page<Tienda> findByAgenciaPaginated(String agencia, Pageable pageable) {
        return repositorio.findByAgencia(agencia, pageable);
    }
    // Obtener lista de agencias únicas y ordenadas
    public List<String> obtenerAgenciasUnicas() {
        return repositorio.findAll().stream()
                .map(Tienda::getAgencia) // Nos quedamos solo con la agencia
                .filter(agencia -> agencia != null && !agencia.trim().isEmpty()) // Quitamos las vacías
                .distinct() // Quitamos los duplicados
                .sorted() // Las ordenamos alfabéticamente
                .collect(Collectors.toList());
    }
}
