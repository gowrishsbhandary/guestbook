package com.bt.guestbook.repo;

import com.bt.guestbook.model.GuestBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestBookRepository extends JpaRepository<GuestBookEntry, Long> {}
