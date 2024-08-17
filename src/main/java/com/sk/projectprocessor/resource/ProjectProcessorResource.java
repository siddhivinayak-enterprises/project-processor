package com.sk.projectprocessor.resource;

import com.sk.projectprocessor.model.ParentInfo;
import com.sk.projectprocessor.service.IProjectProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ProjectProcessorResourceConstant.PROJECT_PROCESSOR_URL)
public class ProjectProcessorResource {

    @Autowired
    private IProjectProcessorService projectProcessorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ParentInfo> status() {
        return new ResponseEntity<>(projectProcessorService.status(), HttpStatus.OK);
    }

    @GetMapping(value = ProjectProcessorResourceConstant.START, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ParentInfo> start(@RequestParam(name = "name", required = false) String name) {
        return new ResponseEntity<>(projectProcessorService.start(name), HttpStatus.OK);
    }

    @GetMapping(value = ProjectProcessorResourceConstant.STOP, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ParentInfo> stop(@RequestParam(name = "name", required = false) String name) {
        return new ResponseEntity<>(projectProcessorService.stop(name), HttpStatus.OK);
    }
}
