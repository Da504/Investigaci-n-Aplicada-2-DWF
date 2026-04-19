package sv.edu.udb.service;

import sv.edu.udb.event.EventPublisher;
import sv.edu.udb.exception.ResourceNotFoundException;
import sv.edu.udb.model.Producto;
import sv.edu.udb.model.dto.ProductoRequestDTO;
import sv.edu.udb.model.dto.ProductoResponseDTO;
import sv.edu.udb.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final EventPublisher eventPublisher;

    private ProductoResponseDTO convertToDTO(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria(),
                producto.getActivo(),
                producto.getFechaCreacion()
        );
    }

    @Override
    public List<ProductoResponseDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return convertToDTO(producto);
    }

    @Override
    public ProductoResponseDTO crearProducto(ProductoRequestDTO request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(request.getCategoria());
        producto.setActivo(true);

        Producto saved = productoRepository.save(producto);
        log.info(" Producto creado: {}", saved.getNombre());

        // Publicar evento asíncrono
        eventPublisher.publishProductEvent("CREATE", saved.getId(), saved.getNombre(), "Creación de producto");

        return convertToDTO(saved);
    }

    @Override
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(request.getCategoria());

        Producto updated = productoRepository.save(producto);
        log.info(" Producto actualizado: {}", updated.getNombre());

        // Publicar evento asíncrono
        eventPublisher.publishProductEvent("UPDATE", updated.getId(), updated.getNombre(), "Actualización de producto");

        return convertToDTO(updated);
    }

    @Override
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        String nombre = producto.getNombre();
        productoRepository.delete(producto);
        log.info(" Producto eliminado: {}", nombre);

        // Publicar evento asíncrono
        eventPublisher.publishProductEvent("DELETE", id, nombre, "Eliminación de producto");
    }

    @Override
    public List<ProductoResponseDTO> listarActivos() {
        return productoRepository.findByActivoTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponseDTO> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}