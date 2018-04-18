package com.cootf.log4droid.service.impl;

import com.cootf.log4droid.entity.ExtractLog;
import com.cootf.log4droid.repository.ExtractLogRepository;
import com.cootf.log4droid.service.ExtractLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtractLogServiceImpl implements ExtractLogService {

    @Autowired
    private ExtractLogRepository repository;

    @Override
    public boolean saveOne(ExtractLog log) {
        repository.insert(log);
        return true;
    }
}
