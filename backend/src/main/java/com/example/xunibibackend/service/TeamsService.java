package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.entity.User;
import com.example.xunibibackend.response.MyResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TeamsService {
    Integer createTeam(Team team);

    List<Team> getAllTeams();

    Team getTeamById(Integer id);

    Integer updateTeam(Integer id, Team team);

    void deleteTeamById(Integer id);

    Double getCoin(HttpSession session);
}
