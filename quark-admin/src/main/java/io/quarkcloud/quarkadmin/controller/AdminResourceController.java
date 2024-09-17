package io.quarkcloud.quarkadmin.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.quarkcloud.quarkadmin.annotation.AdminResourceIndexRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceSaveRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceStoreRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceActionRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceActionValuesRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceCreateRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceDetailRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceEditRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceEditValuesRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceEditableRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceExportRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceFormRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceImportRender;
import io.quarkcloud.quarkadmin.annotation.AdminResourceImportTemplateRender;
import io.quarkcloud.quarkcore.service.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AdminResourceController {

    @RequestMapping(value = "/api/admin/{resource}/index", method = {RequestMethod.GET})
    @ResponseBody
    @AdminResourceIndexRender
    public Object index(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/editable", method = {RequestMethod.GET})
    @ResponseBody
    @AdminResourceEditableRender
    public Object editable(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/action/{uriKey}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @AdminResourceActionRender
    public Object action(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/action/{uriKey}/values", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @AdminResourceActionValuesRender
    public Object actionValues(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/create", method = {RequestMethod.GET})
    @ResponseBody
    @AdminResourceCreateRender
    public Object create(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/store", method = {RequestMethod.POST})
    @ResponseBody
    @AdminResourceStoreRender
    public Object store(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/edit", method = {RequestMethod.GET})
    @ResponseBody
    @AdminResourceEditRender
    public Object edit(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/edit/values", method = {RequestMethod.GET})
    @ResponseBody
    @AdminResourceEditValuesRender
    public Object editValues(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/save", method = {RequestMethod.POST})
    @ResponseBody
    @AdminResourceSaveRender
    public Object save(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/detail", method = {RequestMethod.GET})
    @ResponseBody
    @AdminResourceDetailRender
    public Object detail(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/form", method = {RequestMethod.GET})
    @ResponseBody
    @AdminResourceFormRender
    public Object form(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/import/template", method = {RequestMethod.GET})
    @ResponseBody
    @AdminResourceImportTemplateRender
    public Object importTemplate(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/import", method = {RequestMethod.POST})
    @ResponseBody
    @AdminResourceImportRender
    public Object excelImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return new Context(request, response);
    }

    @RequestMapping(value = "/api/admin/{resource}/export", method = {RequestMethod.GET})
    @ResponseBody
    @AdminResourceExportRender
    public Object excelExport(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }
}
