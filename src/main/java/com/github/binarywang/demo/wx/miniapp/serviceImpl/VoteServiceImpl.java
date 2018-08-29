package com.github.binarywang.demo.wx.miniapp.serviceImpl;

import com.github.binarywang.demo.wx.miniapp.dao.VoteDaoRepository;
import com.github.binarywang.demo.wx.miniapp.pojo.Vote;
import com.github.binarywang.demo.wx.miniapp.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService{

    @Autowired
    private VoteDaoRepository voteDaoRepository;

    @Override
    public List<Vote> getAllVote() {
        return voteDaoRepository.findAll();
    }


}
