package org.api.cardtrader.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.api.cardtrader.enums.ConditionEnum;
import org.api.cardtrader.enums.RarityEnum;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Price price;
	private int quantity;
	private boolean bundle;
	private String description;
	private String graded;
	private List<String> tags;
	private Game game;
	private Categorie category;
	private Expansion expansion;
	private BluePrint blueprint;
	private ConditionEnum condition;
	private String language;
	private boolean foil;
	private boolean altered;
	private boolean signed;
	private RarityEnum mtgrarity;
	private String collectorNumber;
	private double cmc;
	private String mtgcardcolors;
	
	
	
	public Product() {
		tags = new ArrayList<>();
	}
	
	
	public RarityEnum getMtgrarity() {
		return mtgrarity;
	}


	public void setMtgrarity(RarityEnum mtgrarity) {
		this.mtgrarity = mtgrarity;
	}


	public String getCollectorNumber() {
		return collectorNumber;
	}


	public void setCollectorNumber(String collectorNumber) {
		this.collectorNumber = collectorNumber;
	}


	public double getCmc() {
		return cmc;
	}


	public void setCmc(double cmc) {
		this.cmc = cmc;
	}


	public String getMtgcardcolors() {
		return mtgcardcolors;
	}


	public void setMtgcardcolors(String mtgcardcolors) {
		this.mtgcardcolors = mtgcardcolors;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isBundle() {
		return bundle;
	}
	public void setBundle(boolean bundle) {
		this.bundle = bundle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGraded() {
		return graded;
	}
	public void setGraded(String graded) {
		this.graded = graded;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public Categorie getCategory() {
		return category;
	}
	public void setCategory(Categorie category) {
		this.category = category;
	}
	public Expansion getExpansion() {
		return expansion;
	}
	public void setExpansion(Expansion expansion) {
		this.expansion = expansion;
	}
	public BluePrint getBlueprint() {
		return blueprint;
	}
	public void setBlueprint(BluePrint blueprint) {
		this.blueprint = blueprint;
	}
	public ConditionEnum getCondition() {
		return condition;
	}
	public void setCondition(ConditionEnum condition) {
		this.condition = condition;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isFoil() {
		return foil;
	}
	public void setFoil(boolean foil) {
		this.foil = foil;
	}
	public boolean isAltered() {
		return altered;
	}
	public void setAltered(boolean altered) {
		this.altered = altered;
	}
	public boolean isSigned() {
		return signed;
	}
	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	
	
	
	
	
	
}
