package dev.company.contactus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import dev.company.contactus.entity.ContactUs;

public interface ContactUsRepository extends PagingAndSortingRepository<ContactUs, Integer>, CrudRepository<ContactUs, Integer> {

	public Page<ContactUs> findAll(Pageable pageable);
}
