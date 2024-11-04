package com.iMbank.iMbank.domain.counsel.repository;


import com.iMbank.iMbank.domain.counsel.entity.Ticket;
import com.iMbank.iMbank.domain.kiosk.entity.Kiosk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface
TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("SELECT COALESCE(MAX(t.count), 0) FROM Ticket t WHERE t.kiosk = :kioskId AND t.ticket_date = :today")
    Integer findTotalCountByKioskIdAndDate(Kiosk kioskId, String today);

    @Query("SELECT t FROM Ticket t WHERE t.kiosk = :kioskId AND t.user_dvcd = :user_dvcd AND t.ticket_date = :todayDate")
    Ticket findByKioskIdAndDateAndUser(Kiosk kioskId, String user_dvcd, String todayDate);
}
