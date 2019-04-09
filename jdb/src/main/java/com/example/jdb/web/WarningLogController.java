package com.example.jdb.web;

import com.example.jdb.service.WarningLogService;
import com.example.jdb.utils.FileUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常日志Web层
 *
 * @author 白帅雷
 * @date 2019-01-10
 */
@RestController
@CrossOrigin
public class WarningLogController {
    @Autowired
    private WarningLogService warningLogService;

    @Autowired
    private FileUtil fileUtil;
    /**
     * 查询异常日志
     *
     * @param tiId
     * @return
     */
    @ApiOperation(value = "获取异常日志")
    @GetMapping(value = "/warningLog/{tiId}")
    public Map<String, Object> getWarningLogs(@PathVariable String tiId) {
        return warningLogService.getWarningLogs(tiId);
    }

    /**
     * 删除日志
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除异常日志", notes = "根据ID删除异常日志")
    @ApiImplicitParam(name = "id", value = "异常日志主键ID", required = true, dataType = "int")
    @DeleteMapping(value = "/warningLog/{id}")
    public int deleteWarningLogs(@PathVariable int id) {
        return warningLogService.deleteWarningLog(id);
    }

    /**
     * 已处理日志
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "处理异常日志", notes = "根据ID处理异常日志")
    @ApiImplicitParam(name = "id", value = "异常日志主键ID", required = true, dataType = "int")
    @PutMapping(value = "/warningLog/{id}")
    public int updateWarningLogs(@PathVariable int id) {
        return warningLogService.updateWarningLog(id);
    }

    /**
     * 下载异常日志
     *
     * @param id
     * @param response
     * @return
     */
    @ApiOperation(value = "下载异常日志", notes = "根据ID下载异常日志")
    @ApiImplicitParam(name = "id", value = "异常日志主键ID", required = true, dataType = "int")
    @GetMapping(value = "/warningLog/down/{id}")
    @ResponseBody
    public Map<String, Object> downWarningLogs(HttpServletResponse response, @PathVariable int id) {
        return fileUtil.downFile(response, id);
    }

    /**
     * 上传异常日志
     *
     * @param response
     * @return
     */
    @ApiOperation(value = "上传异常日志")
    @ApiImplicitParam(name = "id", value = "异常日志主键ID", required = true, dataType = "int")
    @PostMapping(value = "/warningLog/up")
    public Map<String, Object> upWarningLogs(HttpServletResponse response, HttpServletRequest request,
                                             @RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>(1);
        String fileName = file.getOriginalFilename();
        System.out.println("fileName." + fileName);
        try {
            byte[] data = file.getBytes();
            System.out.println("data.length()" + data.length);
            map = fileUtil.upFile(data, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
