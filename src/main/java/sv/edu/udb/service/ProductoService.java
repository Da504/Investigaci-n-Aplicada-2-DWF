package sv.edu.udb.service;

import sv.edu.udb.model.dto.ProductoRequestDTO;
import sv.edu.udb.model.dto.ProductoResponseDTO;
import java.util.List;

public interface ProductoService {
    List<ProductoResponseDTO> listarTodos();
    ProductoResponseDTO obtenerPorId(Long id);
    ProductoResponseDTO crearProducto(ProductoRequestDTO request);
    ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO request);
    void eliminarProducto(Long id);
    List<ProductoResponseDTO> listarActivos();
    List<ProductoResponseDTO> buscarPorCategoria(String categoria);
}