package org.api.cardtrader.modele;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Expansion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Game game;
	private String code;
	private String name;
	@SerializedName(value="game_id") int gameId;
	
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
