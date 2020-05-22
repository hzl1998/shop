package com.hzl.controller;

import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @GetMapping("/reports/all")
    @PreAuthorize("hasAuthority('/reports/all')")
    public Result reports(){
        return ResultFactory.buildSuccessResult(null,"成功");
    }
}
