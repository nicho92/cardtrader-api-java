package org.api.cardtrader.modele;

import java.io.Serializable;

import org.api.cardtrader.enums.ConditionEnum;

public class MarketProduct implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer idBlueprint;
	private String name;
	private Integer qty;
	private Price price;
	private String description;
	private Expansion expansion;
	private User seller;
	private boolean graded;
	private boolean onVacation;
	private boolean bundle;
	private Integer bundleSize;
	private Integer bundledQuantity;
	private Categorie categorie;
	private Game game;
	
	private ConditionEnum condition;
	private boolean signed;
	private boolean foil;
	private String language;
	private boolean altered;
	
	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
	}
	@Override
	public String toString() {
		return getName();
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public Integer getIdBlueprint() {
		return idBlueprint;
	}
	public void setIdBlueprint(Integer idBlueprint) {
		this.idBlueprint = idBlueprint;
	}
	public String getName() {
		return name;
	}
	public void setName(String nameEn) {
		this.name = nameEn;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Expansion getExpansion() {
		return expansion;
	}
	public void setExpansion(Expansion expansion) {
		this.expansion = expansion;
	}
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	public boolean isGraded() {
		return graded;
	}
	public void setGraded(boolean graded) {
		this.graded = graded;
	}
	public boolean isOnVacation() {
		return onVacation;
	}
	public void setOnVacation(boolean onVacation) {
		this.onVacation = onVacation;
	}
	public boolean isBundle() {
		return bundle;
	}
	public void setBundle(boolean bundle) {
		this.bundle = bundle;
	}
	public Integer getBundleSize() {
		return bundleSize;
	}
	public void setBundleSize(Integer bundleSize) {
		this.bundleSize = bundleSize;
	}
	public Integer getBundledQuantity() {
		return bundledQuantity;
	}
	public void setBundledQuantity(Integer bundledQuantity) {
		this.bundledQuantity = bundledQuantity;
	}
	public ConditionEnum getCondition() {
		return condition;
	}
	public void setCondition(ConditionEnum condition) {
		this.condition = condition;
	}
	public boolean isSigned() {
		return signed;
	}
	public void setSigned(boolean signed) {
		this.signed = signed;
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


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}

}
