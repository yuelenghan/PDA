package com.ghtn.lihe.pda;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.test.ApplicationTestCase;

import com.ghtn.lihe.pda.dao.DepartmentDao;
import com.ghtn.lihe.pda.model.Department;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testAdd() {
        DepartmentDao departmentDao = new DepartmentDao(getContext());
        departmentDao.add("111", "测试部门");
    }

    public void testDeleteAll() {
        DepartmentDao departmentDao = new DepartmentDao(getContext());
        departmentDao.deleteAll();
    }

    public void testGetAll() {
        DepartmentDao departmentDao = new DepartmentDao(getContext());
        List<Department> list = departmentDao.getAll();
        if (list != null && list.size() > 0) {
            for (Department department : list) {
                System.out.println(department.getDeptName());
            }
        }
    }

    public void testGetConnectionStatus() {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        System.out.println(mNetworkInfo.isAvailable());
        System.out.println(mNetworkInfo.getTypeName());
        System.out.println(mNetworkInfo.getType());
        System.out.println(ConnectivityManager.TYPE_WIFI);
    }

}