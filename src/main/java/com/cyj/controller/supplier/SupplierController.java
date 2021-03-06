package com.cyj.controller.supplier;

import com.cyj.model.ApplicationModel;
import com.cyj.model.PublishModel;
import com.cyj.model.SupplierModel;
import com.cyj.service.ApplicationService;
import com.cyj.service.PublishService;
import com.cyj.service.SupplierService;
import com.cyj.tools.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/4/3.
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Resource
    private PublishService publishService;
    @Resource
    private ApplicationService applicationService;
    @Resource
    private SupplierService supplierService;

    @RequestMapping("/supplierMain")
    public String supplierMain(Model model) {
        return "supplier/supplierMain";
    }

    @RequestMapping("/supplierTop")
    public String supplierTop(Model model) {
        return "supplier/supplierTop";
    }

    @RequestMapping("/supplierLeft")
    public String supplierLeft(Model model) {
        return "supplier/supplierLeft";
    }

    @RequestMapping("/supplierRight")
    public String supplierRight(Model model) {
        return "redirect:/supplier/viewPublishInfo";
    }

    @RequestMapping("/viewPublishInfo")
    public String viewPublishInfo(Model model,@RequestParam(value = "pageNumber",defaultValue = "1",required = false) int pageNumber) {
        int pageSize = 8;
        PageUtil curPage = publishService.getOnePagePublishInfo(pageNumber, pageSize,0);
        curPage.setRowNum(4);
        model.addAttribute("curPage",curPage);
        return "supplier/viewPublishInfo";
    }

    @RequestMapping("/processApply")
    public String processApply(Model model, HttpServletRequest request) {
        if(request.getParameterMap().containsKey("applyId")) {
            String[] strPublishIds = request.getParameterValues("applyId");
            SupplierModel supplierModel = (SupplierModel) request.getSession().getAttribute("supplier");
            int supplierId = supplierModel.getId();
            for (String sPublishId : strPublishIds) {
                int publishId = Integer.parseInt(sPublishId);
                PublishModel publishModel = publishService.findModelById(publishId);
                int scheduleId = publishModel.getScheduleId();
                int goodsId = publishModel.getGoodsId();
                String strNumber = request.getParameter("n" + sPublishId);
                String strPrice = request.getParameter("p" + sPublishId);
                int supplyNumber = Integer.parseInt(strNumber);
                double price = Double.parseDouble(strPrice);
                ApplicationModel applicationModel = new ApplicationModel();
                applicationModel.setPublishId(publishId);
                applicationModel.setScheduleId(scheduleId);
                applicationModel.setSupplierId(supplierId);
                applicationModel.setGoodsId(goodsId);
                applicationModel.setSupplyNumber(supplyNumber);
                applicationModel.setPrice(price);
                applicationModel.setValid(0);
                applicationModel.setApplicationState(0);
                applicationService.insertItem(applicationModel);
            }
        }
        return "redirect:/supplier/viewPublishInfo";
    }

    @RequestMapping("/myApplication")
    public String myApplication(Model model,@RequestParam(value = "pageNum",defaultValue = "1",required = false) int pageNum, HttpServletRequest request) {
        int pageSize = 4;
        SupplierModel smodel = (SupplierModel)request.getSession().getAttribute("supplier");
        int supplierId = smodel.getId();
        PageUtil nowPage = applicationService.viewMyApplication(pageNum, pageSize,supplierId);
        nowPage.setRowNum(4);
        model.addAttribute("nowPage",nowPage);
        return "supplier/myApplication";
    }

    @RequestMapping("/processingOrder")
    public String processingOrder(Model model, @RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum, HttpServletRequest request) {
        int pageSize = 4;
        SupplierModel smodel = (SupplierModel)request.getSession().getAttribute("supplier");
        int supplierId = smodel.getId();
        PageUtil nowPage = applicationService.viewOnePageMyOrder(pageNum,pageSize,supplierId,0);
        nowPage.setRowNum(4);
        model.addAttribute("nowPage",nowPage);
        return "supplier/processingOrder";
    }

    @RequestMapping("/purchaseHistory")
    public String purchaseHistory(Model model, @RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum, HttpServletRequest request) {
        int pageSize = 4;
        SupplierModel smodel = (SupplierModel)request.getSession().getAttribute("supplier");
        int supplierId = smodel.getId();
        PageUtil nowPage = applicationService.viewOnePageMyOrder(pageNum,pageSize,supplierId,1);
        nowPage.setRowNum(4);
        model.addAttribute("nowPage", nowPage);
        return "supplier/purchaseHistory";
    }

    @RequestMapping("/supplierExit")
    public String supplierExit(Model model, HttpServletRequest request) {
        request.getSession().removeAttribute("supplier");
        return "index";
    }

    @RequestMapping("/modifySupplier")
    public String modifySupplier(Model model, HttpServletRequest request) {
        return "supplier/modifySupplier";
    }

    @RequestMapping("/modifySupplierVerify")
    public String modifySupplierVerify(Model model, HttpServletRequest request, String oldPassword, String newPassword, String newPassword1) {
        SupplierModel supplierModel = (SupplierModel)request.getSession().getAttribute("supplier");
        String error = "";
        if(! newPassword1.equals(newPassword)) {
            error = "两次密码不一致!请重新输入。";
        } else if(!supplierModel.getPassword().equals(oldPassword)){
            error = "密码错误";
        } else {
            if(supplierService.changePassword(newPassword, supplierModel.getId())) {
                error = "修改成功";
            } else {
                error = "修改密码失败";
            }
        }
        model.addAttribute("result", error);
        return "supplier/modifySupplierVerify";
    }
}
