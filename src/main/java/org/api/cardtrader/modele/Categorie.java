package org.api.cardtrader.modele;

import java.io.Serializable;

import org.api.cardtrader.enums.ConditionEnum;

import com.google.gson.annotations.SerializedName;

public class Categorie implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Game game;
	@SerializedName(value="game_id") int gameId;
		private ConditionEnum condition;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public ConditionEnum getCondition() {
		return condition;
	}
	public void setCondition(ConditionEnum condition) {
		this.condition = condition;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}


	@Override
	public String toString() {
		return getName() + " " + getGame();
	}
	
}
