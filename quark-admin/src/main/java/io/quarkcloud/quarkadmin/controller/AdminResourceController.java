package io.quarkcloud.quarkadmin.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/api/admin/{resource}/index")
    @ResponseBody
    @AdminResourceIndexRender
    public Object index(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/editable")
    @ResponseBody
    @AdminResourceEditableRender
    public Object editable(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/action/{uriKey}")
    @ResponseBody
    @AdminResourceActionRender
    public Object action(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/action/{uriKey}/values")
    @ResponseBody
    @AdminResourceActionValuesRender
    public Object actionValues(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/create")
    @ResponseBody
    @AdminResourceCreateRender
    public Object create(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/store")
    @ResponseBody
    @AdminResourceStoreRender
    public Object store(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/edit")
    @ResponseBody
    @AdminResourceEditRender
    public Object edit(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/edit/values")
    @ResponseBody
    @AdminResourceEditValuesRender
    public Object editValues(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/save")
    @ResponseBody
    @AdminResourceSaveRender
    public Object save(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/detail")
    @ResponseBody
    @AdminResourceDetailRender
    public Object detail(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/{uriKey}/form")
    @ResponseBody
    @AdminResourceFormRender
    public Object form(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/import/template")
    @ResponseBody
    @AdminResourceImportTemplateRender
    public Object importTemplate(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/import")
    @ResponseBody
    @AdminResourceImportRender
    public Object excelImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return new Context(request, response);
    }

    @RequestMapping("/api/admin/{resource}/export")
    @ResponseBody
    @AdminResourceExportRender
    public Object excelExport(HttpServletRequest request, HttpServletResponse response) {
        return new Context(request, response);
    }
}
