package org.lnson.artemis.service;

import org.lnson.artemis.model.Department;
import org.lnson.artemis.service.fallback.DepartmentClientServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 注解@FeignClient必须通过参数value说明要调用的服务
 * 另外，需要注意，在这个接口中所有的路由规则都遵循Spring.MVC的路由规则
 */
@FeignClient(value = "ARTEMIS-DEPT-PROVIDER", fallback = DepartmentClientServiceFallback.class, path = "/dept")
//@RequestMapping(value = "/dept")
//这里的这个注解@RequestMapping需要去掉，否则报异常  There is already 'departmentClientServiceFallback' bean method
//原因是DepartmentClientServiceFallback 与 DepartmentClientService 存在继承关系，spring 在 map 它们两个的 bean method 时，认为重复了
//注意哦，这里表示所有接口的前缀要使用@FeignClient注解的属性path来指定，取代@RequestMapping
public interface DepartmentClientService {

    @GetMapping("/get/{id}")
    public abstract Department queryDepartment(@PathVariable("id") Long deptno);

    @GetMapping("/list")
    public abstract List<Department> queryDepartmentList();

    @PostMapping(value = "/post")
    public abstract Boolean insertDepartment(@RequestBody Department department);

    @PostMapping(value = "/put")
    public abstract Boolean updateDepartment(@RequestBody Department department);

    @PostMapping(value = "/delete/{id}")
    public abstract Boolean deleteDepartment(@PathVariable("id") long deptno);

}
