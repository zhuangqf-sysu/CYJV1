package com.cyj.controller.home;

import com.cyj.model.AdminModel;
import com.cyj.model.SupplierModel;
import com.cyj.service.AdminService;
import com.cyj.service.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/4/3.
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @Resource
    private AdminService adminService;

    @Resource
    private SupplierService supplierService;

    @RequestMapping("/homeTop")
    public String homeTop(Model model) {
        return "home/homeTop";
    }

    @RequestMapping("/homeLeft")
    public String homeLeft(Model model) {
        return "home/homeLeft";
    }

    @RequestMapping("/homeRight")
    public String homeRight(Model model) {
        return "home/logIn";
    }

    @RequestMapping("/homeInfo")
    public String homeInfo(Model model) {
        return "home/homeInfo";
    }

    @RequestMapping("/purchaseInfo")
    public String purchaseInfo(Model model) {
        return "home/purchaseInfo";
    }

    @RequestMapping("/logIn")
    public String logIn(Model model) {
        return "home/logIn";
    }

    @RequestMapping("/adminValid")
    public String adminValid(Model model, String NT, String password, HttpServletRequest request) {
        boolean flag = adminService.adminValid(NT, password);
        //System.out.println("flag:" + flag);
        if(flag) {
            AdminModel adminModel = adminService.findModelByNT(NT);
            request.getSession().setAttribute("admin", adminModel);
            return "admin/adminMain";
        } else {
            return "index";
        }
    }

    @RequestMapping("/supplierValid")
    public String supplierValid(Model model, String username, String password, HttpServletRequest request) {
        boolean flag = supplierService.supplierValid(username, password);
        if(flag) {
            SupplierModel supplierModel = supplierService.getModelByUsername(username);
            request.getSession().setAttribute("supplier", supplierModel);
            return "supplier/supplierMain";
        } else {
            return "index";
        }
    }

    @RequestMapping("/supplierSignup")
    public String supplierSignup(Model model, String corporation, String address, String name, String tel, String email, String username, String password) {
        SupplierModel supplierModel = new SupplierModel();
        supplierModel.setCorporation(corporation);
        supplierModel.setAddress(address);
        supplierModel.setName(name);
        supplierModel.setTel(tel);
        supplierModel.setEmail(email);
        supplierModel.setUsername(username);
        supplierModel.setPassword(password);
        supplierModel.setCredit(1);
        boolean flag = supplierService.insertSupplier(supplierModel);
        return "home/logIn";
    }
}
