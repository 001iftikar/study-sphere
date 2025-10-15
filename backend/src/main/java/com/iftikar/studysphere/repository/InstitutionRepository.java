package com.iftikar.studysphere.repository;

import com.iftikar.studysphere.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InstitutionRepository extends Repository<Institution, Long> {

}
