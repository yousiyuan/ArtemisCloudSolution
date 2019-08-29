package org.lnson.artemis.service.fallback;

import org.lnson.artemis.model.Department;
import org.lnson.artemis.service.DepartmentClientService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentClientServiceFallback implements DepartmentClientService {
    @Override
    public Department queryDepartment(Long deptno) {
        System.out.println("queryDepartment(Long deptno) 降级执行");
        return null;
    }

    @Override
    public List<Department> queryDepartmentList() {
        System.out.println("queryDepartmentList() 降级执行");
        return null;
    }

    @Override
    public Boolean insertDepartment(Department department) {
        System.out.println("insertDepartment(Department department) 降级执行");
        return null;
    }

    @Override
    public Boolean updateDepartment(Department department) {
        System.out.println("updateDepartment(Department department) 降级执行");
        return null;
    }

    @Override
    public Boolean deleteDepartment(long deptno) {
        System.out.println("queryDepartment(Long deptno)降级执行");
        return null;
    }
}
