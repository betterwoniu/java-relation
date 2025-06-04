package org.data.springbootgrpcclient.cotroller;

import org.data.springbootgrpcclient.service.AigcReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("aigc")
public class AigcReportController {


    @Autowired
    private AigcReportService aigcReportService;
    @GetMapping("status")
    public String GetDetectionStatusRequest() {
         aigcReportService.GetDetectionStatusRequest("64f1e525-8bd2-42bc-a10b-1e6c76333ad4");
        return "GetDetectionStatusRequest";
    }
}
