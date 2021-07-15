package org.api.cardtrader.modele;

import java.io.Serializable;

import org.api.cardtrader.enums.ConditionEnum;

public class BluePrint implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Categorie categorie;
	private Expansion expansion;
	private Game game;
	
	private String name;
	private String scryfallId;
	private String language;
	private Boolean foil;
	private ConditionEnum condition;
	
	
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public Expansion getExpansion() {
		return expansion;
	}
	public void setExpansion(Expansion expansion) {
		this.expansion = expansion;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScryfallId() {
		return scryfallId;
	}
	public void setScryfallId(String scryfallId) {
		this.scryfallId = scryfallId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Boolean getFoil() {
		return foil;
	}
	public void setFoil(Boolean foil) {
		this.foil = foil;
	}
	public ConditionEnum getCondition() {
		return condition;
	}
	public void setCondition(ConditionEnum condition) {
		this.condition = condition;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
