package org.api.cardtrader.modele;

import java.io.Serializable;

import org.api.cardtrader.enums.VersionEnum;

public class BluePrint implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Categorie categorie;
	private Expansion expansion;
	private Game game;
	private Integer id;
	private String name;
	private String scryfallId;
	private VersionEnum version = VersionEnum.NORMAL;
	private Integer mkmId;
	private String slug;
	private String collectorNumber;
	private String imageUrl;
	private boolean foiletched;
	private boolean foil;
	
	
	
	public boolean isFoil() {
		return foil;
	}



	public void setFoil(boolean foil) {
		this.foil = foil;
	}



	public String getImageUrl() {
		return imageUrl;
	}



	public boolean isFoiletched() {
		return foiletched;
	}



	public void setFoiletched(boolean foiletched) {
		this.foiletched = foiletched;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public String getCollectorNumber() {
		return collectorNumber;
	}



	public void setCollectorNumber(String collectorNumber) {
		this.collectorNumber = collectorNumber;
	}



	@Override
	public String toString() {
		return getName();
	}
	
	
	
	public String getSlug() {
		return slug;
	}



	public void setSlug(String slug) {
		this.slug = slug;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public VersionEnum getVersion() {
		return version;
	}

	public void setVersion(VersionEnum version) {
		this.version = version;
	}

	public Integer getMkmId() {
		return mkmId;
	}

	public void setMkmId(Integer mkmId) {
		this.mkmId = mkmId;
	}

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
	
	
}
