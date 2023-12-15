package com.example.springsecurity.controllers;

import com.example.springsecurity.models.Test;
import com.example.springsecurity.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin("*")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/all")
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable Long id) {
        return testService.getTestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Test createTest(@RequestBody Test test) {
        return testService.saveTest(test);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Test> updateTest(@PathVariable Long id, @RequestBody Test updatedTest) {
        return testService.getTestById(id)
                .map(existingTest -> {
                    existingTest.setTestName(updatedTest.getTestName());
                    existingTest.setTestType(updatedTest.getTestType());
                    existingTest.setTestPrice(updatedTest.getTestPrice());
                    existingTest.setTestDescription(updatedTest.getTestDescription());
                    existingTest.setTestCategory(updatedTest.getTestCategory());
                    return ResponseEntity.ok(testService.saveTest(existingTest));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}

