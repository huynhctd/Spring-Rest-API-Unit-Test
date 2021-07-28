package com.sundaydevblog.springrestapitest.controller;

import com.sundaydevblog.springrestapitest.entity.Member;
import com.sundaydevblog.springrestapitest.repository.MemberFixtures;
import com.sundaydevblog.springrestapitest.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockMvc mvc;

    @MockBean
    MemberService memberService;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void findMemberById() throws Exception {
        // setup
        Member member = MemberFixtures.createMember1();
        Optional<Member> optionalMember = Optional.of(member);
        given(memberService.getMemberById(member.getId())).willReturn(optionalMember);
//        when(memberService.getMemberById(member.getId())).thenReturn(optionalMember);
        // exercise
        mvc.perform(MockMvcRequestBuilders.get("/api/members/1")
                // verify
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("@.id").value(is(member.getId().intValue())))
                .andExpect(jsonPath("@.name").value(is(member.getName())))
                .andExpect(jsonPath("@.email").value(is(member.getEmail())));
    }

    @Test
    public void shouldVerifyInvalidMemberId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(MemberController.URI + "0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Member with ID: '0' not found."))
                .andReturn();
    }

    @Test
    public void shouldVerifyInvalidMemberArgument() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(MemberController.URI + "abc")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Your request has issued a malformed or illegal request."))
                .andReturn();
    }


    @Test
    public void shouldVerifyInvalidSaveMember() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(MemberController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\", \"email\": \"mm@music.com\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Your request has issued a malformed or illegal request."))
                .andReturn();

    }

    @Test
    public void shouldVerifyInvalidUpdateMemberId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(MemberController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 999, \"name\": \"C. S. Lewis\", \"email\": \"cslewis@books.com\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Member with ID: '999' not found."))
                .andReturn();
    }

    @Test
    public void shouldVerifyInvalidPropertyNameWhenUpdateMember() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(MemberController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 2, \"nnaammee\": \"C. S. Lewis\", \"email\": \"cslewis@books.com\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Your request has issued a malformed or illegal request."))
                .andReturn();
    }
}