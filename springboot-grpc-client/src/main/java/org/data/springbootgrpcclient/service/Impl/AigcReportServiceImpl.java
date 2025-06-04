package org.data.springbootgrpcclient.service.Impl;

import cn.com.wanfangdata.ads.DetectListItem;
import cn.com.wanfangdata.ads.DetectListRequest;
import cn.com.wanfangdata.ads.DetectListResponse;
import cn.com.wanfangdata.ads.DetectorServiceGrpc;
import com.data.grpc.server.UserEntityServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.data.springbootgrpcclient.service.AigcReportService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AigcReportServiceImpl implements AigcReportService {

//    @GrpcClient("localgrpcserver")
//    private UserEntityServiceGrpc.UserEntityServiceBlockingStub blockingStub;
    @GrpcClient("aigcdetector-server")
    private DetectorServiceGrpc.DetectorServiceBlockingStub blockingStub;
    @Override
    public String GetDetectionStatusRequest(String ids) {
        DetectListRequest detectListRequest = DetectListRequest.newBuilder().addAllIds(List.of(ids)).build();

        DetectListResponse detectListResponse =  blockingStub.getDetectionListStatus(detectListRequest);
        List<DetectListItem> itemsList = detectListResponse.getItemsList();

        System.out.printf("detectListResponse"+detectListResponse.toString());

        return "";
    }
}
