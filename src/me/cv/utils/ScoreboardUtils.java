package me.cv.utils;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreboardUtils {
	
	public Team registerTeam(String teamName) {
		Team team = initBoard().registerNewTeam(teamName);
		return team;
	}
	
	public Team getTeam(String teamName) {
		if(initBoard().getTeams().contains(initBoard().getTeam(teamName))) {
			return initBoard().getTeam(teamName);
		}
		return registerTeam(teamName);
	}
	
	private Scoreboard initBoard() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		return board;
	}

}
