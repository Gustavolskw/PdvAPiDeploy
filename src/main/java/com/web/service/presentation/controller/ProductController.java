package com.web.service.presentation.controller;

import com.web.service.application.dto.ProductCreationDTO;
import com.web.service.application.dto.ProductUpdateDTO;
import com.web.service.application.service.ProductService;
import com.web.service.domain.model.Product;
import com.web.service.presentation.viewModel.ApiResponse;
import com.web.service.presentation.viewModel.PageableResponse;
import com.web.service.presentation.viewModel.ProductResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("${api.prefix}/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@RequestParam(value = "image", required = false) MultipartFile image,
                                                     @Valid @RequestPart(value="product") ProductCreationDTO productCreationDTO) {
        return ResponseEntity.ok().body(new ApiResponse("Produto inserido com sucesso!", new ProductResponseDTO(productService.createProduct(productCreationDTO, image))));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("id") Long id,
                                                     @RequestParam(value="image", required = false) MultipartFile image,
                                                     @Valid @RequestPart(value="product", required = false) ProductUpdateDTO productUpdateDTO) {
            return ResponseEntity.ok().body(new ApiResponse("Produto atualizado com sucesso!", new ProductResponseDTO(productService.updateProduct(id, productUpdateDTO, image))));
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse> activateProduct(@PathVariable("id") Long id) {
        ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO("", "", null, null, true);
        return ResponseEntity.ok().body(new ApiResponse("Produto reativado com sucesso!", new ProductResponseDTO(productService.updateProduct(id, productUpdateDTO, null))));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(new ApiResponse("Produto encontrado", new ProductResponseDTO(productService.findProductById(id))));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> searchProductsByParam(
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) Boolean status,
            @RequestParam(name = "type", required = false) Long type
            ) {

        Page<Product> productsPaged = productService.findProductsByParam(name, status, type, pageable);

        Page<ProductResponseDTO> productResponseTransformed = productsPaged.map(ProductResponseDTO::new);

        return ResponseEntity.ok().body(new ApiResponse("Lista de Produtos Encontrados", new PageableResponse<>(productResponseTransformed)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("id") Long id) {
        productService.inactivateProduct(id);
        return ResponseEntity.ok().body(new ApiResponse("Produto Inativado com sucesso", null));
    }


}
