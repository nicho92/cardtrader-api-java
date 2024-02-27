package org.api.cardtrader.modele;

import java.io.Serializable;
import java.util.Map;

import org.api.cardtrader.enums.ConditionEnum;

import com.google.gson.annotations.SerializedName;

public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;
		private int id;
		private String name;
		private String expansion;
		private Expansion expansionProduct;
		private int quantity;
		private boolean bundle;
		private String description;
		private boolean graded;
		@SerializedName(value = "seller_price",alternate = "buyer_price") private Price price;
		private String tag;
		private String lang;
		private boolean foil;
		private boolean altered;
		private boolean signed;
		private Map<String,String> properties;
		private ConditionEnum condition;
		
		@SerializedName(value = "product_id") private int productId;
		@SerializedName(value = "blueprint_id") private int bluePrintId;
		@SerializedName(value = "category_id") private int categoryId;
		@SerializedName(value = "game_id") private int gameId;
		@SerializedName(value = "formatted_price") private String formattedPrice;
		@SerializedName(value = "mkm_id") private int mkmId;
		@SerializedName(value = "tcg_player_id") private int tcgPlayerId;
		@SerializedName(value = "scryfall_id") private String scryfallId;
		@SerializedName(value = "user_data_field") private String userDataField;
		
		public Expansion getExpansionProduct() {
			return expansionProduct;
		}
		
		public void setExpansionProduct(Expansion expansionProduct) {
			this.expansionProduct = expansionProduct;
		}
		
		
		public ConditionEnum getCondition() {
			return condition;
		}


		public void setCondition(ConditionEnum condition) {
			this.condition = condition;
		}


		public Map<String, String> getProperties() {
			return properties;
		}


		public void setProperties(Map<String, String> properties) {
			this.properties = properties;
		}


		public String getLang() {
			return lang;
		}


		public void setLang(String lang) {
			this.lang = lang;
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

		@Override
		public String toString() {
			return getName();
		}
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getProductId() {
			return productId;
		}
		public void setProductId(int productId) {
			this.productId = productId;
		}
		public int getBluePrintId() {
			return bluePrintId;
		}
		public void setBluePrintId(int bluePrintId) {
			this.bluePrintId = bluePrintId;
		}
		public int getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
		}
		public int getGameId() {
			return gameId;
		}
		public void setGameId(int gameId) {
			this.gameId = gameId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getExpansion() {
			return expansion;
		}
		public void setExpansion(String expansion) {
			this.expansion = expansion;
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


		public boolean isGraded() {
			return graded;
		}


		public void setGraded(boolean graded) {
			this.graded = graded;
		}


		public String getFormattedPrice() {
			return formattedPrice;
		}


		public void setFormattedPrice(String formattedPrice) {
			this.formattedPrice = formattedPrice;
		}


		public int getMkmId() {
			return mkmId;
		}


		public void setMkmId(int mkmId) {
			this.mkmId = mkmId;
		}


		public int getTcgPlayerId() {
			return tcgPlayerId;
		}


		public void setTcgPlayerId(int tcgPlayerId) {
			this.tcgPlayerId = tcgPlayerId;
		}


		public String getScryfallId() {
			return scryfallId;
		}


		public void setScryfallId(String scryfallId) {
			this.scryfallId = scryfallId;
		}


		public String getUserDataField() {
			return userDataField;
		}


		public void setUserDataField(String userDataField) {
			this.userDataField = userDataField;
		}


		public Price getPrice() {
			return price;
		}


		public void setPrice(Price price) {
			this.price = price;
		}


		public String getTag() {
			return tag;
		}


		public void setTag(String tag) {
			this.tag = tag;
		}
		
		
		
		
		
		
}
