package com.github.binarywang.demo.wx.miniapp.controller;

import com.github.binarywang.demo.wx.miniapp.dao.VoteDaoRepository;
import com.github.binarywang.demo.wx.miniapp.pojo.Vote;
import com.github.binarywang.demo.wx.miniapp.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vote")
public class Votecontroller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VoteService voteService;

    @GetMapping()
    public List<Vote> salaries() {
        return voteService.getAllVote();
    }
}
