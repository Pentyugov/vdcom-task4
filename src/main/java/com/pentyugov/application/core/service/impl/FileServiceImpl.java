package com.pentyugov.application.core.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.pentyugov.application.core.entity.User;
import com.pentyugov.application.core.service.FileService;
import com.pentyugov.application.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service(FileService.NAME)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final UserService userService;

    @Override
    public void importFromCsv(MultipartFile file) {
        if (!file.isEmpty()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                        .withType(User.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                List<User> users = csvToBean.parse();
                users.forEach(userService::add);

            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
}
