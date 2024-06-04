package com.java.backend.mapper.impl;

import com.java.backend.dto.CertificateDto;
import com.java.backend.entity.CertificateEntity;
import com.java.backend.mapper.CertificateMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateMapperImpl implements CertificateMapper {
    private final ModelMapper modelMapper;

    @Override
    public CertificateDto toDto(CertificateEntity certificate) {
        return modelMapper.map(certificate, CertificateDto.class);
    }
}
