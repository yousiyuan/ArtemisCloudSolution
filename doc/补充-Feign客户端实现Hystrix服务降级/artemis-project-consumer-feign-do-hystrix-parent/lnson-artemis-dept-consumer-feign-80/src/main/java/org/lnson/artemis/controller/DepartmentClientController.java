package org.lnson.artemis.controller;

import org.lnson.artemis.model.Department;
import org.lnson.artemis.service.DepartmentClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(value = "/consumer/dept/")
public class DepartmentClientController {

    @Autowired
    private DepartmentClientService departmentClientService;

    @GetMapping("/get/{id}")
    public Department queryDepartment(@PathVariable("id") Long deptno) {
        return departmentClientService.queryDepartment(deptno);
    }

    @GetMapping("/list")
    public List<Department> queryDepartmentList() {
        return departmentClientService.queryDepartmentList();
    }

}
