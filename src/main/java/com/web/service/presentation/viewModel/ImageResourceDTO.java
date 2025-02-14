package com.web.service.presentation.viewModel;

import org.springframework.core.io.Resource;

import java.nio.file.Path;

public record ImageResourceDTO(Resource resource, Path path) {
    public ImageResourceDTO(Resource resource, Path path) {
        this.resource = resource;
        this.path = path;
    }
}
