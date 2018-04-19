package com.ctf.log.server.oss;

import com.ctf.log.server.component.MongoDbUtil;
import com.ctf.log4droid.mt_body.LogConfig;
import com.ctf.log4droid.mt_body.PkgConfig;
import com.ctf.log4droid.mt_body.Server2Term;
import com.ctf.log4droid.mt_body.TermReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CRMServer {

    @Autowired
    MongoDbUtil mongoDbUtil;
    public void dealMsg(TermReq termReq,byte[] sessionId, Server2Term server2Term) {
        if (1 == termReq.getSessionId()) {
            server2Term.getServerResponse().setSessionId(sessionId);
        }
        if (1 == termReq.getLogConfig()) {
            //获取配置信息
            //LogConfig logConfig = mongoDbUtil.getLogConfig(sessionId);
            LogConfig logConfig = new LogConfig(1,"*:W");
            server2Term.getServerResponse().setLogConfig(logConfig);
        }
        if (1 == termReq.getPkgConfig()) {
            //获取配置信息
            //LogConfig logConfig = mongoDbUtil.getPkgConfig(sessionId);
            PkgConfig pkgConfig = new PkgConfig();
            server2Term.getServerResponse().setPkgConfig(pkgConfig);
        }
    }
}
