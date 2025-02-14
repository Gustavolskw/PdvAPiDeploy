package com.web.service.presentation.controller;

import com.web.service.application.dto.ProductTypeCreation;
import com.web.service.application.service.ProductTypeService;
import com.web.service.presentation.viewModel.ApiResponse;
import com.web.service.presentation.viewModel.ProductTypeResponse;
import com.web.service.presentation.viewModel.ProductTypeResponseComplete;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/product/type")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @PostMapping
    public ResponseEntity<ApiResponse> createProductType(@RequestBody ProductTypeCreation productTypeCreation) {
        return ResponseEntity.ok().body(new ApiResponse("Tipo de produto criado com sucesso!", productTypeService.createProductType(productTypeCreation)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getProductTypes() {
        return ResponseEntity.ok().body(new ApiResponse("Lista de Tipos de produto", productTypeService.getAllProductTypes().stream().map(ProductTypeResponse::new)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductTypeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(new ApiResponse("Tipo de Produto encontrado", new ProductTypeResponseComplete(productTypeService.getProductTypeById(id))));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> getProductTypeByName(@RequestParam String name) {
        return ResponseEntity.ok().body(new ApiResponse("Lista de Tipos de produto", productTypeService.getProductTypeByName(name).stream().map(ProductTypeResponse::new)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProductType(@PathVariable Long id) {
        productTypeService.deleteProductTypeById(id);
        return ResponseEntity.ok().body(new ApiResponse("Tipo de Produto deletado com sucesso", null));
    }
}
