package com.iMbank.iMbank.domain.counsel.service;


import com.iMbank.iMbank.domain.counsel.dto.IssuedTicketInfo;
import com.iMbank.iMbank.domain.counsel.dto.request.StartCounselRequest;
import com.iMbank.iMbank.domain.counsel.dto.response.EndCounselResponse;
import com.iMbank.iMbank.domain.counsel.dto.response.IssuedTicketResponse;
import com.iMbank.iMbank.domain.counsel.dto.response.StartCounselResponse;

public interface CounselService {
    IssuedTicketResponse issueTicket(IssuedTicketInfo ticketInfo);

    StartCounselResponse startCounsel(StartCounselRequest startCounselRequest);

    EndCounselResponse endCounsel(int counselId);
}
