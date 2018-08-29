package com.github.binarywang.demo.wx.miniapp.dao;

import com.github.binarywang.demo.wx.miniapp.pojo.Vote;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VoteDaoRepository extends JpaRepository<Vote,Long>{

}
