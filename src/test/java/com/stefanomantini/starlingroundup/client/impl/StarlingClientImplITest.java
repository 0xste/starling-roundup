package com.stefanomantini.starlingroundup.client.impl;

import com.stefanomantini.starlingroundup.client.dto.FeedItemWrapperDTO;
import com.stefanomantini.starlingroundup.client.exception.RemoteGatewayException;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class StarlingClientImplITest {

  @Autowired private RestTemplate restTemplate;

  @Test
  @Disabled("exemplary, used for some investigative testing")
  void getFeedForAccount() throws RemoteGatewayException {
    final StarlingClientImpl sc =
        new StarlingClientImpl(
            "https://api-sandbox.starlingbank.com",
            "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAH1Uy5KbMBD8lS3OO1sLFg9zyy0_kA8YpJGtspAoSWyylcq_RyBhjLOVm7t7Hj0zwr8L5X3RFzgpEDTaNx_QaWUuA5rbG7dj8Vr4eYgRgjo5nLACWVcSmGAtDFhykGdZ10xWsitFDKZfU9GX9Zk1rO3a9rVQGBJRNyVbCOTcziZ8t1qQ-6FErN01703TyBYqXjbATuwEyPg7dO91zJHYClbH2sHeyOQMdu46Kc8gqayB1RxhONcNcHZqO1bJ2AtjRhzrG-fkfcoqy0ESVQhSogDGxQBDE4sw7FhZkWw5X7I8txMtS0lO4bpaBYMj9Y5QvDwJ4XN6EpQgE5RU5I68Vj4cmAyEcNFkT0KFO0hKCMivI90jd_zTqUAvOIerdcrHk4EyQn0oMaNOwQNqNDxb4-gEcGuCszo1WpisWSOVGzEoa8BKkLMR_i75e_cNpNZ89sGO24g0osqFNUUj5tLjNOnPO1qjRjQCA_WCNMUSG8yau1FYBpkcSXIUvfv_SclG0iaNnOIGAl3cOsdj4r9iTiXHr7hNN1LA6AZ7HuGqZrwONeEn0SYlkIdIYA8CNeIlz5S0_ScEh8Yj3x1GGoZZ3_rtkrRTe7eE94YJ3wtoy-PRH8JXAuxy_Wc2Zzkrld68JvMHao1yxElN4QD8UUqb9PgRr-PhYncfBy5bP3BrnUcmbSfe96sSu_hFrV1MRfmVxKxJQBx7_3o8hRAHnKcMJ9y-jvi3t75SsE48tD-yW98ju-UHWhoB9x_P1CRkpubBcxe3tlx_q_bIrVGPT2Q9zPObKf78BeMY0uG3BQAA.u3WxWa80YHlV-SOqni0X1G5IKlFLTu9W2ptLSHDNI-1crBw6zeSrABeAhU8rgb_6_YTMxxk3-6iSikjV3QwKUxqPNruZjL3RlkzTx_fDzCd7fTc-2QQ1YeqxgXe__ul2gF_aSrXEmKJBAGEIDzby3xQEiCUV5_Q1ZSGKYkEBZcuo9GRBinD7UdFITLlTsLJ0rKE52Ev4vzgkzyV_lseuObi2XVJqOCEMKw-a4zVNJI__qMUNqPMsRorDrmDnXbf3OXnHUaEkzwM7hYBSwJ50glgNcPzyDBYFzb9sUYdquptXUCoU22eIb4wKOOL60eE3n-SNFMMyeOOEMG7Toj5hI_LSMZK178OM6DtNBbmCfszrq6gliZhlNJgpm9mBWZJGUUxSC1cKWtc9D1hsDF6bNlR68boxzyE-pQI95qdznzFDaYG6xM3vYjvMXVtULmZg_PHPG4X8LyH6rzb-UIfBYrabJxkfcUtD6wfBRYLNWH4XrN8Sq46XnwcRI8uN9Wb7hQJYeTq7tZ-OtweESZ3Xw7ejoIsEvlCkCuHBnj6tb-fm1q4eHFytUBtMR2psqIRy1QlNytNptVVgWrbKGoUCtf_Fx8yrQdQVNsN9Jo1f4fwRlPtPlkeKGg5A6skW_jDfTmM68jZhvhrRtDJFHKb52vAkWkSivGzNDk_T94-WlI8",
            "userAgent",
            "",
            "api/v2/feed/account/{accountId}/category/{categoryId}?changesSince={changesSinceTimestamp}",
            "",
            restTemplate);
    final FeedItemWrapperDTO feedItemWrapperDTO =
        sc.GetFeedForAccount(
            UUID.fromString("0f626e6b-d357-4a35-8219-c4e62df5f326"),
            UUID.fromString("3c2ec967-97b9-43bb-a002-36d616408bea"),
            ZonedDateTime.parse("2020-01-01T00:00:00.000Z").withFixedOffsetZone());

    feedItemWrapperDTO.getFeedItems().stream().forEach(i -> System.out.println(i.toString()));
    System.out.println(feedItemWrapperDTO.toString());
  }
}
