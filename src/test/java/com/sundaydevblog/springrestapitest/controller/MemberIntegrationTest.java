package com.sundaydevblog.springrestapitest.controller;

import com.sundaydevblog.springrestapitest.entity.Member;
import com.sundaydevblog.springrestapitest.repository.MemberFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Integration test for API.
 *
 */
@TestPropertySource("classpath:application-integrationtest.properties")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void testFindMemberById() {
        // setup
        HttpHeaders headers = createHeaders();
        Member member = MemberFixtures.createMember1();
        Long id = member.getId();

        // exercise
        ResponseEntity<String> actual =
                restTemplate.exchange(MemberController.URI , HttpMethod.GET,
                        new HttpEntity<>(headers),
                        String.class, id);
        // verify
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        with(actual.getBody())
                .assertThat("$.[0].id", is((member.getId()).intValue()))
                .assertThat("$.[0].name", is(member.getName()))
                .assertThat("$.[0].email", is(member.getEmail()));
    }

//    /**
//     * Test GET /post_offices/prefectures/{prefectureCode}
//     */
//    @Test
//    public void testGetCityByPrefectureCode() {
//        // setup
//        HttpHeaders headers = createHeaders();
//        City city = CityFixtures.createCity();
//        String prefectureCode = city.getPrefecture().getPrefectureCode();
//        // exercise
//        ResponseEntity<String> actual =
//                restTemplate.exchange("/post_offices/prefectures/{prefectureCode}", HttpMethod.GET,
//                        new HttpEntity<>(headers),
//                        String.class, prefectureCode);
//        // verify
//        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
//        with(actual.getBody())
//                .assertThat("$.data[0].prefecture", is(city.getPrefecture().getPrefectureName()))
//                .assertThat("$.data[0].prefecture_kana", is(city.getPrefecture().getPrefectureKana()))
//                .assertThat("$.data[0].code", is(city.getCityCode()));
//    }
}
