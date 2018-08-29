package com.github.binarywang.demo.wx.miniapp.service;

import com.github.binarywang.demo.wx.miniapp.dao.VoteDaoRepository;
import com.github.binarywang.demo.wx.miniapp.pojo.Vote;

import java.util.List;

public interface VoteService {

    public List<Vote> getAllVote();
}
